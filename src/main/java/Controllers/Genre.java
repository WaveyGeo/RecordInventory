package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Genre {

    public static void insertGenre(int GenreID, String GenreName){
        if (GenreName.length() > 1) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Genres(GenreID, GenreName) VALUES (?,?)");
                ps.setInt(1, GenreID);
                ps.setString(2, GenreName);
                ps.executeUpdate();
            } catch (Exception exception) {
                System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
            }
        } else {
            System.out.println("Please enter a valid genre name");
        }
    }
    public static void listAllGenres() {
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("select GenreID, GenreName from Genres");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int GenreID = results.getInt(1);
                String GenreName = results.getString(2);
                System.out.println("The Controllers.Genre is " + GenreName + " (Controllers.Genre ID: " + GenreID + ")");
            }
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
        }

    }
}
