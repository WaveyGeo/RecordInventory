import java.sql.PreparedStatement;

public class User {
    public static void insertUser(int UserID, String UserName, String UserPassword){



        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users(UserID, UserName, UserPassword) VALUES (?,?,?)");
            ps.setInt(1, UserID);
            ps.setString(2, UserName);
            ps.setString(3, UserPassword);
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
        }
    }
}
