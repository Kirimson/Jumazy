package aston.team15.jumazy.backend;

import com.badlogic.gdx.graphics.Texture;

public class Straight extends Block{

	public Straight(Coordinate coordinate, int orientation) {
		super(new Texture("lr.jpg"), coordinate, orientation);
		if (orientation==0 || orientation==2) {
			exits.add("left");
			exits.add("right");
		}
		else
		{
			exits.add("up");
			exits.add("down");
		}
	}
	
	public String toString() {
		String str = "";
		switch(orientation) {
		case 0: str = "Straight hori";break;
		case 1: str = "Striaght vert";break;
		case 2: str = "Straight hori";break;
		case 3: str = "Striaght vert";break;
		}
		str += "\n"+exits.toString();
		return str;
	}
}