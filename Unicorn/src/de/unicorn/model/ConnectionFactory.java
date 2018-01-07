package de.unicorn.model;

import java.net.Socket;


/**
 * Ist zuständig für die Erstellung einer Connection bei eingehendem als auch ausgehendem ConnectionPoke.
 * 
 * 
 * @author Simon
 *
 */

public class ConnectionFactory {
	private Connection conn;
	private Thread timer;
	private long creationTime;
	
	public ConnectionFactory(String ip, int port, Socket in)
	{
		
	}
	
	public ConnectionFactory(String name, String ip, int port, Socket in)
	{
		
	}
	
	public Connection getConnection()
	{
		return conn;
	}
	
	public void create()
	{
		
	}
	
	public void create(String name)
	{
		
	}
	
	private void startTimer()
	{
		
	}
	
}
