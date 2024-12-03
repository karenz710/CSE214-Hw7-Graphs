import java.util.Comparator;

/**
 * The FollowersComparator class sorts a list of users by followings, with the greatest number of followings being first.
 * If they are the same then it is sorted alphabetically.
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
 * Student ID: 115931297
 * CSE214 - R02
 */
public class FollowingComparator implements Comparator<User> {
    /**
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer depending on
     * if the first user's number of followings is greater than, equal to, or less than the second user's number of followings
     * If followings are equal compares username alphabetically.
     */
    @Override
    public int compare(User o1, User o2) {
        if(o1.getNumberOfFollowings() == o2.getNumberOfFollowings()){
            return o1.getUserName().compareTo(o2.getUserName());
        }else if(o1.getNumberOfFollowings()>o2.getNumberOfFollowings()){
            return -1;
        }
        return 1;
    }
}
