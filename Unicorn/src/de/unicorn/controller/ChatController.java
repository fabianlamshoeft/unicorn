package de.unicorn.controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.unicorn.model.Connection;
import de.unicorn.model.Facade;
import de.unicorn.model.IFacadeObserver;
import de.unicorn.model.SyntaxChecker;
import de.unicorn.view.Chat;

public class ChatController implements IFacadeObserver{
	private Chat chat;
	private ArrayList<String> list = new ArrayList<>();
	private String letzterEmpf = null; 
	
	/**
	 * Konstruktor
	 * @param c
	 */
	public ChatController(Chat c) {
		this.chat = c;
		Facade.register(this);
	}

	/**
	 * Ermöglicht das korrekte Schließen der Anwendung und des Fensters 
	 * durch den <i>EXIT</i> Befehl.
	 */
	public void exitAndClose() {
		Facade.exit();
	}
	
	
	public void btnConnect() {								//\
		chat.getTextFeld().setText("CONNECT IP Port");		// \
	}														//  \
															//   \
	public void btnDisconnect() {							//    \
		chat.getTextFeld().setText("DISCONNECT");			//     \
	}														//      \
															//	
	public void btnExit() {									//	Wenn Button gedrückt (via ActionListener in Chat),
		chat.getTextFeld().setText("EXIT");					//	dann Text im TextFeld (Eingabzeile) auf in Methode
	}														//	angegbenen Text gesetzt.
															//
	public void btnM() {										//      /
		chat.getTextFeld().setText("M Name Text");			//     /
	}														//    /
															//   /
	public void btnMx() {									//  /
		chat.getTextFeld().setText("MX IP Port Text");		// /
	}														///
	
	/**
	 * Wenn <i> Senden </i> gedrückt wird, greift diese Methode. 
	 * Sie prüft, welcher Befehl zu beginn des Eingabestrings steht.
	 * Anschließend wird String durch split() in maximal 4 Elemente geteilt
	 * (Eingabebefehl, ggf. zugehörige Argumente, ggf. Textnachricht) und 
	 * in String[] gepackt. 
	 * </br>
	 * Die einzelnen Elemente werden auf Syntaxfehler geprüft. Wenn alles
	 * korrekt ist, werden die Elemente der zugehörige Methode aus 
	 * <i> Facade </i> zugewiesen.
	 * Das <i> textFeld </i> wird anschließend wieder auf den String "" zurückgesetzt.
	 * </br>
	 * Sollte kein Befehl an erster Stelle stehten, wird der Eingabestring
	 * automatisch an den zuletzt kontaktierten Peer gesendet.
	 * Sollte es sich dabei um die erste Nachricht handeln, erscheint ein
	 * Hinweis.
	 */
	public void btnOk() {
		String befehl = chat.getTextFeld().getText();
		if (befehl.startsWith("CONNECT")) {
			String [] befehlArgu = befehl.split(" ");
			if (SyntaxChecker.isWellFormedIpAddress(befehlArgu[1]) &&
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
			if (SyntaxChecker.isWellFormedIpAddress(befehlArgu[1]) &&
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
	
	/**
	 * Ruft das Interface <i> IFacadeObserver </i> auf, um <i>peers</i> JListe zu 
	 * aktualiesiern und neue Peers einzutragen.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		chat.getPeers().removeAll();
		chat.getPeers().setListData(Facade.getPeerList());
		chat.getPeers().repaint();
	}

	/**
	 * Ruft das Interface <i> IFacadeObserver </i> auf, um neue Nachrichten in 
	 * <i>nachrichten</i> (JList) einzutragen.
	 */
	@Override
	public void updateMessageHistory(Connection con) {
		list.add(con.getHistory().getLast());
		String [] realList = new String [list.size()];
		realList = list.toArray(realList);
		chat.getNachrichten().setListData(realList);	
	}	
}
