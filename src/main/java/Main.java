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
            System.out.println("******Disconnected from database******");
        } catch (Exception exception) {
            System.out.println("******Database disconnection error: " + exception.getMessage() + "******");
        }
    }
    private static void listAll() {
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = db.prepareStatement("select UserID, UserName, UserPassword from Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int userID = results.getInt(1);
                String userName = results.getString(2);
                String userPassword = results.getString(3);
                System.out.println("The user is " + userName + ", Password: " + userPassword + " (user ID: " + userID + ")");
            }
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
        }

    }

    public static void main(String[] args) {

        openDatabase("RecordDatabase.db");
        User.insertUser(1,"TestyBrair","PAs3");
        listAll();
        closeDatabase();


    }

}
