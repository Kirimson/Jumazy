package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class StraightHorizontal extends Block{

	public StraightHorizontal(Coordinate coordinate) {
		
		super(new Texture("lr.jpg"), coordinate);
		exits = new Exit(false, false, true, true);
	}
	
	public String toString() {
		return "straighthori";
	}
}