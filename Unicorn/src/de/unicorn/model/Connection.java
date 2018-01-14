package de.unicorn.model;

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
	private LinkedList<String> history = new LinkedList<String>();
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
	/**
	 *  IP des verbundenen Peers
	 */
	private String ip;


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
	/**
	 * Aktualisiert die PokeTime
	 */
	public void updatePokeTime() {
//		System.out.println("Update Poke Time: " + getName());
		pokeTime = System.currentTimeMillis();
	}

	public long getPokeTime() {
		return pokeTime;
	}

	public int getPeerServerPort() {
		return peerServerPort;
	}


	public String getIP()
	{
		return ip;
	}
	public void setIP(String newIp) {
		this.ip = newIp;
	}

	public void setPeerServerPort(int peerServerPort) {
		this.peerServerPort = peerServerPort;
	}



	/**
	 * Sendet eine Disconnect Nachricht an den Kommunikationspartner und 
	 * schließt eingehende und ausgehende Verbindungen.
	 */
	public void close () {
		out.sendDisconnect();

		in.close();
		out.close();
	}
	/**
	 * Stellt fest um was für eine Nachricht es sich handelt und führt abhängig davon die passenden Operationen aus.
	 * @param message einkommende Nachricht
	 */
	public void interpretIncommingMessage(String message) {
		
		if (message.startsWith("POKE")) {
			// Fall 1: Es ist eine Poke Nachricht
			
			String [] pokeArguments = message.split(" ", 4);
			
			// Poke Nachricht auf Gültigkeit prüfen. Ist die Syntax der Nachricht falsch, wird die Poke Nachricht ignoriert.
			if (SyntaxChecker.isWellFormedSessionName(pokeArguments[1]) 
					&& SyntaxChecker.isWellFormedIpAdress(pokeArguments[2]) && SyntaxChecker.isPortNumber(pokeArguments[3])) {
				
				if (pokeArguments[1].equals(name) && pokeArguments[2].equals(ip) && Integer.parseInt(pokeArguments[3]) == peerServerPort) {
					// Poke Parameter entsprechen den Daten des Kommunikationspartners: PokeTime akutalisieren
					
					updatePokeTime();
					
				}else {
					
					/* Poke Parameter ungleich dem Kommunikationspartner:
					 * Fall 1: 	Nachricht in der Liste bekannter Kommunikationspartner vorhanden:
					 * 			- PokeTime bei dem jeweiligen Kommunikationspartner aktualisieren
					 * 
					 * Fall 2:	Nachricht nicht in der Liste bekannter Peers enthalten
					 * 			- Factory für neue Verbindung in ConnectionManager erstellen, welche einen eigenen Poke
					 * 			  an den neuen Peer schickt
					 * 			- Poke Nachricht weiterleiten
					 */
					
					if (ConnectionRegistry.hasConnection(pokeArguments[2], Integer.parseInt(pokeArguments[3]))) {
						// PokeTime Update bei entsprechender Verbindung: Connection aus Registry heraussuchen und Poke Update durchführen
						ConnectionRegistry.getConnection(pokeArguments[1], pokeArguments[2], Integer.parseInt(pokeArguments[3])).updatePokeTime();
					}else {
							// Verbindung hinzufügen: Zunächst sicher stellen, dass die Verbindung noch nicht in der Liste von Factories liegt
						if (!SessionManager.connectionInFactory(pokeArguments[2], Integer.parseInt(pokeArguments[3]))) {
							
							SessionManager.sendConnectionPoke(pokeArguments[2], Integer.parseInt(pokeArguments[3]));
							// Poke weiterleiten
							ConnectionRegistry.getOnwardTransmitter().forwardPokeMessage(this, pokeArguments[1], pokeArguments[2], Integer.parseInt(pokeArguments[3]));
							
						}
		
					}
				}

			}


		}else if (message.startsWith("MESSAGE")) {
			String [] messageArguments = message.split(" ", 5);

			// Message Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
//			System.out.println(message);
			

			if (SyntaxChecker.isWellFormedSessionName(messageArguments[1]) 
					&& SyntaxChecker.isWellFormedIpAdress(messageArguments[2]) && SyntaxChecker.isPortNumber(messageArguments[3])
					&& SyntaxChecker.isWellFormedMessage(messageArguments[4])) {
				// Überprüfen, ob der angegebene Peer in der Pokenachricht der Verbindung entspricht, welche über diesen Port senden darf.
				// Falls ungültig: Nachricht ignorieren


				if (messageArguments[1].equals(name) && messageArguments[2].equals(ip)
						&& Integer.parseInt(messageArguments[3]) == peerServerPort) {
					
					// Msg zur History hinzufügen...
					history.add(name + ": " + messageArguments[4]);
					System.out.println(name + ": " + messageArguments[4]);
					Facade.notifyObservers(this);
				}else {
					
				}

			}else {
				
			}
		}else if (message.startsWith("DISCONNECT")) {

			String [] discArguments = message.split(" ", 4);
			// Disconnect Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
			if (SyntaxChecker.isWellFormedSessionName(discArguments[1]) 
					&& SyntaxChecker.isWellFormedIpAdress(discArguments[2]) && SyntaxChecker.isPortNumber(discArguments[3])) {
				
				/*
				 * Falls die Disconnect Nachricht die Parameter des verbundenen Peers trägt: Verbindung schließen
				 * Sonst wurde die Disconnect Nachricht weitergeleitet. In diesem Fall: Falls Verbindung in der Liste vorhanden: Dessen Verbindung schließen
				 */
				
				if (discArguments[1].equals(name) && discArguments[2].equals(ip)
						&& Integer.parseInt(discArguments[3]) == peerServerPort) {
					// Eigende Verbindung
					ConnectionRegistry.getOnwardTransmitter().forwardDisconnectMessage(this, discArguments[1], discArguments[2], Integer.parseInt(discArguments[3]));
					close();
					System.out.println("DISCONNECT von: " + discArguments[1]);
					ConnectionRegistry.remove(this);
				}else {
					if (ConnectionRegistry.hasConnection(discArguments[2], Integer.parseInt(discArguments[3]))){
						// Entsprechende Verbndung aus Registry heraussuchen und Verbindung trennen. Dann Disconnect weiterleiten.
						Connection closeConn = ConnectionRegistry.getConnection(discArguments[1], discArguments[2], Integer.parseInt(discArguments[3]));
						closeConn.close();
						ConnectionRegistry.remove(closeConn);
						ConnectionRegistry.getOnwardTransmitter().forwardDisconnectMessage(this, discArguments[1], discArguments[2], Integer.parseInt(discArguments[3]));
					}
				}

			}
		}else {
			
		}

	}

	/**
	 * Überprüft, ob sich die Verbindung mit den angegebenen Parametern identifizieren lässt.
	 * @param ip IP-Adresse 
	 * @param port Port
	 * @return true, falls IP-Adresse und Port mit denen der Verbindung übereinstimmen, sonst false.
	 */
	public boolean equals( String ip, int port) {
		return this.ip.equals(ip) && this.peerServerPort == port;
	}

}
