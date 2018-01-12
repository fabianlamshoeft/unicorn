package de.unicorn.controller;

import java.net.InetAddress;
import java.util.Scanner;

import de.unicorn.model.Facade;
import de.unicorn.model.IFacadeObserver;

public class TerminalController extends Thread implements IFacadeObserver{
	
	private boolean isRunning = true;
	
	public void run() {
		
		System.out.println("Willkommen bei Unicorn 1.0!");
		System.out.println("Bitte geben Sie Ihren Session Namen an:");
		Scanner s = new Scanner(System.in);
		
		String name;
		String ip;
		String port;
		
		name = s.nextLine();
		
		System.out.println("Bitte geben Sie Ihren Port an, unter dem Sie erreichbar sein wollen:");
		
		port = s.nextLine();
		
//		System.out.println("Bitte geben Sie Ihre IP-Adresse an, unter der Sie erreichbar sind:");
//		
//		ip = s.nextLine();
		
		System.out.println("------------------------- READY -------------------------");
		
		try {
			Facade.startUp(name, InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(port));
			
			while(isRunning) {
				
				String entry;
				
				entry = s.nextLine();
				
				if (entry.startsWith("CONNECT")) {
					
					String[] a = entry.split(" ");
					System.out.println("Sending POKE...");
					Facade.connect(a[1], Integer.parseInt(a[2]));
					
					
				}else if(entry.startsWith("M")) {
					
					String[] a = entry.split(" ", 3);
					System.out.println("Sending Message...");
					String message = a[2];
					Facade.sendMessage(a[1], message);
					
				}else if(entry.startsWith("MX")) {
					
					String[] a = entry.split(" ", 4);
					System.out.println("Sending Message...");
					Facade.sendMessage(a[1], Integer.parseInt(a[2]), a[3]);
					
					
				}else if (entry.startsWith("DISCONNECT")) {
					Facade.disconnect();
				}else if (entry.startsWith("EXIT")) {
					Facade.exit();
					isRunning = false;
				}
				
				
			}
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
