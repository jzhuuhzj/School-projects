import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Multiset {

	Map<Order, Integer> store;
	
	public Multiset(){
		store = new HashMap<Order, Integer>();
	}
	
	public void add(Order item){
		Integer temp = store.get(item);
		if(temp == null)
			store.put(item, 1);
		else
			store.put(item, temp + 1);
	}
	
	public Integer count(Order item){
		if(store.get(item) == null)
			return 0;
		else
			return store.get(item);
	}
	
	public Iterator<Order> iterator(){
		return store.keySet().iterator();
	}
}
