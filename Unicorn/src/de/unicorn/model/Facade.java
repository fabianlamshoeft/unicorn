package de.unicorn.model;

import java.util.LinkedList;
/**
 * Facade stellt alle Funktionen des Chats als eine Reihe von statischen Methoden als Schnittstelle zur 
 * Verfügung. Zudem bietet Facade die Möglichkeit via eines Oberserver Patterns alle Instanzen, die Facade
 * benuzten möchten über Änderungen im Model zu informieren. 
 * @author fabian
 *
 */
public class Facade {
	/**
	 * Liste von Observern, die bei der Facade registriert sind.
	 */
	private static LinkedList<IFacadeObserver> obsList = new LinkedList<IFacadeObserver>();
	/**
	 * Startet alle für die Funtion nötigen Listener und Manager.
	 * Hierfür muss der eigene SessionName sowie der Port angegeben werden, über den nach neuen 
	 * Verbindungen gelauscht werden soll.
	 * 
	 * @param sessionName SessionName der aktuellen Session
	 * @param serverPort Port, über welchen man erreichbar sein will.
	 */
	public static void startUp(String sessionName, int serverPort) {
		
	}
	/**
	 * Versucht mit den angegebenden Verbindungsinformationen eine Verbindung zu einem Peer aufzubauen.
	 * @param ip IP-Adresse des Peers, zu welchem eine Verbindung aufgebaut werden soll.
	 * @param port ServerPort des Peers, zu welchem eine Verbindung aufgebaut werden soll.
	 */
	public static void connect(String ip, int port) {
		
	}
	/**
	 * Sendet eine Nachricht an alle Peers, welche mit dem angegebenen Namen übereinstimmen.
	 * @param name SessionName der Peers, an denen die Nachricht gesendet werden soll.
	 * @param message Nachricht, die versendet werden soll.
	 */
	public static void sendMessage(String name, String message) {
		
	}
	/**
	 * Sendet eine Nachricht an den Peer, der durch das Tupel IP-Adresse und Port eindeutlich identifiziert wird.
	 * @param ip IP-Adresse des Empfängers
	 * @param port Port des Empfängers
	 * @param message Nachricht, die gesendet werden soll.
	 */
	public static void sendMessage(String ip, int port, String message) {
		
	}
	
	/**
	 * Gibt ein Array aller aktuellen Verbindungen zurück.
	 * @return Liste aller aktuellen Verbindungen.
	 */
	public static String[] getPeerList() {
		return null;
	}
	/**
	 * Gibt den Nachrichtenverlauf einer Verbindung als String Liste zurück.
	 * @param name Name des Peers
	 * @param ip IP-Adresse des Peers
	 * @param port Poer des Peers
	 * @return Liste aller zu dieser Verbindung gesendeten und empfangenden Nachrichten
	 */
	public static LinkedList<String> getMessageHistory(String name, String ip, int port){
		return null;
	}
	/**
	 * Sendet an alle bestehenden Verbindungen eine Disconnect Nachricht und schließt sie anschließend.
	 */
	public static void disconnect() {
		
	}
	/**
	 * Ruft disconnect auf und schließt anschließend den Port für eingehende Verbindungen.
	 * Der Client ist somit offline und nicht mehr erreichbar.
	 */
	public static void exit() {
		
	}
	/**
	 * Meldet das Observer Objekt bei der Facade an
	 * @param obs Observer, der angemeldet werden soll.
	 */
	public static void register(IFacadeObserver obs) {
		
	}
	/**
	 * Meldet das Observer Objekt bei der Facade ab.
	 * @param obs Observer, das abgemeldet werden soll
	 */
	public static void unregister(IFacadeObserver obs) {
		
	}
	/**
	 * Informiert alle Observer der Facade über eine Änderung im Model.
	 */
	public static void notifyObservers(){
		
	}
}
