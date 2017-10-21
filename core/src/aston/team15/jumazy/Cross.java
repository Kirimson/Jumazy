package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class Cross extends Block{

	public Cross(Coordinate coordinate) {
		super(new Texture("cross.jpg"), coordinate);
		exits = new Exit(true, true, true, true);
	}
	
	public String toString() {
		return "cross";
	}
	
}