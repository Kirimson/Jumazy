package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class CornerUpLeft extends Block{

	public CornerUpLeft(Coordinate coordinate) {
		
		super(new Texture("ul.jpg"), coordinate);
		exits = new Exit(true, false, true, false);
	}
	
	public String toString() {
		return "cornerupleft";
	}
	
	
}