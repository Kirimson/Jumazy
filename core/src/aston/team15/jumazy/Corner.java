package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class Corner extends Block{
	
	public Corner(Coordinate coordinate, int orientation) {
		super(new Texture("ul.jpg"), coordinate, orientation);
		
		switch(orientation) {
		case 0: exits.add("up");exits.add("left");break;
		case 1: exits.add("up");exits.add("right");break;
		case 2: exits.add("down");exits.add("right");break;
		case 3: exits.add("down");exits.add("left");break;
		}
	}
	
	public String toString() {
		String str = "";
		switch(orientation) {
		case 0: str = "corner up left";break;
		case 1: str = "corner up right";break;
		case 2: str = "corner down left";break;
		case 3: str = "corner down right";break;
		}
		str += "\n"+exits.toString();
		return str;
	}
	
}