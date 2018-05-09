package adventuregame;

import java.util.Random;

public class Enemy extends Character{
	
	public Enemy(Type enemyType){
		super(enemyType);
	}

	public int dropCoins(){
		Random randomNums = new Random();
		return randomNums.nextInt(21)+30;

	}

	public int getNumGoblins(){
		Random randomNums = new Random();
		return randomNums.nextInt(4)+2;

	}

	public int getNumSkeletons(){
		Random randomNums = new Random();
		return randomNums.nextInt(5)+3;

	}

}