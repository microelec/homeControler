package com.smartHome.homeControler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClient {

	private static final int SERVER_PORT = 4444;

	private DatagramSocket dSocket = null;

	private String msg;

	/**
	 * @param msg
	 */
	public UdpClient(String msg) {
		super();
		this.msg = msg;
	}

	/**
	 * ������Ϣ��������
	 */
	public String send() {
		StringBuilder sb = new StringBuilder();
		InetAddress local = null;
		try {
			local = InetAddress.getByName("127.0.0.1"); // ��������
			sb.append("���ҵ�������,������...").append("/n");
		} catch (UnknownHostException e) {
			sb.append("δ�ҵ�������.").append("/n");
			e.printStackTrace();
		}
		try {
			dSocket = new DatagramSocket(); // ע��˴�Ҫ���������ļ�������Ȩ��,�������Ȩ�޲�����쳣
			sb.append("�������ӷ�����...").append("/n");
		} catch (SocketException e) {
			e.printStackTrace();
			sb.append("����������ʧ��.").append("/n");
		}
		int msg_len = msg == null ? 0 : msg.length();
		DatagramPacket dPacket = new DatagramPacket(msg.getBytes(), msg_len,
				local, SERVER_PORT);
		try {
			dSocket.send(dPacket);
			sb.append("��Ϣ���ͳɹ�!").append("/n");
		} catch (IOException e) {
			e.printStackTrace();
			sb.append("��Ϣ����ʧ��.").append("/n");
		}
		dSocket.close();
		return sb.toString();
	}

}