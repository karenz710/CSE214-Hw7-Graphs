import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
import java.io.File;

/**
 * The FollowerGraph class contains an adjacency matrix for the users.
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
 * Student ID: 115931297
 * CSE214 - R02
 */
public class FollowerGraph implements Serializable {
    // contains all users of the service. This list will be used to map the correspondence between a name and its position in the adjacency matrix.
    private ArrayList<User> users;
    public static final int MAX_USERS = 100; // maximum number of users
    // adjacency matrix. connection[i][j] represents a link from user at i (starting point), to a user at j (ending point). True if a link exits, false if there is no link.
    private boolean[][] connections = new boolean[MAX_USERS][MAX_USERS];

    /**
     * Constructor for FollowGraph object. Initializes all declared variables.
     */
    public FollowerGraph(){
        users = new ArrayList<>();
    }

    /**
     * Adds a new user if not already exist
     *
     * @param userName the username
     */
    public void addUser(String userName){
        boolean userExist = false;
        for(User user : users) {
            if(user.getUserName().equals(userName)){
                userExist = true;
                break;
            }
        }
        if(!userExist && User.getUserCount()<100) {
            users.add(new User(userName));
            System.out.println(userName + " has been added");
        }
    }

    // Helper Method
    public boolean validUsers(String userFrom, String userTo) {
        boolean userFromExist = false;
        boolean userToExist = false;
        for (User user : users) {
            if (user.getUserName().equals(userFrom)) {
                userFromExist = true;
            }
            if (user.getUserName().equals(userTo)) {
                userToExist = true;
            }
        }
        return userFromExist && userToExist;
    }

    /**
     * Returns the corresponding user object given the userName string
     *
     * @param userFrom user at starting point
     * @param userTo user at ending point
     *
     * <dt>Preconditions:
     *    <dd> Assumes users are in the ArrayList
     * @return Returns the corresponding user object given the userName string. Index 0 is userFrom, Index 1 is userTo if exist
     */
    public User[] getUser(String userFrom, String userTo){
        User userFromUser = new User();
        User userToUser = new User();
        if(validUsers(userFrom, userTo)){ // we know both users exist
            for (User user: users){
                if(user.getUserName().equals(userFrom))
                    userFromUser = user;
                if(user.getUserName().equals(userTo))
                    userToUser = user;
            }
            return(new User[]{userFromUser, userToUser});
        }
        // not valid
        return(new User[]{null, null});
    }

    /**
     * Add directed connection starting from userFrom (follower) to userTo (following)
     *
     * @param userFrom user at starting point
     * @param userTo user at ending point
     *
     * <dt>Preconditions:
     *    <dd> Appropriate users are given
     * <dt>Postconditions:
     *    <dd> Updated adjacency matrix to true
     */
    public void addConnection(String userFrom, String userTo){
        User[] indices = getUser(userFrom, userTo);
        if(Arrays.equals(indices, new User[]{null, null})){
            // pass
        }else{
            if(!connections[indices[0].getIndexPos()][indices[1].getIndexPos()]) {
                connections[indices[0].getIndexPos()][indices[1].getIndexPos()] = true;
                indices[0].setNumberOfFollowings(indices[0].getNumberOfFollowings() + 1);
                indices[1].setNumberOfFollowers(indices[1].getNumberOfFollowers() + 1);
            }
        }
    }

    //fix
    /**
     * Removes a given user and all the connections from and to that user
     *
     * @param userName the username
     */
    public void removeUser(String userName){
        boolean userExist = false;
        User userToRemove = new User();
        for(User user : users) {
            if(user.getUserName().equals(userName)){
                userExist = true;
                userToRemove = user;
                break;
            }
        }
        // update adjacency matrix and followers and followings
        if(userExist) {
            System.out.println(userName + " has been added");
        }
    }

    /**
     * Remove directed connection starting from userFrom to userTo
     *
     * @param userFrom user at starting point
     * @param userTo user at ending point
     *
     * <dt>Preconditions:
     *    <dd> Appropriate users are given and there exists a connection
     * <dt>Postconditions:
     *    <dd> Updated adjacency matrix
     */
    public void removeConnection(String userFrom, String userTo){
        User[] indices = getUser(userFrom, userTo);
        if(Arrays.equals(indices, new User[]{null, null})){
            // pass
        }else {
            if(connections[indices[0].getIndexPos()][indices[1].getIndexPos()]){
                connections[indices[0].getIndexPos()][indices[1].getIndexPos()] = false;
                indices[0].setNumberOfFollowings(indices[0].getNumberOfFollowings() - 1);
                indices[1].setNumberOfFollowers(indices[1].getNumberOfFollowers() - 1);
            }
        }
    }

    /**
     * Finds the shortest path between users.
     * Return the path as well as the total number of users.
     * Concatenate these 2 pieces of information using a special delimiter such as "###".
     * Use string.split() to get the result for each piece of information.
     *
     * @param userFrom user at starting point
     * @param userTo user at ending point
     *
     * <dt>Preconditions:
     *    <dd> Appropriate users are given and there exists a connection
     *
     * @return a String representation of the shortest path
     */
    public String shortestPath(String userFrom, String userTo){
        StringBuilder shortestPath = new StringBuilder();
        return shortestPath.toString();
    }

    /**
     * Finds all paths between the users. DFS graph traversals and use auxiliary memory.
     *
     * @param userFrom user at starting point
     * @param userTo user at ending point
     *
     * <dt>Preconditions:
     *    <dd> Appropriate users are given and there exists a connection
     *
     * @return a list of strings, each representing one of the paths, containing all paths in alphabetical order
     */
    public List<String> allPaths(String userFrom, String userTo){
        List<String> result = new ArrayList<>();
        for(int i = 0; i < User.getUserCount(); i++) {

        }
        return result;
    }


    /**
     * Prints all users in the order based on the given Comparator
     *
     * @param comp
     */
    public void printAllUsers(Comparator<User> comp) {
        List<User> sortedUsers = new ArrayList<>(users); // Assuming 'users' is your collection of User objects
        Collections.sort(sortedUsers, comp);
        System.out.println("Users: ");
        System.out.printf("%-25s %-20s %-20s\n", "User Name", "Number of Followers", "Number of Following");
        for (User user : sortedUsers) {
            System.out.println(user);
        }
    }


    /**
     * Prints all the followers of the given user (See sample i/o for format)
     * @param userName
     */
    public void printAllFollowers(String userName) {
    }

    /**
     * prints all the users that the given user is following
     * @param userName
     */
    public void printAllFollowing(String userName){

    }

    /**
     * finds and returns all the loops (cycles) in the graph
     * (A loop from A to B to C and back to A would be written as A -> B -> C -> A,
     * additionaly count each loop once,
     * meaning that A -> B -> C -> A and B -> C -> A -> B, should not be counted as seperate loops)
     * @return
     */
    public List<String> findAllLoops(){
        List<String> result = new ArrayList<>();
        for(int i = 0; i<User.getUserCount(); i++){
            depthFirstSearch(i, new Stack<>(), result, new boolean[User.getUserCount()], i);
        }
        return result;
    }

    public void depthFirstSearch(int currentIndex, Stack<String> path, List<String> result, boolean[] marked, int startIndex){
        path.push(getUserNameByIndex(currentIndex));
        marked[currentIndex] = true;
        for(int neighborIndex=0; neighborIndex < User.getUserCount(); neighborIndex++){
            if(connections[currentIndex][neighborIndex]) {
                // There is a loop
                if (neighborIndex == startIndex) {
                    StringBuilder loop = new StringBuilder();
                    for (String userName : path) {
                        loop.append(userName).append(" -> ");

                    } // add the first element to the end element
//                    loop.append(getUserNameByIndex(startIndex));
                    result.add(loop.toString());
                }else if (!marked[neighborIndex]){
                    depthFirstSearch(neighborIndex, path, result, marked, startIndex);
                }
            }
        }
        path.pop();
        marked[currentIndex] = false;
    }

    public String getUserNameByIndex(int index){
        for(User user: users) {
            if(user.getIndexPos() == index)
                return user.getUserName();
        }
        return "";
    }
    /**
     * parses a file and add all users to the user list.
     * @param filename
     *
     * <dt>Preconditions:
     *    <dd> file content is correct
     */
    public void loadAllUsers(String filename){
        try {
            File file = new File(filename);
            Scanner parse = new Scanner(file);
            while(parse.hasNext()) {
                String userName = parse.nextLine();
                addUser(userName);
            }

        }catch (FileNotFoundException e){
            System.out.print("File does not exist");
        }
    }

    /**
     * parses a file and add all connections to the adjacency matrix.
     *
     * There may be invalid source/destination pairs. (skip)
     *
     * @param filename
     *
     */
    public void loadAllConnections(String filename) {
        try {
            File file = new File(filename);
            Scanner parse = new Scanner(file);
            while(parse.hasNext()) {
                String entireLine = parse.nextLine();
                String[] tokens = entireLine.split(",");
                String userFrom = tokens[0];
                String userTo = tokens[1].trim();
                if(validUsers(userFrom, userTo)){
                    System.out.println(userFrom + ", " + userTo + " added");
                    addConnection(userFrom, userTo);
                }
            }

        }catch (FileNotFoundException e){
            System.out.print("File does not exist");
        }
    }

    // print adjacency matrix for testing
    public void printAdjacencyMatrix() {
        System.out.print("    ");
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("%3d ", i);
        }
        System.out.println();

        for (int i = 0; i < User.getUserCount(); i++) {
            System.out.printf("%3d ", i);
            for (int j = 0; j < User.getUserCount(); j++) {
                System.out.print(connections[i][j] ? "1" : "0");
            }
            System.out.println("  " + getUserNameByIndex(i));
        }
    }

}
