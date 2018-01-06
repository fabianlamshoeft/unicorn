package de.unicorn.model;

import java.util.LinkedList;

public class Facade {
	
	private static LinkedList<IFacadeObserver> obsList = new LinkedList<IFacadeObserver>();
	
	public static void startUp() {
		
	}
	
	public static void connect(String ip, int port) {
		
	}
	
	public static void sendMessage(String name, String message) {
		
	}
	
	public static void sendMessage(String ip, int port, String message) {
		
	}
	
	public static String[] getPeerList() {
		return null;
	}
	
	public static LinkedList<String> getMessageHistory(String name, String ip, int port){
		return null;
	}
	
	public static void disconnect() {
		
	}
	
	public static void exit() {
		
	}
	
	public static void register(IFacadeObserver obs) {
		
	}
	
	public static void unregister(IFacadeObserver obs) {
		
	}
	
	public static void notifyObservers(){
		
	}
}
