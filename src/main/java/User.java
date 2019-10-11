import java.sql.PreparedStatement;

public class User {
    public static void insertUser(int UserID, String UserName, String UserPassword){



        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users(UserID, UserName, UserPassword) VALUES (?,?,?)");

        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
        }
    }
}
