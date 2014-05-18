package zapoctak;

import java.awt.Color;

/** (0.0, 1.0) spectrum is divided into six equally large intervals - first we point to the correct segment and than with linear approximation determine the color
 * 
 * @author daniel
 */
public class sunshine implements palette {

    /** Convert decision to pixel value depending on palette
     * 
     * @param source decision of paint strategy
     * @return RGB value of pixel
     */
    public int decide(double source) {
        Color colors[] = new Color[6];
        colors[0] = new Color(0, 0, 0);
        colors[1] = new Color(128, 0, 0);
        colors[2] = new Color(128, 64, 0);
        colors[3] = new Color(255, 128, 0);
        colors[4] = new Color(255, 192, 0);
        colors[5] = new Color(255, 255, 255);

        int r, g, b;
        int r1, r2, g1, g2, b1, b2;

        double col = source;

        if (col < 0.20) {
            col *= 5.0;

            r1 = colors[0].getRed();
            g1 = colors[0].getGreen();
            b1 = colors[0].getBlue();
            r2 = colors[1].getRed();
            g2 = colors[1].getGreen();
            b2 = colors[1].getBlue();

            r = (int) (col * (double) (r2 - r1)) + r1;
            g = (int) (col * (double) (g2 - g1)) + g1;
            b = (int) (col * (double) (b2 - b1)) + b1;
        } else if (col < 0.40) {
            col -= 0.2;
            col *= 5.0;

            r1 = colors[1].getRed();
            g1 = colors[1].getGreen();
            b1 = colors[1].getBlue();
            r2 = colors[2].getRed();
            g2 = colors[2].getGreen();
            b2 = colors[2].getBlue();

            r = (int) (col * (double) (r2 - r1)) + r1;
            g = (int) (col * (double) (g2 - g1)) + g1;
            b = (int) (col * (double) (b2 - b1)) + b1;
        } else if (col < 0.60) {
            col -= 0.4;
            col *= 5.0;

            r1 = colors[2].getRed();
            g1 = colors[2].getGreen();
            b1 = colors[2].getBlue();
            r2 = colors[3].getRed();
            g2 = colors[3].getGreen();
            b2 = colors[3].getBlue();

            r = (int) (col * (double) (r2 - r1)) + r1;
            g = (int) (col * (double) (g2 - g1)) + g1;
            b = (int) (col * (double) (b2 - b1)) + b1;
        } else if (col < 0.80) {
            col -= 0.6;
            col *= 5.0;

            r1 = colors[3].getRed();
            g1 = colors[3].getGreen();
            b1 = colors[3].getBlue();
            r2 = colors[4].getRed();
            g2 = colors[4].getGreen();
            b2 = colors[4].getBlue();

            r = (int) (col * (double) (r2 - r1)) + r1;
            g = (int) (col * (double) (g2 - g1)) + g1;
            b = (int) (col * (double) (b2 - b1)) + b1;
        } else {
            col -= 0.8;
            col *= 5.0;

            r1 = colors[4].getRed();
            g1 = colors[4].getGreen();
            b1 = colors[4].getBlue();
            r2 = colors[5].getRed();
            g2 = colors[5].getGreen();
            b2 = colors[5].getBlue();

            r = (int) (col * (double) (r2 - r1)) + r1;
            g = (int) (col * (double) (g2 - g1)) + g1;
            b = (int) (col * (double) (b2 - b1)) + b1;
        }

        Color result = new Color(r, g, b);
        return result.getRGB();

    }
}
