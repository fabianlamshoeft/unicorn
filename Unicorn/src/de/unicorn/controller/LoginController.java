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
		if (SyntaxChecker.isWellFormedSessionName(login.textfieldName.getText())
				&& SyntaxChecker.isPortNumber(login.textfieldPort.getText()))
		{
			try {
				Facade.startUp(login.textfieldName.getText(), InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(login.textfieldPort.getText()));
				Chat neuesFenster = new Chat();
				neuesFenster.newScreen();
				
				login.window.setVisible(false);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Bitte korrekte Daten angeben");
			}
		
		}
		// Fehlererkennung und richtige Fehlermeldung anzeigen:
		else
		{
			if(!SyntaxChecker.isWellFormedSessionName(login.textfieldName.getText()))
			{
				login.textfieldName.setText("");
				JOptionPane.showMessageDialog(null, "Bitte korrekten Namen angeben!");
			}
			
			if(!SyntaxChecker.isPortNumber(login.textfieldPort.getText()))
			{
				if(!SyntaxChecker.isNummeric(login.textfieldPort.getText()))
				{
					JOptionPane.showMessageDialog(null, "Der Port muss eine Zahl sein!");
					login.textfieldPort.setText("");
				}
				else
				// Ist ein Zahl außerhalb der erlaubten Grenze
				{
					JOptionPane.showMessageDialog(null, "Der Port muss eine Zahl zwischen 1024 und 49151 sein!");
					login.textfieldPort.setText("");
				}
				
				
			}
			
			
		}
		
		
		
//		if (SyntaxChecker.isWellFormedSessionName(login.textfieldName.getText()) && 
//			SyntaxChecker.isNummeric(login.textfieldPort.getText()) &&
//			Integer.parseInt(login.textfieldPort.getText()) > 1023 &&
//			Integer.parseInt(login.textfieldPort.getText()) < 49152) {
//			
//			try {
//				Facade.startUp(login.textfieldName.getText(), InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(login.textfieldPort.getText()));
//				Chat neuesFenster = new Chat();
//				neuesFenster.newScreen();
//				
//				login.window.setVisible(false);
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null, "Bitte korrekte Daten angeben");
//			}
//		}
//		else {
////			JOptionPane.showMessageDialog(null, "Bitte korrekte Daten angeben!");
//			if (!SyntaxChecker.isWellFormedSessionName(login.textfieldName.getText())) {
//				login.textfieldName.setText("");
//				JOptionPane.showMessageDialog(null, "Bitte korrekten Namen angeben!");
//			}
//			if (SyntaxChecker.isNummeric(login.textfieldPort.getText()) &&
//				!(Integer.parseInt(login.textfieldPort.getText()) > 1023) &&
//				!(Integer.parseInt(login.textfieldPort.getText()) < 49152)) {
//				JOptionPane.showMessageDialog(null, "Der Port muss eine Zahl zwischen 1024 und 49151");
//				login.textfieldPort.setText("");
//			}
//		}

	}
	// fÃ¼r jeden Button eigene Methode login.
}
