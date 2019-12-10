import java.util.*;

public class Graph {

	private Map<String, Set<String>> adjacencyMap;

	public Graph() {
		adjacencyMap = new HashMap<String, Set<String>>();
	}

	/**
	 * Check if the graph has the certain actor
	 * 
	 * @param name Name of actor to be checked
	 * @return True if actor in Graph already, false otherwise
	 */
	public boolean containsActor(String name) {
		return adjacencyMap.keySet().contains(name);
	}

	/**
	 * Add an actor to the graph
	 * 
	 * @param name name of actor to be added to the graph
	 */
	public void addActor(String name) {
		adjacencyMap.put(name.toLowerCase(), new HashSet());
	}

	/**
	 * Add an edge between two actors
	 * 
	 * @param name1 Name of first actor
	 * @param name2 Name of second actor
	 */
	public void addEdge(String name1, String name2) {
		adjacencyMap.get(name1).add(name2);
		adjacencyMap.get(name2).add(name1);
	}

	public Map<String, Set<String>> getAdjacencyMap() {
		return this.adjacencyMap;
	}

}