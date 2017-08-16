import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import jjil.core.*;
import java.util.List;


public class Main {


    public static void main(String[] args) throws Exception {
     //   BufferedImage bi = ImageIO.read(new FileInputStream("/Users/archit.j/Desktop/mongoarchitj/src/main/java/5.jpg"));
       // findFaces(bi, 1, 100, new File("./result.jpg")); // change as needed

        String src = "/Users/archit.j/Desktop/mongoarchitj/src/main/java/4.jpg";
        String destination = "./result.jpg";

        BufferedImage inputImage= ImageIO.read(new FileInputStream(src));
        DigitalImageProcessing dip = new DigitalImageProcessing(src);
        List<Rect> masks = dip.findFaces();
        List<BufferedImage> extractedFaceImages = dip.getAllFaceImages(inputImage,masks);


        //BufferedImage result = ImageIO.read(new FileInputStream("/Users/archit.j/Desktop/mongoarchitj/src/main/java/5.jpg"));
        //BufferedImage dest = result.getSubimage(faces.get(0).getTopLeft().getX(),faces.get(0).getTopLeft().getY(),faces.get(0).getWidth(), faces.get(0).getHeight());

        //ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        //ColorConvertOp op = new ColorConvertOp(cs, null);
        //BufferedImage image = op.filter(dest, null);

        extractedFaceImages = dip.convertToGrayScale(extractedFaceImages);

        //save data yourself

        for(int i = 0;i<extractedFaceImages.size();i++) {

            String outputPath = "image" + i + ".jpg";
            System.out.println(outputPath);
            File outputfile = new File(outputPath);
            ImageIO.write(extractedFaceImages.get(i), "jpg", outputfile);
        }
    }

}