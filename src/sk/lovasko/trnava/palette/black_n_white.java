package zapoctak;

import java.awt.Color;

/** Color palette of gradient ranging from pure white to black
 * 
 * @author daniel
 */
public class black_n_white implements palette {

    /** Convert decision to pixel value depending on palette
     * 
     * @param source decision of paint strategy
     * @return RGB value of pixel
     */
    public int decide(double source) {
        double col = 1.0 - source;
        Color c = new Color((float) col, (float) col, (float) col);
        return c.getRGB();
    }
}
