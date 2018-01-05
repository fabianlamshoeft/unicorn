package de.unicorn.model;

import java.util.Iterator;

/**
 * OnwardTransmitter stellt eine Reihe von Methoden zur Verfügung, mit dessen Hilfe 
 * Poke und Disconnect Nachrichten an alle bekannten Peers weitergeleitet werden kann.
 * @author fabian
 *
 */
public class OnwardTransmitter {
    /**
     * Leitet eine Poke Nachricht an alle bekannten Peers weiter, welche ungleich dem urspr�nglichen
     * Absender der Pokenachricht sowie dem letzten Absender der Pokenachricht sind.
     * @param conn Verbindung, welche die Pokenachricht gesendet bzw. weitergeleitet hat.
     * @param pokeName
     * @param pokeIp
     * @param pokePort f
     */
    public void forwardPokeMessage(Connection conn, String pokeName, String pokeIp, int pokePort) {
    	
    	Iterator <Connection> it = ConnectionRegistry.getIterator();
    	
    	while (it.hasNext()) {
    		
    		Connection c = it.next();
    		if (c.equals(c) || (c.getName().equals(pokeName) && c.getIP().equals(pokeIp) && c.getPeerServerPort()== pokePort)){
    			
    			c.getOut().sendPoke(pokeName, pokeIp, pokePort);
    			
    		}
    		
    	}
    	
    	
    }

    /**
     * Leitet eine Disconnect Nachricht an alle bekannten Peers weiter, welche ungleich dem urspr�nglichen
     * Absender der Pokenachricht sowie dem letzten Absender der Disconnect Nachricht sind.
     * @param conn Verbindung, welche die Disconnect Nachricht empfangen hat
     * @param discName Name des ursprünglichen Senders
     * @param discIp IP des ursprünglichen Senders
     * @param discPort Port des ursprünglichen Senders
     */
    
    public void forwardDeisconnectMessage(Connection conn, String discName, String discIp, int discPort) {
    	Iterator <Connection> it = ConnectionRegistry.getIterator();
    	
    	while (it.hasNext()) {
    		
    		Connection c = it.next();
    		if (c.equals(c) || (c.getName().equals(discName) && c.getIP().equals(discIp) && c.getPeerServerPort()== discPort)){
    			
    			c.getOut().sendPoke(discName, discIp, discPort);
    			
    		}
    		
    	}
    }
}