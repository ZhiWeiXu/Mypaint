package com.json.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketService extends Thread {
	private static Socket socket = null;
	private static OutputStream outputStream = null;

	public SocketService() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			socket = new Socket("192.168.90.103", 8010);
			outputStream = socket.getOutputStream();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sendPostMessgae(String coordinate) {
		try {
			outputStream.write(coordinate.getBytes("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Socket getSocket() {
		return socket;
	}

}
