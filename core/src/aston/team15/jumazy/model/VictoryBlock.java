package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class VictoryBlock extends ActionBlock {

	public VictoryBlock(Coordinate coords) {
		super(new Texture("victory.png"), coords);
	}
	
	public String toString() {
		return "path";
	}
	
}
