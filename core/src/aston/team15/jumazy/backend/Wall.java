package aston.team15.jumazy.backend;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends Block {

	public Wall(Coordinate coords, int orientation) {
		super(new Texture("wall.jpg"), coords, orientation);
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return "wall";
	}

}
