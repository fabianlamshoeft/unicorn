package de.unicorn.view.components;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JButton;

public class BtnUc extends JButton{

	
	public void paintComponent(Graphics g) {
		
		g.setColor(getBackground());
		g.fillRect(0, 0, getSize().width, getSize().height);
		
		
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
