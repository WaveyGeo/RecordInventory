import java.sql.PreparedStatement;

public class Song {

    public static void insertSong(int SongID, String SongName, int RecordID){
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
}
