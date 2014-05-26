package sk.lovasko.trnava.renderer;

import sk.lovasko.trnava.strategy.Strategy;
import sk.lovasko.trnava.palette.Palette;
import sk.lovasko.trnava.math.Complex;
import sk.lovasko.trnava.math.Interpolation;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Dimension;
import java.util.concurrent.Callable;

public final class Worker implements Callable<Result>
{
	public double minx; 
	public double miny; 
	public double maxx; 
	public double maxy;
	public int max_limit;
	public Dimension size;
	public Point start;
	public Strategy strategy;
	public Palette palette;

	public final Result 
	call ()
	{
		BufferedImage image = new BufferedImage(size.width, size.height,
		     BufferedImage.TYPE_INT_RGB);
		Result result = new Result(image, start);

		for (int y = 0; y < size.height; y++) 
		{
			final double ny = Interpolation.single(miny, maxy, 
			    (double)y/(double)size.height);

			for (int x = 0; x < size.width; x++) 
			{
				final double nx = Interpolation.single(minx, maxx, 
				    (double)x/(double)size.width);

				Complex c = new Complex(nx, -ny);
				Complex z = new Complex(0.0, 0.0);

				boolean escaped = false;
				int limit;
				for (limit = 0; limit < max_limit; limit++) 
				{
					z  = z.times(z).plus(c);

					final double r = z.re();
					final double i = z.im();
					if (i * i + r * r > 4.0) 
					{
						escaped = true;
						break;
					}
				}

				final double strategy_decision = strategy.decie(x, y, escaped,
				    max_limit, limit);
				image.setRGB(x, y, palette.decide(strategy_decision));
			}
		}

		return result;
	}
}

