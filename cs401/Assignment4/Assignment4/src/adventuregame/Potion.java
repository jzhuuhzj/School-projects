package adventuregame;

public class Potion{
	

	public enum Type{MINOR_HEALING, HEALING, MINOR_STRENGTH, STRENGTH};

	//attributes
	private Type type;
	private String name = "";
	
	public Potion(Type potionType){	
		switch (potionType){
		case MINOR_HEALING:
			this.type = potionType;
			this.name = "Minor Healing Potion";
			break;
		case HEALING:
			this.type = potionType;
			this.name = "Healing Potion";
			break;
		case MINOR_STRENGTH:
			this.type = potionType;
			this.name = "Minor Strength Potion";
			break;
		case STRENGTH:
			this.type = potionType;
			this.name = "Strength Potion";
			break;
		}
	}

	public String getName(){
		return name;
	}
	
	public void drink(Player player) {
		switch (this.type){
			case MINOR_HEALING:
				player.hitPoints = player.hitPoints + 5;
				break;
			case HEALING:
				player.hitPoints = player.hitPoints + 10;
				break;
			case MINOR_STRENGTH:
				player.strength = player.strength + 2;
				break;
			case STRENGTH:
				player.strength = player.strength + 5;
				break;
		}
	}

}
