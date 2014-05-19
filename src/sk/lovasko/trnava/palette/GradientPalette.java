package sk.lovasko.trnava.palette

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public final class GradientPalette implements Palette
{
	private final List<Double> stops;
	private final List<Integer> colors;

	public GradientPalette (final int zero_color, final int one_color)
	{
		stops.add(new Double(0.0));
		colors.add(new Integer(zero_color));

		stops.add(new Double(1.0));
		colors.add(new Integer(one_color));
	}

	public void
	add (final double stop, final int color)
	{
		int idx;

		for (idx = 0; idx < stops.size() && stops.at(idx) < stop; idx++);
			
		stops.add(idx, stop);
		colors.add(idx, color);
	}

	public final int 
	decide (final double source)
	{
		int idx;

		for (idx = 0; idx < stops.size() && stops.at(idx) <= source; idx++);

		final double max_diff = stops.at(idx) - stops(idx - 1);
		final double diff = source - stops.at(idx - 1);
		final double step = diff / max_diff;

		final Color c1 = new Color(colors.at(idx-1));		
		final Color c2 = new Color(colors.at(idx));

		final Color result = new Color(
			c1.getRed()   + (int)(((double)(c2.getRed()   - c1.getRed()))   * step), 
			c1.getGreen() + (int)(((double)(c2.getGreen() - c1.getGreen())) * step), 
			c1.getBlue()  + (int)(((double)(c2.getBlue()  - c1.getBlue()))  * step));

		return result;
	}
}

