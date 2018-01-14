package de.unicorn.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Ist nur für die Erstellung von Connections zuständig und löscht sich, wenn ihre Arbeit getan ist.
 * Sie kümmert sich sowohl um das Szenario, dass ein Poke gesendet wurde und dann auf den Rückpoke
 * gewartet wird, als auch um das Szenario, dass ein Poke direkt ankommt. In diesem Fall wird die
 * Connection direkt erstellt.
 * 
 * @author Simon
 *
 */
public class ConnectionFactory {
	
	/**
	 * Das zum Schluss an die ConnectionRegistry zu übergebende Objekt, dass in dieser Klasse
	 * fertiggestellt wird.
	 */
	private Connection conn;
	/**
	 * Ein Thread für einen Timer, um ein Timeout zu senden, wenn er auftritt.
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
	 * Konstante, die den Zeitpunkt der Erstellung der Factory festhält.
	 */
	private long creationTime;
	
	/**
	 * FÃ¼gt die fÃ¼r eine Verbindung nÃ¶tigen Daten in das zu erstellende Connection Objekt ein
	 * und startet einen Timer fÃ¼r den RÃ¼ckpoke.
	 *  
	 * Diese Methode soll immer Dann verwendet werden, wenn z.B. seitens des
	 * Users ein Connection Befehl gesendet wird. 
	 * Da zu diesem Zeitpunkt noch nicht alle Verbindungsinformationen
	 * bekannt sind (es fehlt der Session Name), wird anschlieÃŸend auf das RÃ¼ckpoke gewartet,
	 * welches den Session Namen enthÃ¤lt. Die Ankunft des RÃ¼ckpokes wird dann der Factory durch die Methode
	 * createWithIncommingPoke signalisiert.
	 * @param ip IP-Adresse der zu erstellenden Verbindung
	 * @param port Port der zu erstellenden Verbindung
	 * @param out Das Socket, Ã¼ber welchen die erste PokeNachricht gesendet wurde. 
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
					// Factory zerstÃ¶ren
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
	 * Fügt die für eine Verbindung nötigen Daten in das zu erstellende Connection Objekt ein.
	 * 
	 * Diese Methode wird aufgerufen, wenn ein ConnectionPoke ankommt. In diesem Fall wird
	 * die Factory erstellt und wartet nicht weiter auf einen Rückpoke.
	 * Hier werden die im Parameter übergebenen Werte der zu erstellenden Connection zugewiesen
	 * und die Ports werden erstellt.
	 * 
	 * @param name Name des Peers der zu erstellenden Verbindung
	 * @param ip IP-Adresse der zu erstellenden Verbindung
	 * @param port Port der zu erstellenden Verbindung
	 * @param in Socket, über den die Poke-Nachricht ankommt.
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
	 * Gibt die unter UmstÃ¤nden noch unfertige Verbindung zurÃ¼ck.
	 * @return Zu erstellende Verbindung
	 */
	public Connection getConnection()
	{
		return conn;
	}
	/**
	 * Vollendet die zu erstellende Connection, fügt sie der Liste von Connections in
	 * der ConnectionRegistry hinzu und löscht sich dann selbst.
	 * 
	 * Soll aufgerufen werden, wenn ein Rückpoke ankommt.
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
			
			//Verbindung der Registry hinzufÃ¼gen
			ConnectionRegistry.addConnection(conn);
			
			// Factory zerstÃ¶ren
			
				timer.interrupt();
			
			ConnectionRegistry.getOnwardTransmitter().forwardPokeMessage(conn, 
					conn.getName(), conn.getIP(), conn.getPeerServerPort());
//			System.out.println("Factory: Erstellt mit Wartezeit auf RÃ¼ckpoke");
			destroy();
			
		}
	}
	/**
	 * löscht diese Factory aus der Liste des SessionManagers.
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
