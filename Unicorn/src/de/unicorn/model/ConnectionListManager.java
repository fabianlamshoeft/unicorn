package de.unicorn.model;

import java.util.Iterator;

/**
 * Verwaltet die ConnectionRegistry, indem alle 30 Sekunden die Liste durchgegangen 
 * wird unf folgende Schritte abgearbeitet werden:
 * 
 * 1) Löschen aller Überfälligen Verbindungen, dessen letzte Poke Nachricht mehr als
 * eine Minute her ist
 * 
 * 2) Senden von Poke Nachrichten aller aktiver Verbindungen
 * 
 * ConnecionListManager ist ein eigener Thread.
 * @author fabian
 *
 */
public class ConnectionListManager extends Thread{

	private boolean isRunning = false;
	private long lastUpdate = System.currentTimeMillis();
	/**
	 * Beschreibt das Vorgehen des ConnectionListManagers
	 */
	public void run() {
		
		while (isRunning) {
			if ((System.currentTimeMillis() - lastUpdate) >= 30000) {
				
				boolean listHasChanged = false;
				
				Iterator <Connection> it = ConnectionRegistry.getIterator();
				Connection conn;
				while (it.hasNext()) {
					conn = it.next();
					if ((System.currentTimeMillis() - conn.getPokeTime())>= 60000) {
						System.out.println("Verbindung zu lange inaktiv! NAME: " + conn.getName());
						conn.close();
						ConnectionRegistry.remove(conn);
						listHasChanged = true;
					}else {
						conn.getOut().sendPoke();
						System.out.println("Sende Poke an: " + conn.getName());
					}
				}
				if (listHasChanged) {
					//TODO: Facade benachrichtigen
				}
				lastUpdate = System.currentTimeMillis();
			}
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// ignore
			}
		}
		
	}
	/**
	 * Startet den Tread ConnectionListManager.
	 */
	public void startListManager() {
		isRunning = true;
		super.start();
	}
	/**
	 * Hält den Thread ConnectionListManager an.
	 */
	public void stopListManager() {
		isRunning = false;
		try {
			super.join();
		} catch (InterruptedException e) {
			// ignore
		}
	}
	
}
