package aston.team15.jumazy.model;

public enum Item {
	RED_POTION("Red Potion", 4), BLUE_POTION("Blue Potion", 2), GREEN_POTION("Green Potion", 2), SWORD("Sword", 2);

	private final String name;
	private final int value;

	private Item(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		return name;
	}
}
