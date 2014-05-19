package sk.lovasko.trnava.strategy;

public class HighContrastStrategy implements Strategy 
{
	public final double 
	decide (final int _x, final int _y, final boolean _escaped, 
	    final int _maxlimit, final int _limit) 
	{
		if (_escaped)
			return 0.0;
		else
			return 1.0;
	}
}

