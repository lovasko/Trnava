package sk.lovasko.trnava.strategy;

public interface Strategy 
{
	/** 
	 * Decide about every pixel its abstract color value - from 0.0 do 1.0.
	 * 
	 * @param _x X coordinate of pixel
	 * @param _y Y coordinate of pixel
	 * @param _escaped whether the pixel escaped 2.0 radius zone
	 * @param _maxlimit what was the maximum limit
	 * @param _limit what was the limit reached when escaped
	 * @return decision regarding abstract color
	 */
	double 
	decide (int _x, int _y, boolean _escaped, int _maxlimit, int _limit);
}

