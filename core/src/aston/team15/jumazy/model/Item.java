package aston.team15.jumazy.model;

public enum Item {
	RED_POTION(4, "hp"), BLUE_POTION(2, "stamina"), GREEN_POTION(2, "agility"), SWORD(2, "strength");

	private final int value;
	private final String stat;

	private Item(int value, String stat) {
		this.value = value;
		this.stat = stat;
	}

	public int getValue() {
		return value;
	}
	
	public String getStat() {
		return stat;
	}
}
