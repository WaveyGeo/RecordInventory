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

        openDatabase("RecordDatabase.db");
        Records.insertRecord(2,"The Stone Roses","5/5/89",1,1,1,1);
        User.listAllUsers();
        closeDatabase();


    }

}
