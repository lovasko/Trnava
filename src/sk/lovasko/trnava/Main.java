package sk.lovasko.trnava;

import sk.lovasko.trnava.gui.Window;

import javax.swing.SwingUtilities;

public class Main 
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void
			run()
			{
				Window window = new Window();
			}
		});
	}
}

