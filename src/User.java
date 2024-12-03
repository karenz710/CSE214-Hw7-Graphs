import java.io.Serializable;

/**
 * The User class that contains the name of the user (String), indexPos (int), and static variable userCount (int).
 * Every time a new User object is created (the constructor is invoked), assign indexPos of the object to be the userCount,
 * and then increment userCount.
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
 * Student ID: 115931297
 * CSE214 - R02
 */
public class User implements Serializable {
    private String userName;
    private int indexPos;
    private static int userCount;
    private int numberOfFollowers;
    private int numberOfFollowings;


    public User() {
    }

    /**
     * Constructor
     * @param userName
     */
    public User(String userName){
        this.userName = userName;
        indexPos = userCount;
        userCount++;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIndexPos() {
        return indexPos;
    }

    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    public static int getUserCount() {
        return userCount;
    }

    public static void setUserCount(int userCount) {
        User.userCount = userCount;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public int getNumberOfFollowings() {
        return numberOfFollowings;
    }

    public void setNumberOfFollowings(int numberOfFollowings) {
        this.numberOfFollowings = numberOfFollowings;
    }

    @Override
    public String toString() {
        return String.format("%-30s %-20d %-20d\n", userName, numberOfFollowers, numberOfFollowings);
    }
}
