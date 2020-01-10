package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("artist/")
public class Artist {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertArtist(@FormDataParam("id") Integer ArtistID, @FormDataParam("name") String ArtistName, @FormDataParam("photo") String ArtistPhoto, @FormDataParam("Genre") Integer GenreID) {
        if (ArtistName.length() > 1) {
            try {

                if(ArtistID == null || GenreID == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("Artist/new id=" + ArtistID);
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Artists(ArtistID, ArtistName, ArtistPhoto, GenreID) VALUES (?,?,?,?)");
                ps.setInt(1,ArtistID);
                ps.setString(2, ArtistName);
                ps.setString(3, ArtistPhoto);
                ps.setInt(4, GenreID);
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
    public String listArtist() {
        System.out.println("artist/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ArtistID, ArtistName, ArtistPhoto, GenreID FROM Artists");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("id", results.getInt(1));
                item.put("name", results.getString(2));
                item.put("photo", results.getString(3));
                item.put("Genre", results.getInt(4));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more information.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)

    public String deleteArtist(@FormDataParam("id") Integer ArtistID) {
        try {
            if (ArtistID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete id=" + ArtistID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Artists WHERE ArtistID = ?");
            ps.setInt(1, ArtistID);
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
    public String updateArtist(@FormDataParam("id") Integer ArtistID, @FormDataParam("name") String ArtistName, @FormDataParam("photo") String ArtistPhoto, @FormDataParam("Genre") Integer GenreID) {

        System.out.println("Got here!");

        try {
            if (ArtistID == null || GenreID == null || ArtistName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP Request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Genres SET GenreName = ? WHERE GenreID = ?");
            ps.setInt(1, ArtistID);
            ps.setString(2, ArtistName);
            ps.setString(3, ArtistPhoto);
            ps.setInt(4, GenreID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
        }
    }
}
