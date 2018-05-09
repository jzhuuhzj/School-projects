package cs445.a1;

public class Profile implements ProfileInterface{
	private String name;
	private String about;
	private Set<ProfileInterface> followees = new Set<ProfileInterface>();
	private int capacity = 20; 
	
	public Profile(){
		name = "";
		about = "";
	}
	
	public Profile(String name, String about){
		
		if (null == name){
			this.name = "";
		} 
		else{
			this.name = name;
		}
		
		if (null == about){
			this.about = "";
		} 
		else{
			this.about = about;
		}

	}
	
	@Override
	public void setName(String newName) throws IllegalArgumentException{
		if (null == newName) {
			throw new IllegalArgumentException("Name cannot be null");
		} 
		else {
			name = newName;
		}
	}

	@Override
	public String getName(){
		return name;
	}

	@Override
	public void setAbout(String newAbout) throws IllegalArgumentException{
		
		if (null == newAbout){
			throw new IllegalArgumentException("About cannot be null");
		} 
		else{
			about = newAbout;
		}
	}

	@Override
	public String getAbout(){
		return about;
	}

	@Override
	public boolean follow(ProfileInterface other){
		if (followees.getCurrentSize() >= capacity || other == null){
			return false;
		} 
		else {
			followees.add(other);
		}
		return true;
	}

	@Override
	public boolean unfollow(ProfileInterface other){
		if (!followees.contains(other) || other == null){
			return false;
		}
		else{
			followees.remove(other);
		}
		return true;
	}

	@Override
	public ProfileInterface[] following(int howMany){
		int size = followees.getCurrentSize();
		if (howMany < size){
			size = howMany;
		}
		Object[] profileArray = followees.toArray();
		ProfileInterface[] returnArray = new ProfileInterface[size];
		for (int i = 0; i < size; i++){
			returnArray[i] = (ProfileInterface)profileArray[i];
		}
		return returnArray;
	}

	@Override
	public ProfileInterface recommend(){
		int size = followees.getCurrentSize();
		if (size > 0) {
			ProfileInterface[] followings = following(size);
			ProfileInterface[] followeeSfollowees;
			for (int i = 0; i < size; i++){
				followeeSfollowees = followings[i].following(capacity);
				for (int j = 0; j < followeeSfollowees.length; j++){
					if (!followees.equals(followeeSfollowees[j])  && !followees.contains(followeeSfollowees[j])){
						return followeeSfollowees[j];
					}
				}
			}
		}
		return null;
	}
}
