package de.unicorn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import de.unicorn.controller.LoginController;
/**
 * Stellt die Benutzeroberfläche des ersten sich öffnenden Fensters der Anwendung da.
 * 
 */
public class Login{

	// private Variablen zur Größe/Ausrichtung des Fensters
	private Toolkit t;
	private int x = 0;
	private int y = 0;
	private int width = 400;
	private int hight = 300;
	
	// Verknüpfung zum LoginController erstellen
	private LoginController controller = new LoginController(this);
	
	// globale Variablen 
	public JFrame window = new JFrame();
	public JTextField textfieldName = new JTextField();
	public JTextField textfieldPort = new JTextField();
	
	
	// Konstruktor
	public Login() {
		t = Toolkit.getDefaultToolkit(); 
		Dimension d = t.getScreenSize(); 						// Anpassung an jegliche Bildschirmauflösung
		x = (int) (d.getWidth() - width) / 2 ;					// d.getWidth() enspricht gesamter Bildschirmbreite
		y = (int) (d.getHeight() - hight) / 2;					// x und y: Zentrum des Bildschirms
		
		window.setTitle("Login");								// Titel setzen
		window.setBounds(x, y, width, hight);					// Position festlegen
		window.setSize(400, 280);								// Größe des Fensters auf 400 x 280 setzen
		window.setResizable(false);								// Größe fixieren
		
		setupContent(window);									// Fenster mit Inhalten vom JFrame window füllen
																// ganze Anwendung schließt, wenn Eigenschaft gesetzt ist
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// EXIT_ON_CLOSE nicht immer benutzen!!!

		window.setVisible(true);									// Fenster sichtbar machen (immer ganz zum Schluss)	
	}
	
	private void setupContent(JFrame window) {
		// Initialisierung von Panels und Button zur Strukturierung der Oberfläche
		JPanel lay = new JPanel();
		JPanel content = new JPanel();
		JButton button = new JButton("Let's go!");				// Button wird mit Text versehen
		
		// Äußerlichkeiten des JButtons setzen
		button.setPreferredSize(new Dimension(200,40));			// Größe des Buttons wird festgelegt
		button.setFont(new Font("Arial", Font.BOLD, 14));			// Schriftart/-dicke/-größe von "Let's go!" festlegen
		button.setForeground(Color.decode("0x1E647F"));			// Schriftfarbe auf #1E647F (dunkles Blau) festlegen
		button.setBackground(Color.decode("0xFBE7A8"));			// Hintergrund des Buttons ist helles Gelb
		
		// Initialisierung von JLabels, die nähere Informationen zu Textfeldern geben
		JLabel labelName = new JLabel("Name:");					
		JLabel labelPort = new JLabel("Port:");
		
		// Äußerlichkeiten der JLabels setzen
		labelName.setForeground(Color.decode("0x1E647F"));		// Schriftfarbe von "Name"
		labelName.setFont(new Font("Arial", Font.BOLD, 14));		// Schriftart/-dicke/-größe von "Name"
		labelPort.setForeground(Color.decode("0x1E647F"));		// Schriftfarbe von "Port"
		labelPort.setFont(new Font("Arial", Font.BOLD, 14));		// Schriftart/-dicke/-größe von "Port"
		
		// Äußerlichkeiten des JTextfields für Namen setzen
		textfieldName.setPreferredSize(new Dimension(200, 40));	// Größe des Feldes auf 200 x 40
		textfieldName.setFont(new Font("Arial", Font.PLAIN, 14));	// Schriftart/-dicke/-größe des einzugebenden Textes
		textfieldName.setForeground(Color.decode("0x1E647F"));	// Schriftfarbe des einzugebenden Textes
		
		// Äußerlichkeiten des JTextfields für Namen setzen
		textfieldPort.setPreferredSize(new Dimension(200, 40));	// Größe des Feldes auf 200 x 40
		textfieldPort.setFont(new Font("Arial", Font.PLAIN, 14));// Schriftart/-dicke/-größe des einzugebenden Textes
		textfieldPort.setForeground(Color.decode("0x1E647F"));	// Schriftfarbe des einzugebenden Textes
		
		// Anordnung der einzelnen Komponenten mittelst SpringLayout
		SpringLayout layout = new SpringLayout();			// exakte Fixierung der einzelnen Elemente
		layout.putConstraint(SpringLayout.EAST, labelName,	// im Fenster
				-250,												
                SpringLayout.EAST, lay);						
		layout.putConstraint(SpringLayout.NORTH, labelName,	
                53,												
                SpringLayout.NORTH, lay);					
		layout.putConstraint(SpringLayout.EAST, textfieldName,
				-45,													
                SpringLayout.EAST, lay);
		layout.putConstraint(SpringLayout.NORTH, textfieldName,
                40,
                SpringLayout.NORTH, lay);
		layout.putConstraint(SpringLayout.EAST, labelPort,
				-250,
                SpringLayout.EAST, lay);
		layout.putConstraint(SpringLayout.NORTH, labelPort,
                98,
                SpringLayout.NORTH, lay);
		layout.putConstraint(SpringLayout.EAST, textfieldPort,
				-45,
                SpringLayout.EAST, lay);
		layout.putConstraint(SpringLayout.NORTH, textfieldPort,
				85,
                SpringLayout.NORTH, lay);	
		layout.putConstraint(SpringLayout.EAST, button,
				-45,
                SpringLayout.EAST, lay);
		layout.putConstraint(SpringLayout.NORTH, button,
                180,
                SpringLayout.NORTH, lay);
		lay.setLayout(layout);							
		
		// Hinzufügen der oben formatierten Elemente
		lay.add(labelName);	
		lay.add(textfieldName);
		lay.add(labelPort);
		lay.add(textfieldPort);
		lay.add(button);
		
		//Hinzufügen von lay in content
		lay.setBackground(Color.decode("0xFFFFFF"));
		content.setLayout(new BorderLayout());
		content.add(lay, BorderLayout.CENTER);
		
		// Cursor auf erstes Textfeld setzen
		textfieldName.requestFocusInWindow();
		
		// content in window einfügen
		window.setContentPane(content);
		
		
		// ActionListener für "Let's go!" Button
		button.addActionListener(new ActionListener() {
			@Override	
			public void actionPerformed(ActionEvent arg0) {		// wenn Buuton gedrückt, dann controller im 
				controller.buttonOK();							// LoginController geöffnet
			}													//
		});
		
		// KeyListener: Dient zur Belegung einzelner Tasten mit Befehlen
	    KeyListener tfKeyListener = new KeyAdapter() {
	        public void keyPressed(KeyEvent evt) {				//
	            if (evt.getKeyCode() == KeyEvent.VK_ENTER)		// ENTER-Taste entspricht "Let's go" Button
	                button.doClick();							//
	        }													//
	    };
	    
	    // MouseListener für Farbigkeit des "Let's go!" Buttons setzen
	    MouseListener btnMouseListener = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {			// wenn Maus klickt -> weiterhin helles Gelb
				button.setBackground(Color.decode("0xFBE7A8"));	//
			}													//
			@Override
			public void mousePressed(MouseEvent arg0) {			// wenn Maus klickt -> weiterhin helles Gelb
				button.setBackground(Color.decode("0xFBE7A8"));	//
			}													//
			@Override
			public void mouseExited(MouseEvent arg0) {			// wenn Maus Button verlässt -> wieder helles Gelb
				button.setBackground(Color.decode("0xFBE7A8"));	//
			}													//	
			@Override
			public void mouseEntered(MouseEvent arg0) {			// wenn Maus auf Button kommt -> kräftiges Gelb
				button.setBackground(Color.decode("0xFFD75D"));	//
			}													//
			@Override
			public void mouseClicked(MouseEvent arg0) {			// wenn Maus klickt -> weiterhin helles Gelb
				button.setBackground(Color.decode("0xFBE7A8"));	//	
			}													//
		};
	    
		// Hinzufügen der verschiedenen Listener
	    button.addMouseListener(btnMouseListener);
	    textfieldPort.addKeyListener(tfKeyListener);
		
		
	}
}
