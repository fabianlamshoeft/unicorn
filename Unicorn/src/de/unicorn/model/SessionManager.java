package de.unicorn.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;

public class SessionManager {
	private static LinkedList<ConnectionFactory> factories = new LinkedList<>();
	private static ServerPortListener serverPortListener;
	private static String sessionName;
	private static int serverPort;
	private static String ipAdress;
	
	public static int getServerListenerPort() {
		return serverPort;
	}

	public static void setServerListenerPort(int serverListenerP) {
		serverPort = serverListenerP;
	}

	public static void addFactory(ConnectionFactory fac)
	{
		factories.add(fac);
	}
	
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
		serverPortListener.start();;
	}
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
	
	/**
	 * 
	 */
	@SuppressWarnings("resource")
	public static void sendConnectionPoke(String ip, int port)
	{
		Socket poker = new Socket();
		try {
			poker = new Socket(ip, port);
			OutputPort out = new OutputPort(poker);
			out.sendPoke();
			
			addFactory(new ConnectionFactory(ip, port, poker));
			
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
	
}
