package de.unicorn.model;

import java.util.LinkedList;

public class ConnectionManager {
	private LinkedList<ConnectionFactory> factories;
	private ServerPortListener serverPortListener;
	private String sessionName;
	
	/**
	 * 
	 * @param fac
	 */
	public void addFactory(ConnectionFactory fac)
	{
		factories.add(fac);
	}
	
	/**
	 * 
	 * @param fac
	 */
	public void removeFactory(ConnectionFactory fac)
	{
		factories.remove(fac);
	}
	
	/**
	 * Erstellt einen ServerPortListener und startet ihn.
	 */
	public void startServerPortListener()
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
	public boolean connectionInFactory(String ip, int port)
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
	public ConnectionFactory getFactory( String ip, int port)
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
	public void sendConnectionPoke(String ip, int port)
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
	 * aktuell in einer Factory befindet, d.h. aktuell erstellt wird.
	 * 
	 * @param name
	 * @param ip
	 * @param port
	 * @return true, wenn Connection in Factory
	 */
	public boolean connectionInFactory(String name, String ip, int port)
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
	 * (mit Name, IP und Port) zurück.
	 * 
	 * @param name
	 * @param ip
	 * @param port
	 * @return Factory mit gegebener Connection
	 */
	public ConnectionFactory getFactory(String name, String ip, int port)
	{
		if(connectionInFactory(name, ip, port))
		{
			
		}
		return null;
	}
}
