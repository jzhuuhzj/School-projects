package hw3;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeightedGraph {

	Set<Location> vertices;
	Map<Location, Map<Location, Integer>> adjacencyList;
	
	public WeightedGraph(){
		vertices = new HashSet<Location>();
		adjacencyList = new HashMap<Location, Map<Location, Integer>>();
	}
	
	public void addVertex(Location loc){
		vertices.add(loc);
		adjacencyList.put(loc, new HashMap<Location, Integer>());
	}
	
	public void addEdge(Location loc1, Location loc2, Integer weight){
		adjacencyList.get(loc1).put(loc2, weight);
		adjacencyList.get(loc2).put(loc1, weight);
	}
	
	// Returns a set containing all of the neighboring locations
	public Set<Location> getNeighbors(Location loc){
		return adjacencyList.get(loc).keySet();
	}
	
	// Returns the weight of the edge between loc1 and loc2
	public Integer getWeight(Location loc1, Location loc2){
		return adjacencyList.get(loc1).get(loc2);
	}
	
}
