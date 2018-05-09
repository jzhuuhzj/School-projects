package adventuregame;

import java.util.Scanner;

import adventuregame.Potion.Type;

public class ItemShop{
	
	private static final int LONG_SWORD_COST = 120;
	private static final int SHORT_SWORD_COST = 90;
	private static final int MACE_COST = 80;
	private static final int MINOR_HEALING_POTION_COST = 5;
	private static final int HEALING_POTION_COST = 10;
	private static final int MINOR_STRENGTH_COST = 20;
	private static final int STRENGTH_POTION_COST = 40;

	public void visitItemShop(Player player){
		int reshop;
		int itemChoice, quantity;
		int discount = 0, totalCost = 0, finalCost = 0;
		String itemName = "";
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\nWelcome to The Item Shop!\n");
		
		do{
			System.out.println("\nYou currently have " + player.getCoins() + " gold.\n");
			System.out.println("Here's what we have for sale (all prices are in units of gold):\n");
			System.out.printf("1. %-20s%-3d\n", "Long Sword", 120);
			System.out.printf("2. %-20s%-3d\n", "Short Sword", 90);
			System.out.printf("3. %-20s%-3d\n", "Mace", 80);
			System.out.printf("4. %-20s%-3d\n", "Minor Healing Potion", 5);
			System.out.printf("5. %-20s%-3d\n", "Healing Potion", 10);
			System.out.printf("6. %-20s%-3d\n", "Minor Strength Potion", 20);
			System.out.printf("7. %-20s%-3d\n", "Strength Potion", 40);
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
				itemName = "Minor Healing Potion";
				totalCost = MINOR_HEALING_POTION_COST * quantity;
				break;
				case 5:
				itemName = "Healing Potion";
				totalCost = HEALING_POTION_COST * quantity;
				break;
				case 6:
				itemName = "Minor Strength Potion";
				totalCost = MINOR_STRENGTH_COST * quantity;
				break;
				case 7:
				itemName = "Strength Potion";
				totalCost = STRENGTH_POTION_COST * quantity;
				break;
					
			}

		    if (quantity > 2) {
		        discount = (int) (totalCost * 0.1);
		    }
		    finalCost = totalCost - discount;
		    
			System.out.printf("\n%-10s: %5d gold", "Total cost", totalCost);
			System.out.printf("\n%-10s: %5d gold", "Discount", discount);
			System.out.printf("\n%-10s: %5d gold", "Final cost", finalCost);
			
			if (player.getCoins() < finalCost){
				System.out.println("\n\nYou have insufficient funds! Please come back with more gold!\n");
			}
			else
			{
				player.coins = player.coins - finalCost;
				System.out.println("\nThe transaction is successful!\nYour remaining funds: " +
				player.coins + " gold\n");
				System.out.println("Thank you! Your transaction is complete!\n");
				System.out.println("You purchased: " + itemName);
				
				Potion potion;
				switch(itemChoice){
					case 1:
						System.out.println("Your weapon damage is now: 3 - 7");
						player.weapon = new Weapon("Long Sword", 3, 7);
						break;
					case 2:
						System.out.println("Your weapon damage is now: 1 - 4");
						player.weapon = new Weapon("Short Sword", 1, 4);
						break;
					case 3:
						System.out.println("Your weapon damage is now: 2 - 6");
						player.weapon = new Weapon("Mace", 2, 6);
						break;
					case 4:
						for (int i = 0; i < quantity; i++){
							potion = new Potion(Type.MINOR_HEALING);
							player.addToInventory(potion);
						}
						break;
					case 5:
						for (int i = 0; i < quantity; i++){
							potion = new Potion(Type.HEALING);
							player.addToInventory(potion);
						}
						break;
					case 6:
						for (int i = 0; i < quantity; i++){
							potion = new Potion(Type.MINOR_STRENGTH);
							player.addToInventory(potion);
						}
						break;
					case 7:
						for (int i = 0; i < quantity; i++){
							potion = new Potion(Type.STRENGTH);
							player.addToInventory(potion);
						}
						break;			
				}
			}
			
			System.out.println("\nWould you like to make another purchase?");
			System.out.print("Enter 1 for \"yes\" or 0 for \"no\": ");
				reshop = keyboard.nextInt();
			} while(reshop == 1);//end do while
			System.out.println("\nGoodbye! Please stop by again!\n");
			 
		} //end method
	
	}
