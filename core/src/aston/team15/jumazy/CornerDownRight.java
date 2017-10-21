package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class CornerDownRight extends Block{

	public CornerDownRight(Coordinate coordinate) {
		
		super(new Texture("dr.jpg"), coordinate);
		exits = new Exit(false, true, false, true);
	}
	
	public String toString() {
		return "cornerdownright";
	}
	
}