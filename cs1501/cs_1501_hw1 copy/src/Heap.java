import java.util.NoSuchElementException;

public class Heap {
	private Word[] pq;
	int n;
	
	
	public Heap(Word[] array){
		buildHeap(array);	
	}
	
	public void buildHeap(Word[] array){
	// Problem #2
	// Fill in this method with an O(n) time algorithm
	// that builds an n element complete binary heap.
	// Note: You are allowed to add and modify fields
    // and helper methods.
		n = array.length;
		pq = new Word[n+1];
	
		for (int i = 0; i < n; i++){
			pq[i+1] = array[i];
		}
		for (int i = n/2; i > 1; i--){
			sink(i);	
		}	
	}
	
	//check if there is no element left
	public boolean isEmpty(){
		return n == 0;
	}
	
	//rearrange the elements to make a heap
	private void sink(int k){
		while (2*k <= n){
			int j = 2*k;
			if ((j < n) && (pq[j].compareTo(pq[j+1]) < 0)){
				j++;
			}
			if (!((pq[k].compareTo(pq[j])<0))){
				break;
			}
			exchange(k,j);
			k = j;
		}
	}
	
	private void exchange (int i, int j){
		Word swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}
	
	
	public Word removeMax(){
	// Problem #3
	// Fill in this method with an O(log(n)) time algorithm
	// that removes the root element, restores the heap
	// structure, and finally returns the removed root element.
		if (isEmpty()){
			throw new NoSuchElementException("No element left");
		}else{
			Word max = pq[1];
			exchange(1, n--);
			pq[n+1] = null;
			sink(1);
			return max;	
		}
		
	}
	
	
	public Word[] select(int k){
		Word[] result = new Word[k];
		for(int i = 0; i < k; i++){
			result[i] = this.removeMax();
		}
		return result;
	}
}
