package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("friend/")
public class UserFriend {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertFriend(@FormDataParam("id") Integer FriendID,
                               @FormDataParam("name") String FriendName,
                               @FormDataParam("User") Integer UserID){

        System.out.println("Got here (new)!");

        try {
            if (FriendID == null || FriendName == null || UserID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            if (FriendName.length() == 0) {
                System.out.println("Please enter a valid record name and release date and ensure the record ID is numeric");
                return "{\"error\"{\"Unable to create new item, please see server console for more information.\"}";
            }

            System.out.println("record/new id=" + FriendID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Records(FriendID, FriendName, UserID) VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, FriendID);
            ps.setString(2,FriendName);
            ps.setInt(6, UserID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
            return "{\"error\"{\"Unable to create new item, please see server console for more information.\"}";
        }


    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listAllFriends() {
        System.out.println("Friends/list");
        JSONArray list = new JSONArray();
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("select FriendID, FriendName, UserID from UserFriends");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("FriendID", results.getInt(1));
                item.put("FriendName", results.getString(2));
                item.put("UserID", results.getInt(3));
                list.add(item);

            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)

    public String deleteFriend(@FormDataParam("id") Integer FriendID) {
        try {
            if (FriendID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Friends/delete id=" + FriendID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM UserFriends WHERE FriendID = ?");
            ps.setInt(1, FriendID);
            ps.execute();
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see the server console for more information.\"}";
        }

    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateFriend(@FormDataParam("id") Integer FriendID,
                              @FormDataParam("name") String FriendName,
                               @FormDataParam("User") Integer UserID) {

        System.out.println("Got here!");

        try {
            if (FriendID == null || FriendName == null || UserID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP Request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE UserFriends SET FriendName = ? WHERE FriendID = ?");
            ps.setInt(1, FriendID);
            ps.setString(2, FriendName);
            ps.setInt(3, UserID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
        }
    }
}
