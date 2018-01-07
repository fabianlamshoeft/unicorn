package de.unicorn.model;

import java.util.LinkedList;


/**
 * Verwaltet die Instanzen von ConnectionFactory und ServerPortListener. Der ConnectionManager startet den ServerPortListener
 * und erstellt ConnectionFactories.
 * 
 * 
 * @author Simon
 */

public class ConnectionManager {
	private static LinkedList<ConnectionFactory> factories;
	private static ServerPortListener serverPortListener;
	private static String sessionName;
	private static int serverListenerPort;
	
	
	
	public static String getSessionName() {
		return sessionName;
	}

	public static void setSessionName(String sessionN) {
		sessionName = sessionN;
	}
	
	public static int getServerListenerPort() {
		return serverListenerPort;
	}

	public static void setServerListenerPort(int serverListenerP) {
		serverListenerPort = serverListenerP;
	}

	public static void addFactory(ConnectionFactory fac)
	{
		factories.add(fac);
	}
	
	/**
	 * 
	 * @param fac
	 */
	public static void removeFactory(ConnectionFactory fac)
	{
		factories.remove(fac);
	}
	
	/**
	 * Erstellt einen ServerPortListener und startet ihn.
	 */
	public static void startServerPortListener()
	{
		serverPortListener = new ServerPortListener();
		serverPortListener.runServerPortListener();
	}
	
	
	/**
	 * Gibt zurück, ob sich eine Connection (mit IP und Port)
	 * aktuell in einer Factory befindet, d.h. aktuell erstellt wird.
	 * 
	 * @param ip
	 * @param port
	 * @return true, wenn Connection in Factory
	 */
	public static boolean connectionInFactory(String ip, int port)
	{
		for (ConnectionFactory connectionFactory : factories)
		{
			if(connectionFactory.getConnection().getIP().equals(ip)
			&& connectionFactory.getConnection().getPeerServerPort()==(port))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Durchsucht die Factories und gibt die Factory mit der Connection
	 * (mit IP und Port) zurück.
	 * 
	 * @param ip
	 * @param port
	 * @return Factory mit gegebener Connection
	 */
	public static ConnectionFactory getFactory( String ip, int port)
	{
		if(connectionInFactory(ip, port))
		{
			for (ConnectionFactory connectionFactory : factories)
			{
				if(connectionFactory.getConnection().getIP().equals(ip)
				&& connectionFactory.getConnection().getPeerServerPort()==(port))
				{
					return connectionFactory;
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * Überprüfe vorher
	 * 
	 * Wenn der Benutzer versucht sich mit einem Peer zu verbinden,
	 * sendet der ConnectionManager einen Poke an diesen Peer.
	 * 
	 * Ist diese Connection (mit IP und Port) bereits in einer Factory,
	 * so macht diese Methode nichts.
	 * 
	 */
	public static void sendConnectionPoke(String ip, int port)
	{
		if(!connectionInFactory(ip, port))
		{
			
		}
		System.out.println("Warte auf Antwort...");
	}
	
	
	
	/*
	 * Diese Methoden sind zur Reserve da, sollten wir sie später brauchen.
	 * zB. sollte ein Peer nicht eindeutig durch IP und Port
	 * identifizierbar sein.
	 */
	/**
	 * RESERVE-METHODE: NICHT BENUTZEN!
	 * Gibt zurück, ob sich eine Connection (mit Name, IP und Port)
	 * Gibt zurï¿½ck, ob sich eine Connection (mit Name, IP und Port)
	 * aktuell in einer Factory befindet, d.h. aktuell erstellt wird.
	 * 
	 * @param name
	 * @param ip
	 * @param port
	 * @return true, wenn Connection in Factory
	 */
	public static boolean connectionInFactory(String name, String ip, int port)
	{
		for (ConnectionFactory connectionFactory : factories)
		{
			if(connectionFactory.getConnection().getName().equals(name)
			&& connectionFactory.getConnection().getIP().equals(ip)
			&& connectionFactory.getConnection().getPeerServerPort()==(port))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * RESERVE-METHODE: NICHT BENUTZEN!
	 * Durchsucht die Factories und gibt die Factory mit der Connection
	 * (mit Name, IP und Port) zurï¿½ck.
	 * 
	 * @param name
	 * @param ip
	 * @param port
	 * @return Factory mit gegebener Connection
	 */
	public static ConnectionFactory getFactory(String name, String ip, int port)
	{
		if(connectionInFactory(name, ip, port))
		{
			for (ConnectionFactory connectionFactory : factories)
			{
				if(connectionFactory.getConnection().getName().equals(name)
				&& connectionFactory.getConnection().getIP().equals(ip)
				&& connectionFactory.getConnection().getPeerServerPort()==(port))
				{
					return connectionFactory;
				}
			}
		}
		return null;
	}
	
}
