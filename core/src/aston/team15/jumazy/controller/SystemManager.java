package aston.team15.jumazy.controller;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Manages {@link MainSystem} instances, and is used directly with the main libGDX class. allows multiple systems to be added, while only the first system is ever accessed
 * @author kieran, Jawwad
 *
 */
public class SystemManager {

	private Stack<MainSystem> systemStack;
	
	private boolean running;
	
	public SystemManager() {
		systemStack = new Stack<MainSystem>();
		running = false;
	}
	
	private void pop() {
		systemStack.pop();
	}
	
	public void push(MainSystem system) {
		systemStack.push(system);
	}
	
	public MainSystem peek() {
		return systemStack.peek();
	}
	
	/**
	 * Removes the top system on the stack and replaces it with the new system to be used in game
	 * @param system new {@link MainSystem} to be used
	 */
	public void setNewSystem(MainSystem system) {
		pop();
		push(system);
	}

	/**
	 * Accesses the top system in the stack and calls its handleInput method
	 * @return modified SpriteBatch to be drawn
	 */
	public void handleInput() {
		systemStack.peek().handleInput();
	}
	
	/**
	 * Accesses the top system in the stack and calls its draw method
	 * @param batch SpriteBacth to be modified
	 * @return modified SpriteBatch to be drawn
	 */
	public SpriteBatch draw(SpriteBatch batch) {
		return systemStack.peek().draw(batch);
	}
	
	public boolean getGameRunning(){
		return running;
	}
	public void setGameRunning(){
	running = !running;
	}
	
}
