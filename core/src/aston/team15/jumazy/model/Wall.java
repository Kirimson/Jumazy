package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends Block {

	public Wall(Coordinate coords, String type) {
		super(new Texture("wall"+type+".png"), coords);
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return "wall";
	}

}
