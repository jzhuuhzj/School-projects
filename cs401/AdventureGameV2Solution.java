import java.util.Scanner;
import java.util.Random;

public class AdventureGameV2Solution
{
  private static final int SHORT_SWORD_MIN = 1;
  private static final int SHORT_SWORD_MAX = 4;
  private static final int LONG_SWORD_MIN = 3;
  private static final int LONG_SWORD_MAX = 7;
  private static final int JUMP_KICK_MIN = 2;
  private static final int JUMP_KICK_MAX = 6;
  private static final int AXE_MIN = 2;
  private static final int AXE_MAX = 6;
  private static final int MACE_MIN = 2;
  private static final int MACE_MAX = 6;

  public static void main(String[] args)
  {
    final int ROGUE_INIT_HP = 55;
    final int ROGUE_INIT_STRENGTH = 8;
    final int PALADIN_INIT_HP = 35;
    final int PALADIN_INIT_STRENGTH = 14;
    final int CHAN_INIT_HP = 45;
    final int CHAN_INIT_STRENGTH = 10;

    final int MINION_INIT_HP = 25;
    final int GOBLIN_INIT_STRENGTH = 4;
    final int SKELETON_INIT_STRENGTH = 3;

    Scanner keyboard = new Scanner(System.in);
    Random randomNums = new Random();

    final int NUM_GOBLINS = randomNums.nextInt((5-2) + 1) + 2;
    final int NUM_SKELETONS = randomNums.nextInt((7-3) + 1) + 3;

    int[] player = new int[5]; // stores the player attributes
    int[] enemy = new int[4];  // stores the enemy attributes

    String playerName = "";
    String enemyName = "";

    int characterChoice = 0, pathChoice = 0, itemChoice = 0, numEnemies = 0;
    String pathName = "";

    int playerDamage, playerATK;
    int enemyDamage, enemyATK;

    int playerActionChoice, randomNumAnswer, randomNumGuess, visitItemShop;

    characterChoice = getCharacter(); // gets character from method

    switch(characterChoice)
    {
      case 1:
        playerName = "Rogue";
        player[0] = ROGUE_INIT_HP;
        player[1] = ROGUE_INIT_STRENGTH;
        player[2] = SHORT_SWORD_MIN;
        player[3] = SHORT_SWORD_MAX;
        break;
      case 2:
        playerName = "Paladin";
        player[0] = PALADIN_INIT_HP;
        player[1] = PALADIN_INIT_STRENGTH;
        player[2] = LONG_SWORD_MIN;
        player[3] = LONG_SWORD_MAX;
        break;
      case 3:
        playerName = "Jackie Chan";
        player[0] = CHAN_INIT_HP;
        player[1] = CHAN_INIT_STRENGTH;
        player[2] = JUMP_KICK_MIN;
        player[3] = JUMP_KICK_MAX;
        break;
    }

    System.out.printf("\nYou chose: %s\n\n", playerName);

    pathChoice = getPath();// gets path choice from method

    switch(pathChoice)
    {
      case 1:
        pathName = "The Forest";
        enemyName = "Goblin";
        enemy[0] = MINION_INIT_HP;
        enemy[1] = GOBLIN_INIT_STRENGTH;
        enemy[2] = AXE_MIN;
        enemy[3] = AXE_MAX;
        numEnemies = NUM_GOBLINS;
        break;
      case 2:
        pathName = "The Graveyard";
        enemyName = "Skeleton";
        enemy[0] = MINION_INIT_HP;
        enemy[1] = SKELETON_INIT_STRENGTH;
        enemy[2] = SHORT_SWORD_MIN;
        enemy[3] = SHORT_SWORD_MAX;
        numEnemies = NUM_SKELETONS;
        break;
    }

    System.out.printf("\nYou chose: %s\n\n", pathName);
    System.out.printf("Once you enter %s, you encounter %d %ss! Time for battle!\n\n", pathName,
        numEnemies, enemyName);

    fightMinion(player, enemy, playerName, enemyName, numEnemies);// gets character stats after the first battle method
    System.out.println("-- " + playerName + " wins the battle! --\n");

    System.out.printf("Your HP is: %d\n", player[0]);

    System.out.print("\n\nWould you like to visit the Item Shop?\nEnter 1 for \"yes\" or 0 for \"no\": ");
    visitItemShop = keyboard.nextInt();

    if (visitItemShop == 1)
      visitItemShop(player);// gets character stats after the item shop

    fightWizard(player, playerName);// calls final method against the wizard

  }//end main method

  public static int getCharacter()
  {
    int characterChoice;
    Scanner keyboard = new Scanner(System.in);

    System.out.println("\nAdventure Game - Start!\n");
    System.out.println("Here are the characters:");
    System.out.println("1. Rogue\n2. Paladin\n3. Jackie Chan\n");

    System.out.print("Which character do you choose?: ");
    characterChoice = keyboard.nextInt();

    return characterChoice;
  }//end method

  public static int getPath()
  {
    int pathChoice;
    Scanner keyboard = new Scanner(System.in);

    System.out.print("The Evil Wizard must be defeated! He is in The Castle. To get to ");
    System.out.println("The Castle, you must travel through either:");
    System.out.println("1. The Forest\n2. The Graveyard\n");

    System.out.print("Which path will you take?: ");
    pathChoice = keyboard.nextInt();

    return pathChoice;
  }//end method

  public static void fightMinion(int[] player, int[] enemy, String playerName, String enemyName, int numEnemies)
  {
    int playerDamage;
    int playerATK;
    int enemyDamage;
    int enemyATK;
    int coins;
    Random randomNums = new Random();
    Scanner keyboard = new Scanner(System.in);

    for (int i = 1; i <= numEnemies; i++)
    {
      enemy[0] = 25;
      System.out.printf("***%s vs %s %d***\n", playerName, enemyName, i);

      while(enemy[0] > 0 && player[0] > 0)
      {
        playerDamage = randomNums.nextInt(player[3] - player[2] + 1) + player[2];
        playerATK = player[1] + playerDamage;
        enemy[0] -= playerATK;
        System.out.printf("%s attacks with ATK = %d + %d = %d\n", playerName, player[1], playerDamage, playerATK);
        System.out.printf("%s HP is now %d - %d = %d\n\n", enemyName, enemy[0] + playerATK, playerATK, enemy[0]);

        if (enemy[0] <= 0)
          break;

        enemyDamage = randomNums.nextInt(enemy[3] - enemy[2] + 1) + enemy[2];
        enemyATK = enemy[1] + enemyDamage;
        player[0] -= enemyATK;
        System.out.printf("%s attacks with ATK = %d + %d = %d\n", enemyName, enemy[1], enemyDamage, enemyATK);
        System.out.printf("%s HP is now %d - %d = %d\n\n", playerName, player[0] + enemyATK, enemyATK, player[0]);
      } // end of while loop

      if (player[0] > 0){
        System.out.printf("%s defeated %s %d!\n\n", playerName, enemyName, i);
        coins = randomNums.nextInt((50 - 30) + 1) + 30;
        System.out.println(playerName + " gains " + coins + " gold coins!\n");
        player[4] += coins;
      }
      else
      {
        System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n", playerName);
        System.exit(0);
      }

      if(i <= numEnemies - 1){
        System.out.println("Press enter to continue...");
        keyboard.nextLine();
      }
    } // end of for loop

  }//end method

  public static void visitItemShop(int[] player)
  {
    final int LONG_SWORD_COST = 120;
    final int SHORT_SWORD_COST = 90;
    final int MACE_COST = 80;
    final int MAGIC_RING_COST = 150;
    final int HEALING_POTION_COST = 10;

    int reshop;
    int itemChoice, quantity;
    int discount = 0, totalCost = 0, finalCost = 0;
    String userName, itemName = "";

    Scanner keyboard = new Scanner(System.in);

    System.out.println("\nWelcome to The Item Shop!\n");

    do{
      System.out.println("\nYou currently have " + player[4] + " gold.\n");

      System.out.println("Here's what we have for sale (all prices are in units of gold):\n");

      System.out.printf("1. %-18s%-3d\n", "Long Sword", 120);
      System.out.printf("2. %-18s%-3d\n", "Short Sword", 90);
      System.out.printf("3. %-18s%-3d\n", "Mace", 80);
      System.out.printf("4. %-18s%-3d\n", "Ring of Strength", 150);
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
          break;

        case 2:
          itemName = "Short Sword";
          totalCost = SHORT_SWORD_COST * quantity;
          break;

        case 3:
          itemName = "Mace";
          totalCost = MACE_COST * quantity;
          break;

        case 4:
          itemName = "Ring of Strength";
          totalCost = MAGIC_RING_COST * quantity;
          break;

        case 5:
          itemName = "Healing Potion";
          totalCost = HEALING_POTION_COST * quantity;
          break;
      }

      if (quantity > 2)
        discount = (int) (totalCost * 0.1);

      finalCost = totalCost - discount;

      System.out.printf("\n%-10s: %5d gold", "Total cost", totalCost);
      System.out.printf("\n%-10s: %5d gold", "Discount", discount);
      System.out.printf("\n%-10s: %5d gold", "Final cost", finalCost);

      if (player[4] < finalCost){
        System.out.println("\n\nYou have insufficient funds! Please come back with more gold!\n");
      }

      else
      {
        player[4] -= finalCost;
        System.out.println("\nThe transaction is successful!\nYour remaining funds: " + player[4] + " gold\n");
        System.out.println("Thank you! Your transaction is complete!\n");
        System.out.println("You purchased: " + itemName);
        switch(itemChoice){
          case 1:
            System.out.println("Your weapon damage is now: 3 - 7");
            player[3] = LONG_SWORD_MAX;
            player[2] = LONG_SWORD_MIN;
            break;

          case 2:
            System.out.println("Your weapon damage is now: 1 - 4");
            player[3] = SHORT_SWORD_MAX;
            player[2] = SHORT_SWORD_MIN;

            break;

          case 3:
            System.out.println("Your weapon damage is now: 2 - 6");
            player[3] = MACE_MAX;
            player[2] = MACE_MIN;
            break;

          case 4:
            player[1] += (5 * quantity);
            System.out.println("Your strength has increaed to " + player[1]);

            break;

          case 5:
            player[0] += (10 * quantity);
            System.out.println("Your HP has increaed to " + player[0]);
            break;
        }
      }

      System.out.println("\nWould you like to make another purchase?");
      System.out.print("Enter 1 for \"yes\" or 0 for \"no\": ");
      reshop = keyboard.nextInt();

    } while(reshop == 1);//end do while

    System.out.println("\nGoodbye! Please stop by again!\n");

  } //end method

  public static void fightWizard(int[] player, String playerName)
  {
    final int FIRE_BLAST_MIN = 4;
    final int FIRE_BLAST_MAX = 10;
    final int WIZARD_INIT_HP = 40;
    final int WIZARD_INIT_STRENGTH = 8;
    System.out.println("You have now reached The Castle! Time to battle The Evil Wizard!\n");
    String enemyName = "";

    enemyName = "Wizard";
    int[] enemy = new int[5];
    enemy[0] = WIZARD_INIT_HP;
    enemy[1] = WIZARD_INIT_STRENGTH;
    enemy[2] = FIRE_BLAST_MIN;
    enemy[3] = FIRE_BLAST_MAX;

    int playerActionChoice;
    int randomNumAnswer;
    int randomNumGuess;
    int playerATK;
    int playerDamage;
    int enemyATK;
    int enemyDamage;

    Scanner keyboard = new Scanner(System.in);
    Random randomNums = new Random();

    randomNumAnswer = randomNums.nextInt(5) + 1;

    System.out.printf("***%s vs The Evil Wizard***\n", playerName);
    while(player[0] > 0 && enemy[0] > 0)
    {
      System.out.println("Choose your action:\n1. Attack\n2. Attempt Spell Cast\n");
      System.out.print("What would you like to do: ");
      playerActionChoice = keyboard.nextInt();

      switch(playerActionChoice)
      {
        case 1:
          playerDamage = randomNums.nextInt(player[3] - player[2] + 1) + player[2];
          playerATK = player[1] + playerDamage;
          enemy[0] -= playerATK;
          System.out.printf("\n%s attacks with ATK = %d + %d = %d\n", playerName, player[1], playerDamage, playerATK);
          System.out.printf("%s HP is now %d - %d = %d\n\n", enemyName, enemy[0] + playerATK, playerATK, enemy[0]);
          break;

        case 2:
          System.out.print("Enter your guess: ");
          randomNumGuess = keyboard.nextInt();
          if (randomNumGuess == randomNumAnswer)
          {
            System.out.println("\nCorrect!\n");
            System.out.printf("The %s's spell is cast successfully! The Wizard's HP is now 0!\n\n", playerName);
            enemy[0] = 0;
          }
          else
            System.out.println("\nIncorrect! The spell cast fails!\n");
          break;
      }

      if (enemy[0] <= 0)
        break;

      enemyDamage = randomNums.nextInt(enemy[3] - enemy[2] + 1) + enemy[2];
      enemyATK = enemy[1] + enemyDamage;
      player[0] -= enemyATK;
      System.out.printf("%s attacks with ATK = %d + %d = %d\n", enemyName, enemy[1], enemyDamage, enemyATK);
      System.out.printf("%s HP is now %d - %d = %d\n\n", playerName, player[0] + enemyATK, enemyATK, player[0]);

    } // end of while loop

    if (player[0] > 0)
    {
      System.out.printf("--%s wins the battle!--\n\n", playerName);
      System.out.println("You win! Congratulations!");
    }
    else
    {
      System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n", playerName);
    }
  }//end method

}
