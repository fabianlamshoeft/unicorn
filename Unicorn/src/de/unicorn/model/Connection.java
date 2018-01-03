package de.unicorn.model;

import java.net.InetAddress;
import java.util.LinkedList;

public class Connection {

	// Session Name des Kommunikationspartners
	private String name;
	// Nachrichten Historie. Enthält sowohl entpfangende als auch gesendete Nachrichten
	// Einträge entsprechen der Art: <Name><Zeitindex><Nachricht>
	private LinkedList<String> history;
	// InputPort für eingehende Nachrichten des Kommunikationspartners
	private InputPort in;
	// OutputPort zum Senden
	private OutputPort out;
	// Zeitpunkt des letzten Pokes
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
			
			String [] pokeArguments = message.split(" ");
			// Message Nachricht auf Gültigkeit prüfen. Falls ungültige Nachricht: ignorieren
			if (pokeArguments.length == 5 && SyntaxChecker.isWellFormedSessionName(pokeArguments[1]) 
					&& SyntaxChecker.isWellFormedIpAdress(pokeArguments[2]) && SyntaxChecker.isNummeric(pokeArguments[3])) {
				
				// TODO: Reagieren
				
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
