package Controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLOutput;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("user/")
public class User {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertUser(@FormDataParam("id") Integer id, @FormDataParam("userName") String userName, @FormDataParam("userPassword") String userPassword) {
        try {
            if (id == null || userName == null || userPassword == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("user/new id=" + id);

            if (!(userName.length() < 13 && userName.length() > 0 && userPassword.length() > 4)) {
                System.out.println("Please enter a valid username and a password longer than 4 letters");
                return "{\"error\": \"Please enter a valid username and a password longer than 4 letters\"}";
            }

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users(UserID, UserName, UserPassword) VALUES (?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, userName);
            ps.setString(3, userPassword);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
            return "{\"error\": \"Unable to create new user, please see the server console for more information.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)

    public String deleteUser(@FormDataParam("id") Integer id) {
        try {
            if (id == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete id=" + id);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE UserID = ?");
            ps.setInt(1, id);
            ps.execute();
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see the server console for more information.\"}";
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

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(@FormDataParam("id") Integer id,
                             @FormDataParam("name") String UserName,
                             @FormDataParam("password") String UserPassword) {

        System.out.println("Got here!");

        try {
            if (id == null || UserName == null || UserPassword == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP Request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET UserPassword = ? AND UserName = ?  WHERE UserID = ?");
            ps.setString(1, UserPassword);
            ps.setString(2, UserName);
            ps.setInt(3, id);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
        }
    }
}

