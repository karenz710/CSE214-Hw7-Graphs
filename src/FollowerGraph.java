import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
import java.io.File;

/**
 * The FollowerGraph class contains an adjacency matrix for the users. It updates the followings
 * and followers of each user accordingly.
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
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
     * Getter method for ArrayList users
     * @return ArrayList users
     */
    public ArrayList<User> getArrayList(){
        return users;
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

    /**
     * Helper method to determine if the userName exists in the users array list
     * @param userName the name to be determined
     * @return True if user exists with username, false otherwise
     */
    public boolean validOneUser(String userName){
        boolean userExist = false;
        for(User user: users){
            if(user.getUserName().equals(userName)){
                userExist = true;
                break;
            }
        }
        return userExist;
    }

    /**
     * Helper method to see if users are valid in the arraylist user
     *
     * @param userFrom the source
     * @param userTo the destination
     * @return True if both users exist, false otherwise
     */
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
            System.out.println("The vertex " + userFrom + " or the vertex " + userTo + " does not exist");
        }else{
            if(!connections[indices[0].getIndexPos()][indices[1].getIndexPos()]) {
                connections[indices[0].getIndexPos()][indices[1].getIndexPos()] = true;
                indices[0].setNumberOfFollowings(indices[0].getNumberOfFollowings() + 1);
                indices[1].setNumberOfFollowers(indices[1].getNumberOfFollowers() + 1);
                System.out.println("Connection added");
            }else{
                System.out.println("The connection already exists");
            }
        }
    }

    /**
     * Removes a given user and all the connections from and to that user
     *
     * @param userName the username
     */
    public void removeUser(String userName) {
        boolean userExist = false;
        User userToRemove = new User();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                userExist = true;
                userToRemove = user;
                break;
            }
        }
        if (userExist) {
            int userIndex = userToRemove.getIndexPos();
            // update adjacency matrix and followers and followings
            for (int i = 0; i < User.getUserCount(); i++) {
                if (connections[userIndex][i]){
                    connections[userIndex][i] = false;
                    users.get(i).setNumberOfFollowers(users.get(i).getNumberOfFollowers() - 1); // I learned you can use .get in an ArrayList
                }
                if (connections[i][userIndex]){
                    connections[i][userIndex] = false;
                    users.get(i).setNumberOfFollowings(users.get(i).getNumberOfFollowings() - 1);
                }
            }
            users.remove(userIndex);
            // Shift the adj matrix from the removed user
            for (int i = userIndex; i < users.size(); i++){
                for (int j = 0; j < users.size(); j++){
                    connections[i][j] = connections[i + 1][j];
                    connections[j][i] = connections[j][i + 1];
                }
            }
            for (int j = 0; j < users.size(); j++){
                connections[users.size()][j] = false;
                connections[j][users.size()] = false;
            }
            // Update the User size in User class
            User.setUserCount(User.getUserCount()-1);
            System.out.println(userName + " has been removed.");
        }else{
            System.out.println("The vertex " + userName + " does not exist");
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
            System.out.println("The vertex " + userFrom + " or the vertex " + userTo + " does not exist");
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
        List<String> paths = allPaths(userFrom, userTo);
        // Check if there are any paths
        if (paths.isEmpty()){
            return "No path found";
        }
        String[] firstPathParts = paths.get(0).split("#");
        String shortestPath = firstPathParts[0];
        int shortestPathSize = Integer.parseInt(firstPathParts[1]);

        // Loop to find shortest
        for (String path : paths){
            String[] parts = path.split("#");
            int currentPathSize = Integer.parseInt(parts[1]);
            if (currentPathSize < shortestPathSize){
                shortestPath = parts[0];
                shortestPathSize = currentPathSize;
            }
        }

        return shortestPath + "#" + shortestPathSize;
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
        Stack<String> path = new Stack<>();
        boolean[] marked = new boolean[User.getUserCount()];
        User[] indices = getUser(userFrom, userTo);
        if(Arrays.equals(indices, new User[]{null, null})){
            // pass
        }else {
            dfsAllPaths(indices[0].getIndexPos(), indices[1].getIndexPos(), path, result, marked);
        }
        return result;
    }

    /**
     * Helper method that performs a recursive Depth-First Search (DFS) to find all paths
     * between the current user (currentIndex) and the destination user (destIndex).
     * The method recursively traverses the graph and backtracks if necessary
     *
     * @param currentIndex the index of the current user in the graph
     * @param destIndex the index of the destination user
     * @param path the current path being traversed, represented as a stack
     * @param result the list to store all found paths
     * @param marked an array indicating whether a user has already been visited
     */
    private void dfsAllPaths(int currentIndex, int destIndex, Stack<String> path, List<String> result, boolean[] marked) {
        path.push(getUserNameByIndex(currentIndex));
        marked[currentIndex] = true;
        if(currentIndex == destIndex){ // base case
            StringBuilder pathStr = new StringBuilder();
            for (String user : path){
                pathStr.append(user).append(" -> ");
            }
            // remove last " -> " and append the user count
            pathStr.delete(pathStr.length() - 4, pathStr.length()).append("#").append(path.size());
            result.add(pathStr.toString());
        }else{
            for(int neighborIndex = 0; neighborIndex < users.size(); neighborIndex++){
                if(connections[currentIndex][neighborIndex] && !marked[neighborIndex]){
                    dfsAllPaths(neighborIndex, destIndex, path, result, marked);
                }
            }
        }
        path.pop();
        marked[currentIndex] = false;
    }

    /**
     * Prints all users in the order based on the given Comparator, sorts the users based on the specified comparison
     * prints the user's data.
     *
     * @param comp the comparator to define the order of users
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
     * @param userName the user whose followers will be printed
     */
    public void printAllFollowers(String userName) {
        Boolean userExist = false;
        User userToBePrinted = new User();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                userExist = true;
                userToBePrinted = user;
                break;
            }
        }
        if(userExist){
            int userIndex = userToBePrinted.getIndexPos();
            System.out.println("Followers of " + userName + ":");
            for (int i = 0; i < users.size(); i++){
                if (connections[i][userIndex]){
                    System.out.println(users.get(i).getUserName());
                }
            }
        }else{
            System.out.println("User does not exist");
        }
    }

    /**
     * prints all the users that the given user is following
     * @param userName the user whose followings will be printed
     */
    public void printAllFollowing(String userName){
        Boolean userExist = false;
        User userToBePrinted = new User();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                userExist = true;
                userToBePrinted = user;
                break;
            }
        }
        if(userExist){
            int userIndex = userToBePrinted.getIndexPos();
            System.out.println(userName + " is following:");
            for (int i = 0; i < users.size(); i++){
                if (connections[userIndex][i]){
                    System.out.println(users.get(i).getUserName());
                }
            }
        }else{
            System.out.println("User does not exist");
        }
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
        return findUniqueLoops(result);
    }

    /**
     * A helper method to perform Depth-First Search to find loops starting from the given user.
     * @param currentIndex the index of the current user in the graph
     * @param path the stack representing the current path being traversed
     * @param result the list to store all loops
     * @param marked an array to track visited users, do not revisit them
     * @param startIndex the index of the starting user for detecting loops
     */
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
                    loop.append(getUserNameByIndex(startIndex));
                    result.add(loop.toString());

                }else if (!marked[neighborIndex]){
                    depthFirstSearch(neighborIndex, path, result, marked, startIndex);
                }
            }
        }
        path.pop();
        marked[currentIndex] = false;
    }

    /**
     * Check for duplicates by removing last element and checking if the paths are the same
     *
     * @param loops the list of loops to check for duplicates
     * @return a list of unique loops
     */
    public static List<String> findUniqueLoops(List<String> loops) {
        List<String> uniqueLoops = new ArrayList<>();
        Set<String> checkedLoops = new HashSet<>(); // all loops already checked
        for (String loop : loops){
            String[] elements = loop.split(" -> ");
            String[] elementsRemoveLast = Arrays.copyOf(elements, elements.length - 1);
            boolean isDuplicate = false;
            for (String checkedLoop : checkedLoops) {
                String[] checkedElements = checkedLoop.split(" -> ");
                String[] checkedElementsRemoveLast = Arrays.copyOf(checkedElements, checkedElements.length - 1);
                // compare the current loop with the checked loop
                if (checkCircularlyEquivalent(elementsRemoveLast, checkedElementsRemoveLast)){
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate){
                uniqueLoops.add(loop);
                checkedLoops.add(loop);
            }
        }
        return uniqueLoops;
    }

    /**
     * Helper method compares each element of loop1 with loop2 starting at position i and wrapping around when it reaches the end of loop2
     * If they are circularly equivalent.
     *
     * @param loop1 the first loop to be checked
     * @param loop2 the second loop to be checked
     * @return true if loop2 is circularly equivalent to loop1, otherwise false
     */
    public static boolean checkCircularlyEquivalent(String[] loop1, String[] loop2){
        if (loop1.length != loop2.length) {
            return false;
        }
        for (int i = 0; i < loop2.length; i++) {
            boolean match = true;
            for (int j = 0; j < loop1.length; j++){
                if (!loop1[j].equals(loop2[(i + j) % loop2.length])){
                    match = false;
                    break;
                }
            }
            if (match)
                return true;
        }
        return false;
    }

    /**
     * a helper method to help get the username given the index
     *
     * @param index the index position in the arraylist of the user
     * @return the username of the user if user exists, nothing otherwise
     */
    public String getUserNameByIndex(int index){
        for(User user: users) {
            if(user.getIndexPos() == index)
                return user.getUserName();
        }
        return "";
    }

    /**
     * parses a file and add all users to the user list.
     * @param filename the file to be parsed
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
     * @param filename the file to be parsed
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

    /**
     *  print adjacency matrix for testing
     */
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
