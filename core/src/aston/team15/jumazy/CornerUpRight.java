package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class CornerUpRight extends Block{

	public CornerUpRight(Coordinate coordinate) {
		
		super(new Texture("ur.jpg"), coordinate);
		exits = new Exit(true, false, false, true);
	}
	
	public String toString() {
		return "cornerupright";
	}
	
	
	
}