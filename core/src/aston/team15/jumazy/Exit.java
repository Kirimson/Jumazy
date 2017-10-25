package aston.team15.jumazy;

public class Exit {
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	public Exit(boolean up, boolean down, boolean left, boolean right) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}
	
	public boolean[] getExits() {
		boolean[] exits = {up, down, left, right};
		return exits;
	}
	
	public boolean checkExit(String direction) {
		switch (direction) {
			case "up": return up;
			case "down": return down;
			case "left": return left;
			case "right": return right;
		}
		System.out.println("why am i here");
		return false;
	}
	
	public boolean checkEntrance(String direction) {
		switch (direction) {
			case "up": return down;
			case "down": return up;
			case "left": return right;
			case "right": return left;
		}
		System.out.println("why am i here");
		return false;
	}
}
