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
	
	public void startServerPortListener()
	{
		serverPortListener = new ServerPortListener();
		serverPortListener.runServerPortListener();
	}
	
	public boolean connectionInFactory(String ip, int port)
	{
		return true;
	}
	
	public boolean connectionInFactory(String name, String ip, int port)
	{
		return true;
	}
	
	public void sendConnectionPoke()
	{
		
	}
	
	public ConnectionFactory getFactory(String ip, int port)
	{
		if(connectionInFactory(ip, port))
		{
			for (ConnectionFactory factory : factories) 
			{
				if(factory.getConnection() == null)
				{

				}
			}
		}
		return null;
	}
	
	public ConnectionFactory getFactory(String name, String ip, int port)
	{
		return null;
	}
	
}
