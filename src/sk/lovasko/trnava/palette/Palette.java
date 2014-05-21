package sk.lovasko.trnava.palette;

public interface Palette 
{
	/** 
	 * Convert decision to pixel value depending on palette
	 * 
	 * @param source decision of paint strategy
	 * @return RGB value of pixel
	 */
	int 
	decide (final double source);
}

