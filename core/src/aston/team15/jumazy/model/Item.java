package aston.team15.jumazy.model;

/**
 * An Item that can be picked up throughout the game, have certain stat buffs or special effects implemented within the
 * PlayerModel
 *
 * @author Abdullah, Kieran
 */
public enum Item {
	RED_POTION("Red Potion", 4, "Max Health", "consumable", "potion-red"), 
	BLUE_POTION("Blue Potion", 2, "Stamina", "consumable", "potion-blue"), 
	GREEN_POTION("Green Potion", 2, "Luck", "consumable", "potion-green"),
	PURPLE_POTION("Purple Potion", 2, "Agility", "consumable", "potion-blue"),
	APPLE("Apple", 2, "Health", "consumable", "apple"),
	GRAPES("Grapes", 2, "Health", "consumable", "grapes"),
	SWORD("Sword", 2, "Strength", "held", "sword"),
	BOWANDARROW("Bow and Arrow", 2, "Strength", "held", "arrow"),
	KEY("Key", 0, null, "held", "key"),
	TORCH("Torch", 0, null, "held", "torch");

	private final String name;
	private final int value;
	private final String statEffected;
	private final String type;
	private final String atlasString;

	private Item(String name, int value, String statEffect, String type, String atlasString) {
		this.name = name;
		this.value = value;
		this.statEffected = statEffect;
		this.type = type;
		this.atlasString = atlasString;
	}

	public int getValue() {
		return value;
	}
	
	public String getStatEffected() {
		return statEffected;
	}
	
	public String getType() {
		return type;
	}
	
	public String getAtlasString() {
		return atlasString;
	}
	
	public String toString() {
		return name;
	}
}
