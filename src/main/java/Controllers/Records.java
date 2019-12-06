package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Records {

    public static void insertRecord(int RecordID, String RecordName, String RecordRelease, int UserID, int ArtistID, int GenreID, int SongID){
        if (RecordName.length() > 0) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Records(RecordID, RecordName, RecordRelease, UserID, ArtistID, GenreID, SongID ) VALUES (?,?,?,?,?,?,?)");
                ps.setInt(1, RecordID);
                ps.setString(2, RecordName);
                ps.setString(3, RecordRelease);
                ps.setInt(4, UserID);
                ps.setInt(5, ArtistID);
                ps.setInt(6, GenreID);
                ps.setInt(7, SongID);
                ps.executeUpdate();
            } catch (Exception exception) {
                System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
            }
        }
        else {
            System.out.println("Please enter a valid record name and release date and ensure the record ID is numeric");
        }
    }

    public static void updateRecord(int RecordID, String RecordName, String RecordRelease, String RecordSleeve, int ArtistID, int GenreID) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Records SET RecordName = ?, RecordRelease = ?, RecordSleeve = ?, GenreID = ?, ArtistID = ? WHERE RecordID = ?");
            ps.setString(1, RecordName);
            ps.setString(2, RecordRelease);
            ps.setString(3, RecordSleeve);
            ps.setInt(4, GenreID);
            ps.setInt(5, ArtistID);
            ps.setInt(6, RecordID);

            ps.execute();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void listAllRecords() {
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("select RecordID, RecordName, RecordRelease, RecordSleeve from Records");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int RecordID = results.getInt(1);
                String RecordName = results.getString(2);
                String RecordRelease = results.getString(3);
                String RecordSleeve = results.getString(4);
                System.out.println("The Record is " + RecordName + ", Release date: " + RecordRelease + ", RecordSleeve: " + RecordSleeve + " (Record ID: " + RecordID + ")");
            }
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
        }

    }
}
