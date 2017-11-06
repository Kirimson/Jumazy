package aston.team15.jumazy;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SystemManager {

	private Stack<MainSystem> currentSystem;
	
	public SystemManager() {
		currentSystem = new Stack<MainSystem>();
	}
	
	private void pop() {
		currentSystem.pop();
	}
	
	public void push(MainSystem system) {
		currentSystem.push(system);
	}
	
	public void setNewSystem(MainSystem system) {
		pop();
		push(system);
	}

	public void handleInput() {
		currentSystem.peek().handleInput();
	}
	
	public SpriteBatch draw(SpriteBatch batch) {
		return currentSystem.peek().draw(batch);
	}
	
}
