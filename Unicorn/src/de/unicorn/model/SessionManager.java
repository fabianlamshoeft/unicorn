package de.unicorn.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;

public class SessionManager {
	/**
	 * Liste von ConnectionFactories f�r den Fall, dass mehrere Peers sich gleichzeitig verbinden wollen.
	 */
	private static LinkedList<ConnectionFactory> factories = new LinkedList<>();
	private static ServerPortListener serverPortListener;
	/**
	 * Name des Peers
	 */
	private static String sessionName;
	/**
	 * IP-Adresse des Peers
	 */
	private static String ipAdress;
	/**
	 * Port des ServerSockets des Peers
	 */
	private static int serverPort;
	
	
	// Getter und Setter:
	public static String getSessionName() {
		return sessionName;
	}
	public static void setSessionName(String sessionN) {
		sessionName = sessionN;
	}

	public static String getIpAdress() {
		return ipAdress;
	}
	public static void setIpAdress(String ipAdress) {
		SessionManager.ipAdress = ipAdress;
	}
	
	public static int getServerListenerPort() {
		return serverPort;
	}
	public static void setServerListenerPort(int serverListenerP) {
		serverPort = serverListenerP;
	}
	
	public static ServerPortListener getServerPortListener() {
		return serverPortListener;
	}
	
	
	/**
	 * F�gt eine ConnectionFactory der Liste von Factories hinzu.
	 * @param fac hinzuzuf�gende ConnectionFactory
	 */
	public static void addFactory(ConnectionFactory fac)
	{
		factories.add(fac);
		
//		 Testausgabe
//		Iterator<ConnectionFactory> it = factories.iterator();
//		System.out.println("---------------------------");
//		while (it.hasNext()) {
//			System.out.println(it.next().getConnection().getPeerServerPort());
//		}
//		System.out.println("---------------------------");
	}
	/**
	 * Entfernt die �bergebene ConnectionFactory aus der Liste von Factories.
	 * @param fac zu entfernende Factory
	 */
	public static void removeFactory(ConnectionFactory fac)
	{
		factories.remove(fac);
		
		// Testausgabe
//		System.out.println("---------------------------");
//		Iterator<ConnectionFactory> it = factories.iterator();
//		while (it.hasNext()) {
//			System.out.println(it.next().getConnection().getPeerServerPort());
//		}
//		System.out.println("---------------------------");
	}
	
	/**
	 * Erstellt einen ServerPortListener und startet ihn.
	 */
	public static void startServerPortListener()
	{
		serverPortListener = new ServerPortListener();
		serverPortListener.start();
	}
	/**
	 * Beendet den ServerPortListener.
	 */
	public static void stopServerPortListener() {
		serverPortListener.close();
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
	public synchronized static boolean connectionInFactory(String ip, int port)
	{
		for (ConnectionFactory connectionFactory : factories)
		{
			if(connectionFactory.getConnection().equals(ip, port))
			{
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("resource")
	/**
	 * Sendet einen Poke an die mit den Parametern bestimmte Verbindung.
	 * @param ip IP-Adresse des Ziels
	 * @param port Port des Ziels
	 */
	public static void sendConnectionPoke(String ip, int port)
	{
		Socket poker = new Socket();
		try {
			poker = new Socket(ip, port);
			
			ConnectionFactory fac = new ConnectionFactory();
			fac.setFactoryData(ip, port, poker);
			
			addFactory(fac);
			
			OutputPort out = new OutputPort(poker);
			out.sendPoke();
			
		} catch (UnknownHostException e) {
			try {
				poker.close();
			} catch (IOException e1) {}
		} catch (IOException e) {
			try {
				poker.close();
			} catch (IOException e1) {}
		}
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
	public synchronized static ConnectionFactory getFactory(String ip, int port)
	{
		if(connectionInFactory(ip, port))
		{
			Iterator<ConnectionFactory> it = factories.iterator();
			while (it.hasNext()) {
				ConnectionFactory fac = it.next();
				if(fac.getConnection().equals(ip, port)) {
					return fac;
				}
				
			}
		}
		return null;
	}

	
	
}
