import java.io.*;
import java.util.*;

/**
 * FollowGraphDriver class that contains the main method and the user interface. Display a menu with the following options:
 * add user, add connection, load all users, load all connections, print all users, print all loops, remove user, remove connection,
 * find the shortest path, find all paths, and quit. Terminating the program persists the FollowGraph object through serializable interface.
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
 * CSE214 - R02
 */
public class FollowGraphDriver implements Serializable {
    public static void main(String[] args) {

        FollowerGraph graph;
        // load the follower graph if it exists
        try {
            FileInputStream file = new FileInputStream("follow_graph.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            graph = (FollowerGraph) inStream.readObject();
            User.setUserCount(graph.getArrayList().size());
        } catch (IOException | ClassNotFoundException e){
            System.out.println("follow_graph.obj is not found. New FollowGraph object will be created.");
            graph = new FollowerGraph();
        }

        Scanner input = new Scanner(System.in);
        boolean running = true;
        String userInput;
        while(running){
            printMenu();
            userInput = input.nextLine().toUpperCase();
            switch (userInput){
                case "U": { // Add User
                    System.out.print("Please enter the name of the user: ");
                    String userName = input.nextLine();
                    graph.addUser(userName);
                    // if user already exists, not readded
                    break;
                }
                case "C": { // Add Connection
                    String userFrom;
                    String userTo;
                    boolean validInput;

                    // Loop for valid source input
                    do {
                        validInput = true;
                        System.out.print("Please enter the source of the connection to add: ");
                        userFrom = input.nextLine();
                        if(userFrom.isEmpty()) {
                            System.out.println("You can not leave this field empty.");
                            validInput = false;
                        }else if (!graph.validOneUser(userFrom)) {
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            validInput = false;
                        }
                    } while (!validInput);

                    // Loop for valid destination input
                    do {
                        validInput = true;
                        System.out.print("Please enter the dest of the connection to add: ");
                        userTo = input.nextLine();
                        if (userTo.isEmpty()) {
                            System.out.println("You can not leave this field empty.");
                            validInput = false;
                        } else if (!graph.validOneUser(userTo)) {
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            validInput = false;
                        }
                    } while (!validInput);
                    graph.addConnection(userFrom, userTo);

                    break;
                }
                case "AU": { // Load all Users
                    System.out.print("Enter the file name: "); // users.txt
                    String userFileName = input.nextLine();
                    graph.loadAllUsers(userFileName);
                    // prints if file does not exist within the method
                    break;
                }
                case "AC": { // Load all Connections
                    System.out.print("Enter the file name: "); // connections.txt
                    String userFileName = input.nextLine();
                    graph.loadAllConnections(userFileName);
                    // prints if file does not exist within the method
                    break;
                }
                case "P": { // Print all Users
                    System.out.print("(SA) Sort Users by Name\n" +
                            "(SB) Sort Users by Number of Followers\n" +
                            "(SC) Sort Users by Number of Following\n" +
                            "(Q) Quit\n" +
                            "Enter a selection: ");
                    userInput = input.nextLine().toUpperCase();
                    switch(userInput){
                        case "SA": { // Sort Users by Name
                            Comparator<User> nameComparator = new NameComparator();
                            graph.printAllUsers(nameComparator);
                            break;
                        }
                        case "SB": { // Sort Users by Number of Followers
                            Comparator<User> followersComparator = new FollowersComparator();
                            graph.printAllUsers(followersComparator);
                            break;
                        }
                        case "SC": { // Sort Users by Number of Following
                            Comparator<User> followingComparator = new FollowingComparator();
                            graph.printAllUsers(followingComparator);
                            break;
                        }
                        case "Q": { // back to main menu
                            break;
                        }
                        default: {
                            System.out.println("Invalid");
                        }
                    }
                    break;
                }
                case "L": { // Print all Loops (cycles)
                    List<String> result = graph.findAllLoops();
                    int size = result.size();
                    if(size==1){
                        System.out.println("There is 1 loop:");
                        for (String loop : result) {
                            System.out.println(loop);
                        }
                    }else if(!result.isEmpty()) {
                        System.out.println("There are a total of" + size + "loops:");
                        for (String loop : result) {
                            System.out.println(loop);
                        }
                    }else{
                        System.out.println("There are no loops.");
                    }
                    break;
                }
                case "RU": { // Remove User
                    System.out.print("Please enter the user to remove: ");
                    String userName = input.nextLine();
                    graph.removeUser(userName);
                    // prints if User does not exist within the function
                    break;
                }
                case "RC": { // Remove Connection
                    String userFrom;
                    String userTo;
                    boolean validInput;

                    // Loop for valid source input
                    do {
                        validInput = true;
                        System.out.print("Please enter the source of the connection to remove: ");
                        userFrom = input.nextLine();
                        if(userFrom.isEmpty()) {
                            System.out.println("You can not leave this field empty.");
                            validInput = false;
                        }else if (!graph.validOneUser(userFrom)) {
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            validInput = false;
                        }
                    } while (!validInput);

                    // Loop for valid destination input
                    do {
                        validInput = true;
                        System.out.print("Please enter the dest of the connection to remove: ");
                        userTo = input.nextLine();
                        if (userTo.isEmpty()) {
                            System.out.println("You can not leave this field empty.");
                            validInput = false;
                        } else if (!graph.validOneUser(userTo)) {
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            validInput = false;
                        }
                    } while (!validInput);
                    graph.removeConnection(userFrom, userTo);
                    break;
                }
                case "SP": { // Find Shortest Path
                    String userFrom;
                    String userTo;
                    boolean validInput;
                    // Loop for valid source input
                    do {
                        validInput = true;
                        System.out.print("Please enter the desired source: ");
                        userFrom = input.nextLine();
                        if(userFrom.isEmpty()) {
                            System.out.println("You can not leave this field empty.");
                            validInput = false;
                        }else if (!graph.validOneUser(userFrom)) {
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            validInput = false;
                        }
                    } while (!validInput);
                    // Loop for valid destination input
                    do {
                        validInput = true;
                        System.out.print("Please enter the desired destination: ");
                        userTo = input.nextLine();
                        if (userTo.isEmpty()) {
                            System.out.println("You can not leave this field empty.");
                            validInput = false;
                        } else if (!graph.validOneUser(userTo)) {
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            validInput = false;
                        }
                    } while (!validInput);
                    String result = graph.shortestPath(userFrom, userTo);
                    String[] parts = result.split("#");
                    String path = parts[0];
                    int pathSize = Integer.parseInt(parts[1]);
                    System.out.println("The shortest path is: " + path);
                    System.out.println("The number of users in this path is: " + pathSize);
                    break;
                }
                case "AP": { // Find All Paths
                    String userFrom;
                    String userTo;
                    boolean validInput;
                    // Loop for valid source input
                    do {
                        validInput = true;
                        System.out.print("Please enter the desired source: ");
                        userFrom = input.nextLine();
                        if(userFrom.isEmpty()) {
                            System.out.println("You can not leave this field empty.");
                            validInput = false;
                        }else if (!graph.validOneUser(userFrom)) {
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            validInput = false;
                        }
                    } while (!validInput);

                    // Loop for valid destination input
                    do {
                        validInput = true;
                        System.out.print("Please enter the desired destination: ");
                        userTo = input.nextLine();
                        if (userTo.isEmpty()) {
                            System.out.println("You can not leave this field empty.");
                            validInput = false;
                        } else if (!graph.validOneUser(userTo)) {
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            validInput = false;
                        }
                    } while (!validInput);

                    List<String> result = graph.allPaths(userFrom, userTo);
                    List<String> paths = new ArrayList<>();
                    for (String path : result) {
                        String[] parts = path.split("#");
                        paths.add(parts[0]);
                    }
                    System.out.println("There are a total of " + paths.size() + " paths:");
                    Collections.sort(paths);
                    for (String path : paths){
                        System.out.println(path);
                    }
                    break;
                }
                case "Q": { // QUIT PROGRAM
                    System.out.print("Program terminating....");
                    // save the file
                    try {
                        FileOutputStream file = new FileOutputStream("follow_graph.obj");
                        ObjectOutputStream outStream = new ObjectOutputStream(file);
                        outStream.writeObject(graph);
                        System.out.println("FollowGraph object saved into file FollowGraph.obj.");
                        // update user count

                    } catch (IOException e) {
                        System.out.println("File not found");
                    }
                    System.out.print("Program terminating...");
                    running = false;
                    break;
                }

                default: {
                    System.out.println("Invalid command, try again.");
                    break;
                }
            }
        }
    }
    /**
     * Prints all commands users can use.
     */
    public static void printMenu() {
        System.out.print(
                "************ Menu ************\n"+
                        "(U) Add User\n" +
                        "(C) Add Connection\n" +
                        "(AU) Load all Users\n" +
                        "(AC) Load all Connections\n" +
                        "(P) Print all Users\n" +
                        "(L) Print all Loops\n" +
                        "(RU) Remove User\n" +
                        "(RC) Remove Connection\n" +
                        "(SP) Find Shortest Path\n" +
                        "(AP) Find All Paths\n" +
                        "(Q) Quit\n" +
                        "Enter a selection: "
        );
    }
}
