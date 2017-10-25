package aston.team15.jumazy;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	
	private BitmapFont font;
	
	/**
	 * Creates a new maze
	 * stopBlock is a {@link Block} to stop movement OOB, rather than having player "move" to the same coordinates as the block they are on
	 * player, the player object
	 */
	public Maze() {
		font = new BitmapFont();
		font.setColor(0, 0, 0, 1);
		
		rnd = new Random();
		stopBlock = new Block(null, null);
		maze = new Block[MAZE_DIMENSION][MAZE_DIMENSION];
		player = new Player(new Coordinate(0,0));
		newGenMaze();
	}
	
	/**
	 * Returns a 2D array of {@link Block} objects.
	 * Checks Coordinate object and adds block at that coordinate to nearBlocks array.
	 * Checks direction player wants to go. checks if that direction will lead them out of bounds, adding the next Block to nearBlocks accordingly.
	 * Yeah, this shouldn't really be in {@link Maze}, but it's easy to implement like this, sooo...
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
	 * Generates a new maze. Loops through each array index, creating a new Block, by using the {@link Block#blockFactory()} method to create a new {@link Block}
	 * Creates the maze in sections of MAZE_DIMENSION/4 (if maze is 16*16 chunks are 4*4)
	 */	
	private void newGenMaze() {
		/**
		 * MAZE STRUCTURE EXAMPLE:
		 * 
		 *  	---------
		 *     3|		|
		 *     2|		|
		 * ^   1|		|
		 * |   0|		|
		 * K	---------
		 * 	I->  0 1 2 3
		 * 
		 * I is the column, K is the row of that column. Top cell of the first column would be (I=0,K=3)
		 */
		
		for(int i = 0; i < MAZE_DIMENSION; i++) {
			for(int k = 0; k < MAZE_DIMENSION; k++) {
				maze[i][k] = Block.blockFactory("cross", new Coordinate(i, k));
			}
		}
		
		int mazeOffsetI = 0;
		int mazeOffsetK = 0;
		
		int column = 0;
		int row = 0;
		int blobk = 0;
		
		int edgeOfChunk = MAZE_DIMENSION/4-1; //edge = very right and very top of each chunk
		
		while(blobk != MAZE_DIMENSION*MAZE_DIMENSION)
		{
			System.out.println("New Chunk:");
			System.out.println("I: "+column+" K: "+row);
			for(column = mazeOffsetI; column < MAZE_DIMENSION/4+mazeOffsetI; column++) {
				for(row = mazeOffsetK; row < MAZE_DIMENSION/4+mazeOffsetK; row++) {
					System.out.println("Blobk "+(blobk+1));
					int type = 6;
					
					if(rnd.nextInt(3) == 0) {
						int gen = rnd.nextInt(9);
						if(gen < 6) {
							type = 6;
						}
						else if(gen < 8) {
							type = 2;
						}
						else
							type = 3;
					}
					else
					{
						type = rnd.nextInt(10);
					}
					
					if(column == 0 && row == 0)
						maze[column][row] = Block.blockFactory("tUp", new Coordinate(column, row));
					else if (column == edgeOfChunk+mazeOffsetI && row == edgeOfChunk-1+mazeOffsetK)
						maze[column][row] = Block.blockFactory("cross", new Coordinate(column, row));
					else if (column == 0+mazeOffsetI && row == edgeOfChunk-1+mazeOffsetK)
						maze[column][row] = Block.blockFactory("horizontal", new Coordinate(column, row));
					else
						maze[column][row] = Block.blockFactory(type, new Coordinate(column, row));
					
					blobk++;
				}
			}
			System.out.println("I: "+column+" K: "+row);
			 if(column != MAZE_DIMENSION) {
				mazeOffsetI += MAZE_DIMENSION/4;
			}
			else
			{
				mazeOffsetK += MAZE_DIMENSION/4;
				mazeOffsetI = 0;
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
		
		if(player.hasRolled() == false)
		{
			font.draw(batch, "Press Space to roll", 100, 100);
		}
		else
		{
			font.draw(batch, "Spaces left: "+player.getRollSpaces(), 100, 100);
		}
		
		return batch;
	}
	
	/**
	 * Any action that happens on the maze will be called through this method
	 * As of now. it is just listening for player movement
	 * Called each frame by {@link JumazyGame#render()}
	 */
	public void actions() {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.hasRolled() == false) {
			player.roll();
		}
		
		if(player.hasRolled())
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
				player.move(direction);
		}
	}
}
