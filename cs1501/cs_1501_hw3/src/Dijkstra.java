package hw3;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Dijkstra {
	private static Map<Location, Integer> cost;

	// Returns the set of destinations that can be reached without going over budget
	public static Set<Location> getDestinationSet(WeightedGraph graph, Location start, Integer budget){
	// Problem #2
	// Fill in this method to compute the set of all possible destinations 
	// that can be reached while staying within the travel budget. You must
	// use Dijkstra's algorithm to get full credit.  We encourage you to
	// implement the |V|^2 time version of Dijkstra's algorithm.  You are
	// free to add helper methods.
		
		
			
		Set<Location> visitedLocations = new HashSet<Location>();
		Set<Location> unVisitedLocations = new HashSet<Location>();
		Map<Location, Location> predecessors = new HashMap<Location, Location>();
		
		cost = new HashMap<Location, Integer>();
		cost.put(start, 0);
		
		unVisitedLocations.add(start);
		
		while(unVisitedLocations.size() > 0){
			Location node = getMinimum(unVisitedLocations);
			visitedLocations.add(node);
			unVisitedLocations.remove(node);

			Set<Location> adjacentNodes = graph.getNeighbors(node);
			for (Location target : adjacentNodes){
				
				if ((getShortestDistance(target) > (getShortestDistance(node) + graph.getWeight(node, target)) 
						&& ((getShortestDistance(node) + graph.getWeight(node, target)) <= budget)) && (!visitedLocations.contains(target))){
					
					cost.put(target, (getShortestDistance(node) + graph.getWeight(node, target)));
					predecessors.put(target, node);
				    unVisitedLocations.add(target);
				}
			}
		}
			
		return visitedLocations;
	}

	private static int getShortestDistance(Location target) {
		Integer d = cost.get(target);
		if (d == null){
			return Integer.MAX_VALUE;
		}else{
			return d;
		}	
	}


	private static Location getMinimum(Set<Location> locations) {
		Location minimum = null;
		for (Location location : locations){
			if (minimum == null){
				minimum = location;
			} else{
				if(getShortestDistance(location) < getShortestDistance(minimum)){
					minimum = location;
				}
			}
		}
		return minimum;
	}
	

	
}
