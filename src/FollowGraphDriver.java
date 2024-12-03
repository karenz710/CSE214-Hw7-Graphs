import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Karen Zhao
 * Email: karen.zhao@stonybrook.edu
 * Student ID: 115931297
 * CSE214 - R02
 */
public class FollowGraphDriver implements Serializable {
    public static void main(String[] args) {
        FollowerGraph graph;
        try {
            FileInputStream file = new FileInputStream("follow_graph.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            graph = (FollowerGraph) inStream.readObject();
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
                    break;
                }
                case "C": { // Add Connection
                    System.out.print("Please enter the source of the connection to add: ");
                    String userFrom = input.nextLine();
                    System.out.print("Please enter the dest of the connection to add: ");
                    String userTo = input.nextLine();
                    graph.addConnection(userFrom, userTo);
                    break;
                }
                case "AU": { // Load all Users
                    System.out.print("Enter the file name: "); // users.txt
                    String userFileName = input.nextLine();
                    graph.loadAllUsers(userFileName);
                    break;
                }
                case "AC": { // Load all Connections
                    System.out.print("Enter the file name: "); // connections.txt
                    String userFileName = input.nextLine();
                    graph.loadAllConnections(userFileName);
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
                    graph.printAdjacencyMatrix();
                    List<String> result = graph.findAllLoops();
                    for (String loop : result) {
                        System.out.println(loop);
                    }
                    break;
                }
                case "RU": { // Remove User FIX
                    System.out.print("Please enter the user to remove: ");
                    String userName = input.nextLine();
                    graph.removeUser(userName);
                    break;
                }
                case "RC": { // Remove Connection

                    System.out.print("Please enter the source of the connection to remove: ");
                    String userFrom = input.nextLine();
                    System.out.print("Please enter the dest of the connection to remove: ");
                    String userTo = input.nextLine();
                    graph.removeConnection(userFrom, userTo);
                    break;
                }
                case "SP": { // Find Shortest Path
                    break;
                }
                case "AP": { // Find All Paths
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
                "(U) Add User\n" +
                        "(C) Add Connection\n" +
                        "(AU) Load all Users\n" +
                        "(AC) Load all Connections\n" +
                        "(P) Print all Users\n" +
                        "(L) Print all Loops (cycles)\n" +
                        "(RU) Remove User\n" +
                        "(RC) Remove Connection\n" +
                        "(SP) Find Shortest Path\n" +
                        "(AP) Find All Paths\n" +
                        "(Q) Quit\n" +
                        "Enter a selection: "
        );
    }
}
