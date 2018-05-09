package myLeetcode;


import java.util.Scanner;
import java.util.Random;


public class AdventureGame-4
{
	
	public static void main(String[] args)
	{
		
		Scanner keyboard = new Scanner(System.in);
		
		// The first step is to choose a player character
		String characterName = toChooseCharacter(keyboard);
		
		// The second step is to choose a path
		String enemyName = toChoosePath(keyboard);

		// The third step is to fight with several enemies.
		int characterHpLeft = tofightWithEnemy(characterName, enemyName);
		
		// if character's HP is below zero, Game over
		if (characterHpLeft <= 0){
			return;
		}
		int characterStrength = getCharacterStrengthByName(characterName);
		// The fourth step is to choose a reward.
		System.out.print("\nPlease choose a reward.\n"+
		   		   "1. Healing Potion\n"+
		           "2. Ring of Strength\n"+
		           "\nWhich item do you choose?:");
		int characterChosen = keyboard.nextInt();
		switch(characterChosen)
		{
		
			case 1:
				System.out.println("You chose: Healing\n");
				System.out.printf("Your HP has increased to %d + 10 = %d!\n", characterHpLeft, characterHpLeft + 10);
				characterHpLeft = characterHpLeft + 10;
				break;
		
			case 2: 
				System.out.println("You chose: Ring of Strength\n");
				System.out.printf("Your Strength has increased to %d + 5 = %d!\n", characterStrength, characterStrength + 5);
				characterStrength = characterStrength + 5;
				break;
		}
		
		// The last step is to fight with Evil Wizard
		tofightWithEvilWizard(keyboard, characterName, characterHpLeft, characterStrength);
		keyboard.close();
		
	}

	public static String toChooseCharacter(Scanner keyboard)
	{
		System.out.println("Adventure Game - Start!\n\nHere are the characters."+
						   "\n1. Rogue\n2. Paladin\n3. Jackie Chan");
		System.out.print("\nWhich character do you choose?: ");

		int characterChosen = keyboard.nextInt();
		
		String characterName = "";
		
		switch(characterChosen)
		{
			case 1:
				System.out.println("\nYou chose: Rouge");
				characterName = "Rouge";
				break;
			
			case 2:
				System.out.println("\nYou chose: Paladin");
				characterName = "Paladin";
				break;

			case 3:
				System.out.println("\nYou chose: Jackie Chan");
				characterName = "Jackie Chan";

				break;
		}
		return characterName;
	}
	
	public static String toChoosePath(Scanner keyboard){
		System.out.println("\nThe Evil Wizard must be defeated! He is in The Castle. To get"+
		   		   " to The Castle, you must travel through either:\n1. The Forest"+
		           "\n2. The Graveyard");
		System.out.print("\nWhich path will you take?: ");
		
		int pathToken = keyboard.nextInt();
		String enemyName = "";
		switch(pathToken)
		{
			case 1:
				System.out.println("You chose: The Forest\n\nOnce you enter The Forest, "+
					  			   "you encounter 3 Goblins! Time for Battle!");
				enemyName = "Goblin";
				break;
		
			case 2: 
				System.out.println("You chose: The Graveyard \n\nOnce you enter The Graveyard,"+ 
					   			   "you encounter 5 Skeletons! Time for battle!");
				enemyName = "Skeleton";
			break;
		}
		return enemyName;

	}
	public static int tofightWithEnemy(String characterName, String enemyName){
		
		int characterDamage;
		int characterHpLeft;
		int enemyDamage;
		int enemyHpLeft;
		int eachEnemHp;
		int characterAtk;
		int enemyAtk;
		
		int enemyNumber = getEnemyNumberByName(enemyName);
		int enemyHp = getEnemyHpByName(enemyName);
		int enemyStrength = getEnemyStrengthByName(enemyName);
		
		int characterHp = getCharacterHpByName(characterName);
		int characterStrength = getCharacterStrengthByName(characterName);
		
		for (int i = 1; i <= enemyNumber; i++){
			System.out.printf("1\n***%s vs %s %d***\n", characterName, enemyName, i);
			eachEnemHp = enemyHp;
			while (characterHp > 0 && eachEnemHp > 0){
				// play's turn to attack
				characterDamage = getTheDamageByName(characterName);
				characterAtk =  characterStrength + characterDamage;
				System.out.printf("%s attacks with ATK = %d + %d = %d\n", characterName, characterStrength, characterDamage, characterAtk);
				enemyHpLeft = eachEnemHp - characterAtk;
				System.out.printf("%s Hp is now %d - %d = %d\n", enemyName, eachEnemHp, characterAtk, enemyHpLeft);
				eachEnemHp = enemyHpLeft;
				if (eachEnemHp <= 0){
					System.out.printf("%s defeated %s %d!\n",characterName, enemyName, i);
					break;
				}
				System.out.println();
				
				// enemy's turn to attack
				enemyDamage = getTheDamageByName(enemyName);
				enemyAtk =  enemyStrength + enemyDamage;
				System.out.printf("%s attacks with ATK = %d + %d = %d\n", enemyName, enemyStrength, enemyDamage, enemyAtk);
				characterHpLeft = characterHp - enemyAtk;
				System.out.printf("%s Hp is now %d - %d = %d\n", characterName, characterHp, enemyAtk, characterHpLeft);
				characterHp = characterHpLeft;
				if (characterHp <= 0){
					System.out.printf("--%s is defeated in battle!--GAME OVER!\n",characterName);
					return characterHp;
				}
				System.out.println();
			}

		}
		System.out.printf("Your HP is: %d\n", characterHp);
		return characterHp;
		
	}
	
	public static int getTheDamageByName(String name){
		int damage = 0;
		Random random = new Random();
		switch(name){
			case "Rouge": damage = random.nextInt(4) + 1; break;
			case "Paladin": damage = random.nextInt(5) + 3; break;
			case "Jackie Chan": damage = random.nextInt(5) + 2; break;
			case "Goblin": damage = random.nextInt(5) + 2; break;
			case "Skeleton": damage = random.nextInt(4) + 1; break;
			case "Evil Wizard": damage = random.nextInt(7) + 4; break;

		}
		return damage;
	}
	
	
	public static void tofightWithEvilWizard(Scanner keyboard, String characterName , int characterHp, int characterStrength){
		
		int actionChosen;
		
		Random random = new Random();
		int luckNumber = random.nextInt(5)+1;
		
		System.out.println("You have now reached The Castle! Time to battle The Evil Wizard!");
		
		int characterDamage;
		int characterHpLeft;
		
		String evilWizardName = "Evil Wizard";
		int evilWizardHp = 40;
		int evilWizardStrength = 8;
		int evilWizardHpLeft;
		int evilWizardAtk;
		int evilWizardDamage;
		int characterAtk;
		
		int guessNumber;
		
		while (characterHp > 0 && evilWizardHp > 0){
			// play's turn to choose
			System.out.print("Choose your action:\n" +
					"1. Attack\n" +
					"2. Attempt Spell Cast\n" +
					"What would you like to do?:");
			actionChosen = keyboard.nextInt();
			switch(actionChosen)	
			{
				case 1: 
					characterDamage = getTheDamageByName(characterName);
					characterAtk =  characterStrength + characterDamage;
					System.out.printf("%s attacks with ATK = %d + %d = %d\n", characterName, characterStrength, characterDamage, characterAtk);
					evilWizardHpLeft = evilWizardHp - characterAtk;
					System.out.printf("%s Hp is now %d - %d = %d\n", evilWizardName, evilWizardHp, characterAtk, evilWizardHpLeft);
					evilWizardHp = evilWizardHpLeft;
					break;
				case 2:
					System.out.print("\nEnter your guess:");
					guessNumber = keyboard.nextInt();
					if (guessNumber == luckNumber) {
						evilWizardHp = 0;
						System.out.println("Correct!");
						System.out.println("The Rogue’s spell is cast successfully! The Wizard’s HP is now 0!");
					}
					else {
						System.out.println("The spell cast failed！No damage is dealt to the Wizard!");
					}
					break;
			}
			
			if (evilWizardHp <= 0){
				System.out.printf("--%s wins the battle!--\n",characterName);
				System.out.println("You win! Congratulations!");
				break;
			}	
			
			System.out.println();
			
			// Evil Wizard's turn to attack
			evilWizardDamage = getTheDamageByName(evilWizardName);
			evilWizardAtk =  evilWizardStrength + evilWizardDamage;
			System.out.printf("%s attacks with ATK = %d + %d = %d\n", evilWizardName, evilWizardStrength, evilWizardDamage, evilWizardAtk);
			characterHpLeft = characterHp - evilWizardAtk;
			System.out.printf("%s Hp is now %d - %d = %d\n", characterName, characterHp, evilWizardAtk, characterHpLeft);
			characterHp = characterHpLeft;
			if (characterHp <= 0){
				System.out.printf("--%s is defeated in battle!--GAME OVER!\n",characterName);
				break;
			}
			System.out.println();
		}
	}
	
	public static int getCharacterStrengthByName(String name){
		int characterStrength = 0;
		switch(name)
		{
			case "Rouge":
				characterStrength = 8;
				break;
			case "Paladin":
				characterStrength = 14;
				break;
			case "Jackie Chan":
				characterStrength = 10;
				break;
		}
		return 	characterStrength;
	}
	
	public static int getCharacterHpByName(String name){
		int characterHp = 0;
		switch(name)
		{
			case "Rouge":
				characterHp = 55;
				break;
			case "Paladin":
				characterHp = 35;
				break;
			case "Jackie Chan":
				characterHp = 45;
				break;
		}
		return 	characterHp;
	}
	
	public static int getEnemyStrengthByName(String Name){
		int enemyStrength = 0;
		switch(Name)
		{
			case "Goblin":
				enemyStrength = 4;
				break;
		
			case "Skeleton": 
				enemyStrength = 3;
			break;
		}
		return enemyStrength; 
	}
	
	public static int getEnemyNumberByName(String Name){
		int enemyNumber = 0;
		switch(Name)
		{
			case "Goblin":
				enemyNumber = 3;
				break;
			case "Skeleton": 
				enemyNumber = 5;
				break;
		}
		return enemyNumber; 
	}
	
	public static int getEnemyHpByName(String Name){
		int enemyHp = 0;
		switch(Name)
		{
			case "Goblin":
				enemyHp = 25;
				break;
			case "Skeleton": 
				enemyHp = 25;
			break;
		}
		return enemyHp; 
	}
}


