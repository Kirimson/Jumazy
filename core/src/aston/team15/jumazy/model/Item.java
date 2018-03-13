package aston.team15.jumazy.model;

public enum Item {
	RED_POTION(4, "hp"), BLUE_POTION(2, "stamina"), GREEN_POTION(2, "agility"), SWORD(2, "strength");

	private final int value;

	private Item(int value, String stat) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
