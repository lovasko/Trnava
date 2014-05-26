package sk.lovasko.trnava.renderer;

import sk.lovasko.trnava.strategy.Strategy;
import sk.lovasko.trnava.palette.Palette;

import java.awt.Point;
import java.awt.Dimension;

public class SerialRenderer implements Renderer 
{
	public BufferedImage 
	render (final double minx, final double miny, final double maxx, 
	    final double maxy, final int max_limit, final Strategy strategy, 
	    final Palette palette, final Dimension size)
	{
		Worker worker = new Worker();
		worker.minx = minx;
		worker.miny = miny;
		worker.maxx = maxx;
		worker.maxy = maxy;
		worker.max_limit = max_limit;
		worker.strategy = strategy;
		worker.palette = palette;
		worker.start = new Point(0, 0);
		worker.size = size;

		Result result = worker.call();		
		return result.image;
	}
}

