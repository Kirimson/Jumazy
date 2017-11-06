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
	static int MAZE_DIMENSION =20;
	Player player;
	
	
	
	/**
	 * Creates a new maze
	 * stopBlock is a {@link Block} to stop movement OOB, rather than having player "move" to the same coordinates as the block they are on
	 * player, the player object
	 */
	public Maze() {
		
		rnd = new Random();
		stopBlock = new Block(null, null, 0);
		maze = new Block[MAZE_DIMENSION][MAZE_DIMENSION];
		player = new Player(new Coordinate(0,0));
		newGenMaze();
	}
	
	public Player getPlayer() {
		return player;
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
					System.out.println("Block "+(blobk+1));

					String type = "";
					
					int rand=rnd.nextInt(8);
					int orientation=rnd.nextInt(3);
					if(rand>=0 && rand<=3)
						type = "cross";
					else if(rand==4)
						type = "straight";
					else if(rand==5)
						type = "corner";
					else
						type = "tJunction";
					
					if (column == edgeOfChunk+mazeOffsetI && row == edgeOfChunk-1+mazeOffsetK)
						maze[column][row] = Block.blockFactory("cross", new Coordinate(column, row), orientation);
					else if (column == 0+mazeOffsetI && row == edgeOfChunk-1+mazeOffsetK)
						maze[column][row] = Block.blockFactory("horizontal", new Coordinate(column, row), orientation);
					else
						maze[column][row] = Block.blockFactory(type, new Coordinate(column, row), orientation);
					
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
	
	
}
