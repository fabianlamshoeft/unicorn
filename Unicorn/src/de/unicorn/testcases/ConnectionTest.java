package de.unicorn.testcases;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.unicorn.model.Connection;
import de.unicorn.model.InputPort;
import de.unicorn.model.OutputPort;

class ConnectionTest {

	private Connection conn;
	private Thread testClient;
	
	@Test
	void test() throws Exception {
testClient = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Socket clientOut = new Socket(InetAddress.getLocalHost(), 1234);
					
					// Sende Pokenachricht
					
					PrintWriter printWriter = new PrintWriter(clientOut.getOutputStream());
					printWriter.println("POKE Gustaf " + InetAddress.getLocalHost().getHostAddress() + " 1235");
					printWriter.flush();
					System.out.println("Poke send");
					ServerSocket clientServerSocket = new ServerSocket(1235);
					Socket clientIn = clientServerSocket.accept();
					
					BufferedReader br = new BufferedReader(new InputStreamReader(clientIn.getInputStream()));
					System.out.println(br.readLine());
					
					// Richtige Nachricht
					System.out.println("- - - - - - - -");
					System.out.println("SENDE GÜLTIGE NACHRICHT ...");
					System.out.println("MESSAGE Gustaf " + clientOut.getLocalAddress().getHostAddress() + " 1235 Hallo Heinz, hier ist Gustaf!");
					System.out.println("- - - - - - - -");
					printWriter.println("MESSAGE Gustaf " + clientOut.getLocalAddress().getHostAddress() + " 1235 Hallo Heinz, hier ist Gustaf!");
					printWriter.flush();
					
					Thread.sleep(1000);
					// Nachricht mit falscher IP
					System.out.println("- - - - - - - -");
					System.out.println("SENDE NACHRICHT MIT FALSCHER IP");
					System.out.println("MESSAGE Gustaf " + "22.22.22.22" + " 1235 Hallo Heinz, hier ist Gustaf!");
					System.out.println("- - - - - - - -");
					printWriter.println("MESSAGE Gustaf " + "22.22.22.22" + " 1235 Hallo Heinz, hier ist Gustaf!");
					printWriter.flush();
					Thread.sleep(1000);
					// Nachricht mit falschem Port
					System.out.println("- - - - - - - -");
					System.out.println("SENDE NACHRICHT MIT FALSCHEM PORT");
					System.out.println("MESSAGE Gustaf " + clientOut.getLocalAddress().getHostAddress() + " 1444 Hallo Heinz, hier ist Gustaf!");
					System.out.println("- - - - - - - -");
					printWriter.println("MESSAGE Gustaf " + clientOut.getLocalAddress().getHostAddress() + " 1444 Hallo Heinz, hier ist Gustaf!");
					printWriter.flush();
					Thread.sleep(1000);
					// Nachricht mit falschem Namen
					System.out.println("- - - - - - - -");
					System.out.println("SENDE NACHRICHT MIT FALSCHEM NAMEN");
					System.out.println("MESSAGE Horst " +  clientOut.getLocalAddress().getHostAddress()  + " 1235 Hallo Heinz, hier ist Horst!");
					System.out.println("- - - - - - - -");
					printWriter.println("MESSAGE Horst " +  clientOut.getLocalAddress().getHostAddress()  + " 1235 Hallo Heinz, hier ist Horst!");
					printWriter.flush();
					Thread.sleep(1000);
					// Unbekannter Nachrichtentyp
					System.out.println("- - - - - - - -");
					System.out.println("SENDE UNBEKANNTEN NACHRICHTENTYPEN");
					System.out.println("Hallo Heinz, ich fürchte ich komme mit dieser Nachricht nicht durch :(");
					System.out.println("- - - - - - - -");
					printWriter.println("Hallo Heinz, ich fürchte ich komme mit dieser Nachricht nicht durch :(");
					printWriter.flush();
					Thread.sleep(1000);
					
					printWriter.close();
					br.close();
					clientOut.close();
					clientIn.close();
					
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		Thread ConnectionIniziator = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ServerSocket testServerSocket = new ServerSocket(1234);
					System.out.println("Server oben");
					Socket in = testServerSocket.accept();
					System.out.println("connected");
					BufferedReader reader = new BufferedReader(new InputStreamReader(in.getInputStream()));
					
					String pokeMsg = reader.readLine();
					System.out.println("Poke empfangen");
					String [] args = pokeMsg.split(" ");
					
					Thread.sleep(100);
					
					conn = new Connection();
					conn.setIn(new InputPort(in, conn));
					conn.setName(args[1]);
					conn.setPeerServerPort(Integer.parseInt(args[3]));
					
					conn.setOut(new OutputPort(new Socket(InetAddress.getLocalHost(), 1235)));
					conn.getOut().sendPoke("Heinz", InetAddress.getLocalHost().getHostAddress(), 1234);
					
					conn.getIn().startListener();
					
					Thread.sleep(6000);
					
					conn.getIn().close();
					conn.getOut().close();
					System.out.println("Fertig");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		ConnectionIniziator.start();
		System.out.println("CI Start");
		testClient.start();
		System.out.println("Cleint Start");
		Thread.sleep(10000);
	}

	

}
