import java.util.Scanner;

public class VolumeCalculator {
	public static void main(String[] args) {
		//declare variables
		int dim1, dim2, dim3, volume;
		Scanner keyboard = new Scanner(System.in);		
		//get dimensions from the user
		dim1=getDimension(keyboard);
		dim2=getDimension(keyboard);
		dim3=getDimension(keyboard);
		
		
		//calculate the volume
		volume = calculateVolume(dim1, dim2, dim3);
		
		//display the output
		displayResults(dim1, dim2, dim3, volume);
	}
	
	//get input
	public static int getDimension(Scanner keyboard){
		int dim;		
		System.out.println("Enter a dimension");
		dim = keyboard.nextInt();
		
		return dim;
	}
		
	//calculation
	public static int calculateVolume(int d1, int d2, int d3){
		return d1 * d2 * d3;
	}
	
	//display output
	public static void displayResults(int d1, int d2, int d3, int vol){
		System.out.printf("%d units x %d units x %d units = %d units^3\n", d1, d2, d3, vol);	
	}