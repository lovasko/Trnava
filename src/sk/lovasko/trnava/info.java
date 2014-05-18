package zapoctak;

public class Info 
{
	double minx, miny, maxx, maxy;
	int xx, yy;
	int limit;
	boolean done;
	strategy s;
	palette p;

	info(double _minx, double _miny, double _maxx, double _maxy, int _xx, int _yy, int _limit, boolean _done, strategy _s, palette _p) {
			minx = _minx;
			miny = _miny;
			maxx = _maxx;
			maxy = _maxy;
			limit = _limit;
			xx = _xx;
			yy = _yy;
			done = _done;
			s = _s;
			p = _p;
	}
}

