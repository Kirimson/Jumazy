package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Path extends Block {

	public Path(Coordinate coords) {
		super(TextureConstants.getTexture("path"), coords);
	}
	
	public Path(Texture texture, Coordinate coords) {
		super(texture, coords);
	}

	public String toString() {
		return "O";
	}

}
