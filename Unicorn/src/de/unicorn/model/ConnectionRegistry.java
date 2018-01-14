package de.unicorn.model;

import java.util.Iterator;

/**
 * ConnectionRegistry hält die Liste aktiver Verbindungen. 
 * Dafür stellt ConnectionRegistry Metoden zum Hinzufügen, Heraussuchen und Löschen zur Verfügung.
 * Zudem hält ConnectionRegistry Instanzen der Klassen ConnectionList Manager und OnwardTransmitter,
 * welche mithilfe der Liste Verwaltungsoperationen ausführen.
 * @author fabian
 *
 */
import java.util.concurrent.ConcurrentLinkedQueue;
public class ConnectionRegistry {
	
	/**
	 * Liste von Connections
	 */
	private static ConcurrentLinkedQueue<Connection> connections = new ConcurrentLinkedQueue<Connection>();
	private static ConnectionListManager listManager = new ConnectionListManager();
	private static OnwardTransmitter onwardTransmitter = new OnwardTransmitter();
	
	
	/**
	 * Fügt die übergebende Verbindung der Liste hinzu. 
	 * @param conn Verbindung, welche hinzugefügt werden soll.
	 */
	public static void addConnection(Connection conn) {
		connections.add(conn);
		Facade.notifyObservers();
		System.out.println("Neu Verbindung in Liste eingetragen: " + conn.getName());
	}
	
	/**
	 * Gibt die erste Verbindung zurück, welche zu den angegebenen Attributen passt.
	 * 
	 * @param name Sessionname
	 * @param ip IP-Adresse
	 * @param port Port
	 * @return Gibt die Referenz auf das Connectionobjekt zurück. Falls keine Connection
	 * mit den passenden Attributen gefunden wird, gibt die Methode null zurück.
	 */
	public static Connection getConnection(String name, String ip, int port) {
		
		Iterator<Connection> it = connections.iterator();
		Connection result;
		
		while (it.hasNext()) {
			if ((result = it.next()).getName().equals(name) && result.getIP().equals(ip) &&
					result.getPeerServerPort() == port) {
				return result;
			}
		}
		
		return null;
	}
	/**
	 * Gibt die erste Verbindung zurück, welche zu den angegebenen Attributen passt.
	 * 
	 * @param ip IP-Adresse
	 * @param port Port
	 * @return Gibt die Referenz auf das Connectionobjekt zurück. Falls keine Connection
	 * mit den passenden Attributen gefunden wird, gibt die Methode null zurück.
	 */
	public static Connection getConnection(String ip, int port) {
			
			Iterator<Connection> it = connections.iterator();
			Connection result;
			
			while (it.hasNext()) {
				if ((result = it.next()).getIP().equals(ip) &&
						result.getPeerServerPort() == port) {
					return result;
				}
			}
			
			return null;
	}
	/**
	 * @return Gr��e der Liste von Connections
	 */
	public static int getSize() {
		return connections.size();
	}
	/**
	 * Entfernt die angegebene Verbindung.
	 * @param conn Verbindung, welche entfernt werden soll.
	 */
	public static void remove(Connection conn) {
		connections.remove(conn);
		System.out.println("Verbindung aus der Liste entfernt: " + conn.getName());
		Facade.notifyObservers();
	}
	/**
	 * Entfernt die erste Verbindung aus der Liste, welche mit den angegebenen Daten übereinstimmt.
	 * @param name SessionName
	 * @param ip IP-Adresse
	 * @param port Port
	 */
	public static void remove (String name, String ip, int port) {
		Iterator<Connection> it = connections.iterator();
		Connection conn;
		
		while (it.hasNext()) {
			if ((conn = it.next()).getName().equals(name) && conn.getIn().getSocket().getInetAddress().getHostAddress().equals(ip) &&
					conn.getPeerServerPort() == port) {
				connections.remove(conn);
			}
		}
		Facade.notifyObservers();
	}
	
	/**
	 * Gibt ein Iterator Objekt zurück, mit dessen Hilfe man alle Connections abgerufen werden können.
	 * @return ein Iteratorobjekt der Connection Liste
	 */
	public static Iterator <Connection> getIterator() {
		return connections.iterator();
	}
	/**
	 * Übergibt den auf der Liste laufenden ListManager
	 * @return Aktueller ListManager
	 */
	public static ConnectionListManager getListManager() {
		return listManager;
	}
	/**
	 * Übergibt den mit der Liste arbeitenden OnwardTransmitter
	 * @return Aktueller OnwardTransmitter
	 */
	public static OnwardTransmitter getOnwardTransmitter() {
		return onwardTransmitter;
	}
	/**
	 * Durchl�uft die Liste und guckt, ob die Connection mit den Parametern bereits vorhanden ist.
	 * 
	 * @param ip IP-Adresse
	 * @param port Port
	 * @return ob die Connection in der Liste ist
	 */
	public static boolean hasConnection(String ip, int port) {
		Iterator<Connection> it = connections.iterator();
		
		while (it.hasNext()) {
			if (it.next().equals(ip, port)) {
				return true;
			}
		}
		return false;
	}
}
