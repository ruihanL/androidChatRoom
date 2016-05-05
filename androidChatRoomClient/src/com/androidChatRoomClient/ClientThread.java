package com.androidChatRoomClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ClientThread implements Runnable{

	private Socket socket;
	private Handler uiHandler;
	public Handler revHandler;
	BufferedReader br = null;
	OutputStream os = null;
	public ClientThread(Handler handler) {
		this.uiHandler = handler;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Socket s = new Socket("192.168.1.111", 30000);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = s.getOutputStream();
			
			new Thread(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String content = null;
					try {
						while ((content = br.readLine())!=null) {
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = content;
							uiHandler.sendMessage(msg);
							
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}.start();
			
			Looper.prepare();
			
			revHandler = new Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if (msg.what==0x345) {
						try {
							os.write((msg.obj.toString()+"\r\n").getBytes("utf-8"));
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}	
				}
				
			};
			
			Looper.loop();
		}
		catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("the connection is timeout.");
		}	
			
		 catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		
		
		
	}

}
