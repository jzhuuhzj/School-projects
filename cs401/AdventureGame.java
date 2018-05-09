
import java.util.Scanner;
import java.util.Random;

public class AdventureGame
{
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);

		//to choose a player character
		String characterName = chooseCharacter(keyboard);

		//to choose a path
		String enemyName = choosePath(keyboard);

		//battle with enemies on the way to The Castle including goblins or skeletons
		int characterHpLeft = battleWithEnemy(characterName, enemyName);

		//once the character's HP is smaller or euqal to 0, game over
		if (characterHpLeft <=0 ){
			keyboard.close();
			return;
		}

		int characterStrength = characterStrengthByName(characterName);

		//to choose a reward
		System.out.print("\nPlease choose a reward.\n"+"1. Healing Potion\n"+
		           		 "2. Ring of Strength\n"+"\nWhich item do you choose?: ");

		int rewardChosen = keyboard.nextInt();
		switch(rewardChosen)
		{
			case 1:
				System.out.println("\nYou chose: Healing Potion\n");
				System.out.printf("Your HP has increased to %d + 10 = %d!\n\n", 
								  characterHpLeft, characterHpLeft + 10);
				characterHpLeft = characterHpLeft + 10;
				break;
		
			case 2: 
				System.out.println("\nYou chose: Ring of Strength\n");
				System.out.printf("Your Strength has increased to %d + 5 = %d!\n\n", 
								  characterStrength, characterStrength + 5);
				characterStrength = characterStrength + 5;
				break;
		}
		//arrive at The Castle and battle with Evil Wizard
		battleWithEvilWizard(keyboard, characterName, characterHpLeft, characterStrength);
		keyboard.close();
	}

	//choose player character
	public static String chooseCharacter(Scanner keyboard)
	{
		System.out.println("Adventure Game - Start!\n\nHere are the characters."+
						   "\n1. Rogue\n2. Paladin\n3. Jackie Chan");
		System.out.print("\nWhich character do you choose?: ");
		int characterChosen = keyboard.nextInt();
		String characterName="";
		switch(characterChosen)
		{
			case 1: 
				System.out.println("\nYou choose: Rogue");
				characterName = "Rogue";
				break;
			case 2:
				System.out.println("\nYou choose: Paladin");
				characterName = "Paladin";
				break;
			case 3: 
				System.out.println("\nYou choose: Jackie Chan");
				characterName = "Jackie Chan";
				break;
		}
		return characterName;
	}

	//choose path
	public static String choosePath(Scanner keyboard){
		System.out.println("\nThe Evil Wizard must be defeated! He is in The Castle. To get"+
						   " to The Castle, you must travel through either:\n1. The Forest"+
						   "\n2. The Graveyard");
		System.out.print("\nWhich path will you take?: ");
		int pathTaken = keyboard.nextInt();
		String enemyName = "";
		switch(pathTaken)
		{
			case 1:
				System.out.println("\nYou chose: The Forest\n\nOnce you enter The Forest, "+
								   "you encounter 3 Goblins! Time for Battle!");
				enemyName = "Goblin";
				break;
			
			case 2: 
				System.out.println("\nYou chose: The Graveyard \n\nOnce you enter The Graveyard,"+ 
								   	"you encounter 5 Skeletons! Time for battle!");
				enemyName = "Skeleton";
				break;
		}
		return enemyName;
	}

	//start battle with enemies(goblins or skeletons)
	public static int battleWithEnemy(String characterName, String enemyName){
		int characterDamage;
		int characterHpLeft;
		int enemyDamage;
		int enemyHpLeft;
		int eachEnemyHp;
		int characterAtk;
		int enemyAtk;

		int enemyNumber = enemyNumberByName(enemyName);
		int enemyHp = enemyHpByName(enemyName);
		int enemyStrength = enemyStrengthByName(enemyName);

		int characterHp = characterHpByName(characterName);
		int characterStrength = characterStrengthByName(characterName);

		for (int i = 1; i <= enemyNumber; i++){
			System.out.printf("\n***%s vs %s %d***", characterName, enemyName, i);
			eachEnemyHp = enemyHp;
			while ((characterHp > 0)&&(eachEnemyHp > 0)){
				//player character's turn to attack
				characterDamage = damageByName(characterName);
				characterAtk = characterStrength + characterDamage;
				System.out.printf("\n%s attacks with ATK = %d + %d = %d\n", 
					characterName, characterStrength, characterDamage, characterAtk);
				enemyHpLeft = eachEnemyHp - characterAtk;
				System.out.printf("%s HP is now %d - %d = %d\n", enemyName, eachEnemyHp, characterAtk, 
								  enemyHpLeft);
				eachEnemyHp = enemyHpLeft;
				if (eachEnemyHp <= 0){
					System.out.printf("\n%s defeated %s %d!\n",characterName, enemyName, i);
					break;
				}
				System.out.println();
				
				//enemy's(goblin or skeleton) turn to attack
				enemyDamage = damageByName(enemyName);
				enemyAtk = enemyStrength + enemyDamage;
				System.out.printf("%s attacks with ATK = %d + %d = %d\n", enemyName, enemyStrength,
								  enemyDamage, enemyAtk);
				characterHpLeft = characterHp - enemyAtk;
				System.out.printf("%s HP is now %d - %d = %d\n", characterName, characterHp, 
								  enemyAtk, characterHpLeft);
				characterHp = characterHpLeft;
				if (characterHp <= 0){
					System.out.printf("\n--%s is defeated in battle!--\n\nGAME OVER\n\n", characterName);
					return characterHp;
				}
				else if (characterHp < 0)
				{
					
					System.out.println();
				}
			}


		}
		System.out.printf("\n--%s wins the battle!--\n",characterName);
		System.out.printf("\nYour HP is: %d\n", characterHp);
		return characterHp;
	}

	//return random numbers for damage values by names 
	public static int damageByName(String name){
		int damage = 0;
		Random randomNumbers = new Random();
		switch(name){
			case "Rogue":
				damage = randomNumbers.nextInt(4)+1;
				break;
			case "Paladin":
				damage = randomNumbers.nextInt(5)+3;
				break;
			case "Jackie Chan":
				damage = randomNumbers.nextInt(5)+2;
				break;
			case "Goblin":
				damage = randomNumbers.nextInt(5)+2;
				break;
			case "Skeleton":
				damage = randomNumbers.nextInt(4)+1;
				break;
			case "Evil Wizard":
				damage = randomNumbers.nextInt(7)+4;
				break;
		}
		return damage;
	}

	//start battle with Evil Wizard
	public static void battleWithEvilWizard(Scanner keyboard, String characterName, int characterHp, 
											int characterStrength)
	{
		int actionChosen;

		Random randomNumbers = new Random();
		int castNumber = randomNumbers.nextInt(5)+1;

		System.out.println("You have now reached The Castle! Time to battle The Evil Wizard!\n");
		System.out.printf("***%s vs The Evil Wizard***\n",characterName);

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

		while ((characterHp>0)&&(evilWizardHp>0)){
			//player character can choose an action every time before his turn
			System.out.println("Choose your action:\n1. Attack\n2. Attempt Spell Cast\n");
    		System.out.print("What would you like to do?: ");
    		actionChosen = keyboard.nextInt();
    		switch(actionChosen)
    		{
    			case 1: 
					characterDamage = damageByName(characterName);
					characterAtk =  characterStrength + characterDamage;
					System.out.printf("\n%s attacks with ATK = %d + %d = %d\n", characterName, 
									  characterStrength, characterDamage, characterAtk);
					evilWizardHpLeft = evilWizardHp - characterAtk;
					System.out.printf("Wizard HP is now %d - %d = %d\n", evilWizardHp, characterAtk, 
									  evilWizardHpLeft);
					evilWizardHp = evilWizardHpLeft;
					break;
				case 2:
					System.out.print("\nEnter your guess: ");
					guessNumber = keyboard.nextInt();
					if (guessNumber == castNumber) {
						evilWizardHp = 0;
						System.out.println("Correct!");
						System.out.printf("\nThe %s’s spell is cast successfully!" + 
										  " The Wizard’s HP is now 0!\n",characterName);
					}
					else {
						System.out.println("\nThe spell cast failed！No damage is dealt to the Wizard!");
					}
					break;
			}
			if (evilWizardHp <=0){
				System.out.printf("\n--%s wins the battle!--\n",characterName);
				System.out.printf("\nYou win! Congratulations!\n");
				break;
			}
			System.out.println();

			//Evil Wizard attacks back
			evilWizardDamage = damageByName(evilWizardName);
			evilWizardAtk =  evilWizardStrength + evilWizardDamage;
			System.out.printf("Wizard attacks with ATK = %d + %d = %d\n", evilWizardStrength,
							  evilWizardDamage, evilWizardAtk);
			characterHpLeft = characterHp - evilWizardAtk;
			System.out.printf("%s HP is now %d - %d = %d\n\n", characterName, characterHp, 
							  evilWizardAtk, characterHpLeft);
			characterHp = characterHpLeft;
			if (characterHp <= 0){
				System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n",characterName);
				break;
			}
		}
	}

	//return player characters' strength values by names
	public static int characterStrengthByName(String name){
		int characterStrength = 0;
		switch(name)
		{
			case "Rogue":
				characterStrength = 8;
				break;
			case "Paladin":
				characterStrength = 14;
				break;
			case "Jackie Chan":
				characterStrength = 10;
				break;
		}
		return characterStrength;
	}

	//return player characters' HP values by names
	public static int characterHpByName(String name){
		int characterHp = 0;
		switch(name)
		{
			case "Rogue":
				characterHp = 55;
				break;
			case "Paladin":
				characterHp = 35;
				break;
			case "Jackie Chan":
				characterHp = 45;
				break;
		}
		return characterHp;
	}

	//return goblin or skeleton's strenth values by names
	public static int enemyStrengthByName(String name){
		int enemyStrength = 0;
		switch(name)
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

	//return the number of enemies in every path by names
	public static int enemyNumberByName(String name){
		int enemyNumber = 0;
		switch(name)
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

	//return enemies' HP values by names
	public static int enemyHpByName(String name){
		int enemyHp = 0;
		switch(name)
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

