package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

import aston.team15.jumazy.view.Question;

public class Trap extends Path {

	private Question gui;
	
	public Trap(Coordinate coords) {
		super(new Texture("trap.png"), coords);
		gui = new Question ();
	}
	
	public String toString() {
		return "path";
	}
	
	public void createGUI() {
		gui.create();
		renderGui();
		System.out.println("test");
	}
	
	public void renderGui() {
		gui.render();
	}
	
	public boolean stillTrapped() {
		if(!gui.isAlive()) {
			return false;
		}
		System.out.println("in this");
		gui.render();
		return true;
	}
	
	public boolean wasCorrect() {
		return gui.isCorrect();
	}
}
