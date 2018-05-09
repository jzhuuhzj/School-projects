package cs445.a3;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MagicSquare1 {
    // This static value represents the width of the current square
    static int size = -1;
    static int[] blankIndex;
    static int blankSize = 0;
    static int indexMap[][];
    public static boolean isFullSolution(int[][] square) {
        // TODO: Complete this method
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (square[i][j] == 0){
                    return false;
                }
            }
        }
        if (reject(square)){
            return false;
        }
        return true;
    }

    public static boolean reject(int[][] square) {
        // TODO: Complete this method
    	if (isDuplicates(square)) {
    		return true;
    	}
    	
        int sum_vertical = 0;
        int sum_horizontal = 0;
        boolean existBlank = false;
        int constant = (int) (1 + Math.pow(size,2)) * size / 2;
        for (int i = 0; i < size; i++){
        	sum_horizontal = 0;
        	existBlank  = false;
            for (int j = 0; j< size; j++){
                if (square[i][j] > size*size){
                    return true;
                } 
                if (square[i][j] == 0){
                	existBlank = true;
                }  
                sum_horizontal = sum_horizontal + square[i][j];
            }
            if (sum_horizontal > constant) {
            	return  true;
            } 
            if (!existBlank && sum_horizontal != constant) {
            	return true;
            }          	
        }
        
        for (int j = 0; j < size; j++){
        	sum_vertical = 0;
        	existBlank  = false;
            for (int i = 0; i< size; i++){
                if (square[i][j] > size*size){
                    return true;
                } 
                if (square[i][j] == 0){
                	existBlank = true;
                }  
                sum_vertical = sum_vertical + square[i][j];
            }
            if (sum_vertical > constant) {
            	return  true;
            } 
            if (!existBlank && sum_vertical != constant) {
            	return true;
            }          	
        }
        
        return false;
    }

    public static boolean isDuplicates(int[][] square){
        int  max_value = size * size;
		int count[] = new int[max_value + 1];
		for (int i = 1; i < max_value; i++) {
			count[i] = 0;
		}
        for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                	if (square[i][j] > 0) {
	                	count[square[i][j]] ++;
	                	if (count[square[i][j]] > 1) {
	                		return true;
	                	}
                	}
                }
        }
        
        return false;
    }

    public static int[][] extend(int[][] square) {
        // TODO: Complete this method
        int[][] temp;
        boolean getFlag = false;
        temp = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
            	if (square[i][j] == 0 && !getFlag) {
            		temp[i][j] = 1;
            		getFlag = true;
            	}
            	else {
            		temp[i][j] = square[i][j];
            	}
            }
        }
        if (getFlag) {
        	return temp;   
        }
        else {
        	return null;
        }
         
    }

    public static int[][] next(int[][] square) {
        // TODO: Complete this method
        int[][] temp;
        temp = new int[size][size];
        boolean getFlag = false;
        for (int i = 0; i < size; i++) {
           for (int j = 0; j < size;j++) {
        	   int nextI = i;
        	   int nextJ = j;
        	   if (indexMap[i][j] >-1) {
	    		   int nextIndex = blankIndex[indexMap[i][j] + 1];
	    		   nextI = nextIndex / size;
	    		   nextJ = nextIndex % size;
        	   }
        	   if ((indexMap[i][j] == blankSize - 1 || square[nextI][nextJ] == 0) && !getFlag) {
                  
                   if (square[i][j] >= size * size) {
                      
                       return null;
                   } else {
                       
                	   temp[i][j] = square[i][j] + 1;
                       getFlag = true;
                   }
               } else {
                  
                   temp[i][j] = square[i][j];
               }
            
           }
        }
        return temp;
    }

    static void testIsFullSolution() {
        // TODO: Complete this method
        int[][] fullSolutions = new int[][] {
            {8, 1, 6},
            {3, 5, 7},
            {4, 9, 2},
        };

        int[][] notFullSolutions = new int[][] {
            {8, 1, 6},
            {3, 5, 7},
            {4, 9, 0},
        };

        System.err.println("These should be full:");
        if (isFullSolution(fullSolutions)) {
            System.err.println("\tFull sol'n:\t" + Arrays.deepToString(fullSolutions));
        } else {
            System.err.println("\tNot full sol'n:\t" + Arrays.deepToString(fullSolutions));
        }
        
        System.err.println("These should NOT be full:");
        if (isFullSolution(notFullSolutions)) {
            System.err.println("\tFull sol'n:\t" + Arrays.deepToString(notFullSolutions));
        } else {
            System.err.println("\tNot full sol'n:\t" + Arrays.deepToString(notFullSolutions));
        }


    }

    static void testReject() { 
        // TODO: Complete this method
         int[][] notRejected = new int[][] {
             {8, 1, 6},
             {3, 5, 7},
             {4, 9, 2},
         };

         int[][] rejected = new int[][] {
             {2, 0, 0},
             {0, 4, 0},
             {0, 0, 6},
         };
         System.err.println("These should NOT be rejected:");
         if (reject(notRejected)) {
             System.err.println("\tRejected:\t" + Arrays.toString(notRejected));
         } else {
             System.err.println("\tNot rejected:\t" + Arrays.toString(notRejected));
         }
         
         System.err.println("These should be rejected:");
         if (reject(rejected)) {
             System.err.println("\tRejected:\t" + Arrays.toString(rejected));
         } else {
             System.err.println("\tNot rejected:\t" + Arrays.toString(rejected));
         }
    }

    static void testExtend() {
        // TODO: Complete this method
         int [][] noExtend = new int [][] {
            {8,1,6},
            {3,5,7},
            {4,9,2},
        };
        
        int [][] extend = new int [][] {
            {8,1,6},
            {3,5,7},
            {4,9,2},
        };
        
        System.err.println("These can NOT be extended:");
        System.err.println("\tExtended " + Arrays.toString(noExtend) + " to " + Arrays.toString(extend(noExtend)));


        System.err.println("These can be extended:");
        System.err.println("\tExtended " + Arrays.toString(extend) + " to " + Arrays.toString(extend));
    }

    static void testNext() {
        // TODO: Complete this method
        int [][] noNext = new int [][] {
            {8,1,6},
            {3,5,7},
            {4,9,2},
        };
        
        int [][] next = new int [][] {
            {8,1,6},
            {3,5,7},
            {4,9,2},
        };
        
        System.err.println("These can NOT be next'd:");
        System.err.println("\tNexted " + Arrays.toString(noNext) + " to " + Arrays.deepToString(next(noNext)));

        System.err.println("These can be next'd:");
        System.err.println("\tNexted " + Arrays.toString(next) + " to " + Arrays.deepToString(next(next)));

    }

    static String pad(int num) {
        int sum = size * (size * size + 1) / 2;
        int width = Integer.toString(sum).length();
        String result = Integer.toString(num);
        while (result.length() < width) {
            result = " " + result;
        }
        return result;
    }

    public static void printSquare(int[][] square) {
        if (square == null) {
            System.out.println("No solution");
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(pad(square[i][j]) + " ");
            }
            System.out.print("\n");
        }
    }

    public static int[][] readSquare(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int[][] square = new int[size][size];
        int val = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                square[i][j] = scanner.nextInt();
            }
        }
        return square;
    }

    public static int[][] solve(int[][] square) {
        if (reject(square)) return null;
        if (isFullSolution(square)) return square;
        int[][] attempt = extend(square);
        while (attempt != null) {
            int[][] solution;
            solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }
    public static void findBlack(int[][] square) {
    	 blankIndex = new int[size * size + 1];
    	 indexMap = new int[size][size];
    	 for (int i = 0; i < size; i++) {
             for (int j = 0; j < size; j++) {
                 if (square[i][j] == 0){
                	 indexMap[i][j] = blankSize;
                	 blankIndex[blankSize] = i * size + j;
                	 blankSize ++;
                 } 
                 else {
                	 indexMap[i][j] = -1; 
                 }
             }
         }
    }
    
    public static void main(String[] args) {
    	
    	int [][] square = new int [][] {
    			{11,0,7,0,0},
    			{4,0,0,0,0},
    			{0,0,0,21,0},
    			{10,0,0,0,0},
    			{0,0,19,0,0},
    			
    	        };
    	size = 5;
    	System.out.println("Starting square:");
        printSquare(square);
        findBlack(square);
        System.out.println(reject(square)); 
        System.out.println("\nSolution:");
        printSquare(solve(square));
       if (args.length >= 1 && args[0].equals("-t")) {
            System.out.println("Running tests...");
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else if (args.length >= 2) {
            try {
                // First get the specified size
                size = Integer.parseInt(args[0]);
                // Then read the square from the file
                int[][] square = readSquare(args[1]);
                findBlack(square);
                System.out.println("Starting square:");
                printSquare(square);
                System.out.println("\nSolution:");
                printSquare(solve(square));
            } catch (NumberFormatException e) {
                // This happens if the first argument isn't an int
                System.err.println("First argument must be size");
            } catch (FileNotFoundException e) {
                // This happens if the second argument isn't an existing file
                System.err.println("File " + args[1] + " not found");
            }
        } else if (args.length >= 1) {
            try {
                // First get the specified size
                size = Integer.parseInt(args[0]);
                // Then initialize to a blank square
                int[][] square = new int[size][size];
                findBlack(square); 
                System.out.println("Starting square:");
                printSquare(square);
                System.out.println("\nSolution:");
                printSquare(solve(square));
            } catch (NumberFormatException e) {
                // This happens if the first argument isn't an int
                System.err.println("First argument must be size");
            }
        } else {
            System.err.println("See usage in assignment description");
        }*/
    }
}

