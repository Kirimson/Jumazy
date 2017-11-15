package aston.team15.jumazy.backend;

import com.badlogic.gdx.graphics.Texture;

public class Path extends Block {

	public Path(Coordinate coords, int orientation) {
		super(new Texture("path.png"), coords, orientation);
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return "path";
	}
}
