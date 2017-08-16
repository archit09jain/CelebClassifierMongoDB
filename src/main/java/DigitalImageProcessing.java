import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import jjil.algorithm.Gray8Rgb;
import jjil.algorithm.RgbAvgGray;
import jjil.core.*;
import jjil.j2se.RgbImageJ2se;

import java.util.ArrayList;
import java.util.List;
import jjil.core.Image;
import jjil.core.RgbImage;
import jjil.j2se.RgbImageJ2se;
/**
 * Created by archit.j on 16/08/17.
 */
public class DigitalImageProcessing {

    public static final Integer minScale = 1;
    public static final Integer maxScale = 40;
    public static final String harrFilterPath = "/Users/archit.j/Desktop/mongoarchitj/src/main/resources/HCSB.txt";

    private String srcFilePath;
    private BufferedImage inputImage;


    DigitalImageProcessing(String src) {
        srcFilePath = src;

        try {
            inputImage = ImageIO.read(new FileInputStream(src));
        } catch (IOException e) {
            e.printStackTrace();
        }
        findFaces();
    }

    public List<Rect> findFaces() {
        List<Rect> faces;
        try {
            InputStream is  = new FileInputStream(harrFilterPath);
            Gray8DetectHaarMultiScale detectHaar = new Gray8DetectHaarMultiScale(is, minScale, maxScale);
            RgbImage im = RgbImageJ2se.toRgbImage(inputImage);
            RgbAvgGray toGray = new RgbAvgGray();
            toGray.push(im);
            faces = detectHaar.pushAndReturn(toGray.getFront());
            Image i = detectHaar.getFront();
            Gray8Rgb g2rgb = new Gray8Rgb();
            g2rgb.push(i);
            RgbImageJ2se conv = new RgbImageJ2se();
            //coloredFileImage is a file where you want to store the result create it yourself if required
            conv.toFile((RgbImage)g2rgb.getFront(), new File("./result").getCanonicalPath());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        return faces;
    }

    public List<BufferedImage> getAllFaceImages(BufferedImage orignalImage,List<Rect> masks) {

        List<BufferedImage> croppedImages = new ArrayList<BufferedImage>();

        for(int i = 0;i<masks.size();i++) {
            Rect rect = masks.get(i);
            BufferedImage dest =
                    orignalImage.getSubimage(rect.getTopLeft().getX(), rect.getTopLeft().getY(), rect.getWidth(), rect.getHeight());
            croppedImages.add(dest);
        }
        return croppedImages;
    }

    public List<BufferedImage> convertToGrayScale(List<BufferedImage> images) {

        List<BufferedImage> grayScaleImages = new ArrayList<BufferedImage>();
        for(int i = 0;i<images.size();i++) {

            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            BufferedImage image = op.filter(images.get(i), null);
            grayScaleImages.add(image);
        }
        return grayScaleImages;
    }
}
