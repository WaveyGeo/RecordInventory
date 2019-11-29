package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserFriend {

    public static void insertUserFriend(int UserID, String FriendName, int FriendID){
        if (FriendName.length() > 0) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO UserFriends(UserID, FriendName, FriendID) VALUES (?,?,?)");
                ps.setInt(1, UserID);
                ps.setString(2, FriendName);
                ps.setInt(3, FriendID);
                ps.executeUpdate();
            } catch (Exception exception) {
                System.out.println("****** DATABASE ERROR: " + exception.getMessage() + " ******");
            }
        } else {
            System.out.println("Please enter a valid name and a valid FriendID");
        }
    }

    public static void listAllFriends() {
        // code to get data from, write to the database etc goes here!
        try {
            PreparedStatement ps = Main.db.prepareStatement("select FriendID, FriendName from UserFriends");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int FriendID = results.getInt(1);
                String FriendName = results.getString(2);
                System.out.println("The Friend is " + FriendName + " (Friend ID: " + FriendID + ")");
            }
        } catch (Exception exception) {
            System.out.println("Error In Database." + exception.getMessage());
        }

    }
}
