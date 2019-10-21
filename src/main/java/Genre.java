import java.sql.PreparedStatement;

public class Genre {

    public static void insertGenre(int GenreID, String GenreName){

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Genres(GenreID, GenreName) VALUES (?,?)");
            ps.setInt(1, GenreID);
            ps.setString(2, GenreName);
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
        }
    }
}
