package de.unicorn.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Ist nur f�r die Erstellung von Connections zust�ndig und l�scht sich, wenn ihre Arbeit getan ist.
 * Sie k�mmert sich sowohl um das Szenario, dass ein Poke gesendet wurde und dann auf den R�ckpoke
 * gewartet wird, als auch um das Szenario, dass ein Poke direkt ankommt. In diesem Fall wird die
 * Connection direkt erstellt.
 * 
 * @author Simon
 *
 */
public class ConnectionFactory {
	
	/**
	 * Das zum Schluss an die ConnectionRegistry zu �bergebende Objekt, dass in dieser Klasse
	 * fertiggestellt wird.
	 */
	private Connection conn;
	/**
	 * Ein Thread f�r einen Timer, um ein Timeout zu senden, wenn er auftritt.
	 */
	private Thread timer;
	/**
	 * Variable, um anzuzeigen, ob ein Poke angekommen ist.
	 */
	private boolean pokeArrived = false;
	/**
	 * Variable, um einen Timeout anzuzeigen.
	 */
	private boolean timeout = false;
	/**
	 * Konstante, die den Zeitpunkt der Erstellung der Factory festh�lt.
	 */
	private long creationTime;
	
	/**
	 * Fügt die für eine Verbindung nötigen Daten in das zu erstellende Connection Objekt ein
	 * und startet einen Timer für den Rückpoke.
	 *  
	 * Diese Methode soll immer Dann verwendet werden, wenn z.B. seitens des
	 * Users ein Connection Befehl gesendet wird. 
	 * Da zu diesem Zeitpunkt noch nicht alle Verbindungsinformationen
	 * bekannt sind (es fehlt der Session Name), wird anschließend auf das Rückpoke gewartet,
	 * welches den Session Namen enthält. Die Ankunft des Rückpokes wird dann der Factory durch die Methode
	 * createWithIncommingPoke signalisiert.
	 * @param ip IP-Adresse der zu erstellenden Verbindung
	 * @param port Port der zu erstellenden Verbindung
	 * @param out Das Socket, über welchen die erste PokeNachricht gesendet wurde. 
	 */
	public void setFactoryData(String ip, int port, Socket out) {
//		System.out.println("Factory mit Usereingabe: " + port);
		creationTime = System.currentTimeMillis();
		timer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (System.currentTimeMillis() - creationTime <= 5000 && !pokeArrived) {
					// warten
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
					}
				
				}
				timeout = !pokeArrived;
				if (timeout) {
					// Factory zerstören
//					System.out.println(conn.getName() + " " + conn.getPeerServerPort());
					System.out.println("Factory timeout!");
					destroy();
				}
				
			}
		});
		
		conn = new Connection();
		conn.setOut(new OutputPort(out));
		conn.setIP(ip);
		conn.setPeerServerPort(port);
		
		startTimer();
	}
	/**
	 * F�gt die f�r eine Verbindung n�tigen Daten in das zu erstellende Connection Objekt ein.
	 * 
	 * Diese Methode wird aufgerufen, wenn ein ConnectionPoke ankommt. In diesem Fall wird
	 * die Factory erstellt und wartet nicht weiter auf einen R�ckpoke.
	 * Hier werden die im Parameter �bergebenen Werte der zu erstellenden Connection zugewiesen
	 * und die Ports werden erstellt.
	 * 
	 * @param name Name des Peers der zu erstellenden Verbindung
	 * @param ip IP-Adresse der zu erstellenden Verbindung
	 * @param port Port der zu erstellenden Verbindung
	 * @param in Socket, �ber den die Poke-Nachricht ankommt.
	 */
	public void setFactoryData(String name, String ip, int port, Socket in) {
		conn = new Connection();
		conn.setIn(new InputPort(in, conn));
		conn.setName(name);
		conn.setIP(ip);
		conn.setPeerServerPort(port);
		
		try {
			
			OutputPort out = new OutputPort(new Socket(ip, port));
			out.sendPoke();
			
			conn.setOut(out);
			
		} catch (UnknownHostException e) {
			
			// Fehler
			destroy();
			
		} catch (IOException e) {
			// Fehler
			destroy();
		}
	}
	/**
	 * Gibt die unter Umständen noch unfertige Verbindung zurück.
	 * @return Zu erstellende Verbindung
	 */
	public Connection getConnection()
	{
		return conn;
	}
	/**
	 * Vollendet die zu erstellende Connection, f�gt sie der Liste von Connections in
	 * der ConnectionRegistry hinzu und l�scht sich dann selbst.
	 * 
	 * Soll aufgerufen werden, wenn ein R�ckpoke ankommt.
	 */
	public void createWithIncomingPoke()
	{
		conn.updatePokeTime();
		conn.getIn().startListener();
		ConnectionRegistry.addConnection(conn);
		ConnectionRegistry.getOnwardTransmitter().forwardPokeMessage(conn, 
				conn.getName(), conn.getIP(), conn.getPeerServerPort());
		destroy();
		
	}
	
	/**
	 * Wenn der
	 * 
	 * Soll aufgerufen werden, wenn sich der Peer mit einem anderen Peer verbinden will.
	 * 
	 * @param name
	 * @param in
	 */
	public void createWithOutgoingPoke(String name, Socket in)
	{
		if (!timeout) {
			pokeArrived = true;
			
			// Restliche Informationen eintragen
			conn.setIn(new InputPort(in, conn));
			conn.setName(name);
			conn.updatePokeTime();
			conn.getIn().startListener();
			
			//Verbindung der Registry hinzufügen
			ConnectionRegistry.addConnection(conn);
			
			// Factory zerstören
			
				timer.interrupt();
			
			ConnectionRegistry.getOnwardTransmitter().forwardPokeMessage(conn, 
					conn.getName(), conn.getIP(), conn.getPeerServerPort());
//			System.out.println("Factory: Erstellt mit Wartezeit auf Rückpoke");
			destroy();
			
		}
	}
	/**
	 * l�scht diese Factory aus der Liste des SessionManagers.
	 */
	private void destroy() {
		SessionManager.removeFactory(this);
	}
	/**
	 * Startet den Timer.
	 */
	private void startTimer()
	{
		timer.start();
	}
	
}
