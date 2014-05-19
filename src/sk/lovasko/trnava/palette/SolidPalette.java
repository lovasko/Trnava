package sk.lovasko.trnava.palette

public final class SolidPalette implements Palette
{
	private final int background_color;
	private final int foreground_color;

	public SolidPalette (final int background_color, final int foreground_color)
	{
		this.background_color = background_color;
		this.foreground_color = foreground_color;
	}

	public final int 
	decide (final double source)
	{
		if (source < 0.5)
			return background_color;
		else
			return foreground_color;
	}
}

