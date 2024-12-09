import java.io.Serializable;

/**
 * The User class that contains the name of the user (String), indexPos (int), and static variable userCount (int).
 * Every time a new User object is created (the constructor is invoked), assign indexPos of the object to be the userCount,
 * and then increment userCount.
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
 * CSE214 - R02
 */
public class User implements Serializable {
    private String userName;
    private int indexPos; // indexpos in the arraylist of the user
    private static int userCount; // total usercount in the system
    private int numberOfFollowers; // number of followers of user
    private int numberOfFollowings; // number of followings of user

    /**
     * default constructor
     */
    public User() {
    }

    /**
     * Constructor with userName defined the indexPos and UserCount is updated
     *
     * @param userName
     */
    public User(String userName){
        this.userName = userName;
        indexPos = userCount;
        userCount++;
    }

    /**
     * Getter method for userName
     *
     * @return the name of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for userName
     * @param userName the name of the user to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method
     * @return the index position
     */
    public int getIndexPos() {
        return indexPos;
    }

    /**
     * Setter method for indexPos
     * @param indexPos the index position to set
     */
    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    /**
     * Getter method userCount
     * @return the userCount
     */
    public static int getUserCount() {
        return userCount;
    }

    /**
     * Setter method for userCount
     * @param userCount the number of userCount to set
     */
    public static void setUserCount(int userCount) {
        User.userCount = userCount;
    }

    /**
     * Getter method numberOfFollowers
     * @return the numberOfFollowers
     */
    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    /**
     * Setter method for numberOfFollowers
     * @param numberOfFollowers the number of followers to set
     */
    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    /**
     * Getter method numberOfFollowings
     * @return the numberOfFollowings
     */
    public int getNumberOfFollowings() {
        return numberOfFollowings;
    }

    /**
     * Setter method for numberOfFollowings
     * @param numberOfFollowings the number of followings to set
     */
    public void setNumberOfFollowings(int numberOfFollowings) {
        this.numberOfFollowings = numberOfFollowings;
    }

    /**
     * Returns a string representation of the user, formatted with the user's name,
     * the number of followers, and the number of followings.
     *
     * @return a formatted string containing the user's name, number of followers, and number of followings
     */
    @Override
    public String toString() {
        return String.format("%-30s %-20d %-20d\n", userName, numberOfFollowers, numberOfFollowings);
    }
}
