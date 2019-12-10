import java.util.*;
import java.io.*;
import java.lang.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class A3 {
    /**
     * Instantiate graph object and BFS
     */
    private static Graph g = new Graph();
    private static BreadthFirstSearch search = new BreadthFirstSearch(g);

    /**
     * Take in a string and capitalize first letter of the first and last names
     * 
     * @param str string to capitalize
     * @return capitalized string
     */
    private static String capitalize(String str) {
        StringBuilder sb = new StringBuilder();
        int start = 0, space = str.indexOf(" ") + 1;
        for (int i = 0; i < str.length(); i++) {
            if (i == start || i == space) {
                sb.append((char) ((int) (str.charAt(i)) - 32));
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * Parse the document and setup the graph
     * 
     * @param args Arguments passed in from the main method
     * @throws Exception
     */
    private static void parse(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        String delim = ",\"";
        String fileName = args[0];

        BufferedReader reader;
        String l = "";

        try {
            reader = new BufferedReader(new FileReader(fileName));
            l = reader.readLine();
            while ((l = reader.readLine()) != null) {
                if (l.contains("[],[]")) {
                    l = reader.readLine();
                }

                String[] castInfo = l.split(delim);
                String castMember = "";

                if (castInfo[1].contains("[{")) {
                    castMember = castInfo[1].replace("\"\"", "\"");
                } else if (castInfo[2].contains("[{"))
                    castMember = castInfo[2].replace("\"\"", "\"");
                JSONParser parser = new JSONParser();
                JSONArray castArray;
                try {
                    castArray = (JSONArray) parser.parse(castMember);
                } catch (Exception e) {
                    l = reader.readLine();
                    castInfo = l.split(delim);
                    castMember = "";
                    if (castInfo[1].contains("[{"))
                        castMember = castInfo[1].replace("\"\"", "\"");
                    castArray = (JSONArray) parser.parse(castMember);
                }
                for (Object o : castArray) {
                    JSONObject one = (JSONObject) o;
                    String name1 = ((String) one.get("name"));

                    if (!g.containsActor(name1.toLowerCase())) {
                        g.addActor(name1);
                    }

                    for (Object j : castArray) {
                        JSONObject two = (JSONObject) j;
                        String name2 = ((String) two.get("name"));

                        if (!g.containsActor(name2.toLowerCase())) {
                            g.addActor(name2);
                        }

                        g.addEdge(name1.toLowerCase(), name2.toLowerCase());
                    }
                }
            }
            reader.close();
        }
        // If file not found, print out file not found.
        catch (FileNotFoundException e) {
            System.out.println("File not found! Please try again with a correct file path or file name!");
            System.out.println("Exitting!");
            System.exit(1);
        }
    }

    /**
     * Handle getting actors from user
     * 
     * @param scan Scanner
     * @return List of two actors requested from user
     */
    private static List<String> getActors(Scanner scan) {
        // Actor 1 inputs
        System.out.print("Actor 1 name: ");
        String a1 = scan.nextLine();
        while (!g.containsActor(a1.toLowerCase())) {
            System.out.println("No such actor.");
            System.out.print("Actor 1 name: ");
            a1 = scan.nextLine();
        }

        // Actor 2 inputs
        System.out.print("Actor 2 name: ");
        String a2 = scan.nextLine();
        while (!g.containsActor(a2.toLowerCase())) {
            System.out.println("No such actor.");
            System.out.print("Actor 2 name: ");
            a2 = scan.nextLine();
        }

        List<String> actors = new LinkedList<String>();
        actors.add(a1);
        actors.add(a2);
        return actors;
    }

    /**
     * Print paths based on actors provided from getActors
     * 
     * @param actors list of actors from getActors
     */
    private static void printPaths() {
        List<String> actors = getActors(new Scanner(System.in));
        List<String> path = search.shortestPath(actors.get(0).toLowerCase(), actors.get(1).toLowerCase());

        // Print out statement of path between actors
        System.out.print(
                "Path between " + capitalize(path.get(0)) + " and " + capitalize(path.get(path.size() - 1)) + ": ");

        // Print out statement if there's only one actor
        if (path.size() == 1) {
            System.out.println(capitalize(path.get(0)));
        }

        // Print out total path
        else {
            for (int i = 0; i < path.size(); i++) {
                System.out.print(capitalize(path.get(i)));
                if (i != path.size() - 1)
                    System.out.print(" --> ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        // Check if proper inputs are inputted
        if (args.length < 1) {
            System.out.println("Please include the path to the file as a command line argument!");
        } else {
            parse(args);
            printPaths();
        }
    }
}