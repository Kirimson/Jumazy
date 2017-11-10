package aston.team15.jumazy;

public class Exit {
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	public Exit() {
		up = false;
		down = false;
		left = false;
		right = false;
	}
	
	public void add(String dir) {
		switch(dir) {
			case "up": up = true;break;
			case "down": down = true;break;
			case "left": left = true;break;
			case "right": right = true;break;
		}
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
	
	public String toString() {
		String str;
		
		str = "up:	"+up+"\n";
		str += "down:	"+down+"\n";
		str += "left:	"+left+"\n";
		str += "right:	"+right+"\n";
		
		return str;
	}
}
