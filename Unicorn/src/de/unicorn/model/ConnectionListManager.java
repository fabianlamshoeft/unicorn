package de.unicorn.model;

import java.util.Iterator;

/**
 * Verwaltet die ConnectionRegistry, indem alle 30 Sekunden die Liste durchgegangen 
 * wird unf folgende Schritte abgearbeitet werden:
 * 
 * 1) LÃ¶schen aller ÃœberfÃ¤lligen Verbindungen, dessen letzte Poke Nachricht mehr als
 * eine Minute her ist
 * 
 * 2) Senden von Poke Nachrichten aller aktiver Verbindungen
 * 
 * ConnecionListManager ist ein eigener Thread.
 * @author fabian
 *
 */
public class ConnectionListManager extends Thread{
	
	/**
	 * Sagt aus, ob der Thread laufen kann oder nicht.
	 */
	private boolean isRunning = false;
	/**
	 * Hält den Thread an, sodass er nichts macht, aber weiterläuft.
	 */
	private boolean hold = false;
	/**
	 * Zeitpunkt des letzten Updates
	 */
	private long lastUpdate = System.currentTimeMillis();
	
	/**
	 * Geht durch die Liste aktuell verbundener Peers und guckt, ob die Peers aktuell sind. Wenn ja,
	 * dann wird automatisch alle 30 Sekunden ein Poke gesendet. Wenn nicht, so wird die
	 * Connection geschlossen und aus der Liste der ConnectionRegistry gelöscht.
	 */
	public void run() {
		
		while (isRunning) {
			if (((System.currentTimeMillis() - lastUpdate) >= 30000) && !hold) {
				
				Iterator <Connection> it = ConnectionRegistry.getIterator();
				Connection conn;
				while (it.hasNext()) {
					conn = it.next();
					if ((System.currentTimeMillis() - conn.getPokeTime())>= 60000) {
						System.out.println("Verbindung zu lange inaktiv! NAME: " + conn.getName());
						conn.close();
						ConnectionRegistry.remove(conn);
						
					}else {
						conn.getOut().sendPoke();
//						System.out.println("Sende Poke an: " + conn.getName());
					}
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
	 * Setzt einen Wert für hold und kann so die Funktion des ConnectionListManagers aktivieren oder
	 * aussetzen lassen ohne den Thread zu beenden.
	 * 
	 * @param hold Sagt, ob die Funktionen angehalten werden sollen oder nicht.
	 */
	public void setHold(boolean hold) {
		this.hold = hold;
	}
	/**
	 * Startet den Thread ConnectionListManager.
	 */
	public void startListManager() {
		isRunning = true;
		super.start();
	}
	/**
	 * HÃ¤lt den Thread ConnectionListManager an.
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
