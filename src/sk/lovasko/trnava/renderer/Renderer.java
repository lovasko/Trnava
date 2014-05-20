package sk.lovasko.trnava.renderer;

import sk.lovasko.trnava.strategy.Strategy;
import sk.lovasko.trnava.palette.Palette;

import java.awt.image.BufferedImage;
import java.awt.Dimension;

public interface Renderer
{
	/** 
	 * Generate fractal image using custom technology.
	 * 
	 * @param minx fractal top left X coordinate
	 * @param miny fractal top left Y coordinate
	 * @param maxx fractal bottom right X coordinate
	 * @param maxy fractal bottom right Y coordinate
	 * @param max_limit fractal detail
	 * @param s used strategy 
	 * @param p used palette
	 * @return generated fractal image
	 */
	BufferedImage 
	render (final double minx, final double miny, final double maxx, 
	    final double maxy, final int max_limit, final Strategy strategy, 
	    final Palette palette, final Dimension size);
}

