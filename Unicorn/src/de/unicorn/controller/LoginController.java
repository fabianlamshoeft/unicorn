package de.unicorn.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import de.unicorn.model.Facade;
import de.unicorn.model.SyntaxChecker;
import de.unicorn.view.Chat;
import de.unicorn.view.Login;

public class LoginController {
	
	private Login login;
	
	public LoginController(Login l) {
		this.login = l;
	}
	
	public void buttonOK () {
		if (SyntaxChecker.isWellFormedSessionName(login.textfieldName.getText()) && 
			SyntaxChecker.isNummeric(login.textfieldPort.getText())) {
			
			try {
				Facade.startUp(login.textfieldName.getText(), InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(login.textfieldPort.getText()));
				Chat neuesFenster = new Chat();
				neuesFenster.newScreen();
				
				login.window.setVisible(false);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Bitte neuen Port angeben.");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Bitte korrekte Daten angeben!");
			if (!SyntaxChecker.isWellFormedSessionName(login.textfieldName.getText())) {
				login.textfieldName.setText("");
			}
			if (!SyntaxChecker.isNummeric(login.textfieldPort.getText())) {
				login.textfieldPort.setText("");
			}
		}

	}
	// für jeden Button eigene Methode login.
}
