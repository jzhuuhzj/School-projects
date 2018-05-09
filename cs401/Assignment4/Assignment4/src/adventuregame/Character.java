package adventuregame;

public abstract class Character{
	public enum Type {ROGUE, PALADIN, JACKIE_CHAN, GOBLIN, SKELETON, WIZARD};

	//attributes of character
	public String name;
	public int hitPoints;
	public int strength;
	public Weapon weapon;

	//methods
	public Character(Type characterType){
		switch (characterType){
			case ROGUE:
				this.name = "Rogue";
				this.hitPoints = 55;
				this.strength = 8;
				this.weapon = new Weapon("short sword", 1, 4); 
				break;
			case PALADIN:
				this.name = "Paladin";
				this.hitPoints = 35;
				this.strength = 14;
				this.weapon = new Weapon("long sword", 3, 7); 
				break;
			case JACKIE_CHAN:
				this.name = "Jackie ChanWeapon";
				this.hitPoints = 45;
				this.strength = 10;
				this.weapon = new Weapon("jump kick", 2, 6); 
				break;
			case GOBLIN:
				this.name = "Goblin";
				this.hitPoints = 25;
				this.strength = 4;
				this.weapon = new Weapon("axe", 2, 6); 
				break;
			case SKELETON:
				this.name = "Skeleton";
				this.hitPoints = 25;
				this.strength = 3;
				this.weapon = new Weapon("short sword", 1, 4); 
				break;
			case WIZARD:
				this.name = "Wizard";
				this.hitPoints = 40;
				this.strength = 8;
				this.weapon = new Weapon("fire blast", 4, 10); 
				break;
		}
	}

	public String getName(){
		return name;
	}

	public int getHitPoints(){
		return hitPoints;
	}

	public int getStrength(){
		return strength;
	}

	public void setStrength(int strength){
		this.strength = strength;
	}

	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}

	public void attack(Character opponent){
		int damage = this.weapon.getDamage();
		int atk = this.strength + damage;
		System.out.println(this.name + " attacks with ATK = " + this.strength + "+" + damage + "=" + atk);
		int originalHitPoints = opponent.hitPoints;
		opponent.hitPoints = opponent.hitPoints - atk;
		System.out.println(opponent.name + " HP is now " + originalHitPoints + "-" + atk + "=" + opponent.hitPoints);
		
	}

	public void increaseHitPoints(int pointIncrease){
		this.hitPoints = this.hitPoints + pointIncrease; 
	}

	public void decreaseHitPoints(int pointDecrease){
		this.hitPoints = this.hitPoints - pointDecrease;
	}

	public boolean isDefeated(){
		if (hitPoints <= 0)
			return true;
		else
			return false;
		
	}
}