package sk.lovasko.trnava.strategy;

public class Gradient implements Strategy 
{
	public final double 
	decide (final int _x, final int _y, final boolean _escaped, 
	    final int _maxlimit, final int _limit) 
	{
		return ((double)_limit) / ((double)_maxlimit);
	}
}

