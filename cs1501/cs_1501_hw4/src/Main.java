import java.util.Iterator;

public class Main {

	public static void main(String[] args){
		int costLimit = 1000;
		int timeLimit = 30;
		
		Order[] orders = new Order[4];
		// Arguments: String name, int cost, int time, int numberOfCookies
		orders[0] = new Order("Chocolate Chip", 50, 2, 150);
		orders[1] = new Order("Peanut Butter", 100, 3, 250);
		orders[2] = new Order("Snickerdoodle", 50, 6, 200);
		orders[3] = new Order("Oatmeal Raisin", 120, 1, 200);
		
		Multiset set = KnapsackSolver.solve(orders, costLimit, timeLimit);	
		printResult(set, costLimit, timeLimit);
		
		// ANSWER:
		// Chocolate Chip x 11
		// Peanut Butter x 2
		// Oatmeal Raisin x 2
	}
	
	private static void printResult(Multiset set, int costLimit, int timeLimit){
		System.out.println("**** Orders ****");
		System.out.println();
		
		int total = 0;
		Iterator<Order> it = set.iterator();
		while(it.hasNext()){
			Order order = it.next();
			total += order.numberOfCookies * set.count(order);
			System.out.println(order.name + " x "  + set.count(order));
		}
		
		System.out.println();
		System.out.println("A total of " + total + " cookies in at most " + timeLimit + " hours with at most $" + costLimit + ".");
	}
	
}
