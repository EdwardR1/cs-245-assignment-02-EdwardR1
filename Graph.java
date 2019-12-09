import java.util.*;

public class Graph {

	private boolean found;

	private Map<String, Set<String>> adjacencyMap;
	private Queue<Pair> queue;
	private Set<String> visited;
	private String start, query;
	private Map<String, String> parents;
	private List<String> results;

	public Graph() {
		adjacencyMap = new HashMap<String, Set<String>>();
		queue = new LinkedList<Pair>();
		visited = new HashSet<String>();
		parents = new HashMap<String, String>();
		results = new LinkedList<String>();
	}

	/**
	 * Check if the graph has the certain actor
	 * @param name Name of actor to be checked
	 * @return True if actor in Graph already, false otherwise
	 */
	public boolean containsActor(String name) {
		return adjacencyMap.keySet().contains(name);
	}

	/**
	 * Add an actor to the graph
	 * @param name name of actor to be added to the graph
	 */
	public void addActor(String name) {
		adjacencyMap.put(name.toLowerCase(), new HashSet());
	}

	/**
	 * Add an edge between two actors
	 * @param name1 Name of first actor
	 * @param name2 Name of second actor
	 */
	public void addEdge(String name1, String name2) {
		adjacencyMap.get(name1).add(name2);
		adjacencyMap.get(name2).add(name1);
	}

	/**
	 * Calculate the shortest path between two actors
	 * @param name1 Name of the first actor
	 * @param name2 Name of the second actor
	 * @return List serving as the shortest path between actors
	 */
	public List<String> shortestPath(String name1, String name2) {
		start = name1;
		query = name2;
		if (found) {
			return results;
		}
		if (start.equals(query)) {
			found = true;
			results = new LinkedList<>();
			results.add(start);
			return results;
		}
		return bfs();
	}

	/**
	 * Helper function to calculate the shortest path
	 * @return List of shortest path between two actors
	 */
	private List<String> bfs() {
		queue.add(new Pair(start, adjacencyMap.get(start)));
		visited.add(start);

		while (!queue.isEmpty()) {
			for (String s : queue.peek().getValue()) {
				if (visited.contains(s)) {
					continue;
				}
				visited.add(s);
				queue.add(new Pair(s, adjacencyMap.get(s)));
				parents.put(s, queue.peek().getKey());

				if (s.equals(query)) {
					found = true;
					break;
				}
			}
			queue.poll();
		}
		if (!found) {
			return null;
		}
		List<String> output = new LinkedList<>();
		String currentPerson = query;

		while (!currentPerson.equals(start)) {
			output.add(0, currentPerson);
			currentPerson = parents.get(currentPerson);
		}
		output.add(0, start);
		return output;
	}
}