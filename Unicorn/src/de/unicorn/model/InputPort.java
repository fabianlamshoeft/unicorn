package de.unicorn.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputPort extends Thread{

	private Socket in;
	private Connection conn;
	private boolean isRunning = false;
	
	public InputPort (Socket in, Connection conn) {
		this.in = in;
		this.conn = conn;
	}
	
	public void run() {
		
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in.getInputStream()));
			String message = "";
			
			while (isRunning) {
				message = reader.readLine();
				conn.interpretIncommingMessage(message);
			}
			
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startListener() {
		isRunning = true;
		super.start();
	}
	
	public void close() {
		isRunning = false;
		
		try {
			super.join();
			in.close();
		} catch (InterruptedException e) {
			// ignore
		} catch (IOException e) {
			// ignore
		}
		
	}
	
}
