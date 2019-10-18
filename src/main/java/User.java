import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.sql.SQLOutput;

public class User {


    public static void insertUser(int UserID, String UserName, String UserPassword){
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users(UserID, UserName, UserPassword) VALUES (?,?,?)");
            ps.setInt(1, UserID);
            ps.setString(2, UserName);
            ps.setString(3, UserPassword);
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
        }
    }

    public static void deleteUser(String UserName, String UserPassword) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE UserName = ? AND UserPassword = ?");
            ps.setString(1, UserName);
            ps.setString(2, UserPassword);
            ps.executeUpdate();
            } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
        }

    }

    public static void listAllUsers() {
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("select UserID, UserName, UserPassword from Users");

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


}

