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
	
	public void run() {
		
		try {
			serverSocket = new ServerSocket(SessionManager.getServerListenerPort());
			while (isRunning) {
				Socket incommingRequest = serverSocket.accept();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(incommingRequest.getInputStream()));
				String message = reader.readLine();
				//reader.close();
				
				if (message.startsWith("POKE")) {

					String [] pokeArguments = message.split(" ");
					// Poke Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
					if (pokeArguments.length == 4 && SyntaxChecker.isWellFormedSessionName(pokeArguments[1]) 
							&& SyntaxChecker.isWellFormedIpAdress(pokeArguments[2]) && SyntaxChecker.isNummeric(pokeArguments[3])) {
						
						// Peer noch nicht in der Factory oder Liste?
						
						if (ConnectionRegistry.hasConnection(pokeArguments[2], Integer.parseInt(pokeArguments[3]))) {
							// PokeTime update
							System.out.println("ServerPortListener: POKE Update");
							ConnectionRegistry.getConnection(pokeArguments[1], pokeArguments [2], Integer.parseInt(pokeArguments[3])).updatePokeTime();
							
						}else if (SessionManager.connectionInFactory(pokeArguments[2], Integer.parseInt(pokeArguments[3]))) {
							// Rest Infos für Factory zum erstellen übergeben
							System.out.println("ServerPortListener: Poke Informationen ergänzen...");
							SessionManager.getFactory(pokeArguments[2], Integer.parseInt(pokeArguments[3])).create(pokeArguments[1], incommingRequest);
							
						}else {
							// Neue Factory hinzufügen
							System.out.println("1. ServerPortListener: Neues Poke! Verbindung hinzufügen!");
							SessionManager.addFactory(new ConnectionFactory(pokeArguments[1], pokeArguments [2], Integer.parseInt(pokeArguments[3]),incommingRequest));
							
						}
						

					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("Schließe Socket...");
			
			try {
				serverSocket.close();
			} catch (Exception e) {}
		}
		
	}
	
	public void start() {
		isRunning = true;
		super.start();
	}
	public void close()
	{
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
	
	
}
