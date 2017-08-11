import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by archit.j on 09/08/17.
 */
public class MongoDBWrapper {

    private static String DB_IP = "localhost";
    private static int PORT = 27017;
    private String userName = "sampleUser";
    private static String database = "celebrities3";
    private String password = "password";
    private static MongoClient mongo = new MongoClient(DB_IP ,PORT);
    private MongoCredential credential = MongoCredential.createCredential(userName,database,password.toCharArray());

    //private because singleton pattern
    private MongoDBWrapper() {}

    //returns an instance of database specified
    public static MongoDatabase getMongoDatabase() {
        System.out.println("Connecting...");
        return mongo.getDatabase(database);
    }


    public static void insertADocument(HashMap<String, Object> data) {
        MongoCollection<Document> collection = getMongoDatabase().getCollection(database);
        Document document = new Document();

        for(String key : data.keySet()) {
            document.append(key,data.get(key));
        }
        collection.insertOne(document);
        System.out.println("Document inserted successfully");
    }


    public static void getAllDocuments() {
        MongoCollection<Document> collection = getMongoDatabase().getCollection(database);
        System.out.println("Collection sampleCollection selected successfully");

        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;

        // Getting the iterator
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
            i++;
        }
    }

    //write a find function ( to find a specific document)
    //temporary
    public static void findAllDocumentsWithGivenID(int _id) {
        MongoCollection<Document> collection = getMongoDatabase().getCollection(database);

    }

    // Upload File
    public static ObjectId uploadImage(String filePath,String fileName) {
        System.out.println("Calling upload...");
        ObjectId fileId = null;
        try {
            MongoDatabase mongodatabase = mongo.getDatabase(database);
            GridFSBucket gridBucket = GridFSBuckets.create(mongodatabase);
            InputStream inputStream = new FileInputStream(new File(filePath));
            // Create some custom options
            GridFSUploadOptions uploadOptions = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", "image").append("content_type", "image/jpg"));
            fileId = gridBucket.uploadFromStream(fileName, inputStream, uploadOptions);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileId;
    }

    public static void find(ObjectId objectId) {
        System.out.println("Calling find...");

        try {
            MongoDatabase db = mongo.getDatabase(database);
            GridFSBucket gridBucket = GridFSBuckets.create(db);

            GridFSFile gridFSFile = gridBucket.find(eq("_id",objectId)).first();
            System.out.println("File Name:- " + gridFSFile.getFilename());
            System.out.println("Meta Data:- " + gridFSFile.getMetadata());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void download(String fileName) {
        System.out.println("Calling download...");
        //  MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

        try {
            MongoDatabase db = mongo.getDatabase(database);
            GridFSBucket gridBucket = GridFSBuckets.create(db);

            FileOutputStream fileOutputStream = new FileOutputStream("/Users/archit.j/Desktop/myImg.jpg");
            gridBucket.downloadToStream(fileName, fileOutputStream);
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static  void main(String args[]) {

        HashMap<String,Object> hm = new HashMap<String,Object>();
        //hm.put("Hello1","Archit1");
        //  MongoDBWrapper.insertADocument(hm);
        MongoDBWrapper.getAllDocuments();
        //  ObjectId id = MongoDBWrapper.uploadImage("/Users/archit.j/Desktop/sampleTraining/adam brody/88.jpg","adam brody3");
        //MongoDBWrapper.find(id);
        MongoDBWrapper.download(Integer.toString(1));
    }


}

