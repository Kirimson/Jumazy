package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Path extends Block {

	public Path(Coordinate coords, int orientation) {
		super(new Texture("path.png"), coords);
	}
	
	public String toString() {
		return "path";
	}
}
