package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class StraightVertical extends Block{

	public StraightVertical(Coordinate coordinate) {
		
		super(new Texture("du.jpg"), coordinate);
		exits = new Exit(true, true, false, false);
	}
	public String toString() {
		return "straightvert";
	}
	
}