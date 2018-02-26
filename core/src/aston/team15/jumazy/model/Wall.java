package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends Block {

	public Wall(String type) {
		super(TextureConstants.getTexture("wall"+type), new Coordinate(0,0));
	}

	public Wall(Coordinate coords, String type) {
		super(TextureConstants.getTexture("wall"+type), coords);
	}
	
	public String toString() {
		return "#";
	}

}
