package aston.team15.jumazy.model;

public enum Item {
	RED_POTION("Red Potion", 4, "Max Health"), 
	BLUE_POTION("Blue Potion", 2, "Stamina"), 
	GREEN_POTION("Green Potion", 2, "Luck"),
	PURPLE_POTION("Purple Potion", 2, "Agility"),
	SWORD("Sword", 2, "Strength"),
	BOWANDARROW("Bow and Arrow", 2, "Strength"),
	APPLE("Apple", 2, "Health"),
	GRAPES("Grapes", 2, "Health"),
	KEY("Key", 0, null);

	private final String name;
	private final int value;
	private final String statEffected;

	private Item(String name, int value, String statEffect) {
		this.name = name;
		this.value = value;
		this.statEffected = statEffect;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		return name;
	}
	
	public String getStatEffected() {
		return statEffected;
	}
}
