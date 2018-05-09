package hw3;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFS {

	// Returns the set of reachable locations using breadth first search

	public static Set<Location> getReachableSet(WeightedGraph graph, Location start){
	// Problem #1
	// Fill in this method to compute the set of all possible reachable 
	// locations (ignoring costs and budget).  You must use Breadth
	// First Search to get full credit.
		
		Set<Location> visited = new HashSet<Location>();
		Queue<Location> current = new LinkedList<Location>();
		
		current.add(start);
		visited.add(start);

		while(current.size()!= 0){
			Location v = current.poll();
			for (Location i : graph.getNeighbors(v)){
				if (!visited.contains(i)){
					current.add(i);
					visited.add(i);
					v = i;
				}
			}
		}		
		
		return visited;
	
	}

}
