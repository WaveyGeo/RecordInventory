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

    public static void main(String[] args) {

        openDatabase("RecordDatabase.db");

        User.insertUser(1,"TestyBrair","PAs3");


    }

}
