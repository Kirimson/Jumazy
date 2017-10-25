package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class TDown extends Block{

	public TDown(Coordinate coordinate) {
		super(new Texture("td.jpg"), coordinate);
		exits = new Exit(false, true, true, true);
	}
	
	public String toString() {
		return "T-Junction Down";
	}
}
