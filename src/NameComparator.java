import java.util.Comparator;

/**
 * The NameComparator class sorts a list of users by name alphabetically.
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
 * CSE214 - R02
 */
public class NameComparator implements Comparator<User> {
    /**
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer depending on
     * if the first user's UserName is less than, equal to, or greater than the second user's UserName
     */
    @Override
    public int compare(User o1, User o2) {
        return o1.getUserName().compareTo(o2.getUserName());
    }


}
