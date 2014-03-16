package com.smartHome.homeControler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.smartHome.homeControler.MainActivity.Client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {
	  static final String ACTION = "android.intent.action.BOOT_COMPLETED";

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(ACTION)) 
    {
        //Intent service = new Intent(context, UdpServer.class);
       // context.startService(service);

        Log.i(getResultData(), "UdpServer started");
    	// ¿ªÆô·þÎñÆ÷
        /*
    	ExecutorService exec = Executors.newCachedThreadPool();
    	UdpServer server = new UdpServer();
    	exec.execute(server);
    	*/
        new Thread(new UdpServer()).start();
    }

  }
} 