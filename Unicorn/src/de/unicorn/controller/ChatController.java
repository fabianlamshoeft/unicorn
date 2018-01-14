package de.unicorn.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import de.unicorn.model.Connection;
import de.unicorn.model.Facade;
import de.unicorn.model.IFacadeObserver;
import de.unicorn.model.SyntaxChecker;
import de.unicorn.view.Chat;

public class ChatController implements IFacadeObserver{

	private Chat chat;
	private ArrayList<String> list = new ArrayList<>();
	
	private String letzterEmpf = null; 
	
	public ChatController(Chat c) {
		this.chat = c;
		Facade.register(this);
	}

	public void exitAndClose() {
		Facade.exit();
	}
	
	public void btnConnect() {
		chat.getTextFeld().setText("CONNECT IP Port");
	}
	
	public void btnDisconnect() {
		chat.getTextFeld().setText("DISCONNECT");
	}
	
	public void btnExit() {
		chat.getTextFeld().setText("EXIT");
	}
	
	public void btnM() {
		chat.getTextFeld().setText("M Name Text");
	}
	
	public void btnMx() {
		chat.getTextFeld().setText("MX IP Port Text");
	}
	
	public void btnOk() {
		String befehl = chat.getTextFeld().getText();
		if (befehl.startsWith("CONNECT")) {
			String [] befehlArgu = befehl.split(" ");
			if (SyntaxChecker.isWellFormedIpAdress(befehlArgu[1]) &&
				SyntaxChecker.isPortNumber(befehlArgu[2])) {
				int port = Integer.parseInt(befehlArgu[2]);
				Facade.connect(befehlArgu[1], port);
				chat.getTextFeld().setText("");
			}
		} 
		else if (befehl.startsWith("DISCONNECT")) {
			Facade.disconnect();
			chat.getTextFeld().setText("");
		}
		else if (befehl.startsWith("EXIT")) {
			Facade.exit();
			chat.getTextFeld().setText("");
			chat.dispose();
			System.exit(0);
		}
		else if (befehl.startsWith("MX")) {
			letzterEmpf = befehl;
			String [] befehlArgu = befehl.split(" ", 4);
			if (SyntaxChecker.isWellFormedIpAdress(befehlArgu[1]) &&
				SyntaxChecker.isPortNumber(befehlArgu[2])) {
				int port = Integer.parseInt(befehlArgu[2]);
				Facade.sendMessage(befehlArgu[1], port, befehlArgu[3]);
				chat.getTextFeld().setText("");
			}
		}
		else if (befehl.startsWith("M")) {
			letzterEmpf = befehl;
			String [] befehlArgu = befehl.split(" ", 3);
			if (SyntaxChecker.isWellFormedSessionName(befehlArgu[1])) {
				Facade.sendMessage(befehlArgu[1], befehlArgu[2]);
				chat.getTextFeld().setText("");
			}
		}
		else {
			if (letzterEmpf == null) {
				JOptionPane.showMessageDialog(null, "Beginne die Eingabe mit einem Befehl aus der Befehlleiste.");
			}
			else if (letzterEmpf.startsWith("MX")) {
				String [] letzterEmpfArr = letzterEmpf.split(" ", 4);
				if (SyntaxChecker.isWellFormedIpAdress(letzterEmpfArr[1]) &&
					SyntaxChecker.isPortNumber(letzterEmpfArr[2])) {
					int port = Integer.parseInt(letzterEmpfArr[2]);
					Facade.sendMessage(letzterEmpfArr[1], port, chat.getTextFeld().getText());
					chat.getTextFeld().setText("");
				}
			}
			else if (letzterEmpf.startsWith("M")) {
				String [] letzterEmpfArr = letzterEmpf.split(" ", 3);
				if (SyntaxChecker.isWellFormedSessionName(letzterEmpfArr[1])) {
					Facade.sendMessage(letzterEmpfArr[1], chat.getTextFeld().getText());
					chat.getTextFeld().setText("");
				}
			}
		}
	}
	
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		chat.getPeers().removeAll();
		chat.getPeers().setListData(Facade.getPeerList());
		chat.getPeers().repaint();
	}

	@Override
	public void updateMessageHistory(Connection con) {
		list.add(con.getHistory().getLast());
		String [] realList = new String [list.size()];
		realList = list.toArray(realList);
		
		
		chat.getNachrichten().setListData(realList);
		
	}
	
	
	
}
