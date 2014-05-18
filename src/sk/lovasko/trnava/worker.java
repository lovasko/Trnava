package zapoctak;

import java.awt.image.BufferedImage;

/** 
 * 
 * @author daniel
 */
public class worker extends Thread {

    private double minx, miny, maxx, maxy;
    private int limit;
    private int xx, yy;
    private BufferedImage img;
    private parallel p;
    private strategy s;
    private palette pp;

    worker() {
        img = new BufferedImage(60, 40, BufferedImage.TYPE_INT_RGB);
    }

    /** Set parallel fractal renderer
     * 
     * @param _p parallel fractal renderer, used for get_new_job and put_part_img
     */
    public void set_parallel(parallel _p) {
        System.out.print("worker::set_parallel");
        p = _p;
    }

    /** return generated image
     * 
     * @return generated image
     */
    public BufferedImage getImage() {
        return img;
    }

    /** Method which generates specified part of fractal image. After the part is finished, thread does not exit, just asks for new job.
     * 
     */
    @Override
    public void run() {
        while (true) {
            info inf = p.get_new_job();
            if (inf.done == true) {
                break;
            }

            minx = inf.minx;
            miny = inf.miny;
            maxx = inf.maxx;
            maxy = inf.maxy;
            limit = inf.limit;
            xx = inf.xx;
            yy = inf.yy;
            s = inf.s;
            pp = inf.p;

            boolean morethan2 = false;
            double xxx, yyx;
            double r, i;
            int pocet;

            for (int y = 0; y < 40; y++) {
                yyx = -((double) y / 40.0 * (maxy - miny) + miny);
                for (int x = 0; x < 60; x++) {
                    xxx = (double) x / 60.0 * (maxx - minx) + minx;
                    Complex c = new Complex(xxx, yyx);
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
                    img.setRGB(x, y, pp.decide(s.decide(xx, yy, morethan2, limit, pocet)));
                    morethan2 = false;
                }
            }
            p.put_img_part(img, this.xx, this.yy);
        }
    }
}
