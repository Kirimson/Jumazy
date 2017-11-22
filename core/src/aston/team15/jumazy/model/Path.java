package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Path extends Block {

	public Path(Coordinate coords) {
		super(new Texture("path.png"), coords);
	}
	
	public Path(Texture texture, Coordinate coords) {
		super(texture, coords);
	}

	public String toString() {
		return "path";
	}

}
