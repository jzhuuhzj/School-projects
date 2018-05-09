
public class Word implements Comparable<Word>{
	public String value;
	public Integer frequency;
	
	public Word(String value, Integer frequency){
		this.value = value;
		this.frequency = frequency;
	}

	@Override
	public int compareTo(Word otherWord) {
		if(this.frequency.equals(otherWord.frequency))
			return -1 * this.value.compareTo(otherWord.value);
		else
			return this.frequency.compareTo(otherWord.frequency);
	}
	
	public String toString(){
		return "[" + value + ", " + frequency + "]";
	}
}
