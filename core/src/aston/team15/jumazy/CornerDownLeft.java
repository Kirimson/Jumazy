package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class CornerDownLeft extends Block{
	
	public CornerDownLeft(Coordinate coordinate) {
		super(new Texture("dl.jpg"), coordinate);
		exits = new Exit(false, true, true, false);
	}
	
	public String toString() {
		return "cornerdownleft";
	}
	
}