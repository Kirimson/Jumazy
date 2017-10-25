package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class TLeft extends Block {

	public TLeft(Coordinate coordinate) {
		super(new Texture("tl.jpg"), coordinate);
		exits = new Exit(true, true, true, false);
	}
	
	public String toString() {
		return "T-Junction Left";
	}
}
