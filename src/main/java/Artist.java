import java.sql.PreparedStatement;

public class Artist {

    public static void insertArtist(int ArtistID, String ArtistName, String ArtistPhoto, int GenreID){
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
    }
}
