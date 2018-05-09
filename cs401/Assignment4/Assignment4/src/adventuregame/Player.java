package adventuregame;

import java.util.Random;
import java.util.Scanner;

public class Player extends Character{
	
	//ATTRBUTS FOR PLAYER CLASS TODO:
	public int coins = 0;
	public Potion[] inventory;

	//methods
	public Player(Type playerType){
		super(playerType);
		coins = 0;
		inventory = new Potion[5];
	}

	public void increaseStrength(int strengthIncrease){
		strength += strengthIncrease;

	}

	public int getCoins(){
		return coins;
	}

	public void incraseCoind(int coins){
		this.coins = this.coins + coins;
	}

	public void decreaseCoins(int coins){
		this.coins = this.coins - coins;
	}

	public void addToInventory(Potion potion){
		for (int i = 0; i < inventory.length; i++) {
			if (null == inventory[i]) {
				inventory[i] = potion;
				break;
			}
		}
	}

	public void removeFromInventory(int index){
		inventory[index] = null;
	}

	public void displayInventory(){
		System.out.println("Your inventory is:");
		for (int i = 0; i < inventory.length; i++) {
			if (null != inventory[i]) {
				System.out.println("[" + i +"] " + inventory[i].getName());
			}
			else {
				System.out.println("[" + i +"] ");
			}
				
		}
	}

	public int getNumOpenSlots(){
		int slotsSum = 0;
		for (int i = 0; i < inventory.length; i++) {
			if (null != inventory[i]) {
				slotsSum ++;
			}
		}
		return slotsSum;
	}

	public boolean battleMinion(Enemy enemy){
		while (this.hitPoints > 0 && enemy.hitPoints > 0) {
			this.attack(enemy);
			if (enemy.hitPoints <= 0 ){
				System.out.println();
				return true;
			}
			enemy.attack(this);
			if (this.hitPoints <=0 ){
				System.out.println();
				return false;
			}
		}
		return true;
	}

	public void battleWizard(Enemy enemy){
		
		System.out.println("You have now reached The Castle! Time to battle The Evil Wizard!\n");
		System.out.printf("***%s vs The Evil Wizard***\n", this.name);
		
		Scanner keyboard = new Scanner(System.in);
	    Random randomNums = new Random();
	    int randomNumAnswer = randomNums.nextInt(5) + 1;
	    int playerActionChoice;
	    int randomNumGuess;
	    
		while (this.hitPoints > 0 && enemy.hitPoints > 0) {
			
			System.out.println("Choose your action:\n1. Attack\n2. Attempt Spell Cast\n");
		    System.out.print("What would you like to do: ");
		    
		    playerActionChoice = keyboard.nextInt();

		    switch(playerActionChoice)
		    {
		    case 1:
		    	this.attack(enemy);
		    	break;
		    case 2:
				System.out.print("Enter your guess: ");
				randomNumGuess = keyboard.nextInt();
				if (randomNumGuess == randomNumAnswer)
				{
					System.out.println("\nCorrect!\n");
					System.out.printf("The %s's spell is cast successfully! The Wizard's HP is now 0!\n\n", this.name);
				    enemy.hitPoints = 0;
				}
				else 
				{
					System.out.println("\nIncorrect! The spell cast fails!\n");
				}  
				break;
		    }		
		    
		    if (enemy.hitPoints <= 0) {
		    	System.out.printf("--%s wins the battle!--\n\n", this.name);
			    System.out.println("You win! Congratulations!");
		    	keyboard.close();
			    return;
		    }
		    
		    enemy.attack(this);
			if (this.hitPoints <=0 ){
				System.out.printf("--%s is defeated in battle!--\n\n", this.name);
				keyboard.close();
				return;
			}
		} 
		keyboard.close();
		return;
	}
}