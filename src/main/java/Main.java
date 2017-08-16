import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import jjil.core.*;

import java.io.FilenameFilter;
import java.util.List;


public class Main {


    public static void storeCroppedDataset(String sourcePath,String destinationPath,int index) throws Exception{

        String src = sourcePath;
        String destination = destinationPath;

        BufferedImage inputImage= ImageIO.read(new FileInputStream(src));
        DigitalImageProcessing dip = new DigitalImageProcessing(src);
        List<Rect> masks = dip.findFaces();
        System.out.println("Masks Found");
        List<BufferedImage> extractedFaceImages = dip.getAllFaceImages(inputImage,masks);
        System.out.println("Faces Extracted");

        extractedFaceImages = dip.convertToGrayScale(extractedFaceImages);
        System.out.println("Converted to Grayscal");
        System.out.println(extractedFaceImages.size());

        //save data yourself
        for(int i = 0;i<extractedFaceImages.size();i++) {

            File directory = new File(destination);
            if(!directory.exists())
                directory.mkdir();

            String outputPath =  destinationPath + "/" + index  + i + ".jpg";
            System.out.println(outputPath);
            File outputfile = new File(outputPath);
            if(!outputfile.exists())
                outputfile.createNewFile();
            System.out.println(outputPath);
            ImageIO.write(extractedFaceImages.get(i), "jpg", outputfile);
        }
    }

    public static void main(String[] args) throws Exception {

        File dir = new File("/Users/archit.j/Desktop/dataset");
        String[] dirList = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });;

        for(String directories: dirList) {


            String path = "/Users/archit.j/Desktop/dataset/" + directories;
            File innerdir = new File(path);

            File[] files = innerdir.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                         return name.toLowerCase().endsWith(".jpg");
                    }
                });

            for(int i = 0;i<Math.min(files.length,50);i++) {
                File file = files[i];
                System.out.println(file);
                String destination = "/Users/archit.j/Desktop/dataset2/" + directories;
                String source = file.getAbsolutePath();
                System.out.println(source);
                storeCroppedDataset(source,destination,i);
                System.out.println("DONE");
            }
        }
    }

}