package cs445.a3;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MagicSquare {
    // This static value represents the width of the current square
    static int size = -1;
    static int[] blankIndex;
    static int blankSize = 0;
    static int[][] indexMap;

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
        if (isDuplicates(square)){
            return true;
        }

        int sum_vertical = 0;
        int sum_horizontal = 0;
        boolean existBlank = false;
        int constant = (int)(1 + Math.pow(size , 2)) * size / 2;
        
        for (int i = 0; i < size; i++){
            sum_horizontal = 0;
            existBlank = false;
            for (int j = 0; j< size; j++){
                if (square[i][j] > size*size){
                    return true;
                } 
                if (square[i][j] == 0){
                    existBlank = true;
                }
                sum_horizontal = sum_horizontal + square[i][j];
                
            }
            if (sum_horizontal > constant){
                return true;
            }
            if (!existBlank && sum_horizontal != constant){
                return true;
            }
                   
        }

        for (int j = 0; j < size; j++){
            sum_vertical = 0;
            existBlank = false;
            for (int i = 0; i < size; i++){
                if (square[i][j] > size*size){
                    return true;
                }
                if (square[i][j] == 0){
                    existBlank = true;
                }
                sum_vertical = sum_vertical + square[i][j];
            }
            if (sum_vertical > constant){
                return true;
            }
            if (!existBlank && sum_vertical != constant){
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
                if (square[i][j] == 0 && !getFlag){
                    temp[i][j] = 1;
                    getFlag = true;
                }
                else{
                    temp[i][j] = square[i][j];
                }
            }
        }
        if (getFlag){
            return temp;
        }
        else{
            return null;
        }   
    }

    public static int[][] next(int[][] square) {
      
    // TODO: Complete this method
        int[][] temp = new int[size][size];

        boolean getFlag = false;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                int nextI = i;
                int nextJ = j;
                if (indexMap[i][j] > -1){
                    int nextIndex = blankIndex[indexMap[i][j] + 1];
                    nextI = nextIndex / size;
                    nextJ = nextIndex % size;
                }
                if ((indexMap[i][j] == blankSize - 1 || square[nextI][nextJ] == 0) && !getFlag) {
                    if(square[i][j] >= size*size ){
                        return null;
                    }else{
                        temp[i][j] = square[i][j] + 1;
                        getFlag = true;
                }

                } else {
                    temp[i][j] = square[i][j];
                }
            }
        }
        if (Arrays.deepEquals(square, temp)) return null;
        return temp;
    }

    static void testIsFullSolution() {
        // TODO: Complete this method
        int[][] fullSolutions1 = new int[][] {
            {8, 1, 6},
            {3, 5, 7},
            {4, 9, 2},
        };

        int[][] fullSolutions2 = new int[][] {
            {1, 12, 14, 7},
            {6, 15, 9, 4},
            {11, 2, 8, 13},
            {16, 5, 3, 10}
        };


        int[][] notFullSolutions1 = new int[][] {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
        };

        int[][] notFullSolutions2 = new int[][] {
            {8, 1, 6},
            {3, 5, 7},
            {4, 9, 0},
        };

        int[][] notFullSolutions3 = new int[][] {
            {1, 6, 9},
            {5, 7, 2},
            {8, 3, 4},
        };

        System.err.println("These should be full:");
        if (isFullSolution(fullSolutions1)) {
            System.err.println("\tFull sol'n:\t" + Arrays.deepToString(fullSolutions1));
        } else {
            System.err.println("\tNot full sol'n:\t" + Arrays.deepToString(fullSolutions1));
        }
        if (isFullSolution(fullSolutions2)) {
            System.err.println("\tFull sol'n:\t" + Arrays.deepToString(fullSolutions2));
        } else {
            System.err.println("\tNot full sol'n:\t" + Arrays.deepToString(fullSolutions2));
        }
        
        System.err.println("These should NOT be full:");
        if (isFullSolution(notFullSolutions1)) {
            System.err.println("\tFull sol'n:\t" + Arrays.deepToString(notFullSolutions1));
        } else {
            System.err.println("\tNot full sol'n:\t" + Arrays.deepToString(notFullSolutions1));
        }
        if (isFullSolution(notFullSolutions2)) {
            System.err.println("\tFull sol'n:\t" + Arrays.deepToString(notFullSolutions2));
        } else {
            System.err.println("\tNot full sol'n:\t" + Arrays.deepToString(notFullSolutions2));
        }
        if (isFullSolution(notFullSolutions3)) {
            System.err.println("\tFull sol'n:\t" + Arrays.deepToString(notFullSolutions3));
        } else {
            System.err.println("\tNot full sol'n:\t" + Arrays.deepToString(notFullSolutions3));
        }


    }

    static void testReject() { 
        // TODO: Complete this method
         size = 3;
         int[][] notRejected1 = new int[][] {
             {8, 1, 6},
             {3, 5, 7},
             {4, 9, 2},
         };
         int[][] notRejected2 = new int[][] {
             {0,0,0},
             {0,0,0},
             {0,0,0},
         };
         int[][] notRejected3 = new int[][] {
             {1,2,0},
             {0,0,0},
             {0,0,0},
         };

        
         int[][] rejected1 = new int[][] 
         {
             {1, 2, 3},
             {0, 0, 0},
             {0, 0, 0}
         };
         int[][] rejected2 = new int[][] 
         {
             {1, 2, 18},
             {0, 0, 0},
             {0, 0, 0}
         };

         findBlank(notRejected1);
         System.err.println("These should NOT be rejected:");
         if (reject(notRejected1)) {
             System.err.println("\tRejected:\t" + Arrays.deepToString(notRejected1));
         } else {
             System.err.println("\tNot rejected:\t" + Arrays.deepToString(notRejected1));
         }
         findBlank(notRejected2);
         
         if (reject(notRejected2)) {
             System.err.println("\tRejected:\t" + Arrays.deepToString(notRejected2));
         } else {
             System.err.println("\tNot rejected:\t" + Arrays.deepToString(notRejected2));
         }
         findBlank(notRejected3);
         
         if (reject(notRejected3)) {
             System.err.println("\tRejected:\t" + Arrays.deepToString(notRejected3));
         } else {
             System.err.println("\tNot rejected:\t" + Arrays.deepToString(notRejected3));
         }

         findBlank(rejected1);
         System.err.println("These should be rejected:");
         if (reject(rejected1)) {
             System.err.println("\tRejected:\t" + Arrays.deepToString(rejected1));
         } else {
             System.err.println("\tNot rejected:\t" + Arrays.deepToString(rejected1));
         }
         findBlank(rejected2);
         System.err.println("These should be rejected:");
         if (reject(rejected2)) {
             System.err.println("\tRejected:\t" + Arrays.deepToString(rejected2));
         } else {
             System.err.println("\tNot rejected:\t" + Arrays.deepToString(rejected2));
         }
        
    }

    static void testExtend() {
        // TODO: Complete this method
        size = 3;
        int [][] noExtend = new int [][] {
            {8,1,6},
            {3,5,7},
            {4,9,2},
        };

        int [][] extend1 = new int[][]{
            {0,0,0},
            {0,0,0},
            {0,0,0},
        };
        
        int [][] extend2 = new int [][] {
            {8,1,6},
            {3,5,7},
            {4,9,0},
        };

        int[][] extend3 = new int[][] {
            {8,1,6},
            {3,5,7},
            {0,0,2},
        };
        

        findBlank(noExtend);
        System.err.println("These can NOT be extended:");
        System.err.println("\tExtended " + Arrays.deepToString(noExtend)+ " to " 
                            + Arrays.deepToString(extend(noExtend)));
        findBlank(extend1);
        System.err.println("These can be extended:");
        System.err.println("\tExtended " + Arrays.deepToString(extend1) + " to " 
                            + Arrays.deepToString(extend(extend1)));
        findBlank(extend2);
        System.err.println("\tExtended " + Arrays.deepToString(extend2) + " to " 
                            + Arrays.deepToString(extend(extend2)));
        findBlank(extend3);
        System.err.println("\tExtended " + Arrays.deepToString(extend3) + " to " 
                            + Arrays.deepToString(extend(extend3)));

    }

    static void testNext() {
        // TODO: Complete this method
        size = 3;
        int [][] noNext = new int [][] {
            {1,2,9},
            {0,0,0},
            {0,0,0},
        };
        
        int [][] next1 = new int [][] {
            {8,1,6},
            {3,5,7},
            {4,9,0},
        };

        int [][] next2 = new int [][] {
            {1,8,6},
            {5,0,0},
            {0,0,0},
        };


        findBlank(noNext);
        System.err.println("These can NOT be next'd:");
        System.err.println("\tNexted " + Arrays.deepToString(noNext) + " to " 
                            + Arrays.deepToString(next(noNext)));
        findBlank(next1);
        System.err.println("These can be next'd:");
        System.err.println("\tNexted " + Arrays.deepToString(next1) + " to " 
                            + Arrays.deepToString(next(next1)));
        findBlank(next2);
        System.err.println("\tNexted " + Arrays.deepToString(next2)+ " to " 
                            + Arrays.deepToString(next(next2)));

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

    public static void findBlank(int[][] square){
        blankSize = 0;
        blankIndex = new int[size * size +1];
        indexMap = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (square[i][j] == 0){
                    indexMap[i][j] = blankSize;
                    blankIndex[blankSize] = i * size + j;
                    blankSize++;
                }
                else{
                    indexMap[i][j] = -1;
                }
            }
        }
    }

    public static void main(String[] args) {
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

                System.out.println("Starting square:");
                printSquare(square);
                
                findBlank(square);
                
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

                System.out.println("Starting square:");
                printSquare(square);

                findBlank(square);
                
                System.out.println("\nSolution:");
                printSquare(solve(square));
            } catch (NumberFormatException e) {
                // This happens if the first argument isn't an int
                System.err.println("First argument must be size");
            }
        } else {
            System.err.println("See usage in assignment description");
        }
    }
}

//back tracking recursion:
//build a recursion tree:
    //

