package aston.team15.jumazy.model;

public enum Item {
	RED_POTION("Red Potion", 4, "max-health"), 
	BLUE_POTION("Blue Potion", 2, "stamina"), 
	GREEN_POTION("Green Potion", 2, "luck"),
	PURPLE_POTION("Purple Potion", 2, "agility"),
	SWORD("Sword", 2, "strength"),
	BOWANDARROW("Bow and Arrow", 2, "strength"),
	APPLE("Apple", 2, "health"),
	GRAPES("Grapes", 2, "health"),
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
