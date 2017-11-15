package aston.team15.jumazy.backend;

import com.badlogic.gdx.graphics.Texture;

public class TJunction extends Block{

	public TJunction(Coordinate coordinate, int orientation) {
		super(new Texture("td.jpg"), coordinate, orientation);
		
		if (orientation==0) {
			exits.add("right");
			exits.add("down");
			exits.add("left");
			} else if (orientation==1){
			exits.add("down");
			exits.add("left");
			exits.add("up");
			} else if (orientation==2){
			exits.add("left");
			exits.add("up");
			exits.add("right");
			} else {
			exits.add("up");
			exits.add("right");
			exits.add("down");
			}
	}
	
	public String toString() {
		
		String str = "";
		switch(orientation) {
		case 0: str = "Tjunction down";break;
		case 1: str = "Tjunction left";break;
		case 2: str = "Tjunction up";break;
		case 3: str = "Tjunction right";break;
		}
		str += "\n"+exits.toString();
		return str;
	}
}
