import jjil.core.Point;

import java.util.ArrayList;

/**
 * Created by archit.j on 16/08/17.
 */
public class ImageCordinates {

    private ArrayList<Point> topLeft;
    private ArrayList<Point> bottomRight;

    public void add(Point top,Point bottom) {
        topLeft.add(top);
        bottomRight.add(bottom);
    }

}
