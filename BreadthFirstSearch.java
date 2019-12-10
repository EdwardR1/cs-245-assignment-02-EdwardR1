import java.util.*;

public class BreadthFirstSearch {
	private Graph g;
	private Queue<Pair> queue;
	private Set<String> visited;
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
	 * @param first Name of the first actor
	 * @param second Name of the second actor
	 * @return List serving as the shortest path between actors
	 */
	public List<String> shortestPath(String first, String second) {
		if (found) {
			return results;
		}
		if (first.equals(second)) {
			found = true;
			results = new LinkedList<>();
			results.add(first);
			return results;
		}
		return bfs(first, second);
	}

	/**
	 * Helper function to calculate the shortest path
	 * 
	 * @param first Starting name
	 * @param Query query name
	 * @return List of shortest path between two actors
	 */
	private List<String> bfs(String first, String second) {
		queue.add(new Pair(first, g.getAdjacencyMap().get(first)));
		visited.add(first);

		while (!queue.isEmpty()) {
			for (String s : queue.peek().getValue()) {
				if (visited.contains(s)) {
					continue;
				}
				visited.add(s);
				queue.add(new Pair(s, g.getAdjacencyMap().get(s)));
				parents.put(s, queue.peek().getKey());

				if (s.equals(second)) {
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
		String currentPerson = second;

		while (!currentPerson.equals(first)) {
			output.add(0, currentPerson);
			currentPerson = parents.get(currentPerson);
		}
		output.add(0, first);
		return output;
	}
}