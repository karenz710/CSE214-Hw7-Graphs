import java.util.Comparator;

/**
 * The FollowersComparator class sorts a list of users by followers, with the greatest number of followers being first.
 * If they are the same then it is sorted alphabetically.
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
 * Student ID: 115931297
 * CSE214 - R02
 */
public class FollowersComparator implements Comparator<User> {
    /**
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer depending on
     * if the first user's number of followers is greater than, equal to, or less than the second user's number of followers
     * If followers are equal compares username alphabetically
     */
    @Override
    public int compare(User o1, User o2) {
        if(o1.getNumberOfFollowers() == o2.getNumberOfFollowers()){
            return o1.getUserName().compareTo(o2.getUserName());
        }else if(o1.getNumberOfFollowers()>o2.getNumberOfFollowers()){
            return -1;
        }
        return 1;
    }
}
