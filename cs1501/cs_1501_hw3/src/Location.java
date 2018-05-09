package hw3;



public class Location {

	public String name;
	
	public Location(String name){
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof Location)) return false;
		else return this.name.equals(((Location)obj).name);
	}
	
}
