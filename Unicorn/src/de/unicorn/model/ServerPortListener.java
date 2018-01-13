package de.unicorn.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPortListener extends Thread{
	private ServerSocket serverSocket;
	private boolean isRunning = false;
	private boolean acceptNewConnections = false;
	
	public void run() {
		
		try {
			serverSocket = new ServerSocket(SessionManager.getServerListenerPort());
			while (isRunning) {
				Socket incommingRequest = serverSocket.accept();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(incommingRequest.getInputStream()));
				String message = reader.readLine();
				
				if (message.startsWith("POKE")) {

					String [] pokeArguments = message.split(" ", 4);
					// Poke Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
					if (SyntaxChecker.isWellFormedSessionName(pokeArguments[1])
							&& SyntaxChecker.isWellFormedIpAdress(pokeArguments[2])
							&& SyntaxChecker.isPortNumber(pokeArguments[3])) {
						
						// Peer noch nicht in der Factory oder Liste?
						System.out.println(message);
						if (ConnectionRegistry.hasConnection(pokeArguments[2], Integer.parseInt(pokeArguments[3]))) {
							// PokeTime update
//							System.out.println("ServerPortListener: POKE Update");
							ConnectionRegistry.getConnection(pokeArguments[1], pokeArguments [2], Integer.parseInt(pokeArguments[3])).updatePokeTime();
							
						}else if (SessionManager.connectionInFactory(pokeArguments[2], Integer.parseInt(pokeArguments[3]))) {
							// Rest Infos für Factory zum erstellen übergeben
//							System.out.println("ServerPortListener: Poke Informationen ergänzen...");
							if (acceptingNewConnections()) {
								SessionManager.getFactory(pokeArguments[2], Integer.parseInt(pokeArguments[3])).createWithOutgoingPoke(pokeArguments[1], incommingRequest);
							}
						}else {
							// Neue Factory hinzufügen
//							System.out.println("ServerPortListener: Neues Poke! Verbindung hinzufügen!");
							if (acceptingNewConnections()) {
								ConnectionFactory fac = new ConnectionFactory();
								fac.setFactoryData(pokeArguments[1], pokeArguments [2], Integer.parseInt(pokeArguments[3]),incommingRequest);
								SessionManager.addFactory(fac);
								fac.createWithIncomingPoke();
							}
							
						}
						

					}
				}
			}
			
			
		} catch (Exception e) {
			
		}finally {
			try {
				serverSocket.close();
			} catch (Exception e) {}
		}
		
	}
	
	public void start() {
		isRunning = true;
		setAcceptNewConnections(true);
		super.start();
	}
	public void close()
	{
		setAcceptNewConnections(false);
		isRunning = false;
		
		try {
			serverSocket.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		
		try {
			join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean acceptingNewConnections() {
		return acceptNewConnections;
	}

	public void setAcceptNewConnections(boolean acceptNewConnections) {
		this.acceptNewConnections = acceptNewConnections;
	}
	
	
}
