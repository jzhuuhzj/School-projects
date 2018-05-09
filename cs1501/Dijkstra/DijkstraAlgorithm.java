import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class DijkstraAlgorithm {
	
	//this is all the nodes
    private final List<Vertex> nodes;
	//all the edges
    private final List<Edge> edges;
	//all the visited nodes
    private Set<Vertex> settledNodes;
	//all the unvisited nodes
    private Set<Vertex> unSettledNodes;
	//the previous node to each node in the best path (up to this point)
    private Map<Vertex, Vertex> predecessors;
	//distance of each node form the source
    private Map<Vertex, Integer> distance;

    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

	
	/**
		this method gets the source vertex and executes Dijkstra's algorithm
		it will reinstantiate distance and predecessors with new values
		distance of each node will be distance from the source
		predecessors of node t is the node before that in the path between the source and t
	*/
    public void execute(Vertex source) {
		//initializing sets and maps
        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        predecessors = new HashMap<Vertex, Vertex>();
		
		//add distance of zero for the source
		//.
		//.
		//.
		
		//add the source itself to the unseteled nodes
        //.
		//.
		//.
		
		
		//as long as there is some unseteled node do the followings:
		//1- find the unseteled node with the minimum cost
		//2- add it to seteled nodes
		//3- remove it from the unseteled nodes
		//4- update all of its neighbors with new cost and new predecessors
        
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes;
		//save all of the neighbors of node in adjacentNodes
		//.
		//.
		//.
		
		//for all the neighbors of node do the followings if its current cost (shortest distance from source up to this point)
		//is more than node's cost + distance between node and the neighbors do the followings:
		//1- update the neighbors cost to be node's cost+distance between node and neighbor
		//2- update the neighbors predecessors to be "node"
		//3- add the neighbor into the unSettledNodes
		

    }
	
	
	//returns the distance between two neighbors
	//if you give two vertices that are not neighbors it will throw exception
    private int getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

	
    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

	
	//returns the vertex with the minimum cost among the given set of vertexes
    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

	//it returns current cost of each vertex
    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}