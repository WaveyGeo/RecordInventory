package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Artist {

    public static void insertArtist(int ArtistID, String ArtistName, String ArtistPhoto, int GenreID){
        if (ArtistName.length() > 0) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Artists(ArtistID, ArtistName, ArtistPhoto, GenreID) VALUES (?,?,?,?)");
                ps.setInt(1, ArtistID);
                ps.setString(2, ArtistName);
                ps.setString(3, ArtistPhoto);
                ps.setInt(4, GenreID);
                ps.executeUpdate();
            } catch (Exception exception) {
                System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
            }
        } else {
            System.out.println("Please enter a valid artist name and ID");
        }
    }

    public static void listAllArtists() {
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ArtistID, ArtistName, ArtistPhoto, GenreID from Artists");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int ArtistID = results.getInt(1);
                String ArtistName = results.getString(2);
                String ArtistPhoto = results.getString(3);
                int GenreID = results.getInt(4);
                System.out.println("The Controllers.Artist is " + ArtistName + ", Controllers.Artist Photo URL: " + ArtistPhoto + " (GenreID: " + GenreID + " (ArtistID: " + ArtistID + ")");
            }
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
        }

    }
}
