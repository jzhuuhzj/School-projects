
public class Word {
	public String value;
	
	public Word(String value){
		this.value = value;
	}
	
	public int hashCode(){
		if(value.length() == 0) return 0;
		else if(value.length() == 1) return (int)value.charAt(0);
		else if(value.length() == 2) return (int)value.charAt(0) + (int)value.charAt(1) * 128;
		else return (int)value.charAt(0) + (int)value.charAt(1) * 128 + 
				(int)value.charAt(value.length() - 1) * 128 * 128;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof Word)) return false;
		else return this.value.equals(((Word)obj).value);
	}
}
