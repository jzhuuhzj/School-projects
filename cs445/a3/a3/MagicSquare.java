package cs445.a3;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MagicSquare {
    // This static value represents the width of the current square
    static int size = -1;

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
        int sum_horizontal = 0;
        int sum_vertical = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j< size; j++){
                if (square[i][j] > Math.pow(size,2)){
                    return true;
                } 
                else if (isDuplicates(square)){
                    return true;
                }
                else{
                    sum_vertical = square[i][j] + sum_vertical;
                    sum_horizontal = square[j][i] + sum_horizontal;
                    if ((sum_horizontal != sum_vertical) && (sum_horizontal != (1+ Math.pow(size,2))*size*0.5) && (sum_vertical != (1+ Math.pow(size,2))*size*0.5))
                        return true;
                }
                
            }
        }
        return false;
    }

    public static boolean isDuplicates(int[][] square){
        int count = 0;
        for (int k = 1; k <= Math.pow(size,2); k++){
            for (int i = 0; i < size; i++)
                for (int j = 0; j< size; j++){
                    if (square[i][j] == k){
                        count++;
                    }
            }
            
        }
        if (count >= 2){
            return true;
        }
        return false;
    }

    public static int[][] extend(int[][] square) {
        // TODO: Complete this method
        int[][] temp;
        temp = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                temp[i][j] = square[i][j];
            }
        }

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (temp[i][j] == 0){
                    temp[i][j] = -1;
                    return temp;
                }
            }
        }
        return null;    
    }

    public static int[][] next(int[][] square) {
        // TODO: Complete this method
        int[][] temp;
        temp = new int[size][size];
        for (int i = 0; i < size; i++) {
            int j = 0;
            if (temp[i][j] != 0) {
            }
            if(j == size){
                i++;
                j=0;
                
            } 
        }
        return null;
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
        for (int[] test : noExtend) {
            System.err.println("\tExtended " + Arrays.toString(test) + " to " + Arrays.toString(extend(test)));
        }

        System.err.println("These can be extended:");
        for (int[] test : extend) {
            System.err.println("\tExtended " + Arrays.toString(test) + " to " + Arrays.toString(test));
        }
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
        for (int[] test : noNext) {
            System.err.println("\tNexted " + Arrays.toString(test) + " to " + Arrays.deepToString(next(test)));
        }

        System.err.println("These can be next'd:");
        for (int[] test : next) {
            System.err.println("\tNexted " + Arrays.toString(test) + " to " + Arrays.deepToString(next(test)));
        }

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

