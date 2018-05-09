package adventuregame;

import java.util.Random;

public class Weapon{
	//public constants
	public static final int SHORT_SWORD_MIN = 1;
	public static final int SHORT_SWORD_MAX = 4;
	public static final int LONG_SWORD_MIN = 3;
	public static final int LONG_SWORD_MAX = 7;
	public static final int JUMP_KICK_MIN = 2;
	public static final int JUMP_KICK_MAX = 6;
	public static final int AXE_MIN = 2;
	public static final int AXE_MAX = 6;
	public static final int MACE_MIN = 2;
	public static final int MACE_MAX = 6;

	//attributs
	private String name;
	private int minDamage;
	private int maxDamage;

	//methods
	public Weapon(String name, int minDamage, int maxDamage){
		this.name = name;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}

	public String getName(){
		return name;

	}

	public int getMinDamage(){
		return minDamage;

	}

	public int getMaxDamage(){
		return maxDamage;

	}

	public int getDamage(){
		Random randomNums = new Random();
		return randomNums.nextInt(maxDamage - minDamage + 1) + minDamage;
	}
}	