import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class JavaMongoExample {

    public static void main(String[] args) {
        // Creating a Mongo client
        try (MongoClient mongo = new MongoClient("localhost", 27017);) {

            // Accessing the database
            MongoDatabase database = mongo.getDatabase("myDb");

            MongoCollection<Document> collection = database.getCollection("myCollection");
            System.out.println("Collection created successfully");


            collection.createIndex(new Document("title", 1), new IndexOptions().unique(true));

            Document document1 = new Document("title", "MongoDB")
                    .append("description", "database");
            Document document2 = new Document("title", "Cassandra")
                    .append("description", "database");
            Document document3 = new Document("title", "MongoDB")
                    .append("description", "database");

            List<Document> list = new ArrayList<>();
            list.add(document1);
            list.add(document2);
            list.add(document3);


            collection.insertMany(list);
            System.out.println("Document inserted successfully");

            System.out.println("Databases in Mongo!!");
            mongo.listDatabaseNames().iterator().forEachRemaining(System.out::println);

            collection.find().iterator().forEachRemaining(System.out::println);

            System.out.println("Updating all document with title MongoDB !!");

            collection.updateMany(Filters.eq("title", "MongoDB"), Updates.set("description", "No SQL DB"));

            collection.find().iterator().forEachRemaining(System.out::println);

            System.out.println("Deleting all document with title Cassandra !!");
            collection.deleteMany(Filters.eq("title", "Cassandra"));

            collection.find().iterator().forEachRemaining(System.out::println);

            System.out.println("List collections");
            for (String name : database.listCollectionNames()) {
                System.out.println(name);
            }

            System.out.println("Dropping collection !!");
            collection.drop();
            System.out.println("Collection dropped successfully");

            System.out.println("List collections");
            for (String name : database.listCollectionNames()) {
                System.out.println(name);
            }
        }
    }

}
