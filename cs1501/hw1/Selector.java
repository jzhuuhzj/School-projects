
public class Selector {
    
    private static void swap(Word[] array, int i, int j){
        if(i == j) return;
        
        Word temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public static Word[] select(Word[] array, int k){
        // Problem #1
        // Fill in this method with an O(n*k) time algorithm
        // that returns the k largest elements of array in
        // order from largest to smallest.
        // Note: This should return an array with k elements.
        int n = array.length;
        Word[] tempArray = new Word[k];
        for (int i = 0; i < k; i++){
            for (int j = i + 1; j < n; j++){
                if (array[j].compareTo(array[i]) > 0){
                    swap(array, j, i);
                }
            }
            tempArray[i] = array[i];
        }
        return tempArray;
    }
}
