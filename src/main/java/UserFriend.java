import java.sql.PreparedStatement;

public class UserFriend {

    public static void insertUserFriend(int UserID, String FriendName, int FriendID){
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO UserFriends(UserID, FriendName, FriendID) VALUES (?,?,?)");
            ps.setInt(1, UserID);
            ps.setString(2, FriendName);
            ps.setInt(3, FriendID);
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
        }
    }
}
