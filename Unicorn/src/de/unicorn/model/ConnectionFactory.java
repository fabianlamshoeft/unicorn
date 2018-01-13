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
	
	
	public void setFactoryData(String ip, int port, Socket out) {
		System.out.println("Factory mit Usereingabe: " + port);
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
						
					}
				
				}
				timeout = !pokeArrived;
				if (timeout) {
					// Factory zerstören
					System.out.println(conn.getName() + " " + conn.getPeerServerPort());
					System.out.println("Factory timeout!");
					destroy();
				}
				
			}
		});
		
		conn = new Connection();
		conn.setOut(new OutputPort(out));
		conn.setIP(ip);
		conn.setPeerServerPort(port);
		
		startTimer();
	}
	
	public void setFactoryData(String name, String ip, int port, Socket in) {
		System.out.println("Factory mit eingegangenem POKE");
		conn = new Connection();
		conn.setIn(new InputPort(in, conn));
		conn.setName(name);
		conn.setIP(ip);
		conn.setPeerServerPort(port);
		
		try {
			
			OutputPort out = new OutputPort(new Socket(ip, port));
			out.sendPoke();
			
			conn.setOut(out);
			
		} catch (UnknownHostException e) {
			
			// Fehler
			destroy();
			
		} catch (IOException e) {
			// Fehler
			destroy();
		}
	}
	
	public Connection getConnection()
	{
		return conn;
	}
	
	public void createWithIncomingPoke()
	{
		conn.updatePokeTime();
		conn.getIn().startListener();
		ConnectionRegistry.addConnection(conn);
		System.out.println("Factory: Erstelle sofort");
		ConnectionRegistry.getOnwardTransmitter().forwardPokeMessage(conn, 
				conn.getName(), conn.getIP(), conn.getPeerServerPort());
		destroy();
		
	}
	
	public void createWithOutgoingPoke(String name, Socket in)
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
			
			ConnectionRegistry.getOnwardTransmitter().forwardPokeMessage(conn, 
					conn.getName(), conn.getIP(), conn.getPeerServerPort());
			System.out.println("Factory: Erstellt mit Wartezeit auf Rückpoke");
			destroy();
			
		}
	}
	
	private void destroy() {
		SessionManager.removeFactory(this);
	}
	
	private void startTimer()
	{
		timer.start();
	}
	
}
