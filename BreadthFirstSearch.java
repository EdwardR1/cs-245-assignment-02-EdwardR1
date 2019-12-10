import java.util.*;

public class BreadthFirstSearch {
	private Graph g;
	private Queue<Pair> queue;
	private Set<String> visited;
	private String start, query;
	private Map<String, String> parents;
	private List<String> results;
	private boolean found;

	public BreadthFirstSearch(Graph g) {
		this.g = g;
		queue = new LinkedList<Pair>();
		visited = new HashSet<String>();
		parents = new HashMap<String, String>();
		results = new LinkedList<String>();
		found = false;
	}

	/**
	 * Calculate the shortest path between two actors
	 * 
	 * @param start Name of the first actor
	 * @param query Name of the second actor
	 * @return List serving as the shortest path between actors
	 */
	public List<String> shortestPath(String start, String query) {
		if (found) {
			return results;
		}
		if (start.equals(query)) {
			found = true;
			results = new LinkedList<>();
			results.add(start);
			return results;
		}
		return bfs(start, query);
	}

	/**
	 * Helper function to calculate the shortest path
	 * 
	 * @param start Starting name
	 * @param Query query name
	 * @return List of shortest path between two actors
	 */
	private List<String> bfs(String start, String query) {
		queue.add(new Pair(start, g.getAdjacencyMap().get(start)));
		visited.add(start);

		while (!queue.isEmpty()) {
			for (String s : queue.peek().getValue()) {
				if (visited.contains(s)) {
					continue;
				}
				visited.add(s);
				queue.add(new Pair(s, g.getAdjacencyMap().get(s)));
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