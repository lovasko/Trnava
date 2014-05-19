package zapoctak;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Parallel implementation of fractal renderer
 * 
 * @author daniel
 */
public class parallel implements fractal_renderer {

    public int xx, yy;
    private double minx, miny, maxx, maxy;
    private int limit;
    private BufferedImage img;
    private strategy s;
    private palette p;

    parallel() {
        xx = -60;
        yy = 0;
        img = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
    }

    /** Copy image data from thread to main buffered image
     * 
     * @param _img what to copy
     * @param _xx on which X coordinate
     * @param _yy on which Y coordinate
     */
    public synchronized void put_img_part(BufferedImage _img, int _xx, int _yy) {
        Graphics2D createGraphics = img.createGraphics();
        createGraphics.drawImage(_img, _xx, _yy, null);
        createGraphics.dispose();
    }

    /** Returns unique image part - (xx, yy) as starting point
     * 
     * @return new unique job info
     */
    public synchronized info get_new_job() {
        xx += 60;
        if (xx >= 600) {
            xx = 0;
            yy += 40;
        }

        if (yy >= 400) {
            return new info(0.0, 0.0, 0.0, 0.0, 0, 0, 0, true, s, p);
        }
        
        double w = maxx - minx;
        double h = maxy - miny;
        return new info(minx + w / 600.0 * ((double) xx), miny + h / 400.0 * ((double) yy), minx + w / 600.0 * ((double) (xx + 60)), miny + h / 400.0 * ((double) (yy + 40)), xx, yy, limit, false, s, p);
    }

    /** Generate fractal image using parallel technology
     * 
     * @param _minx fractal top left X coordinate
     * @param _miny fractal top left Y coordinate
     * @param _maxx fractal bottom right X coordinate
     * @param _maxy fractal bottom right Y coordinate
     * @param _limit fractal detail
     * @param _s used strategy 
     * @param _p used palette
     * @return generated fractal image
     */
    public BufferedImage render(double _minx, double _miny, double _maxx, double _maxy, int _limit, strategy _s, palette _p) {
        minx = _minx;
        miny = _miny;
        maxx = _maxx;
        maxy = _maxy;
        limit = _limit;
        s = _s;
        p = _p;
        worker[] w = new worker[4];
        for (int i = 0; i < 4; i++) {
            w[i] = new worker();
            w[i].setName("worker" + i);
            w[i].set_parallel(this);
            w[i].start();
        }
        
        try {
            w[0].join();
            w[1].join();
            w[2].join();
            w[3].join();
        } catch (InterruptedException ex) {
            Logger.getLogger(parallel.class.getName()).log(Level.SEVERE, null, ex);
        }

        xx = -60;
        yy = 0;
        return img;
    }
}
