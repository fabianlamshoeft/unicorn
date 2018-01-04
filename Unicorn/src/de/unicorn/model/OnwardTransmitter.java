package de.unicorn.model;
/**
 * OnwardTransmitter stellt eine Reihe von Methoden zur Verfügung, mit dessen Hilfe 
 * Poke und Disconnect Nachrichten an alle bekannten Peers weitergeleitet werden kann.
 * @author fabian
 *
 */
public class OnwardTransmitter {
    /**
     * Leitet eine Poke Nachricht an alle bekannten Peers weiter, welche ungleich dem ursprünglichen
     * Absender der Pokenachricht sowie dem letzten Absender der Pokenachricht sind.
     * @param conn Verbindung, welche die Pokenachricht gesendet bzw. weitergeleitet hat.
     * @param pokeName
     * @param pokeIp
     * @param pokePort f
     */
    public void forwardPushMessage(Connection conn, String pokeName, String pokeIp, int pokePort) {

    }

    public void forwardDeisconnectMessage(Connection conn, String discName, String discIp, int discPort) {

    }
}