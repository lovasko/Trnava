package sk.lovasko.trnava.strategy;

public class Gradient implements Strategy 
{
	public double 
	decide (int _x, int _y, boolean _escaped, int _maxlimit, int _limit) 
	{
		return ((double)_limit) / ((double)_maxlimit);
	}
}

