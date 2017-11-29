package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.Rain;
import aston.team15.jumazy.model.Sun;
import aston.team15.jumazy.model.Weather;
import aston.team15.jumazy.view.GraphicsManager;
import aston.team15.jumazy.view.JumazyGame;
/**
 * Subclass of {@link MainSystem}. The main class containing the maze and gameplay elements of the game.
 * Allows drawing of the maze, player and other UI elements
 * @author kieran
 *
 */
public class GameSystem extends MainSystem{
	
	private int nextPlayers;
	private Maze maze;
	private GraphicsManager gMan;
	

	public GameSystem(SystemManager sysMan) {
		super(sysMan);
		maze = new Maze(35, 20, 4);
		gMan = new GraphicsManager();
		setupCamera();
	}
	
	/**
	 * Sends needed parameters to the {@link GraphcisManager} to draw all needed textures
	 */
	public SpriteBatch draw(SpriteBatch batch) {
		return gMan.draw(batch, maze);
	}
	
	/**
	 * Any action that happens on the maze will be called through this method
	 * As of now. it is just listening for player movement
	 * Called each frame by {@link JumazyGame#render()}
	 */
	@Override
	public void handleInput() {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
		&& maze.getCurrPlayer().rolled() == false 
		&& maze.getCurrPlayer().isTrapped() == false) {
			focusCamera();
			maze.getCurrPlayer().switchRolled();
			maze.getCurrPlayer().roll(maze.getWeather().getMovementMod());
			System.out.println("Current player " + maze.getCurrPlayerVal());
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) 
		&& maze.getCurrPlayer().getRollSpaces() == 0
		&& maze.getCurrPlayer().isTrapped() == false) {
			maze.getCurrPlayer().switchRolled();
			unfocusCamera();
			maze.nextPlayer();
			System.out.println("CHANGE TURN");
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.B)
		&& maze.getCurrPlayer().isTrapped() == false) {
			maze.getCurrPlayer().moveToStartOfTurn();
		}
		
		if(maze.getCurrPlayer().rolled())
		{
			String direction = "";
			if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
				direction = "right";
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
				direction = "left";
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				direction = "up";
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				direction = "down";
			}
			
			if(direction != "")
				maze.getCurrPlayer().newMove(direction);
			
//			if(maze.getCurrPlayer().getRollSpaces() == 0) {
//				unfocusCamera();
//				maze.nextPlayer();
//				System.out.println("CHANGE TURN");
//			}
			
		}
		
		if(maze.getCurrPlayer().isTrapped()) {
			maze.getCurrPlayer().checkStillTrapped();
		}

	}
	
	private void focusCamera() {
		cam.setToOrtho(false,GAME_WIDTH, GAME_HEIGHT);
		float currPlayerXPos = gMan.getCurPlayerFloatXPos( maze);
		float currPlayerYPos = gMan.getCurPlayerFloatYPos(maze);
		System.out.println(currPlayerXPos+"+"+currPlayerYPos);
		if(currPlayerXPos<=-314)
			currPlayerXPos=-324+(GAME_WIDTH/2);
		if(currPlayerYPos<=-318)
			currPlayerYPos=-334+(GAME_HEIGHT/2);
		if(currPlayerXPos>=1862)
			currPlayerXPos=1888-(GAME_WIDTH/2);
		if(currPlayerYPos>=994)
			currPlayerYPos=1018-(GAME_HEIGHT/2);
		cam.position.set(currPlayerXPos,currPlayerYPos, 0);
	}
	
	private void unfocusCamera() {
		setupCamera();
	}
	
	protected void setupCamera() {
		cam.setToOrtho(false,GAME_WIDTH*2, GAME_HEIGHT*2);
		cam.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);
	}
}