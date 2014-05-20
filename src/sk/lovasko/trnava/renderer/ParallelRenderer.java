package sk.lovasko.trnava.renderer;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ParallelRenderer implements Renderer 
{
	private final int thread_count;

	public ParallelRenderer (final int thread_count) 
	{
		this.thread_count = thread_count;
	}

	public BufferedImage 
	render (final double minx, final double miny, final double maxx, 
	    final double maxy, final int max_limit, final Strategy strategy, 
	    final Palette palette)
	{
		ExecutorService pool = Executors.newFixedThreadPool(thread_count);
		List<Future<Result>> results = new ArrayList<Future<Result>>();
		List<Worker> tasks = new ArrayList<Worker>();

		for (int x = 0; x < image.get_width(); x += tile_w)
		for (int y = 0; y < image.get_height(); y += tile_h)
		{
			int end_x;
			int end_y;

			if (x + tile_w >= image.get_width())
				end_x = image.get_width() - 1;
			else
				end_x = x + tile_w - 1;

			if (y + tile_h >= image.get_height())
				end_y = image.get_height() - 1;
			else
				end_y = y + tile_h - 1;

			Worker worker = new Worker();
			worker.strategy = strategy;
			worker.palette = palette;
			//TODO finish worker loading
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

