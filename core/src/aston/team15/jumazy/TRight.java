package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class TRight extends Block {

	public TRight(Coordinate coordinate) {
		super(new Texture("tr.jpg"), coordinate);
		exits = new Exit(true, true, false, true);
	}
	
	public String toString() {
		return "T-Junction Right";
	}

}
