package de.unicorn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.unicorn.controller.LoginController;

public class Chat extends JFrame {

	public Chat() throws HeadlessException {
		setUpElements();
	}

	public Chat(GraphicsConfiguration arg0) {
		super(arg0);
		setUpElements();
	}

	public Chat(String arg0) throws HeadlessException {
		super(arg0);
		setUpElements();
	}

	public Chat(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		setUpElements();
	}
	
	public void newScreen() {
		JFrame window = new Chat();
		Toolkit t = Toolkit.getDefaultToolkit(); 
		Dimension d = t.getScreenSize(); 
		int x = (int) (d.getWidth() - WIDTH) / 2 ;				// d.getWidth() gesamte Bildschirmbreite
		int y = (int) (d.getHeight() - HEIGHT) / 2;				// Zentrum des Bildschirms berechnen
		
		
		window.setTitle("Chat");	
		window.setSize(new Dimension(1000, 750));
		window.setMinimumSize(new Dimension(400, 300));
		window.setBounds(x, y, 1000, 750);
		//window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private  void setUpElements() {
		
		Container content = this.getContentPane();

		content.setBackground(Color.decode("#B2CCDE"));
		content.setLayout(new BorderLayout());
		
		JTextField textFeld = new JTextField(30);
		//textFeld.setSize(textFeld.getPreferredSize());
		//textFeld.setForeground(Color.decode("1E647F"));
		textFeld.requestFocusInWindow();
		content.add(textFeld, BorderLayout.SOUTH); 
		
		JButton btnConnect 		= new JButton("CONNECT <IP><Port>");
		JButton btnDisconnect	= new JButton("DISCONNECT");
		JButton btnExit 			= new JButton("EXIT");
		JButton btnM	 			= new JButton("M <Name><Text>");
		JButton btnMx 			= new JButton("MX <IP><Port><Text>");
		btnConnect.setSize(new Dimension(150,20));
		btnConnect.setPreferredSize(new Dimension(150,20));
		btnConnect.setMinimumSize(new Dimension(150,20));
		btnConnect.setMaximumSize(new Dimension(150,20));
		btnDisconnect.setSize(new Dimension(150,20));
		btnDisconnect.setPreferredSize(new Dimension(150,20));
		btnDisconnect.setMinimumSize(new Dimension(150,20));
		btnDisconnect.setMaximumSize(new Dimension(150,20));
		btnExit.setPreferredSize(new Dimension(150, 20));
		btnExit.setPreferredSize(new Dimension(150,20));
		btnExit.setMinimumSize(new Dimension(150,20));
		btnExit.setMaximumSize(new Dimension(150,20));
		btnM.setPreferredSize(new Dimension(150, 20));
		btnM.setPreferredSize(new Dimension(150,20));
		btnM.setMinimumSize(new Dimension(150,20));
		btnM.setMaximumSize(new Dimension(150,20));
		btnMx.setPreferredSize(new Dimension(150,20));
		btnMx.setPreferredSize(new Dimension(150,20));
		btnMx.setMinimumSize(new Dimension(150,20));
		btnMx.setMaximumSize(new Dimension(150,20));
		
		btnConnect.setToolTipText("Sende eine POKE-Nachricht mit den eigenen Daten an den unter IP/Port erreichbaren Peer." );
		btnDisconnect.setToolTipText("Sende eine DISCONNECT-Nachricht mit den eigenen Daten an alle aktiven Peers und entferne diese aus der Peer-Liste.");
		btnExit.setToolTipText("Sende eine DISCONNECT-Nachricht mit den eigenen Daten an alle bekannten Peers und beende den Client.");
		btnM.setToolTipText("Sende Text als MESSAGE -Nachricht an alle bekannten Peers, die sich mit Name identizieren.");
		btnMx.setToolTipText("Sende Text als MESSAGE -Nachricht an den durch IP und Port eindeutig identizierten Peer.");
		
		JLabel ipAdresse = new JLabel("<html>Meine IP-Adresse: </html>");
		//ipAdresse.setSize(ipAdresse.getPreferredSize());
		ipAdresse.setForeground(Color.decode("#1E647F"));
		ipAdresse.setBackground(Color.decode("#ECE198"));
		content.add(ipAdresse, BorderLayout.NORTH);
		
		JPanel peers = new JPanel();
		//peers.setSize(peers.getPreferredSize());
		peers.setBackground(Color.decode("#B2CCDE"));
		content.add(peers, BorderLayout.WEST);
		
		JPanel chatbereich = new JPanel();
		//chatbereich.setSize(chatbereich.getPreferredSize());
		//chatbereich.setHorizontalAlignment(JPanel.CENTER);
		chatbereich.setBackground(Color.decode("#B2CCDE"));
		content.add(chatbereich, BorderLayout.CENTER);
		chatbereich.setLayout(new BoxLayout(chatbereich, BoxLayout.Y_AXIS));
		chatbereich.add(new JLabel("Nachrichten"));
		chatbereich.add(textFeld);
		
		JPanel befehle = new JPanel();
		befehle.setSize(befehle.getPreferredSize());
		befehle.setBackground(Color.decode("#B2CCDE"));
		content.add(befehle, BorderLayout.EAST);
		befehle.setLayout(new BoxLayout(befehle, BoxLayout.Y_AXIS));
		befehle.add(btnConnect);
		befehle.add(btnDisconnect);
		befehle.add(btnExit);
		befehle.add(btnM);
		befehle.add(btnMx);
		
		
		
	}
}
