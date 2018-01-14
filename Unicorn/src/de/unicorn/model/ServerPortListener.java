package de.unicorn.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerPortListener ist ein eigener Thread, der den ServerSocket und somit den allgemeinen
 * Port verwaltet, auf dem der Peer f�r andere Peers erreichbar ist. Somit bestimmt der ServerPortListener
 * das Verhalten beim Eintreffen von Poke-Nachrichten unbekannter Peers.
 * 
 * @author Simon
 *
 */
public class ServerPortListener extends Thread{
	private ServerSocket serverSocket;
	private boolean isRunning = false;
	private boolean acceptNewConnections = false;
	
	/**
	 * Verarbeitung der beim ServerSocket ankommenden Nachrichten.
	 */
	public void run() {
		
		try {
			serverSocket = new ServerSocket(SessionManager.getServerListenerPort());
			while (isRunning) {
				Socket incomingRequest = serverSocket.accept();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(incomingRequest.getInputStream()));
				String message = reader.readLine();
				
				if (message.startsWith("POKE")) {

					String [] pokeArguments = message.split(" ", 4);
					// Poke Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
					if (SyntaxChecker.isWellFormedSessionName(pokeArguments[1])
							&& SyntaxChecker.isWellFormedIpAddress(pokeArguments[2])
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
								SessionManager.getFactory(pokeArguments[2], Integer.parseInt(pokeArguments[3])).createWithOutgoingPoke(pokeArguments[1], incomingRequest);
							}
						}else {
							// Neue Factory hinzufügen
//							System.out.println("ServerPortListener: Neues Poke! Verbindung hinzufügen!");
							if (acceptingNewConnections()) {
								ConnectionFactory fac = new ConnectionFactory();
								fac.setFactoryData(pokeArguments[1], pokeArguments [2], Integer.parseInt(pokeArguments[3]),incomingRequest);
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
	
	/**
	 * Startet den ServerPortListener.
	 */
	public void start() {
		isRunning = true;
		setAcceptNewConnections(true);
		super.start();
	}
	/**
	 * Beendet den ServerPortListener und schlie�t den ServerSocket.
	 */
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
	/**
	 * Guckt, ob eine neue Verbindung aufgebaut werden kann.
	 * @return ob neue Verbindungen aufgebaut werden d�rfen.
	 */
	public boolean acceptingNewConnections() {
		return acceptNewConnections;
	}
	/**
	 * Setzt, ob neue Verbindungen aufgebaut werden d�rfen oder nicht.
	 * @param acceptNewConnections Wert, ob neue Verbindung aufgebaut werden darf.
	 */
	public void setAcceptNewConnections(boolean acceptNewConnections) {
		this.acceptNewConnections = acceptNewConnections;
	}
	
	
}
