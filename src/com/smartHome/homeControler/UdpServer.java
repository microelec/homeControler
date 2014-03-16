package com.smartHome.homeControler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.util.Log;
import android.widget.TextView;

public class UdpServer implements Runnable {

	private static final int PORT = 6000;

	private byte[] msg = new byte[1024];

	private boolean life = true;

	public UdpServer() {

	}

	/**
	 * @return the life
	 */
	public boolean isLife() {
		return life;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setLife(boolean life) {
		this.life = life;
	}

	@Override
	public void run() {
		DatagramSocket dSocket = null;
		DatagramPacket dPacket = new DatagramPacket(msg, msg.length);
		Log.i("UdpServer is running", "");

		try {
			dSocket = new DatagramSocket(PORT);
			while (life) {
				try {
					dSocket.receive(dPacket);
					Log.i("msg sever received", new String(dPacket.getData()));
					InetAddress peer_ip = dPacket.getAddress();
					
					UdpClient client = new UdpClient("Receiver get it");
					client.setServerIp(peer_ip);
					client.send();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}