package de.unicorn.model;

import java.util.LinkedList;

public class ConnectionManager {
	private LinkedList<ConnectionFactory> factories;
	private ServerPortListener serverPortListener;
	private static String sessionName;
	private static int serverListenerPort;
	
	
	/*
	 *\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	 * \\\\	Simon, alle Attribute und Methoden des ConnectionManagers müssen statisch sein,	\\\\
	 * \\\\	sonst komme ich von meinen Klassen aus nicht an deine Methoden dran :D			\\\\
	 * \\\\	Steht unter anderem auch im UML Diagramm ;)										\\\\
	 * \\\\																					\\\\
	 * \\\\	Viele Grüße																		\\\\
	 * \\\\	Fabian :)																		\\\\
	 * \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	 */
	
	
	public static int getServerListenerPort() {
		return serverListenerPort;
	}

	public static void setServerListenerPort(int serverListenerP) {
		serverListenerPort = serverListenerP;
	}

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
	 * Gibt zur�ck, ob sich eine Connection (mit Name, IP und Port)
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
	 * (mit Name, IP und Port) zur�ck.
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

	public static String getSessionName() {
		return sessionName;
	}

	public static void setSessionName(String sessionN) {
		sessionName = sessionN;
	}
	
}
