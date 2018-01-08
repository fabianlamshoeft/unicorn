package de.unicorn.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * OutputPort verwaltet den Socket, über welchen Nachrichten gesendet werden sollen und stellt hiefür nach 
 * außen eine Reihe von Methoden zur Verfügung, mit welchen Nachrichten gemäß den Protokollvorschriften 
 * versendet werden können.
 * @author fabian
 * 
 *
 */
public class OutputPort {

	/**
	 * Socket über welchen die Nachrichten ausgegeben werden.
	 */
	private Socket out;
	/**
	 * PrintWriter zum versenden von Nachrichten mit unicode Codierung
	 */
	private PrintWriter writer;
	
	/**
	 * Erzeugt einen OutputPort mit gegebenen Socket.
	 * @param out Socket, welcher verwendet werden soll.
	 * 
	 */
	public OutputPort(Socket out) {
		
		this.out = out;
		try {
			writer = new PrintWriter(out.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Sendet einen Poke des eigenen Peers
	 */
	public void sendPoke() {
		String pokeMsg = "POKE " + SessionManager.getSessionName() + " " + SessionManager.getIpAdress() + " " + Integer.toString(SessionManager.getServerListenerPort());
		writer.println(pokeMsg);
		writer.flush();
		
	}
	/**
	 * Sendet einen Poke mit den angegebenen Daten
	 * @param syntaktisch korrekter name Name des Peers
	 * @param syntaktisch korrekte ip IP-Adresse des Peers
	 * @param port Port des Peers
	 */
	public void sendPoke(String name, String ip, int port) {
		
		String PokeMsg = "POKE " + name + " " + ip + " " + Integer.toString(port);
		writer.println(PokeMsg);
		writer.flush();
		
	}
	/**
	 * Sendet eine Nachricht
	 * @param message Nachricht, die gesendet werden soll
	 */
	public void sendMessage(String message) {
		try {
			String messageMsg = "MESSAGE " + SessionManager.getSessionName() + " " + InetAddress.getLocalHost().getHostAddress() + " " + Integer.toString(SessionManager.getServerListenerPort()) + " " + message;
			writer.println(messageMsg);
			writer.flush();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Sendet eine Disconnect Nacricht mit den Daten des eigenen Peers
	 */
	public void sendDisconnect() {
		String disconnectMsg = "DISCONNECT " + SessionManager.getSessionName() + " " + SessionManager.getIpAdress() + " " + Integer.toString(SessionManager.getServerListenerPort());
		writer.println(disconnectMsg);
		writer.flush();
	}
	/**
	 * Sendet eine Disconnect Nachricht mit den Daten des angegebenen Peers
	 * @param name syntaktisch korrekter Name des Peers
	 * @param ip syntaktisch korrekte IP-Adresse des Peers
	 * @param port Port des Peers
	 */
	public void sendDisconnect(String name, String ip, int port) {
		String DisconnectMsg = "DISCONNECT " + name + " " + ip + " " + Integer.toString(port);
		writer.println(DisconnectMsg);
		writer.flush();
	}
	/**
	 * Schießt den Socket für ausgehende Nachrichten.
	 */
	public void close() {
		writer.close();
		try {
			out.close();
		} catch (IOException e) {
			// ignore
		}
	}
	
}
