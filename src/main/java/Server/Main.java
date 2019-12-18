package Server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;

import java.sql.*;

public class Main {

    public static Connection db;

    private static void openDatabase(String dbFile) {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("****** DATABASE CONNECTION SUCCESSFULLY ESTABLISHED ******");
        } catch (Exception exception) {
            System.out.println("****** DATABASE CONNECTION ERROR: " + exception.getMessage() + " ******");
        }
    }
    private static void closeDatabase(){
        try {
            db.close();
            System.out.println("****** DISCONNECTED FROM DATABASE ******");
        } catch (Exception exception) {
            System.out.println("****** DATABASE DISCONNECTION ERROR: " + exception.getMessage() + " ******");
        }
    }


    public static void main(String[] args) {
        openDatabase("RecordDatabase2.db");

        ResourceConfig config = new ResourceConfig();
        config.packages("Controllers");
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            System.out.println("Server successfully started.");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
