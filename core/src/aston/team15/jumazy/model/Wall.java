package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends Block {

	public Wall(Coordinate coords, String type) {
		super(TextureConstants.getTexture(type), coords);
	}
	
	public String toString() {
		return "wall";
	}

}
