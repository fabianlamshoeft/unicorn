package de.unicorn.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * InputPort ist ein Listener für eingehende Nachrichten.
 * 
 * @author fabian
 *
 */

public class InputPort extends Thread{
	/**
	 * Socket, über welchen eingehende Nachrichten empfangen werden.
	 */
	private Socket in;
	/**
	 * Referenz auf das Connection Objekt, welches diesen Listener benutzt. 
	 * Auf diese weise kann das Connection Objekt auf eingehende Nachrichten reagieren. 
	 * siehe: interpretIncommingMessage
	 */
	private Connection conn;
	/**
	 * Gibt an, ob der Listener gerade läuft.
	 */
	private boolean isRunning = false;
	
	
	/**
	 * Erzeugt ein InputPort Objekt, welches den in Socket überwacht.
	 * @param in Socket, der überwacht werden soll.
	 * @param conn Referenz auf das Connection Objekt, welches den InputListener benutzt. Wird benötigt, um 
	 * auf eingehende Nachrichten reagieren zu können.
	 */
	public InputPort (Socket in, Connection conn) {
		this.in = in;
		this.conn = conn;
	}
	/**
	 * Beschreibt das Vorgehen das Überwachungsprozesses
	 */
	public void run() {
		
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in.getInputStream()));
			String message = "";
			
			while (isRunning) {
				message = reader.readLine();
				// Das interpretieren der Nachricht übernimmt die Instanz des Connection Objektes, welches 
				// diesen Listener benutzt
				System.out.println(conn.getName() + ": Rohdaten = " + message);
				if (message != null) {
					conn.interpretIncomingMessage(message);
				}else {
					reader.close();
					in.close();
				}
				
			}
			
			reader.close();
			
		} catch (IOException e) {
		}
		
	}
	/**
	 * Startet den InputPortListener
	 */
	public void startListener() {
		isRunning = true;
		super.start();
//		System.out.println("Listener gestartet");
	}
	/**
	 * Gibt den InputSocket zurück
	 * @return Referenz auf das Socket des InputPort Objektes 
	 */
	public Socket getSocket() {
		return in;
	}
	
	/**
	 * Stoppt den InputPortListener und schließt den Socket.
	 */
	public void close() {
		isRunning = false;
		
		
			super.interrupt();
			
			try {
				
				if (!in.isClosed()) {
					in.close();
				}
				
			} catch (IOException e) {
			}
		
		
	}
	
}
