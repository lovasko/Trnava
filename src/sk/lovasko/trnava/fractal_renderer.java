package zapoctak;

/** Interface for fractal renderers
 * 
 * @author daniel
 */
public interface fractal_renderer {

    /** Generate fractal image using custom technology
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
    java.awt.image.BufferedImage render(double minx, double miny, double maxx, double maxy, int limit, strategy s, palette p);
}
