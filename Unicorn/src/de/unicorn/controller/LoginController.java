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
				JOptionPane.showMessageDialog(null, "Log In fehlgeschlagen. Bitte korrekte Daten eingeben.");
			}
		
		}
		// Fehlererkennung und richtige Fehlermeldung anzeigen:
		else
		{
			if(!SyntaxChecker.isWellFormedSessionName(login.textfieldName.getText()))
			{
				login.textfieldName.setText("");
				if(!SyntaxChecker.isPortNumber(login.textfieldPort.getText()))
				{
					login.textfieldPort.setText("");
					JOptionPane.showMessageDialog(null,
							"Der Name darf nicht leer sein oder Leerzeichen oder Zeilenumbrüche enthalten und die Portnummer muss zwischen 1024 und 65535 liegen!");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Der Name darf nicht leer sein oder Leerzeichen oder Zeilenumbrüche enthalten");
				}
			}
			else
			{
				login.textfieldPort.setText("");
				JOptionPane.showMessageDialog(null, "Der Port muss eine Zahl zwischen 1024 und 65535 sein!");
			}
		}


		

	}
	// fÃ¼r jeden Button eigene Methode login.
}
