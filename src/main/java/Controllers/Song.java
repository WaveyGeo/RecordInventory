package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Song {

    public static void insertSong(int SongID, String SongName, int RecordID){
        if (SongName.length() > 0) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Songs(SongID, SongName, RecordID) VALUES (?,?,?)");
                ps.setInt(1, SongID);
                ps.setString(2, SongName);
                ps.setInt(3, RecordID);
                ps.executeUpdate();
            } catch (Exception exception) {
                System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
            }
        }
        else {
            System.out.println("Please enter a valid song name and ensure the song ID is an integer");
        }
    }

    public static void listAllSongs() {
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT SongID, SongName, RecordID from Songs");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int SongID = results.getInt(1);
                String SongName = results.getString(2);
                int RecordID = results.getInt(3);
                System.out.println("The song is " + SongName + " (RecordID: " + RecordID + " (SongID: " + SongID + ")");
            }
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
        }

    }
}
