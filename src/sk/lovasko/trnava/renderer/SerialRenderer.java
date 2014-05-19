package zapoctak;

import java.awt.image.BufferedImage;

/** Serial implementation of fractal renderer
 * 
 * @author daniel
 */
public class serial implements fractal_renderer {

    /** Generate fractal image using serial technology
     * 
     * @param minx fractal top left X coordinate
     * @param miny fractal top left Y coordinate
     * @param maxx fractal bottom right X coordinate
     * @param maxy fractal bottom right Y coordinate
     * @param limit fractal detail
     * @param s used strategy 
     * @param p used palette
     * @return generated fractal image
     */
    public BufferedImage render(double minx, double miny, double maxx, double maxy, int limit, strategy s, palette p) {
        java.awt.image.BufferedImage result = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
        boolean morethan2 = false;
        double xx, yy; 
        double r, i;
        int pocet;
        
        for (int y = 0; y < 400; y++) {
            yy = -((double) y / 400 * (maxy - miny) + miny);
            for (int x = 0; x < 600; x++) {
                xx = (double) x / 600 * (maxx - minx) + minx;
                Complex c = new Complex(xx, yy);
                Complex z = new Complex(0.0f, 0.0f);
                for (pocet = 0; pocet < limit; pocet++) {
                    Complex momo = z.times(z);
                    z = momo;
                    
                    momo = z.plus(c);
                    z = momo;
                    
                    r = z.re();
                    i = z.im();
                    if (i * i + r * r > 4) {
                        morethan2 = true;
                        break;
                    }
                }
                result.setRGB(x, y, p.decide(s.decide(x, y, morethan2, limit, pocet)));
                morethan2 = false;
            }
        }
        
        return result;
    }
}
