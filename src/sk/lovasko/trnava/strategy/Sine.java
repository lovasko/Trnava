package sk.lovasko.trnava.strategy;

public class Sine implements Strategy 
{
	public double 
	decide(int _x, int _y, boolean _escaped, int _maxlimit, int _limit) 
	{
		return Math.abs(Math.sin(_limit));
	}
}

