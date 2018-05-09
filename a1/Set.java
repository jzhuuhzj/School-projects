package cs445.a1;

import java.util.Random;
import java.util.Arrays;

public class Set<E> implements SetInterface<E>{

	private static final int DEFAULT_CAPACITY = 10;
	private E[] entries;
	private int size;

	public Set(){
		this(DEFAULT_CAPACITY);
	}
	
	public Set(int capacity){
		E[] tempSet= (E[]) new Object[capacity];
		this.entries = tempSet;
		size = 0;
		
	}
	
	public Set(E[] entries){
		this(DEFAULT_CAPACITY);
		size = 0;
        for(int i = 0; i<entries.length;i++){
        	this.add(entries[i]);
        }       
	}
	
	@Override
	public int getCurrentSize(){
		return size;
	}

	@Override
	public boolean isEmpty(){
		if (size == 0) {
			return true;
		} 
		else {
			return false;
		}
	}

	@Override
	public boolean add(E newEntry) throws IllegalArgumentException 
	{
		boolean result = true;
		
		if(newEntry == null)
		{  
		    throw new IllegalArgumentException("newEntry is null");
		} 
		else
		{
			if (contains(newEntry))
			{
				result = false;
			}
			else
			{
				if(isArrayFull())
				{
					entries = Arrays.copyOf(entries, 2 * entries.length);
					entries[size] = newEntry;
					size++;
					// you can throw the SetFullException in this condition only if the capacity is fixed while it is not
				} 
				else 
				{
					// Assertion: result is true
					entries[size] = newEntry;
					size++;
				}
			}

		}
		return result;
	}

	@Override
	public boolean remove(E entry) throws IllegalArgumentException{
		if(entry == null)
		{  
		    throw new IllegalArgumentException("entry is null");
		} 
		
		boolean result = true;
		int index = getIndexOf(entry);
		if (index != -1)
		{
			for (int i = index; i < size - 1; i++)
			{
				entries[i] = entries[i+1];
			}
			entries[size -1] = null;
			size = size - 1;
			result = true;
		} 
		else{
			result = false;
		}
		return result;
	}

	@Override
	public E remove()
	{
		E removeE = null;
		
		if (isEmpty()){
			return removeE;
		}
		
		Random rand = new Random();
		int index = rand.nextInt(size);
		index = size -1;
		
		removeE = entries[index];
		boolean removeResult = remove(entries[index]);
		if (!removeResult){
			removeE = null; 
		}
		return removeE;
	}

	@Override
	public void clear(){
		while (!isEmpty())
			remove();
	}

	@Override
	public boolean contains(E entry) throws IllegalArgumentException {
		int index = getIndexOf(entry);
		if (index > -1){
			return true;
		}
		return false;
	}

	@Override
	public E[] toArray(){
		E[] array = (E[]) new Object[size];
		for (int i = 0; i < size; i++){
			array[i] = entries[i];
		}
		return array;
	}
	
	private boolean isArrayFull(){
		if (size < entries.length){
			return false;
		} 
		else{
			return true;
		}
	}
	
	private int getIndexOf(E anEntry){
		int where = -1;
		boolean found = false;
		int index = 0;

		while(!found && (index < size)){
			if(anEntry.equals(entries[index])){
				found = true;
				where = index;
			}
			index++;
		}
		return where;
	}


}

