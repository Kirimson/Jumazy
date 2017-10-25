package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class TUp extends Block{

	public TUp(Coordinate coordinate) {
		super(new Texture("tu.jpg"), coordinate);
		exits = new Exit(true, false, true, true);
	}
	
	public String toString() {
		return "T-Junction Up";
	}

}
