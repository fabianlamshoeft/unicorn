package de.unicorn.controller;

import java.net.InetAddress;

import javax.swing.JOptionPane;

import de.unicorn.model.Facade;
import de.unicorn.model.SyntaxChecker;
import de.unicorn.view.Chat;
import de.unicorn.view.Login;

public class LoginController {
	
	private Login login;
	/**
	 * Konstruktor von LoginController
	 * @param l
	 */
	public LoginController(Login l) {
		this.login = l;
	}
	/**
	 * Kontrolliert, ob Syntax von in textfieldName und textfieldPort (aus Login)
	 * eingegebenem Namen und Port korrekt ist.
	 */
	public void buttonOK () {
		// wenn keine Syntaxfehler erkannt wurden, werden Name, IP und Port an Facade.startUp gegeben und Chat öffnet sich
		if (SyntaxChecker.isWellFormedSessionName(login.textfieldName.getText())
				&& SyntaxChecker.isPortNumber(login.textfieldPort.getText()))
		{
			try {
				Facade.startUp(login.textfieldName.getText(), InetAddress.getLocalHost().getHostAddress(), Integer.parseInt(login.textfieldPort.getText()));
				Chat.newScreen();
				
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
							"<html>Der Name darf nicht leer sein oder Leerzeichen oder Zeilenumbr&uuml;che enthalten und die Portnummer muss zwischen 1024 und 65535 liegen!</html>");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "<html>Der Name darf nicht leer sein oder Leerzeichen oder Zeilenumbr&uuml;che enthalten</html>");
				}
			}
			else
			{
				login.textfieldPort.setText("");
				JOptionPane.showMessageDialog(null, "Der Port muss eine Zahl zwischen 1024 und 65535 sein!");
			}
		}
	}
}
