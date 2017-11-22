package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends Block {

	public Wall(Coordinate coords, int orientation, String type) {
		super(new Texture("wall"+type+".png"), coords, orientation);
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return "wall";
	}

}
