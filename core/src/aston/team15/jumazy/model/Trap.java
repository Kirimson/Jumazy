package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

import aston.team15.jumazy.view.RiddleGUI;

public class Trap extends Path {

	private RiddleGUI gui;

	public Trap() {
		super(new Texture("trap.png"), new Coordinate(0,0));
	}
	
	public Trap(Coordinate coords) {
		super(new Texture("trap.png"), coords);
	}
	
	public void createGUI() {
		gui = new RiddleGUI();
	}
	
	public boolean stillTrapped() {
		if(!gui.isAlive()) {
			return false;
		}
		return true;
	}
	
	public boolean wasCorrect() {
		return gui.isCorrect();
	}

	public String toString() {
		return "T";
	}
}
