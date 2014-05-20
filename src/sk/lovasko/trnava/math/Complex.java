package sk.lovasko.trnava.math;

public final class Complex 
{
	private final double re;   
	private final double im; 

	public Complex (final double re, final double im) 
	{
		this.re = re;
		this.im = im;
	}

	@Override
	public final String toString() 
	{
		if (im == 0)
			return String.valueOf(re);

		if (re == 0)
			return im + "i";

		if (im < 0) 
			return re + " - " + (-im) + "i";

		return re + " + " + im + "i";
	}

	public final double abs() 
	{
		return Math.hypot(re, im);
	}

	public final double phase() 
	{
		return Math.atan2(im, re);
	}

	public final Complex plus (final Complex b) 
	{
		final Complex a = this;
		final double real = a.re + b.re;
		final double imag = a.im + b.im;

		return new Complex(real, imag);
	}

	public final Complex minus (final Complex b) 
	{
		final Complex a = this;
		final double real = a.re - b.re;
		final double imag = a.im - b.im;

		return new Complex(real, imag);
	}

	public final Complex times (final Complex b) 
	{
		final Complex a = this;
		final double real = a.re * b.re - a.im * b.im;
		final double imag = a.re * b.im + a.im * b.re;

		return new Complex(real, imag);
	}

	public final Complex times (final double alpha) 
	{
		return new Complex(alpha * re, alpha * im);
	}

	public final Complex conjugate () 
	{
		return new Complex(re, -im);
	}

	public final Complex reciprocal () 
	{
		final double scale = re * re + im * im;
		return new Complex(re / scale, -im / scale);
	}

	public final double re () 
	{
		return re;
	}

	public final double im () 
	{
		return im;
	}

	public final Complex divides (final Complex b) 
	{
		final Complex a = this;
		return a.times(b.reciprocal());
	}

	public final Complex exp () 
	{
		return new Complex(
			Math.exp(re) * Math.cos(im), 
			Math.exp(re) * Math.sin(im));
	}

	public final Complex sin () 
	{
		return new Complex(
			Math.sin(re) * Math.cosh(im), 
			Math.cos(re) * Math.sinh(im));
	}

	public final Complex cos () 
	{
		return new Complex(
			 Math.cos(re) * Math.cosh(im), 
			-Math.sin(re) * Math.sinh(im));
	}

	public final Complex tan () 
	{
		return sin().divides(cos());
	}

	public static final Complex plus (final Complex a, final Complex b) 
	{
		final double real = a.re + b.re;
		final double imag = a.im + b.im;
		final Complex sum = new Complex(real, imag);

		return sum;
	}
}

