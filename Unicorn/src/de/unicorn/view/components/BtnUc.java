package de.unicorn.view.components;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class BtnUc extends JButton{

	private int paintStatus = 0;
	
	public BtnUc() {
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				paintStatus = 0;
				repaint();
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				paintStatus = 1;
				repaint();
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	public void paintComponent(Graphics g) {
		
		switch(paintStatus) {
		case 0: 
			g.setColor(Color.decode("0xFBE7A8"));
			break;
		case 1:
			//g.setColor(getBackground().darker());
			g.setColor(Color.decode("0xFFD75D"));
			break;
		}
		
		//g.setColor(getBackground());
		//g.fillRect(0, 0, getSize().width, getSize().height);
		g.fillRoundRect(0, 0, getSize().width, getSize().height, 15, 15);
		
		
		Font f = getFont();
		g.setFont(f);
		
		String text = getText();
		FontMetrics m = g.getFontMetrics();
		
		int w = m.stringWidth(text);
		int h = m.getHeight();
		
		g.setColor(getForeground());
		g.drawString(text, (getWidth()/2) - (w/2), (getHeight()/2) - (h/2) + 10);
	}
	
}
