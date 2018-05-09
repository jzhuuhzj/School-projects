/**
Jingxia Zhu
CS0401
Spring 2017
Assignment 3
*/

import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class AdventureGameV2{
	
	public static void main (String[] args) throws IOException{
		System.out.println("AdventureGame - Start!\n");
		Scanner keyboard = new Scanner(System.in);
		//create new file used to store saved game data
		String fileName = "gameData.txt";
		File myFile = new File(fileName);
		//enter 1 to open and read the file; enter 0 to break(not showing the saved game data);
		System.out.print("Would you like to display the saved game results?"+
			"Enter 1 for \"yes\" or 0 for \"no\": ");
		int displayChoice = keyboard.nextInt();
		switch (displayChoice)
		{
			case 0:
				break;
			case 1: 
				System.out.printf("\n%-15s%-15s%s%18s%21s","Character","Weapon","HP","Path","Won or Lost?");
				System.out.printf("\n%-15s%-15s%s%18s%21s\n","---------","------","--","----","------------");

				//test if the file exists
				if (!myFile.exists()){
                    System.out.println("The input file is not found.");
                    break;
                }
				//In while loop, using m to count, once printed 5 numbers from file, print a blank line;
				Scanner inputFile = new Scanner(myFile);
				while (inputFile.hasNext()){
					int m = 0;
					for (m = 0; m < 5; m++){
						String savedData= inputFile.nextLine();
						System.out.printf("%-15s",savedData);	
					}
					System.out.println();
				}
				
				inputFile.close(); 

				break;	
		}
		System.out.println("\nAdventure Game - Start!\n");
        //characterChosen is returned from getCharacter() method
		int characterChosen = getCharacter();
		String characterName = "";
		int [] characterData = {};
		
		//different choices of characters
		switch(characterChosen){
			case 1:
				characterName = "Rogue";
				int[] rogueData = {1,4,55,8};
				//SHORT_SWORD_MIN = 1
				//SHORT_SWORD_MAX = 4
				//ROGUE_INIT_HP = 55
				//ROGUE_INIT_STRENGTH = 8
				characterData = rogueData;
				
				break;
			case 2:
				characterName = "Paladin";
				int[] paladinData = {2,6,45,10};
				//LONG_SWORD_MIN = 3;
				//LONG_SWORD_MAX = 7;
				//PALADIN_INIT_HP = 35;
				//PALADIN_INIT_STRENGTH = 14;
				characterData = paladinData;
				
				break;
			case 3:
				characterName = "Jackie Chan";
				int[] chanData = {2,6,45,10};
				//JUMP_KICK_ MIN = 2;
				//JUMP_KICK_MAX = 6;
				//CHAN_INIT_HP = 45;
				//CHAN_INIT_STRENGTH = 10;
				characterData = chanData;
				
				break;
		}
		
		
		
		System.out.printf("\nYou chose: %s\n\n", characterName);
		System.out.print("The Evil Wizard must be defeated! He is in The Castle. To get to ");
        System.out.println("The Castle, you must travel through either:");
        System.out.println("1. The Forest\n2. The Graveyard\n");
		System.out.print("Which path will you take?: ");
		//pathChosen is returned from getPath() method
		int pathChosen = getPath();
		Random randomNumbers = new Random();

		
		String pathName = "";
		String minionName = "";
	    int[] minionData = {};
		int minionNum = 0;
		

        switch(pathChosen)
        {
            case 1:
                pathName = "The Forest";
                minionName = "Goblin";
                int [] goblinData = {2,6,25,4};
                //AXE_MIN = 2;
				//AXE_MAX = 6;
				//GOBLIN_INIT_HP = 25;
				//GOBLIN_INIT_STRENGTH = 4;
                minionData = goblinData;
                minionNum = randomNumbers.nextInt(4)+2;                
                break; 

            case 2:
                pathName = "The Graveyard";
               	minionName = "Skeleton";
                int[] skeletonData = {1,4,25,3};
                //SHORT_SWORD_MIN = 2;
				//SHORT_SWORD_MAX = 6;
				//SKELETON_INIT_HP = 25;
				//SKELETON_INIT_STRENGTH = 4;
                minionData = skeletonData;
                minionNum = randomNumbers.nextInt(5)+3;                
                break;
        }  
       


        System.out.printf("\nYou chose: %s\n\n", pathName);
        System.out.printf("Once you enter %s, you encounter %d %ss! Time for battle!\n\n", pathName, minionNum, minionName); 
	  	
	  	fightMinion(characterName, characterData, minionName, minionData, minionNum, pathName);  	
	  	
	}

	//choose character
	public static int getCharacter(){ 
		System.out.println("Here are the characters:");
        System.out.println("1. Rogue\n2. Paladin\n3. Jackie Chan\n");
        System.out.print("Which character do you choose? ");
        Scanner keyboard = new Scanner(System.in);
        int characterChoice = keyboard.nextInt();
        return characterChoice;
    }

    //choose path
    public static int getPath(){
    	Scanner keyboard = new Scanner(System.in);
        int pathChoice = keyboard.nextInt();
        return pathChoice;
    } 

    //fight with minions
    public static void fightMinion(String playerName, int[] playerData, String enemyName, int[] enemyData, int minionNum, 
    	String pathName )throws IOException{
    	
    	Random randomNums = new Random();
    	Scanner keyboard = new Scanner(System.in);

        int enemyHP = enemyData[2];  //[0]: min; [1]:max; [2]:HP; [3]:Strength
        int playerHP = playerData[2];
        int playerStrength = playerData[3];
        int enemyStrength = enemyData[3];
        int coins[] = new int[minionNum];
        
		for (int i = 1; i <= minionNum; i++)
        {
        	coins[i-1] = randomNums.nextInt(21)+30;
        	enemyHP = enemyData[2];   
            System.out.printf("***%s vs %s %d***\n", playerName, enemyName, i);
            while(enemyHP > 0 && playerHP > 0)
            {
            	
                int playerDamage = randomNums.nextInt(playerData[1] - playerData[0] + 1) + playerData[0];
                int playerATK = playerData[3] + playerDamage;
                enemyHP -= playerATK;
                System.out.printf("%s attacks with ATK = %d + %d = %d\n", playerName, playerStrength, playerDamage, playerATK);
                System.out.printf("%s HP is now %d - %d = %d\n\n", enemyName, enemyHP + playerATK, playerATK, enemyHP);

            	if (enemyHP <= 0)
                	break;

                int enemyDamage = randomNums.nextInt(enemyData[1] - enemyData[0] + 1) + enemyData[0];
                int enemyATK = enemyData[3] + enemyDamage;
                playerHP -= enemyATK;
                System.out.printf("%s attacks with ATK = %d + %d = %d\n", enemyName, enemyStrength, enemyDamage, enemyATK);
                System.out.printf("%s HP is now %d - %d = %d\n\n", playerName, playerHP + enemyATK, enemyATK, playerHP);
            	
            } 
       
            if (playerHP > 0){
            	
                System.out.printf("%s defeated %s %d!\n", playerName, enemyName, i);
                System.out.printf("%s gains %d gold coins!\n\n", playerName,coins[i-1]);
                System.out.println("Press Enter to continue...");
                keyboard.nextLine();
                
            }
            else
            {
            System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n", playerName);
            //if player is defeated by minions, game over, save the game data here
            String fileName = "GameData.txt";
	        PrintWriter outputWriter = new PrintWriter(new FileWriter(fileName,true));
	        
	        outputWriter.println(playerName);
	        
	     
	       	if (playerName=="Rogue")
	       		outputWriter.println("Short Sword");
	       	else if (playerName=="Paladin")
	        	outputWriter.println("Long Sword");
	        else if (playerName=="Jackie Chan")
	        	outputWriter.println("Jump Kick");
	        
	        outputWriter.println(playerHP);
	       
	       	if (pathName =="The Forest")
       			outputWriter.println("Forest");
       		if (pathName == "The Graveyard")
       			outputWriter.println("Graveyard");
	        
			outputWriter.println("Lost");
	       
	        outputWriter.close();  


	        System.out.println("\nThe game results have been saved!");
            System.exit(0);
            }
              
        } 
        System.out.printf("--%s wins the battle!--\n",playerName);
        System.out.printf("\nYour HP is: %d\n\n", playerHP);
        visitItemShop(coins, playerHP, playerStrength, playerData, enemyData, playerName,pathName);
	  		

    }

    //visit The Item Shop
    public static void visitItemShop(int[] golds, int remainPlayerHP, int playerStrenth, int[] playerData, int[] enemyData, 
    	String playerName, String pathName)throws IOException{
    	
    	Random randomNums = new Random();
    	Scanner keyboard = new Scanner(System.in);
    	final int LONG_SWORD_COST = 120;
    	final int SHORT_SWORD_COST = 90;
    	final int MACE_COST = 80;
    	final int RING_OF_STRENGTH_COST = 150;
    	final int HEALING_POTION_COST = 10;
    	int userGoldCoins = 0;
    	int discount = 0;
    	int totalCost = 0;
    	String itemName = "";
    	int newPlayerDamage = 0;
    	int newPlayerATK = 0;
    	int finalCost = 0;
    	int quantity = 0;
    	int itemChoice = 0;
    	
    	for (int i = 0; i < golds.length; i++) //calculate the total golds that player has
			userGoldCoins = userGoldCoins + golds[i];

		System.out.println("Would you like to visit The Item Shop?");
		System.out.print("Enter 1 for \"yes\" or 0 for \"no\": ");
		int visitOrNot = keyboard.nextInt();

		//choices of whether to visit the Item Shop
		switch(visitOrNot){
			case 0:
		  		System.out.println("\nGoodbye! Please stop by again!");
		  		break;

		  	case 1 :
		  		int visitAgain;
		  		System.out.printf("\nWelcome to The Item Shop!\n");
		  		//do-while loops for whether to visit again
		  		do    
		  		{
			  		if (userGoldCoins > 0)
    				{
            			System.out.printf("\nYou currently have %d gold.\n", userGoldCoins);
            			System.out.println("\nHere's what we have for sale (all prices are in units of gold):\n");

            			System.out.printf("1. %-18s%-3d\n", "Long Sword", 120);
            			System.out.printf("2. %-18s%-3d\n", "Short Sword", 90);
			            System.out.printf("3. %-18s%-3d\n", "Mace", 80);
			            System.out.printf("4. %-18s%-3d\n", "Magic Ring", 150);
			            System.out.printf("5. %-18s%-3d\n", "Healing Potion", 10);

			            System.out.print("\nPlease enter the item number: ");
			            itemChoice = keyboard.nextInt();

			            System.out.print("Please enter the quantity: ");
			           	quantity = keyboard.nextInt();

			            switch(itemChoice)
			            {
			                case 1:
			                    itemName = "Long Sword";
			                    totalCost = LONG_SWORD_COST * quantity;
			                    playerData[1] = 7;
			                    playerData[0] = 3;
								newPlayerDamage = randomNums.nextInt(playerData[1] - playerData[0] + 1) + playerData[0];
			                    newPlayerATK = playerData[3] + newPlayerDamage;
			                    break;
			                case 2:
			                    itemName = "Short Sword";
			                    totalCost = SHORT_SWORD_COST * quantity;
			                    playerData[1] = 4;
			                    playerData[0] = 1;
			                    newPlayerDamage = randomNums.nextInt(playerData[1] - playerData[0] + 1) + playerData[0];
			                    newPlayerATK = playerData[3] + newPlayerDamage;
			                    break;
			                case 3:
			                    itemName = "Mace";
			                    totalCost = MACE_COST * quantity;
			                    playerData[1] = 6;
			                    playerData[0] = 2;
			                    newPlayerDamage = randomNums.nextInt(playerData[1] - playerData[0] + 1) + playerData[0];
			                    newPlayerATK = playerData[3] + newPlayerDamage;
			                    break;
			                case 4:
			                    itemName = "Ring of Strength";
			                    totalCost = RING_OF_STRENGTH_COST * quantity;
			                    playerData[3] = playerData [3] + (5 * quantity);
			                    break;
			                case 5:
			                    itemName = "Healing Potion";
			                    totalCost = HEALING_POTION_COST * quantity;
			                    playerData[2] = playerData[2] + (10 * quantity);
			                    break;
			            }
			        }

		            if (quantity > 2)
		                discount = (int) (totalCost * 0.1);
					
					finalCost = totalCost - discount;

		            System.out.printf("\n%-10s: %5d gold", "Total cost", totalCost);
		            System.out.printf("\n%-8s: %5d gold", "Discount", discount);
		            System.out.printf("\n%-10s: %5d gold", "Final cost", finalCost);

		            if (userGoldCoins < finalCost)
		                System.out.println("\n\nThe transaction is unsuccessful!\nYou have insufficient funds!");

		            else
		            {
		            	userGoldCoins = userGoldCoins - finalCost;
		                System.out.printf("\n\nThe transaction is successful!\nYour remaining funds: %d gold\n\n"+
		                 				   "Thank you! Your transaction is complete!\n\n", userGoldCoins);
		                System.out.printf("You purchased: %s\n", itemName);
		                switch (itemChoice)
		                {
		                	case 1:
		                		System.out.printf("Your weapon damage is now: %d - %d\n", playerData[0],playerData[1]);
		                		break;
		                	case 2:
		                		System.out.printf("Your weapon damage is now: %d - %d\n", playerData[0],playerData[1]);
		                		break;
		                	case 3:
		                		System.out.printf("Your weapon damage is now: %d - %d\n", playerData[0],playerData[1]);
		                		break;
		                	case 4:
		                		System.out.printf("Your Strength has increased to %d + %d = %d!\n\n", (playerData[3] - (5 * quantity)), 
		                			(5 * quantity), playerData[3]);
		                			
		                		break;
		                	case 5:
		                		System.out.printf("Your HP has increased to %d + %d = %d\n\n", (playerData[2] - (10 * quantity)), 
		                			(10 * quantity), playerData[2]);
		                			
		                		break;

		                }
		        	}
                   

			  		System.out.println("\nWould you like to make another purchase?");
		  			System.out.print("Enter 1 for \"yes\" or 0 for \"no\": ");
		  			visitAgain = keyboard.nextInt();
		  				
				}while (visitAgain == 1);
				System.out.println("\nGoodbye! Please stop by again!");
		  		break;
		  
		}
        fightWizard(playerName, playerData, newPlayerDamage, newPlayerATK, pathName, itemName);
    }

    //fight with the evil wizard
    public static void fightWizard(String playerName, int[] playerData, int newPlayerDamage, int newPlayerATK, 
    	String pathName, String itemName)throws IOException{
    	
    	Random randomNums = new Random();
    	Scanner keyboard = new Scanner(System.in);
    	//wizard weapon min = 4; wizard weapon max = 10; wizard HP = 40; wizard Strength = 8;
    	int [] enemyData = {4,10,40,8}; 
    	String enemyName = "Wizard";
    	int enemyATK = 0;
    	int enemyDamage = 0;
    	System.out.println("\nYou have now reached The Castle! Time to battle The Evil Wizard!\n");
    	System.out.printf("***%s vs The Evil Wizard***\n", playerName);
        while(playerData[2] > 0 && enemyData[2] > 0)
        {
            System.out.println("Choose your action:\n1. Attack\n2. Attempt Spell Cast\n");
            System.out.print("What would you like to do?: ");
            int playerActionChoice = keyboard.nextInt();
 
            switch(playerActionChoice)
            {
                case 1:
                    
                    enemyData[2] -= newPlayerATK;
                    System.out.printf("\n%s attacks with ATK = %d + %d = %d\n", playerName, playerData[3], newPlayerDamage, 
                    	playerData[3] + newPlayerDamage);
                    System.out.printf("%s HP is now %d - %d = %d\n\n", enemyName, enemyData[2] + newPlayerATK, newPlayerATK, 
                    	enemyData[2]);
                    break;
                case 2:
                    System.out.print("\nEnter your guess: ");
                    int randomNumGuess = keyboard.nextInt();
                    int randomNumAnswer = randomNums.nextInt(6) + 1;
                    if (randomNumGuess == randomNumAnswer)
                    {
                        System.out.println("Correct!\n");
                        System.out.printf("The %s's spell is cast successfully! The Wizard's HP is now 0!\n\n", playerName);
                        enemyData[2] = 0;
                    }
                    else
                        System.out.println("Incorrect! The spell cast fails!\n");
                        break;
            }

            if (enemyData[2] <= 0)
            	break;

            enemyDamage = randomNums.nextInt(enemyData[1] - enemyData[0] + 1) + enemyData[0];
            enemyATK = enemyData[3] + enemyDamage;
            playerData[2] -= enemyATK;
            System.out.printf("%s attacks with ATK = %d + %d = %d\n", enemyName, enemyData[3], enemyDamage, enemyATK);
            System.out.printf("%s HP is now %d - %d = %d\n\n", playerName, playerData[2] + enemyATK, enemyATK, playerData[2]);

        } 

        if (playerData[2] > 0)
        {
            System.out.printf("--the %s wins the battle!--\n\n", playerName);
            System.out.println("You win! Congratulations!");
            
        }
        else
        {
            System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n", playerName); 
            
        }
        
        //If the player reached the evil wizard, end the game here and save game data
        String fileName = "GameData.txt";
        PrintWriter outputWriter = new PrintWriter(new FileWriter(fileName,true));
        outputWriter.println(playerName);
        
       	if ((itemName == "Long Sword") || (itemName == "Short Sword") || (itemName == "Mace"))
        	outputWriter.println(itemName);
       	else if (((itemName == "Healing Potion")||(itemName == "Ring of Strength"))&&(playerName=="Rogue"))
       		outputWriter.println("Short Sword");
       	else if (((itemName == "Healing Potion")||(itemName == "Ring of Strength"))&&(playerName=="Paladin"))
        	outputWriter.println("Long Sword");
        else if (((itemName == "Healing Potion")||(itemName == "Ring of Strength"))&&(playerName=="Jackie Chan"))
        	outputWriter.println("Jump Kick");
        
        outputWriter.println(playerData[2]);
       
        if (pathName =="The Forest")
       		outputWriter.println("Forest");
       	if (pathName == "The Graveyard")
       		outputWriter.println("Graveyard");
        
        if (playerData[2]>0)
        	outputWriter.println("Won");
        else
        	outputWriter.println("Lost");
       
        
        outputWriter.close();  


        System.out.println("\nThe game results have been saved!");



    }
    

}