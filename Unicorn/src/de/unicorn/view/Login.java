package de.unicorn.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import de.unicorn.controller.LoginController;

public class Login{

	private Toolkit t;
	private int x = 0;
	private int y = 0;
	private int width = 400;
	private int hight = 300;
	private LoginController controller = new LoginController(this);
	
	
	public JTextField textfieldName = new JTextField(10);
	public JTextField textfieldPort = new JTextField(10);
	public JFrame window = new JFrame();
	
	public Login() {
		t = Toolkit.getDefaultToolkit(); 
		Dimension d = t.getScreenSize(); 
		x = (int) (d.getWidth() - width) / 2 ;				// d.getWidth() gesamte Bildschirmbreite
		y = (int) (d.getHeight() - hight) / 2;				// Zentrum des Bildschirms berechnen
		
		window.setTitle("Login");							// Titel setzen
		window.setBounds(x, y, width, hight);				// Position festlegen
		window.setBounds(x, 0, 1000, 750);
		
		window.setSize(400, 300);
		window.setMinimumSize(new Dimension(400, 300));
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
		JLabel labelName = new JLabel("<html><b>Name</b></html>");	// <font color = "red">
		JLabel labelPort = new JLabel("<html><b>Port</b></html>");
		JButton button = new JButton("Ok");
		button.setSize(new Dimension(30,20));
		textfieldName.setSize(100, 20);
		textfieldPort.setSize(100, 20);
		/**
		name.setBackground(Color.decode("#B2CCDE"));
		name.setLayout(new BoxLayout(name, BoxLayout.X_AXIS));
		name.add(labelName);
		name.add(textfieldName);
		**/
		SpringLayout nameLayout = new SpringLayout();
		
		nameLayout.putConstraint(SpringLayout.WEST, labelName,
                100,
                SpringLayout.WEST, nameLay);
		nameLayout.putConstraint(SpringLayout.NORTH, labelName,
				60,
                SpringLayout.NORTH, nameLay);
		
		nameLayout.putConstraint(SpringLayout.WEST, textfieldName,
				140,
                SpringLayout.WEST, nameLay);
		nameLayout.putConstraint(SpringLayout.NORTH, textfieldName,
                60,
                SpringLayout.NORTH, nameLay);
		nameLayout.putConstraint(SpringLayout.WEST, labelPort,
				100,
                SpringLayout.WEST, nameLay);
		nameLayout.putConstraint(SpringLayout.NORTH, labelPort,
                90,
                SpringLayout.NORTH, nameLay);
		
		nameLayout.putConstraint(SpringLayout.WEST, textfieldPort,
				140,
                SpringLayout.WEST, nameLay);
		nameLayout.putConstraint(SpringLayout.NORTH, textfieldPort,
				90,
                SpringLayout.NORTH, nameLay);
		
		nameLayout.putConstraint(SpringLayout.WEST, button,
				280,
                SpringLayout.WEST, nameLay);
		nameLayout.putConstraint(SpringLayout.NORTH, button,
                90,
                SpringLayout.NORTH, nameLay);
		
		nameLay.setLayout(nameLayout);
		nameLay.setBackground(Color.decode("0xB2CCDE"));
		nameLay.add(labelName);
		nameLay.add(textfieldName);
		nameLay.add(labelPort);
		nameLay.add(textfieldPort);
		nameLay.add(button);
		
		namePort.setLayout(new BorderLayout());
		namePort.add(nameLay, BorderLayout.CENTER);
		namePort.setBackground(Color.decode("0xB2CCDE"));
		
		
		/**
		port.setBackground(Color.decode("#B2CCDE"));
		port.setLayout(new BoxLayout(port, BoxLayout.X_AXIS));
		port.add(labelPort);
		port.add(textfieldPort);
		port.add(button);
		
		namePort.setLayout(new BoxLayout(namePort, BoxLayout.Y_AXIS));
		namePort.setBackground(Color.decode("0xB2CCDE"));
		namePort.add(name);
		namePort.add(port);
		
		content.setBackground(Color.decode("0xB2CCDE"));
		content.setLayout(new BorderLayout());
		content.add(namePort, BorderLayout.CENTER);
		**/
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
	    
	    textfieldPort.addKeyListener(tfKeyListener);
		
	}
}
