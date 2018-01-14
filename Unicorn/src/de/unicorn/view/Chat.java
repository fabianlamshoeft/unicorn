package de.unicorn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import de.unicorn.controller.ChatController;
import de.unicorn.model.Facade;

@SuppressWarnings("serial")
public class Chat extends JFrame {
	
	private ChatController controller;
	private JTextField textFeld = new JTextField(30);
	private JList<String> nachrichten = new JList<String>();
	private JList<String> peers = new JList<String>();
	
	/**
	 * <i>nachrichten</i>-Getter für <i>ChatController</i>
	 * @return nachrichten
	 */
	public JList<String> getNachrichten() {
		return nachrichten;
	}
	
	/**
	 * <i>textFeld</i>-Getter für <i>ChatController</i>
	 * @return textFeld
	 */
	public JTextField getTextFeld() {
		return textFeld;
	}
	
	/**
	 * <i>peers</i>-Getter für <i>ChatController</i>
	 * @return peers
	 */
	public JList<String> getPeers() {
		return peers;
	}
	
	/**
	 * Konstruktor
	 * @throws HeadlessException
	 */
	public Chat() throws HeadlessException {
		setUpElements();
		controller = new ChatController(this);
	}
	
	/**
	 * Wird durch <i>Let's go!</i>-Button in Login bzw. LoginController aufgerufen.
	 * Das <i>Chat</i>-Fenster öffnet sich. </br>
	 * Größe und Name von <i>window</i> werden gesetzt.
	 */
	public static void newScreen() {
		Toolkit t = Toolkit.getDefaultToolkit(); 
		Dimension d = t.getScreenSize(); 
		int x = (int) (d.getWidth() - 1000) / 2 ;				
		
		Chat window = new Chat();
		window.setTitle("Chat - " + Facade.getName() + " - " + Facade.getPort());	
		window.setMinimumSize(new Dimension(700, 320));
		window.setBounds(x, 0, 1000, 750);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	/**
	 * In dieser Methode stehen alle Informationen bezüglich des Layouts, der Farbigkeit, 
	 * der Größe der einzelnen Bausteine, der Schrift etc.
	 * </br>
	 * Es folgt die Verknüpfung der Buttons mit ActionListenern, welche mit dem 
	 * <i>ChatController</i> verknüpft sind, um dort die Funktion zu ergänzen.
	 */
	private  void setUpElements() {
		Container alles = this.getContentPane();
		
		// Initialisierung von content + Festlegung von Äußerlichkeiten
		JPanel content = new JPanel();
		content.setBackground(Color.decode("0xFFFFFF"));
		content.setLayout(new BorderLayout());
		content.setBorder(BorderFactory.createLineBorder(Color.decode("0x1E647F"), 1));
		content.getSize(new Dimension(WIDTH, 400));
		content.setBounds(100, 100, WIDTH, 400);
		content.setForeground(Color.decode("0x1E647F"));
		content.setBackground(Color.decode("0xFFFFFF"));
		
		// Festlegung der Äußerlichkeiten von textFeld
		textFeld.setForeground(Color.decode("0x1E647F"));
		textFeld.requestFocusInWindow();
		textFeld.setSize(new Dimension(14000, 38));
		textFeld.setPreferredSize(new Dimension(14000, 38));
		textFeld.setMinimumSize(new Dimension(14000, 38));
		textFeld.setMaximumSize(new Dimension(14000, 38));
		
		// Initialisierung der JButtons
		JButton btnConnect 		= new JButton();
		JButton btnDisconnect	= new JButton("DISCONNECT");
		JButton btnExit 			= new JButton("EXIT");
		JButton btnM	 			= new JButton("<html>M <i>Name</i> <i>Text</i></html>");
		JButton btnMx 			= new JButton("<html>MX <i>IP</i> <i>Port</i> <i>Text</i></html>");
		JButton btnOk 			= new JButton("SENDEN");
		
		// Festlegung der Äußerlichkeiten von btnConnect
		btnConnect.setText("<html>CONNECT <i>IP</i> <i>Port</i></html");
		btnConnect.setSize(new Dimension(170,40));
		btnConnect.setPreferredSize(new Dimension(170,40));
		btnConnect.setMinimumSize(new Dimension(170,40));
		btnConnect.setMaximumSize(new Dimension(170,40));
		btnConnect.setFont(new Font("Arial", Font.PLAIN, 14));
		btnConnect.setForeground(Color.decode("0x1E647F"));
		btnConnect.setBackground(Color.decode("0xFBE7A8"));
		btnConnect.setToolTipText("Sende eine POKE-Nachricht mit den eigenen Daten an den unter IP/Port erreichbaren Peer." );
		
		// Festlegung der Äußerlichkeiten von btnDisconnect
		btnDisconnect.setSize(new Dimension(170,40));
		btnDisconnect.setPreferredSize(new Dimension(170,40));
		btnDisconnect.setMinimumSize(new Dimension(170,40));
		btnDisconnect.setMaximumSize(new Dimension(170,40));
		btnDisconnect.setFont(new Font("Arial", Font.PLAIN, 14));
		btnDisconnect.setForeground(Color.decode("0x1E647F"));
		btnDisconnect.setBackground(Color.decode("0xFBE7A8"));
		btnDisconnect.setToolTipText("Sende eine DISCONNECT-Nachricht mit den eigenen Daten an alle aktiven Peers und entferne diese aus der Peer-Liste.");
		
		// Festlegung der Äußerlichkeiten von btnExit
		btnExit.setSize(new Dimension(170, 40));
		btnExit.setPreferredSize(new Dimension(170,40));
		btnExit.setMinimumSize(new Dimension(170,40));
		btnExit.setMaximumSize(new Dimension(170,40));
		btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
		btnExit.setForeground(Color.decode("0x1E647F"));
		btnExit.setBackground(Color.decode("0xFBE7A8"));
		btnExit.setToolTipText("Sende eine DISCONNECT-Nachricht mit den eigenen Daten an alle bekannten Peers und beende den Client.");
		
		// Festlegung der Äußerlichkeiten von btnM
		btnM.setSize(new Dimension(170, 40));
		btnM.setPreferredSize(new Dimension(170,40));
		btnM.setMinimumSize(new Dimension(170,40));
		btnM.setMaximumSize(new Dimension(170,40));
		btnM.setFont(new Font("Arial", Font.PLAIN, 14));
		btnM.setForeground(Color.decode("0x1E647F"));
		btnM.setBackground(Color.decode("0xFBE7A8"));
		btnM.setToolTipText("Sende Text als MESSAGE -Nachricht an alle bekannten Peers, die sich mit Name identizieren.");
		
		// Festlegung der Äußerlichkeiten von Mx
		btnMx.setSize(new Dimension(170,40));
		btnMx.setPreferredSize(new Dimension(170,40));
		btnMx.setMinimumSize(new Dimension(170,40));
		btnMx.setMaximumSize(new Dimension(170,40));
		btnMx.setFont(new Font("Arial", Font.PLAIN, 14));
		btnMx.setForeground(Color.decode("0x1E647F"));
		btnMx.setBackground(Color.decode("0xFBE7A8"));
		btnMx.setToolTipText("Sende Text als MESSAGE -Nachricht an den durch IP und Port eindeutig identizierten Peer.");
		
		// Festlegung der Äußerlichkeiten von btnOk
		btnOk.setSize(new Dimension(170,40));
		btnOk.setPreferredSize(new Dimension(170,40));
		btnOk.setMinimumSize(new Dimension(170,40));
		btnOk.setMaximumSize(new Dimension(170,40));
		btnOk.setFont(new Font("Arial", Font.BOLD, 14));
		btnOk.setForeground(Color.decode("0x1E647F"));
		btnOk.setBackground(Color.decode("0xFBE7A8"));
		
		// Initialisierung von ipAdresse + Festlegung von Äußerlichkeiten
		JLabel ipAdresse = new JLabel("   Meine IP-Adresse: " + Facade.getIp());
		ipAdresse.getIconTextGap();
		ipAdresse.setMinimumSize(new Dimension(WIDTH, 30));
		ipAdresse.setPreferredSize(new Dimension(WIDTH, 30));
		ipAdresse.setSize(new Dimension(WIDTH, 30));
		ipAdresse.setMaximumSize(new Dimension(WIDTH, 30));
		ipAdresse.setAlignmentX(LEFT_ALIGNMENT);
		ipAdresse.setForeground(Color.decode("0x1E647F"));
		ipAdresse.setBackground(Color.decode("0x1E647F"));
		ipAdresse.setFont(new Font("Arial", Font.PLAIN, 14));
		ipAdresse.setBorder(BorderFactory.createLineBorder(Color.decode("0x1E647F"), 1));

		// Festlegung der Äußerlichkeiten von peers 
		peers.setBackground(Color.decode("0xFFFFFF"));
		peers.setForeground(Color.decode("0x1E647F"));
		
		// Initialisierung von unten + Festlegung von Äußerlichkeiten und Layout
		JPanel unten = new JPanel();
		unten.setLayout(new BoxLayout(unten, BoxLayout.X_AXIS));
		unten.setBackground(Color.decode("0xFFFFFF"));
		unten.add(textFeld);
		unten.setBorder(BorderFactory.createLineBorder(Color.decode("0x1E647F"), 1));
		
		// Festlegung von Äußerlichkeiten von nachrichten
		nachrichten.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nachrichten.setSelectionForeground(Color.decode("0x1E647F"));
		nachrichten.setForeground(Color.decode("0x1E647F"));
		
		// Initialisierung von chatbereich + Festlegung von Äußerlichkeiten und Layout
		JPanel chatbereich = new JPanel();
		chatbereich.setBackground(Color.decode("0xFFFFFF"));
		chatbereich.setLayout(new BoxLayout(chatbereich, BoxLayout.Y_AXIS));
		chatbereich.add(Box.createVerticalGlue());
		
		// Initialisierung von scroller + Festlegung von Äußerlichkeiten
		JScrollPane scroller = new JScrollPane(nachrichten);
		scroller.setMaximumSize(getMaximumSize());
		scroller.setBorder(BorderFactory.createLineBorder(Color.decode("0x1E647F"), 1));
		
		// scroller und unten werden zu chatbereich (nächst größerer Baustein) hinzufügen, 
		chatbereich.add(scroller);
		chatbereich.add(unten);
		
		// Initialisierung von rechts + Festlegung von Äußerlichkeiten und Layout + Buttons hinzufügen
		JPanel rechts = new JPanel();
		rechts.setBorder(BorderFactory.createLineBorder(Color.decode("0x1E647F"), 1));
		rechts.setSize(rechts.getPreferredSize());
		rechts.setBackground(Color.decode("0xFFFFFF"));
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
		
		// Initialisierung von scrollerPeers + Festlegung von Äußerlichkeiten und Layout
		JScrollPane scrollerPeers = new JScrollPane(peers);
		scrollerPeers.setBorder(BorderFactory.createLineBorder(Color.decode("0x1E647F"), 1));
		chatbereich.add(scrollerPeers);
		
		// Einfügen der Bausteine in content
		content.add(ipAdresse, BorderLayout.BEFORE_FIRST_LINE);
		content.add(scrollerPeers, BorderLayout.WEST);
		content.add(chatbereich, BorderLayout.CENTER);
		content.add(rechts, BorderLayout.EAST);
		
		alles.add(content);
		
		btnConnect.addActionListener(new ActionListener() {		//\
			@Override											// \
			public void actionPerformed(ActionEvent arg0) {		//  \
				controller.btnConnect();						//   \
			}													//    \
		});														//     \
																//\
		btnDisconnect.addActionListener(new ActionListener() {	// \
			@Override											//  \
			public void actionPerformed(ActionEvent arg0) {		//	 \
				controller.btnDisconnect();						//    \
			}													//     \
		});														//\
																// \
		btnExit.addActionListener(new ActionListener() {		//  \
			@Override											//	 \	
			public void actionPerformed(ActionEvent arg0) {		//	  \	
				controller.btnExit();							//		
			}													//		Befehlsbuttons mittelst ActionListeners
		});														//		mit ChatController verknüpfen, um
																//		Funktionen im ChatController zu setzen.
		btnM.addActionListener(new ActionListener() {			//
			@Override											//	   /
			public void actionPerformed(ActionEvent arg0) {		//	  /
				controller.btnM();								//   /
			}													//  /
		});														// /
																///
		btnMx.addActionListener(new ActionListener() {			//     /
			@Override											//    /
			public void actionPerformed(ActionEvent arg0) {		//   /
				controller.btnMx();								//  /
			}													// /
		});														///
																//      /
		btnOk.addActionListener(new ActionListener() {			//     /
			@Override											//    /
			public void actionPerformed(ActionEvent arg0) {		//   /	
				controller.btnOk();								//  /
			}													// /
		});														///
					
		// Bei ENTER-Taste gleiche Aktion wie bei btnOk
	    KeyListener tfKeyListener = new KeyAdapter() {
	        public void keyPressed(KeyEvent evt) {
	            if (evt.getKeyCode() == KeyEvent.VK_ENTER)
	                btnOk.doClick();
	        }
	    };
	    
	    // Verbindung zum ChatController herstellen, um exitAndClose() auszuführen
	    addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            		controller.exitAndClose();
            		e.getWindow().dispose();
            }
        });
    
	    textFeld.addKeyListener(tfKeyListener);		
	}
}
