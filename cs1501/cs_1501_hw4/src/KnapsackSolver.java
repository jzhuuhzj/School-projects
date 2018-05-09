
public class KnapsackSolver {
	private static Order[][] orderType;
	

	public static int[][] buildTable(Order[] orders, int costLimit, int timeLimit){
	// Problem #1
	// Fill in this method to create a (costLimit + 1) by (timeLimit + 1) table
	// that for each (i, j) stores the maximum number of cookies that can be
	// produced with cost at most i in time at most j.
		orderType = new Order[costLimit + 1][timeLimit + 1];
		int[][] table = new int[costLimit + 1][timeLimit + 1];
		
		//initialization
		for(int i = 0; i <= costLimit; i ++){
			for(int j = 0; j <= timeLimit; j ++){
				orderType[i][j] = null;
				table[i][j] = 0;
			}
		}
		
		for  (int m = 0; m < orders.length; m++){
			for (int i = orders[m].cost; i <=costLimit ; i++){
				for (int j = orders[m].time; j <=timeLimit ; j++){
					if (table[i][j] < orders[m].numberOfCookies + table[i - orders[m].cost][j - orders[m].time]){
						//update the table
						table[i][j]=orders[m].numberOfCookies + table[i - orders[m].cost][j - orders[m].time];
						//keep track of the orders
						orderType[i][j] = orders[m]; 
					}						
				}		
			}			
		}
			                                                 
		return table;
	}

	public static Multiset solve(Order[] orders, int costLimit, int timeLimit){
	// Problem #2
	// Fill in this method to create a multiset that represents a combination of
	// cookie choices that maximizes the number of cookies with cost at most 
	// costLimit in time at most timeLimit.  Note: You can call buildTable as
	// a subroutine.
		
		Multiset solution = new Multiset();
		buildTable(orders, costLimit, timeLimit);
		
		while (orderType[costLimit][timeLimit] != null) {
			//add orders to solution
			solution.add(orderType[costLimit][timeLimit]); 
					
			int former_time = timeLimit;
			timeLimit = timeLimit - orderType[costLimit][timeLimit].time;
			costLimit = costLimit - orderType[costLimit][former_time].cost;
		}	
		
		return solution;
	}
}
	