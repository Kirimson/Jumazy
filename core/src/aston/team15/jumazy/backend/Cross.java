package aston.team15.jumazy.backend;

import com.badlogic.gdx.graphics.Texture;

public class Cross extends Block{

	public Cross(Coordinate coordinate, int orientation) {
		super(new Texture("cross.jpg"), coordinate, orientation);
		exits.add("up");
		exits.add("down");
		exits.add("left");
		exits.add("right");
	}
	
	public String toString() {
		return "cross";
	}
	
}