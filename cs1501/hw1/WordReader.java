import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordReader {
	
	public static Word[] loadWords(){
		Word[] wordArray = new Word[10000];
		
		try{
			BufferedReader bf = new BufferedReader(new FileReader("words.csv"));
			int count = 0;
			while(count < wordArray.length){
				wordArray[count] = parseWord(bf.readLine());
				count++;
			}
			bf.close();
		}
		catch(IOException e){
			System.out.println("File 'words.csv' not found.");
		}
		
		return wordArray;
	}
	
	private static Word parseWord(String line){
		if(line == null)
			return null;
		
		String[] strPair = line.split(",");
		if(strPair.length == 2)
			return new Word(strPair[0], Integer.parseInt(strPair[1]));
		else
			return null;
	}
}
