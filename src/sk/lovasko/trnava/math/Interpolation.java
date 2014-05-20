package sk.lovasko.trnava.math;

public final class Interpolation
{
	public static final double 
	single (double a, double b, double v)
	{
		return (a + (b - a) * v);	
	}
}

