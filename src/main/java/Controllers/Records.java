package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Path("records/")
public class Records {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String testAPI() {
        return "I got here!";
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertRecord(@FormDataParam("id") Integer RecordID,
                               @FormDataParam("name") String RecordName,
                               @FormDataParam("release") String RecordRelease,
                               @FormDataParam("Artist") Integer ArtistID,
                               @FormDataParam("Genre") Integer GenreID,
                               @FormDataParam("User") Integer UserID,
                               @FormDataParam("Song") Integer SongID){

        System.out.println("Got here (new)!");

        try {
            if (RecordID == null || RecordName == null || RecordRelease == null || ArtistID == null || GenreID == null || UserID == null || SongID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            if (RecordName.length() == 0) {
                System.out.println("Please enter a valid record name and release date and ensure the record ID is numeric");
                return "{\"error\"{\"Unable to create new item, please see server console for more information.\"}";
            }

            System.out.println("record/new id=" + RecordID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Records(RecordID, RecordName, RecordRelease, ArtistID, GenreID, UserID, SongID ) VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, RecordID);
            ps.setString(2, RecordName);
            ps.setString(3, RecordRelease);
            ps.setInt(4, ArtistID);
            ps.setInt(5, GenreID);
            ps.setInt(6, UserID);
            ps.setInt(7, SongID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
            return "{\"error\"{\"Unable to create new item, please see server console for more information.\"}";
        }


    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateRecord(@FormDataParam("id") Integer RecordID,
                               @FormDataParam("name") String RecordName,
                               @FormDataParam("release") String RecordRelease,
                               @FormDataParam("Artist") Integer ArtistID,
                               @FormDataParam("Genre") Integer GenreID,
                               @FormDataParam("User") Integer UserID,
                               @FormDataParam("Song") Integer SongID,
                               @FormDataParam("sleeve") String RecordSleeve) {

        System.out.println("Got here!");

        try {
            if (RecordID == null || RecordName == null || RecordRelease == null || ArtistID == null || GenreID == null || UserID == null || SongID == null || RecordSleeve == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP Request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Records SET RecordName = ?, RecordRelease = ?, RecordSleeve = ?, GenreID = ?, ArtistID = ?, UserID = ?, SongID = ? WHERE RecordID = ?");
            ps.setString(1, RecordName);
            ps.setString(2, RecordRelease);
            ps.setString(3, RecordSleeve);
            ps.setInt(4, GenreID);
            ps.setInt(5, ArtistID);
            ps.setInt(6, UserID);
            ps.setInt(7, SongID);
            ps.setInt(8, RecordID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
        }
    }


    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listAllRecords() {
        System.out.println("records/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("select RecordID, RecordName, RecordRelease, RecordSleeve, UserID, SongID, GenreID, ArtistID from Records");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("id", results.getInt(1));
                item.put("name", results.getString(2));
                item.put("release", results.getString(3));
                item.put("sleeve", results.getString(4));
                item.put("User", results.getInt(5));
                item.put("Song", results.getInt(6));
                item.put("Genre", results.getInt(7));
                item.put("Artist", results.getString(8));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
            return "{\"error\": \"Unable to list records, please see the server console for more information.\"}";
        }

    }
}
