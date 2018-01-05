package de.unicorn.model;

import java.util.LinkedList;

public class ConnectionManager {
	private LinkedList<ConnectionFactory> factories;
	private ServerPortListener serverPortListener;
	private String sessionName;
	
	public void addFactory(ConnectionFactory fac)
	{
		factories.add(fac);
	}
	
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
				
			}
		}
		return true;
	}
	
	/**
	 * 
	 */
	public void sendConnectionPoke()
	{
		
	}
	
	/**
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
