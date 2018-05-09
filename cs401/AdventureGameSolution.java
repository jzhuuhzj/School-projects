import java.util.Scanner;
import java.util.Random;

public class AdventureGameSolution
{
        public static void main(String[] args)
        {
                final int SHORT_SWORD_MIN = 1;
                final int SHORT_SWORD_MAX = 4;
                final int LONG_SWORD_MIN = 3;
                final int LONG_SWORD_MAX = 7;
                final int JUMP_KICK_MIN = 2;
                final int JUMP_KICK_MAX = 6;
                final int AXE_MIN = 2;
                final int AXE_MAX = 6;
                final int FIRE_BLAST_MIN = 4;
                final int FIRE_BLAST_MAX = 10;

                final int ROGUE_INIT_HP = 55;
                final int ROGUE_INIT_STRENGTH = 8;
                final int PALADIN_INIT_HP = 35;
                final int PALADIN_INIT_STRENGTH = 14;
                final int CHAN_INIT_HP = 45;
                final int CHAN_INIT_STRENGTH = 10;

                final int MINION_INIT_HP = 25;
                final int GOBLIN_INIT_STRENGTH = 4;
                final int SKELETON_INIT_STRENGTH = 3;
                final int WIZARD_INIT_HP = 40;
                final int WIZARD_INIT_STRENGTH = 8;

                final int NUM_GOBLINS = 3;
                final int NUM_SKELETONS = 5;

                String playerName = "";
                int playerHP = 0, playerStrength = 0, playerDamageMin = 0, playerDamageMax = 0;

                String enemyName = "";
                int enemyHP = 0, enemyStrength = 0, enemyDamageMin = 0, enemyDamageMax = 0;

                int characterChoice = 0, pathChoice = 0, itemChoice = 0, numEnemies = 0;
                String pathName = "";

                int playerDamage, playerATK;
                int enemyDamage, enemyATK;

                int playerActionChoice, randomNumAnswer, randomNumGuess;

                Scanner keyboard = new Scanner(System.in);
                Random randomNums = new Random();

                System.out.println("\nAdventure Game - Start!\n");
                System.out.println("Here are the characters:");
                System.out.println("1. Rogue\n2. Paladin\n3. Jackie Chan\n");

                System.out.print("Which character do you choose?: ");
                characterChoice = keyboard.nextInt();

                switch(characterChoice)
                {
                        case 1:
                                playerName = "Rogue";
                                playerHP = ROGUE_INIT_HP;
                                playerStrength = ROGUE_INIT_STRENGTH;
                                playerDamageMin = SHORT_SWORD_MIN;
                                playerDamageMax = SHORT_SWORD_MAX;
                                break;
                        case 2:
                                playerName = "Paladin";
                                playerHP = PALADIN_INIT_HP; 
                                playerStrength = PALADIN_INIT_STRENGTH; 
                                playerDamageMin = LONG_SWORD_MIN; 
                                playerDamageMax = LONG_SWORD_MAX;
                                break;
                        case 3:
                                playerName = "Jackie Chan";
                                playerHP = CHAN_INIT_HP;
                                playerStrength = CHAN_INIT_STRENGTH;
                                playerDamageMin = JUMP_KICK_MIN;
                                playerDamageMax = JUMP_KICK_MAX;
                                break;
                }

                System.out.printf("\nYou chose: %s\n\n", playerName);

                System.out.print("The Evil Wizard must be defeated! He is in The Castle. To get to ");
                System.out.println("The Castle, you must travel through either:");
                System.out.println("1. The Forest\n2. The Graveyard\n");

                System.out.print("Which path will you take?: ");
                pathChoice = keyboard.nextInt();

                switch(pathChoice)
                {
                        case 1:
                                pathName = "The Forest";
                                enemyName = "Goblin";
                                enemyStrength = GOBLIN_INIT_STRENGTH;
                                enemyDamageMin = AXE_MIN;
                                enemyDamageMax = AXE_MAX;
                                numEnemies = NUM_GOBLINS;
                                break;
                        case 2:
                                pathName = "The Graveyard";
                                enemyName = "Skeleton";
                                enemyStrength = SKELETON_INIT_STRENGTH;
                                enemyDamageMin = SHORT_SWORD_MIN;
                                enemyDamageMax = SHORT_SWORD_MAX;
                                numEnemies = NUM_SKELETONS;
                                break;
                }

                System.out.printf("\nYou chose: %s\n\n", pathName);
                System.out.printf("Once you enter %s, you encounter %d %ss! Time for battle!\n\n", pathName,
                                  numEnemies, enemyName); 

                for (int i = 1; i <= numEnemies; i++)
                {
                        enemyHP = MINION_INIT_HP;

                        System.out.printf("***%s vs %s %d***\n", playerName, enemyName, i);

                        while(enemyHP > 0 && playerHP > 0)
                        {
                                playerDamage = randomNums.nextInt(playerDamageMax - playerDamageMin + 1) + playerDamageMin;
                                playerATK = playerStrength + playerDamage;
                                enemyHP -= playerATK;
                                System.out.printf("%s attacks with ATK = %d + %d = %d\n", playerName, playerStrength, playerDamage, playerATK);
                                System.out.printf("%s HP is now %d - %d = %d\n\n", enemyName, enemyHP + playerATK, playerATK, enemyHP);

                                if (enemyHP <= 0)
                                        break;

                                enemyDamage = randomNums.nextInt(enemyDamageMax - enemyDamageMin + 1) + enemyDamageMin;
                                enemyATK = enemyStrength + enemyDamage;
                                playerHP -= enemyATK;
                                System.out.printf("%s attacks with ATK = %d + %d = %d\n", enemyName, enemyStrength, enemyDamage, enemyATK);
                                System.out.printf("%s HP is now %d - %d = %d\n\n", playerName, playerHP + enemyATK, enemyATK, playerHP);
                        } // end of while loop

                        if (playerHP > 0)
                                System.out.printf("%s defeated %s %d!\n\n", playerName, enemyName, i);
                        else
                        {
                                System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n", playerName);
                                System.exit(0);
                        }
                } // end of for loop

                System.out.printf("Your HP is: %d\n\n", playerHP);

                System.out.println("Please choose a reward.\n1. Healing Potion\n2. Ring of Strength\n");
                System.out.print("Which item do you choose?: ");
                itemChoice = keyboard.nextInt();

                switch(itemChoice)
                {
                        case 1:
                                System.out.println("\nYou chose: Healing Potion\n");
                                playerHP += 10;
                                System.out.printf("Your HP has increased to %d + %d = %d!\n\n", playerHP - 10, 10, playerHP);
                                break;
                        case 2:
                                System.out.println("\nYou chose: Ring of Strength\n");
                                playerStrength += 5;
                                System.out.printf("Your Strength has increased to %d + %d = %d!\n\n", playerStrength - 5, 5, playerStrength);
                }

                System.out.println("You have now reached The Castle! Time to battle The Evil Wizard!\n");

                enemyName = "Wizard";
                enemyHP = WIZARD_INIT_HP;
                enemyStrength = WIZARD_INIT_STRENGTH;
                enemyDamageMin = FIRE_BLAST_MIN; 
                enemyDamageMax = FIRE_BLAST_MAX; 

                randomNumAnswer = randomNums.nextInt(6) + 1;

                System.out.printf("***%s vs The Evil Wizard***\n", playerName);
                while(playerHP > 0 && enemyHP > 0)
                {
                        System.out.println("Choose your action:\n1. Attack\n2. Attempt Spell Cast\n");
                        System.out.print("What would you like to do: ");
                        playerActionChoice = keyboard.nextInt();

                        switch(playerActionChoice)
                        {
                                case 1:
                                        playerDamage = randomNums.nextInt(playerDamageMax - playerDamageMin + 1) + playerDamageMin;
                                        playerATK = playerStrength + playerDamage;
                                        enemyHP -= playerATK;
                                        System.out.printf("\n%s attacks with ATK = %d + %d = %d\n", playerName, playerStrength, playerDamage, playerATK);
                                        System.out.printf("%s HP is now %d - %d = %d\n\n", enemyName, enemyHP + playerATK, playerATK, enemyHP);
                                        break;
                                case 2:
                                        System.out.print("Enter your guess: ");
                                        randomNumGuess = keyboard.nextInt();
                                        if (randomNumGuess == randomNumAnswer)
                                        {
                                                System.out.println("\nCorrect!\n");
                                                System.out.printf("The %s's spell is cast successfully! The Wizard's HP is now 0!\n\n", playerName);
                                                enemyHP = 0;
                                        }
                                        else
                                                System.out.println("\nIncorrect! The spell cast fails!\n");
                                        break;
                        }

                        if (enemyHP <= 0)
                                break;

                        enemyDamage = randomNums.nextInt(enemyDamageMax - enemyDamageMin + 1) + enemyDamageMin;
                        enemyATK = enemyStrength + enemyDamage;
                        playerHP -= enemyATK;
                        System.out.printf("%s attacks with ATK = %d + %d = %d\n", enemyName, enemyStrength, enemyDamage, enemyATK);
                        System.out.printf("%s HP is now %d - %d = %d\n\n", playerName, playerHP + enemyATK, enemyATK, playerHP);

                } // end of while loop

                if (playerHP > 0)
                {
                        System.out.printf("--the%s wins the battle!--\n\n", playerName);
                        System.out.println("You win! Congratulations!");
                }
                else
                {
                        System.out.printf("--%s is defeated in battle!--\n\nGAME OVER\n", playerName);
                }

        } // end of main 
} // end of class
