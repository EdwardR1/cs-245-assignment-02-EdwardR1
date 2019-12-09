import java.util.*;
import java.io.*;
import java.lang.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class A3 {
    /**
     * Take in a string and capitalize first letter of the first and last names
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

    public static void main(String[] args) throws Exception {
        // Check if proper inputs are inputted
        if (args.length < 1) {
            System.out.println("Please include the path to the file as a command line argument!");
        } else {
            // Instantiate a new graph
            Graph g = new Graph();
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
                    String cast = "";

                    if (castInfo[1].contains("[{"))
                        cast = castInfo[1].replace("\"\"", "\"");
                    else if (castInfo[2].contains("[{"))
                        cast = castInfo[2].replace("\"\"", "\"");
                    JSONParser parser = new JSONParser();
                    JSONArray castArray;
                    try {
                        castArray = (JSONArray) parser.parse(cast);
                    } catch (Exception e) {
                        l = reader.readLine();
                        castInfo = l.split(delim);
                        cast = "";
                        if (castInfo[1].contains("[{"))
                            cast = castInfo[1].replace("\"\"", "\"");
                        castArray = (JSONArray) parser.parse(cast);
                    }
                    for (Object o : castArray)
                    {
                        JSONObject one = (JSONObject) o;
                        String name1 = ((String) one.get("name"));

                        if (!g.containsActor(name1.toLowerCase())) {
                            g.addActor(name1);
                        }

                        for (Object j : castArray) 
                        {
                            JSONObject two = (JSONObject) j;
                            String name2 = ((String) two.get("name"));

                            if (!g.containsActor(name2.toLowerCase())) {
                                g.addActor(name2);
                            }

                            g.addEdge(name1.toLowerCase(), name2.toLowerCase());
                        }
                    }
                }
            }
            // If file not found, print out file not found.
            catch (FileNotFoundException e) {
                System.out.println("File not found! Please try again with a correct file path or file name!");
                System.out.println("Exitting!");
                System.exit(1);
            }
            
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

            // get shortest path between two actors
            List<String> path = g.shortestPath(a1.toLowerCase(), a2.toLowerCase());

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
    }
}