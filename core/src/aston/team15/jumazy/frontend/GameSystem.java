package aston.team15.jumazy.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aston.team15.jumazy.backend.MainSystem;
import aston.team15.jumazy.backend.Maze;
import aston.team15.jumazy.backend.SystemManager;
import aston.team15.jumazy.backend.GraphicsManager;
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
		maze = new Maze(20);
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
			maze.getPlayer().roll();
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
