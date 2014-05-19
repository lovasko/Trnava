package sk.lovasko.trnava.strategy;

public class HighContrast implements Strategy 
{
	public double 
	decide (int _x, int _y, boolean _escaped, int _maxlimit, int _limit) 
	{
		if (_escaped)
			return 0.0;
		else
			return 1.0;
	}
}

