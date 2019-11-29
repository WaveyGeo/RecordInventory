package Controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLOutput;

import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class User {


    public static void insertUser(int UserID, String UserName, String UserPassword){
        if (UserName.length() < 13 && UserName.length() > 0 && UserPassword.length() > 4) {
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
        else {
            System.out.println("Please enter a valid username and a password longer than 4 letters");
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

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listAllUsers() {
        System.out.println("user/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("select UserID, UserName, UserPassword from Users");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("userID", results.getInt(1));
                item.put("userName", results.getString(2));
                item.put("userPassword", results.getString(3));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
            return"{\"error\": \"Unable to list users, please see server console for more info.\"}";
        }
    }
}

