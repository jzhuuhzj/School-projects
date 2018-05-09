
public class ChainingHashMap{
	Node[] array;
	int size;
	
	public ChainingHashMap(int size){
		this.size = size;
		array = new Node[size];
	}

	public Integer get(Word key) {
	// Problem #1A
	// Fill in this method to get the value corresponding
	// with the key. Note: if the key is not found, then
	// return null.
		if (key == null){
			throw new IllegalArgumentException("first argument to get is null");
		}
		int i = key.hashCode() % size;
		
		for (Node x = array[i]; x != null; x = x.next){
			if (key.equals(x.word)){
				return x.frequency;
			}
		}
		return null;
	}

	public void put(Word key, Integer value) {
	// Problem #1B
	// Fill in this method to insert a new key-value pair into
	// the map or update the existing key-value pair if the
	// key is already in the map.
		
		if (key == null){
			throw new IllegalArgumentException("first argument to put is null");
		}
		if (value == null){
			remove(key);
			return;
		}
		
		int i = key.hashCode() % size;
		
		if (get(key) == null){
			array[i] = new Node(key, value, array[i]);
		}
		
	
		
		for (Node x = array[i]; x != null; x = x.next){
			if (key.equals(x.word)){
				x.frequency = value;
				return;
			}	
		}
		
		array[i] = new Node(key, value, array[i]);
		
	}

	public Integer remove(Word key) {
	// Problem #1C
	// Fill in this method to remove a key-value pair from the
	// map and return the corresponding value. If the key is not
	// found, then return null.
		if (key == null){
			throw new IllegalArgumentException("Argument is null!");
		}
		
		int i = key.hashCode() % size;
		
		if (get(key) == null){
			return null;
		}
		
		Node y = array[i];
		Node x = y.next;
		for (; x != null; x = x.next){
			
			if (key.equals(x.word)){
				int tFrequency = x.frequency;
				y.next = x.next;
				return tFrequency;	
			}
			y=x;
		}
		return null;
	}
	
	// This method returns the total size of the underlying array.
	// In other words, it returns the total number of linkedlists.
	public int getSize(){
		return size;
	}
	
	// This method counts how many keys are stored at the given array index.
	// In other words, it computes the size of the linkedlist at the given index.
	public int countCollisions(int index){
		if(index < 0 || index >= size) return -1;
		
		int count = 0;
		Node temp = array[index];
		while(temp != null){
			temp = temp.next;
			count++;
		}
		
		return count;
	}
	
	
}
