package cs445.a1;

public class SocialClient1 {
	public static void main(String[] args) {
		
		Set<Integer> set =  new Set<Integer>();
		
		for(int i = 0; i < 11; i++) {
			set.add(i);
		}
		printSet(set);
		
		set.remove(2);
		printSet(set);
		
		set.clear();
		System.out.println(set.isEmpty());
		
		Profile profile1 = new Profile("name1" ,"about1");
		/*Profile profile2 = new Profile("name2" ,"about2");
		Profile profile3 = new Profile("name3" ,"about3");
		Profile profile4 = new Profile("name4" ,"about4");
		profile1.follow(profile2);
		profile1.follow(profile3);
		profile3.follow(profile4);
		ProfileInterface[] followings = profile1.following(2);
		for (int i = 0; i < 2; i++){
			System.out.println(followings[i].getName() + " " + followings[i].getAbout());
		}*/
		ProfileInterface recommendPro = profile1.recommend();
		if (recommendPro != null) {
			System.out.println("recommend Profile is:" + recommendPro.getName() + " " + recommendPro.getAbout());
		}
		
	}
	private static void printSet(Set<Integer> set) {
		Object[] arr = set.toArray();
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
