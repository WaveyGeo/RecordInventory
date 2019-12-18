package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("genre/")
public class Genre {


    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertGenre(@FormDataParam("id") Integer id, @FormDataParam("genreName") String genreName){
        if (genreName.length() > 1) {
            try {

                if(id == null || genreName == null) {
                    System.out.println("thing/new id=" + id);
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");

                }
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Genres(GenreID, GenreName) VALUES (?,?)");
                ps.setInt(1, id);
                ps.setString(2, genreName);
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
    public String listAllGenres() {
        System.out.println("genre/list");
        JSONArray list = new JSONArray();
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("select GenreID, GenreName from Genres");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("GenreID", results.getInt(1));
                item.put("GenreName", results.getString(2));
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

    public String deleteUser(@FormDataParam("GenreID") Integer GenreID) {
        try {
            if (GenreID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete id=" + GenreID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Genres WHERE GenreID = ?");
            ps.setInt(1, GenreID);
            ps.execute();
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see the server console for more information.\"}";
        }

    }
}

