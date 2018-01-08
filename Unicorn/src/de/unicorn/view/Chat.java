package de.unicorn.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
	
	static void NewScreen() {
		JFrame window = new Chat();
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private  void setUpElements() {
		this.setSize(800, 600);
		
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		
		JLabel ipAdresse = new JLabel("Meine IP-Adresse: ");
		content.add(ipAdresse, BorderLayout.PAGE_START);
		
		JLabel peers = new JLabel("");
		content.add(peers, BorderLayout.WEST);
		
		JLabel nachrichten = new JLabel("");
		content.add(nachrichten, BorderLayout.CENTER);
		
		JLabel befehle = new JLabel("");
		content.add(befehle, BorderLayout.EAST);
		
		JTextField textFeld = new JTextField();
		textFeld.setSize(textFeld.getPreferredSize());
	}
}
