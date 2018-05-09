package adventuregame;

import java.util.Scanner;
import java.util.Random;

public class AdventureGameV3 
{
	public static void main(String[] args)
  	{
		
    	int characterChoice = getCharacter();	
    	
		Player player = null;
		
		
		switch(characterChoice){
			case 1:   //ROGUE case
				player = new Player(Character.Type.ROGUE);  // OR : player = new Player("Rogue", 55, 14, new Weapon());
				break;

			case 2:  //PALADIN case
				player = new Player(Character.Type.PALADIN);
				break;

			case 3: // JACKIE_CHAN case
				player = new Player(Character.Type.JACKIE_CHAN);
				break;
		}

		System.out.printf("\nYou chose: %s\n\n", player.getName());
    	System.out.println("The Evil Wizard must be defeated! He is in The Castle. To get to The Castle, you" +
    		" must travel through The Forest and then through The Graveyard. Let's go!");
		
    	String pathName = "The Forest";
    	int enemyNumber = 2;
    	Enemy enemy = new Enemy(Character.Type.GOBLIN) ;
    	if (!battleMinon(player, enemy , enemyNumber, pathName)) {
    		System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n", player.name);
            System.exit(0);	
    	}
    	
    	System.out.println("-- " + player.name + " wins the battle! --\n");
        System.out.printf("Your HP is: %d\n", player.hitPoints);
        
        LetsHaveARest(player);
	
        pathName = "The Graveyard";
    	enemyNumber = 5;
    	enemy =new Enemy(Character.Type.SKELETON) ;
    	if (!battleMinon(player, enemy , enemyNumber, pathName)) {
    		System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n", player.name);
            System.exit(0);	
    	}
    	
    	LetsHaveARest(player);
    	enemy =new Enemy(Character.Type.WIZARD);
    	player.battleWizard(enemy);
    	System.out.println("GAME OVER\n");
	}
	
	private static int getCharacter()
	{
		int characterChoice;
		Scanner keyboard = new Scanner(System.in);

		System.out.println("\nAdventure Game - Start!\n");
		System.out.println("Here are the characters:");
		System.out.println("1. Rogue\n2. Paladin\n3. Jackie Chan\n");
		System.out.print("Which character do you choose?: ");
		characterChoice = keyboard.nextInt();
		 
		return characterChoice;
	}
	private static boolean battleMinon(Player player, Enemy enemy, int enemyNumber, String pathName){
		
		Scanner keyboard = new Scanner(System.in);
		Random randomNums = new Random();
		int randomCoins;
		
		System.out.printf("Once you enter %s, you encounter %d %ss! Time for battle!\n\n", pathName,
				 enemyNumber, enemy.name);
		 
		 for (int i = 1; i <= enemyNumber; i++) {
			 	System.out.printf("***%s vs %s %d***\n", player.name, enemy.name, i);
	    		enemy = new Enemy(Character.Type.GOBLIN);
	    		if (player.battleMinion(enemy)){
	    			
	    			System.out.printf("%s defeated %s %d!\n\n", player.name, enemy.name, enemyNumber);
	    			randomCoins = randomNums.nextInt((50 - 30) + 1) + 30;
	    	        System.out.println(player.name + " gains " + randomCoins + " gold coins!\n");
	    	        player.coins += randomCoins;
	    	        
	    	        System.out.println("Press enter to continue...");
	    	        //keyboard.nextLine();
	    		} 
	    		else 
	    		{
	    			 
	    			return false;
	    		}
	    	}
		  
		 return true;
	}
	
	private static void LetsHaveARest(Player player){
		Scanner keyboard = new Scanner(System.in);
		ItemShop itemShop = new ItemShop();
		int playerChoice;
		int potionChoice;
		while (true) {
			System.out.println("What would you like to do now?");
			System.out.println("1. View inventory");
			System.out.println("2. Drink a potion");
			System.out.println("3. Visit the Item Shop");
			System.out.println("4. Continue\n");
			System.out.print("Enter your choice here:");
			playerChoice = keyboard.nextInt();
			System.out.println();
			
			switch (playerChoice){
				case 1:
					player.displayInventory();
					break;
				case 2:
					if (player.getNumOpenSlots() == 0){
						System.out.println("You do not have any potions!\n");
					} else {
						System.out.print("Which potion would you like to drink?");
						potionChoice = keyboard.nextInt();
						player.inventory[potionChoice].drink(player);
						player.inventory[potionChoice] = null;
					}
					break;
				case 3:
					itemShop.visitItemShop(player);
					break;
				case 4:
					 
					return;
			}
		}
	}
}




  		


    	


    	