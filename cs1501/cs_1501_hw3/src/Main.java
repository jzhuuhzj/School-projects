package hw3;


import java.util.Iterator;
import java.util.Set;

public class Main {

	public static void main(String[] args){
		Location start = new Location("START");
		WeightedGraph graph = new WeightedGraph();
		createExampleGraph(graph, start);
		
		Set<Location> reachable = BFS.getReachableSet(graph, start);
		Set<Location> destinations = Dijkstra.getDestinationSet(graph, start, 650);
		
		System.out.println("Reachable Locations: ");
		printLocations(reachable);
		System.out.println();
		
		System.out.println("Destinations: ");
		printLocations(destinations);
	}
	
	public static void printLocations(Set<Location> set){
		Iterator<Location> it = set.iterator();
		while(it.hasNext()){
			System.out.println(it.next().name);
		}
	}
	
	public static void createExampleGraph(WeightedGraph graph, Location start){
		Location A = new Location("A");
		Location B = new Location("B");
		Location C = new Location("C");
		Location D = new Location("D");
		Location E = new Location("E");
		Location F = new Location("F");
		Location G = new Location("G");
		Location H = new Location("H");
		Location I = new Location("I");
		
		graph.addVertex(start);
		graph.addVertex(A);
		graph.addVertex(B);
		graph.addVertex(C);
		graph.addVertex(D);
		graph.addVertex(E);
		graph.addVertex(F);
		graph.addVertex(G);
		graph.addVertex(H);
		graph.addVertex(I);
		
		graph.addEdge(start, A, 50);
		graph.addEdge(start, B, 125);
		graph.addEdge(start, C, 250);
		graph.addEdge(A, B, 100);
		graph.addEdge(A, C, 250);
		graph.addEdge(A, D, 400);
		graph.addEdge(A, G, 800);
		graph.addEdge(B, D, 200);
		graph.addEdge(B, E, 400);
		graph.addEdge(B, G, 650);
		graph.addEdge(C, D, 50);
		graph.addEdge(C, F, 250);
		graph.addEdge(D, E, 200);
		graph.addEdge(E, F, 100);
		graph.addEdge(F, G, 200);
		graph.addEdge(H, I, 200);
	}
	
}
