package de.unicorn.model;

import java.util.Iterator;
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
	 * @throws Exception Falls ein Fehler beim sertellen des ServerPortListeners auftaucht. 
	 */
	public static void startUp(String sessionName, String ip, int serverPort) throws Exception{
		SessionManager.setIpAdress(ip);
		SessionManager.setServerListenerPort(serverPort);
		SessionManager.setSessionName(sessionName);
		SessionManager.startServerPortListener();
		ConnectionRegistry.getListManager().startListManager();
	}
	/**
	 * Versucht mit den angegebenden Verbindungsinformationen eine Verbindung zu einem Peer aufzubauen.
	 * @param ip IP-Adresse des Peers, zu welchem eine Verbindung aufgebaut werden soll.
	 * @param port ServerPort des Peers, zu welchem eine Verbindung aufgebaut werden soll.
	 */
	public static void connect(String ip, int port) {
		SessionManager.sendConnectionPoke(ip, port);
	}
	/**
	 * Sendet eine Nachricht an alle Peers, welche mit dem angegebenen Namen übereinstimmen.
	 * @param name SessionName der Peers, an denen die Nachricht gesendet werden soll.
	 * @param message Nachricht, die versendet werden soll.
	 */
	public static void sendMessage(String name, String message) {
		Iterator <Connection> it = ConnectionRegistry.getIterator();
		while (it.hasNext()) {
			Connection conn = it.next();
			if(conn.getName().equals(name)) {
				conn.getOut().sendMessage(message);
			}
		}
	}
	/**
	 * Sendet eine Nachricht an den Peer, der durch das Tupel IP-Adresse und Port eindeutlich identifiziert wird.
	 * @param ip IP-Adresse des Empfängers
	 * @param port Port des Empfängers
	 * @param message Nachricht, die gesendet werden soll.
	 */
	public static void sendMessage(String ip, int port, String message) {
		if (ConnectionRegistry.hasConnection(ip, port)) {
			Connection conn = ConnectionRegistry.getConnection(ip, port);
			conn.getOut().sendMessage(message);
		}
	}
	
	/**
	 * Gibt ein Array aller aktuellen Verbindungen zurück.
	 * @return Liste aller aktuellen Verbindungen.
	 */
	public static String[] getPeerList() {
		String [] result = new String [ConnectionRegistry.getSize()];
		Iterator <Connection> it = ConnectionRegistry.getIterator();
		int index = 0;
		while (it.hasNext()) {
			Connection conn = it.next();
			result[index] = conn.getName() + "\n" + conn.getIP() + "\n" + conn.getPeerServerPort();
			index ++;
		}
		System.out.println("Da holt sich jemand was ab...");
		return result;
	}
	/**
	 * Gibt den Nachrichtenverlauf einer Verbindung als String Liste zurück.
	 * @param name Name des Peers
	 * @param ip IP-Adresse des Peers
	 * @param port Poer des Peers
	 * @return Liste aller zu dieser Verbindung gesendeten und empfangenden Nachrichten
	 */
	public static LinkedList<String> getMessageHistory(String name, String ip, int port){
		return ConnectionRegistry.getConnection(name, ip, port).getHistory();
	}
	/**
	 * Sendet an alle bestehenden Verbindungen eine Disconnect Nachricht und schließt sie anschließend.
	 */
	public static void disconnect() {
		
		SessionManager.getServerPortListener().setAcceptNewConnections(false);
		ConnectionRegistry.getListManager().setHold(true);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator <Connection> it = ConnectionRegistry.getIterator();
		while (it.hasNext()) {
			Connection conn = it.next();
			System.out.println("Entferne: " + conn.getName());
			conn.close();
			ConnectionRegistry.remove(conn);
		}
		
		SessionManager.getServerPortListener().setAcceptNewConnections(true);
		ConnectionRegistry.getListManager().setHold(false);
	}
	/**
	 * Ruft disconnect auf und schließt anschließend den Port für eingehende Verbindungen.
	 * Der Client ist somit offline und nicht mehr erreichbar.
	 */
	public static void exit() {
//		System.out.println("beenden ...");
		SessionManager.stopServerPortListener();
		disconnect();
		ConnectionRegistry.getListManager().stopListManager();
//		System.out.println("alles zu");
	}
	/**
	 * Meldet das Observer Objekt bei der Facade an
	 * @param obs Observer, der angemeldet werden soll.
	 */
	public static void register(IFacadeObserver obs) {
		obsList.add(obs);
	}
	/**
	 * Meldet das Observer Objekt bei der Facade ab.
	 * @param obs Observer, das abgemeldet werden soll
	 */
	public static void unregister(IFacadeObserver obs) {
		obsList.remove(obs);
	}
	/**
	 * Informiert alle Observer der Facade über eine Änderung im Model.
	 */
	public static void notifyObservers(){
		Iterator <IFacadeObserver> it = obsList.iterator();
		while(it.hasNext()) {
			it.next().update();
			System.out.println("update ... " + SessionManager.getSessionName() );
		}
		System.out.println("ich sage es allen weiter...");
	}
	public static void notifyObservers(Connection conn) {
		Iterator <IFacadeObserver> it = obsList.iterator();
		while(it.hasNext()) {
			it.next().updateMessageHistory(conn);
		}
	}
	
	public static String getIp() {
		return SessionManager.getIpAdress();
	}
	
	public static int getPort() {
		return SessionManager.getServerListenerPort();
	}
}
