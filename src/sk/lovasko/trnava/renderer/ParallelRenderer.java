package sk.lovasko.trnava.renderer;

import sk.lovasko.trnava.strategy.Strategy;
import sk.lovasko.trnava.palette.Palette;
import sk.lovasko.trnava.math.Interpolation;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ParallelRenderer implements Renderer 
{
	private final int thread_count;
	private final Dimension tile_size;

	public ParallelRenderer (final int thread_count, final Dimension tile_size) 
	{
		this.thread_count = thread_count;
		this.tile_size = tile_size;
	}

	public BufferedImage 
	render (final double minx, final double miny, final double maxx, 
	    final double maxy, final int max_limit, final Strategy strategy, 
	    final Palette palette, final Dimension size)
	{
		ExecutorService pool = Executors.newFixedThreadPool(thread_count);
		List<Future<Result>> results = new ArrayList<Future<Result>>();
		List<Worker> tasks = new ArrayList<Worker>();

		for (int x = 0; x < image.get_width(); x += tile_w)
		for (int y = 0; y < image.get_height(); y += tile_h)
		{
			int end_x;
			int end_y;

			if (x + tile_size.width >= size.width)
				end_x = size.width - 1;
			else
				end_x = x + tile_size.width - 1;

			if (y + tile_size.height >= size.height)
				end_y = size.height - 1;
			else
				end_y = y + tile_h - 1;

			Worker worker = new Worker();

			worker.strategy = strategy;
			worker.palette = palette;
			worker.start = new Point(x, y);
			worker.size = new Dimension(end_x - x, endy - y);
			worker.minx = Interpolation.single(minx, maxx,
			    (double)x/(double)size.width);
			worker.maxx = Interpolation.single(minx, maxx,
			    (double)end_x/(double)size.width);
			worker.miny = Interpolation.single(miny, maxy,
			    (double)y/(double)size.height);
			worker.maxy = Interpolation.single(miny, maxy,
			    (double)end_y/(double)size.height);
			worker.max_limit = max_limit;

			tasks.add(worker);
		}

		try
		{
			results = pool.invokeAll(tasks);
			for (Future<Result> future : results)
			{
				Result result = future.get();
				result.put_to_image(image);
			}
		}
		catch (InterruptedException|ExecutionException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}

		pool.shutdown();
	}
}

