package de.unicorn.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import de.unicorn.model.Facade;
import de.unicorn.model.IFacadeObserver;
import de.unicorn.model.SyntaxChecker;
import de.unicorn.view.Chat;

public class ChatController implements IFacadeObserver{

	private Chat chat;
	
	
	public ChatController(Chat c) {
		this.chat = c;
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
			}
		} 
		else if (befehl.startsWith("DISCONNECT")) {
			Facade.disconnect();
		}
		else if (befehl.startsWith("EXIT")) {
			Facade.exit();
		}
		else if (befehl.startsWith("M")) {
			String [] befehlArgu = befehl.split(" ", 3);
			if (SyntaxChecker.isWellFormedSessionName(befehlArgu[1])) {
				Facade.sendMessage(befehlArgu[1], befehlArgu[2]);
			}
		}
		else if (befehl.startsWith("MX")) {
			String [] befehlArgu = befehl.split(" ", 4);
			if (SyntaxChecker.isWellFormedIpAdress(befehlArgu[1]) &&
				SyntaxChecker.isPortNumber(befehlArgu[2])) {
				int port = Integer.parseInt(befehlArgu[2]);
				Facade.sendMessage(befehlArgu[1], port, befehlArgu[3]);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Beginne die Eingabe mit einem Befehl aus der Befehlleiste.");
		}
	}
	


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	
}
