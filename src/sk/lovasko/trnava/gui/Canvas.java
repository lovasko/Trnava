package sk.lovasko.trnava.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public final class Canvas extends JPanel 
{
	public BufferedImage image;

	@Override
	public void 
	paint (Graphics g) 
	{
		g.drawImage(img, 0, 0, null);
	}
}

