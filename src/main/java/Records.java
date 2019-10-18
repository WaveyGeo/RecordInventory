
import java.sql.PreparedStatement;

public class Records {

    public static void insertRecord(int RecordID, String RecordName, String RecordRelease, int UserID, int ArtistID, int GenreID, int SongID){
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
}
