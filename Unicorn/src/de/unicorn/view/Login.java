package de.unicorn.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Login{

	private static Toolkit t;
	private static int x = 0;
	private static int y = 0;
	private static int width = 400;
	private static int hight = 300;


	public static void main (String[] args) {
		JFrame window = new JFrame();
		
		t = Toolkit.getDefaultToolkit(); 
		Dimension d = t.getScreenSize(); 
		x = (int) (d.getWidth() - width) / 2 ;				// d.getWidth() gesamte Bildschirmbreite
		y = (int) (d.getHeight() - hight) / 2;				// Zentrum des Bildschirms berechnen
		
		window.setTitle("Login");							// Titel setzen
		window.setBounds(x, y, width, hight);				// Position festlegen
		
		window.setSize(400, 300);
		window.setMinimumSize(new Dimension(200, 150));
		setupContent(window);
																// ganze Anwendung schließt, wenn Eigenschaft gesetzt ist
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// EXIT_ON_CLOSE nicht immer benutzen!!!

		window.setVisible(true);									// Fenster sichtbar machen (immer ganz zum Schluss)
		
	}
	
	private static void setupContent(JFrame window) {
		JPanel content = new JPanel(); 
		JLabel labelName = new JLabel("<html><b>Name</b></html>");	// <font color = "red">
		JTextField textfieldName = new JTextField(10);
		JLabel labelPort = new JLabel("<html><b>Port</b></html>");
		JTextField textfieldPort = new JTextField(10);
		JLabel labelIP = new JLabel("<html><b>IP</b></html>");
		JTextField textfieldIP = new JTextField(10);
		JButton button = new JButton("Ok");
		
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.WEST, labelName,
                100,
                SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, labelName,
				60,
                SpringLayout.NORTH, content);
		
		layout.putConstraint(SpringLayout.WEST, textfieldName,
				140,
                SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, textfieldName,
                60,
                SpringLayout.NORTH, content);
		
		layout.putConstraint(SpringLayout.WEST, labelPort,
				100,
                SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, labelPort,
                90,
                SpringLayout.NORTH, content);
		
		layout.putConstraint(SpringLayout.WEST, textfieldPort,
				140,
                SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, textfieldPort,
				90,
                SpringLayout.NORTH, content);
		
		layout.putConstraint(SpringLayout.WEST, labelIP,
				100,
                SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, labelIP,
				120,
                SpringLayout.NORTH, content);
		
		layout.putConstraint(SpringLayout.WEST, textfieldIP,
				140,
                SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, textfieldIP,
                120,
                SpringLayout.NORTH, content);
		
		layout.putConstraint(SpringLayout.WEST, button,
				280,
                SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, button,
                120,
                SpringLayout.NORTH, content);
		
        content.setLayout(layout);
        
		content.add(labelName);
		content.add(textfieldName);
		content.add(labelPort);
		content.add(textfieldPort);
		content.add(labelIP);
		content.add(textfieldIP);
		content.add(button);
		
		window.setContentPane(content);
		
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Name: " + textfieldName.getText());
				System.out.println("Port: " + textfieldPort.getText());
				System.out.println("IP:   " + textfieldIP.getText());
				textfieldName.setText("");										//Inhalt von textfield auf "" setzen
				textfieldPort.setText("");
				textfieldIP.setText("");
				
				Chat neuesFenster = new Chat();
				neuesFenster.NewScreen();
				
				window.setVisible(false);
			}
		});
		
	}
}
