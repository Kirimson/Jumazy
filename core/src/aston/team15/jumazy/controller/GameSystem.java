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
	
	
	private Maze maze;
	private GraphicsManager gMan;

	public GameSystem(SystemManager sysMan) {
		super(sysMan);
		maze = new Maze(35, 20);
		gMan = new GraphicsManager();
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
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && maze.getPlayer().hasRolled() == false) {
			maze.getPlayer().roll(maze.getWeather().getMovementMod());
		}
		
		if(maze.getPlayer().hasRolled())
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
				maze.getPlayer().newMove(direction);
		}
	}

}
