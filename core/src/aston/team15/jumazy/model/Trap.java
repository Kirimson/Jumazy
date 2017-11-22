package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class Trap extends Path {

	private RiddleGUI gui;
	
	public Trap(Coordinate coords) {
		super(new Texture("trap.png"), coords);
	}
	
	public String toString() {
		return "path";
	}
	
	public void createGUI() {
		gui = new RiddleGUI();
		System.out.println("creating");
		gui.closeRiddle();
	}
	
	public boolean stillTrapped() {
		if(!gui.isAlive()) {
			System.out.println("not trapped no mo");
			return false;
		}
		return true;
	}
}
