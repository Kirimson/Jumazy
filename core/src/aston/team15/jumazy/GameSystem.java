package aston.team15.jumazy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class GameSystem extends MainSystem{
	
	private BitmapFont font;
	private Maze maze;
	private static Texture moves = new Texture("moves.png");
        private Random rnd;
        private Weather weather; 

	public GameSystem(SystemManager sysMan) {
		super(sysMan);
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
                rnd = new Random();
                if (rnd.nextBoolean())
                {
                    weather = new Sun();
                }
                else
                {
                    weather = new Rain(); 
                }
		maze = new Maze();
	}
	
	/**
	 * Draws the maze to the given {@link SpriteBatch} object
	 * @param batch the {@link SpriteBatch} you want to draw to
	 * @return returns the {@link SpriteBatch} passed, with maze set to draw
	 */
	@Override
	public SpriteBatch draw(SpriteBatch batch) {
		
		int xOffset = (1280-(Maze.MAZE_DIMENSION*32))/2;
		int yOffset = (720-(Maze.MAZE_DIMENSION*32))/2;
		for(int i = 0; i < Maze.MAZE_DIMENSION; i++) {
			for(int k = 0; k < Maze.MAZE_DIMENSION; k++) {
				batch.draw(maze.getBlock(i, k).getTexture(), xOffset+32*i, yOffset+32*k, 16, 16, 32, 32, 1, 1, maze.getBlock(i, k).getOrientation()*-90, 0, 0, 32, 32, false, false);
			}
		}
		
		batch = maze.getPlayer().drawPlayer(batch);
		
		font.draw(batch, "Weather: " + weather.getName(), 10,80);
                
                if(maze.getPlayer().hasRolled() == false)
		{
			font.draw(batch, "Press Space to roll", 10,60);
		}
		else
		{
			for (int i=0;i<maze.getPlayer().getRollSpaces();i++) {
				batch.draw(moves,10+(i*10),20);
			}
		}
		
		return batch;
	}
	
	/**
	 * Any action that happens on the maze will be called through this method
	 * As of now. it is just listening for player movement
	 * Called each frame by {@link JumazyGame#render()}
	 */
	@Override
	public void handleInput() {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && maze.getPlayer().hasRolled() == false) {
			maze.getPlayer().roll(weather.getMovementMod());
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
				maze.getPlayer().move(direction);
		}
	}

}
