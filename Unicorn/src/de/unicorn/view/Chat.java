package de.unicorn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.unicorn.controller.ChatController;
import de.unicorn.controller.LoginController;
import de.unicorn.model.Facade;

public class Chat extends JFrame {
	
	private ChatController controller = new ChatController(this);
	private JTextField textFeld = new JTextField(30);
	
	public JTextField getTextFeld() {
		return textFeld;
	}

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
		
		window.setTitle("Chat");	
		//window.setSize(new Dimension(1000, 750));
		window.setMinimumSize(new Dimension(400, 300));
		
		
		Toolkit t = Toolkit.getDefaultToolkit(); 
		Dimension d = t.getScreenSize(); 
		int x = (int) (d.getWidth() - 1000) / 2 ;				// d.getWidth() gesamte Bildschirmbreite
		//int y = (int) (d.getHeight() - 750) / 2;					// Zentrum des Bildschirms berechnen
		window.setBounds(x, 0, 1000, 750);
		
		//window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private  void setUpElements() {
		
		Container content = this.getContentPane();

		content.setBackground(Color.decode("0xFFFFFF"));
		content.setLayout(new BorderLayout());
		
		
		//textFeld.setSize(textFeld.getPreferredSize());
		Color c = Color.decode("0x1E647F");
		textFeld.setForeground(c);
		textFeld.requestFocusInWindow();
		textFeld.setSize(new Dimension(700, 30));
		textFeld.setPreferredSize(new Dimension(700, 30));
		textFeld.setMinimumSize(new Dimension(700, 30));
		textFeld.setMaximumSize(new Dimension(700, 30));
		
		content.add(textFeld, BorderLayout.SOUTH); 
		
		
		JButton btnConnect 		= new JButton("<html>CONNECT <i>IP</i> <i>Port</i></html");
		JButton btnDisconnect	= new JButton("DISCONNECT");
		JButton btnExit 			= new JButton("EXIT");
		JButton btnM	 			= new JButton("<html>M <i>Name</i> <i>Text</i></html>");
		JButton btnMx 			= new JButton("<html>MX <i>IP</i> <i>Port</i> <i>Text</i></html>");
		JButton btnOk 			= new JButton("SENDEN");
		
		btnConnect.setSize(new Dimension(170,40));
		btnConnect.setPreferredSize(new Dimension(170,40));
		btnConnect.setMinimumSize(new Dimension(170,40));
		btnConnect.setMaximumSize(new Dimension(170,40));
		btnConnect.setFont(new Font("Arial", Font.PLAIN, 14));
		btnConnect.setForeground(Color.decode("0x1E647F"));
		btnConnect.setBackground(Color.decode("0xFBE7A8"));
		btnConnect.setToolTipText("Sende eine POKE-Nachricht mit den eigenen Daten an den unter IP/Port erreichbaren Peer." );
		
		btnDisconnect.setSize(new Dimension(170,40));
		btnDisconnect.setPreferredSize(new Dimension(170,40));
		btnDisconnect.setMinimumSize(new Dimension(170,40));
		btnDisconnect.setMaximumSize(new Dimension(170,40));
		btnDisconnect.setFont(new Font("Arial", Font.PLAIN, 14));
		btnDisconnect.setForeground(Color.decode("0x1E647F"));
		btnDisconnect.setBackground(Color.decode("0xFBE7A8"));
		btnDisconnect.setToolTipText("Sende eine DISCONNECT-Nachricht mit den eigenen Daten an alle aktiven Peers und entferne diese aus der Peer-Liste.");
		
		btnExit.setSize(new Dimension(170, 40));
		btnExit.setPreferredSize(new Dimension(170,40));
		btnExit.setMinimumSize(new Dimension(170,40));
		btnExit.setMaximumSize(new Dimension(170,40));
		btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
		btnExit.setForeground(Color.decode("0x1E647F"));
		btnExit.setBackground(Color.decode("0xFBE7A8"));
		btnExit.setToolTipText("Sende eine DISCONNECT-Nachricht mit den eigenen Daten an alle bekannten Peers und beende den Client.");
		
		btnM.setSize(new Dimension(170, 40));
		btnM.setPreferredSize(new Dimension(170,40));
		btnM.setMinimumSize(new Dimension(170,40));
		btnM.setMaximumSize(new Dimension(170,40));
		btnM.setFont(new Font("Arial", Font.PLAIN, 14));
		btnM.setForeground(Color.decode("0x1E647F"));
		btnM.setBackground(Color.decode("0xFBE7A8"));
		btnM.setToolTipText("Sende Text als MESSAGE -Nachricht an alle bekannten Peers, die sich mit Name identizieren.");
		
		btnMx.setSize(new Dimension(170,40));
		btnMx.setPreferredSize(new Dimension(170,40));
		btnMx.setMinimumSize(new Dimension(170,40));
		btnMx.setMaximumSize(new Dimension(170,40));
		btnMx.setFont(new Font("Arial", Font.PLAIN, 14));
		btnMx.setForeground(Color.decode("0x1E647F"));
		btnMx.setBackground(Color.decode("0xFBE7A8"));
		btnMx.setToolTipText("Sende Text als MESSAGE -Nachricht an den durch IP und Port eindeutig identizierten Peer.");
		
		btnOk.setSize(new Dimension(170,40));
		btnOk.setPreferredSize(new Dimension(170,40));
		btnOk.setMinimumSize(new Dimension(170,40));
		btnOk.setMaximumSize(new Dimension(170,40));
		btnOk.setFont(new Font("Arial", Font.BOLD, 14));
		btnOk.setForeground(Color.decode("0x1E647F"));
		btnOk.setBackground(Color.decode("0xFBE7A8"));
		
		
		JLabel ipAdresse = new JLabel("Meine IP-Adresse: " + Facade.getIp());
		ipAdresse.getIconTextGap();
		ipAdresse.setAlignmentX(LEFT_ALIGNMENT);
		//ipAdresse.setBounds(100, 100, WIDTH, 400);
		ipAdresse.setForeground(Color.decode("0x1E647F"));
		ipAdresse.setBackground(Color.decode("0x1E647F"));
		ipAdresse.setFont(new Font("Arial", Font.PLAIN, 14));

		JPanel platzhalter = new JPanel();
		//platzhalter.setLayout(new BoxLayout(platzhalter, BoxLayout.X_AXIS));
		platzhalter.setAlignmentX(LEFT_ALIGNMENT);
		platzhalter.getSize(new Dimension(WIDTH, 400));
		platzhalter.setBounds(100, 100, WIDTH, 400);
		platzhalter.setBackground(Color.decode("0xFFFFFF"));
		//platzhalter.setLayout(new BoxLayout(platzhalter, BoxLayout.X_AXIS));
		//platzhalter.add(ipAdresse);
		//platzhalter.add(Box.createHorizontalStrut((int) LEFT_ALIGNMENT));
		
		content.add(ipAdresse, BorderLayout.BEFORE_FIRST_LINE);
		content.getSize(new Dimension(WIDTH, 400));
		content.setBounds(100, 100, WIDTH, 400);
		content.setForeground(Color.decode("0x1E647F"));
		content.setBackground(Color.decode("0xFFFFFF"));
		//content.add(platzhalter, BorderLayout.NORTH);
		
		
		JPanel links = new JPanel();
		//peers.setSize(peers.getPreferredSize());
		links.setBackground(Color.decode("0xFFFFFF"));
		content.add(links, BorderLayout.WEST);
		
		
		
		JPanel unten = new JPanel();
		unten.setLayout(new BoxLayout(unten, BoxLayout.X_AXIS));
		unten.setBackground(Color.decode("0xFFFFFF"));
		unten.add(textFeld);
		
		JPanel chatbereich = new JPanel();
		chatbereich.setBackground(Color.decode("0xFFFFFF"));
		content.add(chatbereich, BorderLayout.CENTER);
		chatbereich.setLayout(new BoxLayout(chatbereich, BoxLayout.Y_AXIS));
		chatbereich.add(Box.createVerticalGlue());
		chatbereich.add(new JLabel("Nachrichten"));
		chatbereich.add(unten);
		
		JPanel rechts = new JPanel();
		rechts.setSize(rechts.getPreferredSize());
		rechts.setBackground(Color.decode("0xFFFFFF"));
		content.add(rechts, BorderLayout.EAST);
		rechts.setLayout(new BoxLayout(rechts, BoxLayout.Y_AXIS));
		rechts.add(btnConnect);
		rechts.add(Box.createVerticalStrut(5));
		rechts.add(btnDisconnect);
		rechts.add(Box.createVerticalStrut(5));
		rechts.add(btnExit);
		rechts.add(Box.createVerticalStrut(5));
		rechts.add(btnM);
		rechts.add(Box.createVerticalStrut(5));
		rechts.add(btnMx);
		rechts.add(Box.createVerticalStrut(5));
		rechts.add(Box.createVerticalGlue());
		rechts.add(btnOk);
		
		// Befehlsbuttons mit ChatController verknüpfen, um Funktionen zu setzen
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.btnConnect();
				
			}
		});
		
		btnDisconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.btnDisconnect();
				
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.btnExit();
				
			}
		});
		
		btnM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.btnM();
				
			}
		});
		
		btnMx.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.btnMx();
				
			}
		});
		
		//Sendenbutton mit ChatController verknüpfen, um Funktionen zu setzen
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.btnOk();
				
			}
		});
		
		// Bei ENTER-Taste gleiche Aktion wie bei btnOk
	    KeyListener tfKeyListener = new KeyAdapter() {
	        public void keyPressed(KeyEvent evt) {
	            if (evt.getKeyCode() == KeyEvent.VK_ENTER)
	                btnOk.doClick();
	        }
	    };
	    
	    textFeld.addKeyListener(tfKeyListener);
		
		
		
	}
}
