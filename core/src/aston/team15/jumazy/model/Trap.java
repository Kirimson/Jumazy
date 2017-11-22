package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Trap extends Path {

	public Trap(Coordinate coords) {
		super(new Texture("trap.png"), coords);
	}
	
	public String toString() {
		return "path";
	}
	
	public void createGUI() {
		RiddleGUI gui = new RiddleGUI();
	}
}
