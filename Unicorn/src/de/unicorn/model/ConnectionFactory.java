package de.unicorn.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionFactory {
	private Connection conn;
	private Thread timer;
	private boolean pokeArrived = false;
	private boolean timeout = false;
	private long creationTime;
	
	public ConnectionFactory(String ip, int port, Socket out)
	{	
		System.out.println("Factory mit Usereingabe");
		creationTime = System.currentTimeMillis();
		timer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (System.currentTimeMillis() - creationTime <= 5000 && !pokeArrived) {
					// warten
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				timeout = !pokeArrived;
				if (timeout) {
					// Factory zerstören
					System.out.println("Factory timeout!");
					distroy();
				}
				
			}
		});
		
		conn = new Connection();
		conn.setOut(new OutputPort(out));
		conn.setIP(ip);
		conn.setPeerServerPort(port);
		
		startTimer();
		
		
	}
	
	public ConnectionFactory(String name, String ip, int port, Socket in)
	{
		System.out.println("Factory mit eingeganngenden POKE");
		conn = new Connection();
		conn.setIn(new InputPort(in, conn));
		conn.setName(name);
		conn.setIP(ip);
		conn.setPeerServerPort(port);
		
		try {
			
			OutputPort out = new OutputPort(new Socket(ip, port));
			out.sendPoke();
			
			conn.setOut(out);
			
			create();
			
		} catch (UnknownHostException e) {
			
			// Fehler
			distroy();
			
		} catch (IOException e) {
			// Fehler
			distroy();
		}
		
	}
	
	public Connection getConnection()
	{
		return conn;
	}
	
	public void create()
	{
		conn.updatePokeTime();
		conn.getIn().startListener();
		ConnectionRegistry.addConnection(conn);
		System.out.println("Factory: Erstelle sofort");
		distroy();
		
	}
	
	public void create(String name, Socket in)
	{
		if (!timeout) {
			pokeArrived = true;
			
			// Restliche Informationen eintragen
			conn.setIn(new InputPort(in, conn));
			conn.setName(name);
			conn.updatePokeTime();
			conn.getIn().startListener();
			
			//Verbindung der Registry hinzufügen
			ConnectionRegistry.addConnection(conn);
			
			// Factory zerstören
			
				timer.interrupt();
			
			System.out.println("Factory: Erstellt mit wartezeit auf Rückpoke");
			distroy();
			
		}
	}
	
	private void distroy() {
		SessionManager.removeFactory(this);
	}
	
	private void startTimer()
	{
		timer.start();
	}
	
}
