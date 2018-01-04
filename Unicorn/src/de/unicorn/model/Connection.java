package de.unicorn.model;

import java.net.InetAddress;
import java.util.LinkedList;

public class Connection {

	/**
	 * Session Name des Kommunikationspartners
	 */
	private String name;
	/**
	 * Portnummer des Serverports vom Kommunikationspartner
	 */
	private int peerServerPort;
	/**
	 *  Nachrichten Historie. Enthält sowohl entpfangende als auch gesendete Nachrichten
	 *  Einträge entsprechen der Art: <Name><Zeitindex><Nachricht>
	 */
	
	private LinkedList<String> history;
	/**
	 *  InputPort für eingehende Nachrichten des Kommunikationspartners
	 */
	private InputPort in;
	/**
	 *  OutputPort zum Senden
	 */
	private OutputPort out;
	/**
	 *  Zeitpunkt des letzten Pokes
	 */
	private long pokeTime;
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public InputPort getIn() {
		return in;
	}



	public void setIn(InputPort in) {
		this.in = in;
	}



	public OutputPort getOut() {
		return out;
	}



	public void setOut(OutputPort out) {
		this.out = out;
	}



	public LinkedList<String> getHistory() {
		return history;
	}
	
	public void updatePokeTime() {
		pokeTime = System.currentTimeMillis();
	}
	
	public long getPokeTime() {
		return pokeTime;
	}
	
	public int getPeerServerPort() {
		return peerServerPort;
	}



	public void setPeerServerPort(int peerServerPort) {
		this.peerServerPort = peerServerPort;
	}
	
	
	/**
	 * Stellt fest um was für eine Nachricht es sich handelt und führt abhängig davon die passenden Operationen aus.
	 * @param message einkommende Nachricht
	 */
	public void interpretIncommingMessage(String message) {
		
		if (message.startsWith("POKE")) {
			
			String [] pokeArguments = message.split(" ");
			// Poke Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
			if (pokeArguments.length == 4 && SyntaxChecker.isWellFormedSessionName(pokeArguments[1]) 
					&& SyntaxChecker.isWellFormedIpAdress(pokeArguments[2]) && SyntaxChecker.isNummeric(pokeArguments[3])) {
				
				// TODO: Reagieren
				
			}
			
			
		}else if (message.startsWith("MESSAGE")) {
			
			String [] messageArguments = message.split(" ");
			// Message Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
			if (messageArguments.length == 5 && SyntaxChecker.isWellFormedSessionName(messageArguments[1]) 
					&& SyntaxChecker.isWellFormedIpAdress(messageArguments[2]) && SyntaxChecker.isNummeric(messageArguments[3])) {
				
				// Überprüfen, ob der angegebene Peer in der Pokenachricht der Verbindung entspricht, welche über diesen Port senden darf.
				// Falls ungültig: Nachricht ignorieren
				if (messageArguments[1].equals(name) && messageArguments[2].equals(in.getSocket().getInetAddress().getHostAddress())
						&& Integer.parseInt(messageArguments[3]) == peerServerPort) {
					
					// Eintragen in Message History
					
					history.add(name + ": " + messageArguments[4]);
					// TODO: notifyObervers in Facade aufrufen...
				}
				
			}
		}else if (message.startsWith("DISCONNECT")) {
			
			String [] pokeArguments = message.split(" ");
			// Disconnect Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
			if (pokeArguments.length == 4 && SyntaxChecker.isWellFormedSessionName(pokeArguments[1]) 
					&& SyntaxChecker.isWellFormedIpAdress(pokeArguments[2]) && SyntaxChecker.isNummeric(pokeArguments[3])) {
				
				// TODO: Reagieren
				
			}
		}
		
	}



	
}
