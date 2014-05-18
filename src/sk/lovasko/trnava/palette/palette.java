package zapoctak;

/** Implement this interface to set your own coloring palette
 * 
 * @author daniel
 */
public interface palette {

    /** Convert decision to pixel value depending on palette
     * 
     * @param source decision of paint strategy
     * @return RGB value of pixel
     */
    int decide(double source);
}
