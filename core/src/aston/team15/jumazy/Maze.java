package aston.team15.jumazy;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Representation of a maze in the game. Made of of {@link Block} objects
 * Has a set size, denoted in MAZE_DIMENSION
 * Contains players that will be inside the maze
 * @author kieran
 *
 */
public class Maze {
	
	private static Block[][] statMaze;
	private Block[][] maze;
	private Random rnd;
	private static Block stopBlock;
	static int MAZE_DIMENSION =16;
	Player player;
	
	/**
	 * Creates a new maze
	 * stopBlock is a {@link Block} to stop movement OOB, rather than having player "move" to the same coordinates as the block they are on
	 * player, the player object
	 */
	public Maze() {
		stopBlock = new Block(null, null);
		maze = new Block[MAZE_DIMENSION][MAZE_DIMENSION];
		player = new Player(new Coordinate(0,0));
		genMaze();
	}
	
	/**
	 * Returns a 2D array of {@link Block} objects.
	 * Checks Coordinate object and adds block at that coordinate to nearBlocks array.
	 * Checks direction player wants to go. checks if that direction will lead them out of bounds, adding the next Block to nearBlocks accordingly
	 * 
	 * @param coord a {@link Coordinate} object giving the base location the method should start at
	 * @param direction String denoting direction the method needs to check
	 * @return 2D array of Block objects, listing the current block and the block next to it, according to direction
	 */
	public static Block[] getSurroundingBlocks(Coordinate coord, String direction) {
		int xDir = 0;
		int yDir = 0;
		
		switch(direction) {
			case "up": yDir = 1;break;
			case "down": yDir = -1;break;
			case "left": xDir = -1;break;
			case "right": xDir = 1;break;
		}
		
		Block[] nearBlocks = new Block[2];
		nearBlocks[0] = statMaze[coord.getX()][coord.getY()];
		nearBlocks[1] = stopBlock;
		
		System.out.println("block player is on: "+statMaze[coord.getX()][coord.getY()].toString());
		if(coord.getX()+xDir >= 0 && coord.getY()+yDir >= 0 && coord.getX()+xDir < MAZE_DIMENSION && coord.getY()+yDir < MAZE_DIMENSION ) {
			
			System.out.println("block player wants to move to: "+statMaze[coord.getX()+xDir][coord.getY()+yDir].toString());
			nearBlocks[0] = statMaze[coord.getX()][coord.getY()];
			nearBlocks[1] = statMaze[coord.getX()+xDir][coord.getY()+yDir];
		}
		else
		{
			System.out.println("player wanted to go OOB");
		}
		
		return nearBlocks;
		
	}
	
	/**
	 * Generates a new maze. Loops through each array index, creating a new Block, by using the {@link Block#blockFactory()} method
	 */
	private void genMaze() {
		
		for(int i = 0; i < MAZE_DIMENSION; i++) {
			for(int k = 0; k < MAZE_DIMENSION; k++) {
				if(i == 0 && k == 0)
					maze[i][k] = Block.blockFactory(5, new Coordinate(i, k));
				else
					maze[i][k] = Block.blockFactory(new Coordinate(i, k));
			}
		}

		statMaze = maze;
	}
	
	/**
	 * Returns a Block in a set column/row. specified by parameters
	 * @param row row wanted {@link Block} is on
	 * @param column column wanted {@link Block} is on
	 * @return
	 */
	public Block getBlock(int row, int column) {
		Block b = null;
		if(row >= 0 && row <= MAZE_DIMENSION && column >= 0 && column <= MAZE_DIMENSION)
			b = maze[row][column];
		
		return b;
	}
	
	/**
	 * Draws the maze to the given {@link SpriteBatch} object
	 * @param batch the {@link SpriteBatch} you want to draw to
	 * @return returns the {@link SpriteBatch} passed, with maze set to draw
	 */
	public SpriteBatch drawMaze(SpriteBatch batch) {
		
		int xOffset = (Gdx.graphics.getWidth()/2)-(MAZE_DIMENSION*32/2);
		int yOffset = Gdx.graphics.getHeight()/2-(MAZE_DIMENSION*32/2);
				
		for(int i = 0; i < MAZE_DIMENSION; i++) {
			for(int k = 0; k < MAZE_DIMENSION; k++) {
				batch.draw(maze[i][k].getTexture(),xOffset+32*i,yOffset+32*k);
			}
		}
		batch = player.drawPlayer(batch);
		
		return batch;
	}
	
	/**
	 * Any action that happens on the maze will be called through this method
	 * As of now. it is just listening for player movement
	 * Called each frame by {@link JumazyGame#render()}
	 */
	public void actions() {
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
			player.move(direction);
	}
}
