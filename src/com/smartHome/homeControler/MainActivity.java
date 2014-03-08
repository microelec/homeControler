package com.smartHome.homeControler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText msg_et = null;
	Button send_bt = null;
	TextView info_tv = null;
	public boolean clientStart = false;
    public static final String SERVERIP = "127.0.0.1"; // 'Within' the emulator!
    public static final int SERVERPORT = 6000;
    public Handler Handler;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		msg_et = (EditText) findViewById(R.id.editText1);
		send_bt = (Button) findViewById(R.id.button1);
		info_tv = (TextView) findViewById(R.id.textView1);

		// 开启服务器
		ExecutorService exec = Executors.newCachedThreadPool();
		UdpServer server = new UdpServer();
		exec.execute(server);
		
        new Thread(new Client()).start();
        Handler = new Handler() {
      @Override public void handleMessage(Message msg) {
            String text = (String)msg.obj;
            info_tv.append(text);
      }
      };
		// 发送消息
		send_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clientStart = true;
			}
		});
	}
	   public class Client implements Runnable {
	        @Override
	        public void run() {
	        	while(true)
	        	{
	            while(clientStart ==false)
	            {
	            }
	            try {
	                        Thread.sleep(500);
	                  } catch (InterruptedException e1) {
	                        // TODO Auto-generated catch block
	                        e1.printStackTrace();
	                  }
	            try {
	                InetAddress serverAddr = InetAddress.getByName(SERVERIP);
	                updatetrack("Client: Start connecting\n");
	                DatagramSocket socket = new DatagramSocket();
	                byte[] buf;
	                if(!msg_et.getText().toString().isEmpty())
	                {
	                  buf=msg_et.getText().toString().getBytes();
	                }
	                else
	                {
	                  buf = ("Default message").getBytes();
	                }
	                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, SERVERPORT);
	                updatetrack("Client: Sending '" + new String(buf) + "'\n");
	                socket.send(packet);
	                updatetrack("Client: Message sent\n");
	                updatetrack("Client: Succeed!\n");
	        } catch (Exception e) {
	                  updatetrack("Client: Error!\n");
	        }
	           clientStart = false;
	        }
	        }

	}
	      public void updatetrack(String s){
              Message msg = new Message();
              String textTochange = s;
              msg.obj = textTochange;
              Handler.sendMessage(msg);
         }	
}
