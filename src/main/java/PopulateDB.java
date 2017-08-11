import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by archit.j on 09/08/17.
 */
public class PopulateDB {

    public static void main(String args[]) {

        File dir = new File("/Users/archit.j/Desktop/dataset");
        String[] dirList = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });;

        int celbId = -1;
        for(String directories: dirList) {

            String path = "/Users/archit.j/Desktop/dataset/" + directories;
            File innerdir = new File(path);
            File[] files = innerdir.listFiles(new FileFilter() {
                boolean first = true;
                public boolean accept(final File pathname) {
                    if (first) {
                        first = false;
                        return true;
                    }
                    return false;
                }
            });

            //System.out.println(files[0].getAbsolutePath());

            celbId = celbId +1;
            ObjectId imageId = MongoDBWrapper.uploadImage(files[0].getAbsolutePath(),Integer.toString(celbId));
            Random random = new Random();

            HashMap<String,Object> hashMap = new HashMap<String,Object>();
            hashMap.put("id",celbId);
            hashMap.put("name",innerdir.getName());
            hashMap.put("age",random.nextInt(200));
            hashMap.put("gender","MALE");
            hashMap.put("ethinicity","ASIAN");

            MongoDBWrapper.insertADocument(hashMap);

        }

    }
}
