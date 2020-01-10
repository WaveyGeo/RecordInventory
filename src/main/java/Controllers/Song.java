package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("song/")
public class Song {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertSong(@FormDataParam("id") Integer SongID, @FormDataParam("name") String SongName, @FormDataParam("Record") Integer RecordID) {
        if (SongName.length() > 1) {
            try {

                if(SongID == null || RecordID == null || SongName == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("song/new id=" + SongID);
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Songs(SongID, SongName, RecordID) VALUES (?,?,?)");
                ps.setInt(1,SongID);
                ps.setString(2, SongName);
                ps.setInt(3, RecordID);
                ps.execute();
                return "{\"status\": \"OK\"}";
            } catch (Exception exception) {
                System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
                return "{\"error\": \"Unable to create new item, please see server console for more information.\"}";
            }
        } else {
            System.out.println("Please enter a valid genre name");
            return "{\"error\": \"Please enter a valid genre name]\"}";
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listAllSongs() {
        System.out.println("song/list");
        JSONArray list = new JSONArray();
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("select SongID, SongName,RecordID from Songs");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("SongID", results.getInt(1));
                item.put("SongName", results.getString(2));
                item.put("RecordID", results.getInt(3));
                list.add(item);

            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateSong(@FormDataParam("id") Integer id,
                              @FormDataParam("name") String SongName,
                             @FormDataParam("Record") Integer RecordID) {

        System.out.println("Got here!");

        try {
            if (id == null || SongName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP Request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Songs SET SongName = ? AND RecordID = ? WHERE SongID = ?");
            ps.setInt(1, id);
            ps.setString(2, SongName);
            ps.setInt(3, RecordID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
        }
    }


    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)

    public String deleteSong(@FormDataParam("id") Integer SongID) {
        try {
            if (SongID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete id=" + SongID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Songs WHERE SongID = ?");
            ps.setInt(1, SongID);
            ps.execute();
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see the server console for more information.\"}";
        }

    }
}
