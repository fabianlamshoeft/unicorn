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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import de.unicorn.controller.LoginController;

public class Login{

	// private Variablen zur Größe/Ausrichtung des Fensters
	private Toolkit t;
	private int x = 0;
	private int y = 0;
	private int width = 400;
	private int hight = 300;
	
	// Verknüpfung zum LoginController erstellen
	private LoginController controller = new LoginController(this);
	
	public JFrame window = new JFrame();
	public JTextField textfieldName = new JTextField();
	public JTextField textfieldPort = new JTextField();
	
	
	
	public Login() {
		t = Toolkit.getDefaultToolkit(); 
		Dimension d = t.getScreenSize(); 
		x = (int) (d.getWidth() - width) / 2 ;				// d.getWidth() gesamte Bildschirmbreite
		y = (int) (d.getHeight() - hight) / 2;				// Zentrum des Bildschirms berechnen
		
		window.setTitle("Login");							// Titel setzen
		window.setBounds(x, y, width, hight);				// Position festlegen
		//window.setBounds(x, 0, 1000, 750);
		
		window.setSize(400, 280);
		window.setMinimumSize(new Dimension(400, 280));
		setupContent(window);
																// ganze Anwendung schließt, wenn Eigenschaft gesetzt ist
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// EXIT_ON_CLOSE nicht immer benutzen!!!

		window.setVisible(true);									// Fenster sichtbar machen (immer ganz zum Schluss)	
	}
	
	private void setupContent(JFrame window) {
		JPanel content = new JPanel(); 
		JPanel name = new JPanel();
		JPanel port = new JPanel();
		JPanel nameLay = new JPanel();
		JPanel namePort = new JPanel();
		//JLabel labelName = new JLabel("<html><b>Name</b></html>");	// <font color = "red">
		//JLabel labelPort = new JLabel("<html><b>Port</b></html>");
		JButton button = new JButton("Let's go!");

		button.setPreferredSize(new Dimension(200,40));
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setForeground(Color.decode("0x1E647F"));
		button.setBackground(Color.decode("0x1E647F"));
		
		textfieldName.setPreferredSize(new Dimension(200, 40));
		textfieldName.setFont(new Font("Arial", Font.PLAIN, 14));
		textfieldName.setForeground(Color.decode("0x1E647F"));
		textfieldName.setText("Name");
		
		textfieldPort.setPreferredSize(new Dimension(200, 40));
		textfieldPort.setFont(new Font("Arial", Font.PLAIN, 14));
		textfieldPort.setForeground(Color.decode("0x1E647F"));
		textfieldPort.setText("Port");
		
		SpringLayout nameLayout = new SpringLayout();
		nameLayout.putConstraint(SpringLayout.EAST, textfieldName,
				-45,
                SpringLayout.EAST, nameLay);
		nameLayout.putConstraint(SpringLayout.NORTH, textfieldName,
                40,
                SpringLayout.NORTH, nameLay);
		nameLayout.putConstraint(SpringLayout.EAST, textfieldPort,
				-45,
                SpringLayout.EAST, nameLay);
		nameLayout.putConstraint(SpringLayout.NORTH, textfieldPort,
				85,
                SpringLayout.NORTH, nameLay);	
		nameLayout.putConstraint(SpringLayout.EAST, button,
				-45,
                SpringLayout.EAST, nameLay);
		nameLayout.putConstraint(SpringLayout.NORTH, button,
                180,
                SpringLayout.NORTH, nameLay);
		
		nameLay.setLayout(nameLayout);
		
		nameLay.add(textfieldName);
		nameLay.add(textfieldPort);
		nameLay.add(button);
		
		namePort.setLayout(new BorderLayout());
		namePort.add(nameLay, BorderLayout.CENTER);
		nameLay.setBackground(Color.decode("0xFFFFFF"));
		
		textfieldName.requestFocusInWindow();
		window.setContentPane(namePort);
		
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.buttonOK();
				
			}
		});
		
		// Bei ENTER-Taste gleiche Aktion wie bei button
	    KeyListener tfKeyListener = new KeyAdapter() {
	        public void keyPressed(KeyEvent evt) {
	            if (evt.getKeyCode() == KeyEvent.VK_ENTER)
	                button.doClick();
	        }
	    };
	    
	    MouseListener btnMouseListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				button.setBackground(Color.decode("0xFFD75D"));
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				button.setBackground(Color.decode("0xFFD75D"));
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				button.setBackground(Color.decode("0xFFD75D"));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setBackground(Color.decode("0xFFD75D"));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				button.setBackground(Color.decode("0xFFD75D"));
				
			}
		};
	    
	    
	    button.addMouseListener(btnMouseListener);
	    textfieldPort.addKeyListener(tfKeyListener);
	    
	    textfieldName.requestFocusInWindow();
		window.setContentPane(namePort);
	}
}
