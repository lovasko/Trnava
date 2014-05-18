package zapoctak;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;

/** Canvas component - displays fractal image and holds current settings of rendering
 * 
 * @author daniel
 */
public class canvas extends JPanel {

    public BufferedImage img;
    fractal_renderer r;
    strategy s;
    palette p;
    double minx, miny, maxx, maxy;
    int limit;

    canvas() throws IOException {
        img = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
        r = new serial();
        s = new highcontrast();
        p = new black_n_white();
    }

    /** When paint is called, not whole fractal is being regenerated - only old version is painted instead
     * 
     * @param g canvas graphics context
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    /** Request for regenerating the fractal image & update of zoom info
     * 
     * @param _minx fractal top left X coordinate
     * @param _miny fractal top left Y coordinate
     * @param _maxx fractal bottom right X coordinate
     * @param _maxy fractal bottom right Y coordinate
     * @param _limit fractal detail
     */
    public void render(double _minx, double _miny, double _maxx, double _maxy, int _limit) {
        minx = _minx;
        miny = _miny;
        maxx = _maxx;
        maxy = _maxy;
        limit = _limit;

        img = r.render(minx, miny, maxx, maxy, limit, s, p);
    }

    /** Set renderer to use serial technology
     * 
     */
    public void change_to_serial() {
        r = new serial();
        img = r.render(minx, miny, maxx, maxy, limit, s, p);
    }

    /** Set renderer to use parallel technology
     * 
     */
    public void change_to_parallel() {
        r = new parallel();
        img = r.render(minx, miny, maxx, maxy, limit, s, p);
    }

    /** Set paint strategy to contrast colors
     * 
     */
    public void change_to_contrast() {
        s = new highcontrast();
        img = r.render(minx, miny, maxx, maxy, limit, s, p);
    }

    /** Set paint strategy to gradient colors
     * 
     */
    public void change_to_gradient() {
        s = new gradient();
        img = r.render(minx, miny, maxx, maxy, limit, s, p);
    }

    /** Set color palette to black and white
     * 
     */
    public void change_to_black_n_white() {
        p = new black_n_white();
        img = r.render(minx, miny, maxx, maxy, limit, s, p);
    }

    /** Set color palette to sunshine colors
     * 
     */
    public void change_to_sunshine() {
        p = new sunshine();
        img = r.render(minx, miny, maxx, maxy, limit, s, p);
    }

    /**
     * 
     * @param _new_limit new detail of rendering
     */
    public void change_limit(int _new_limit) {
        limit = _new_limit;
        img = r.render(minx, miny, maxx, maxy, limit, s, p);
    }
}
