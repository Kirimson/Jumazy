package aston.team15.jumazy.model;

import java.util.ArrayList;
import java.util.Random;

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
	private static Block stopBlock;
	public static int MAZE_DIMENSIONX;
	public static int MAZE_DIMENSIONY;
	private ArrayList<Player> players;
	private int currPlayer;
	private int totalPlayers;
	private Generator mazeGenerator;
	
	private Weather weather;
	
	/**
	 * Creates a new maze
	 * stopBlock is a {@link Block} to stop movement OOB, rather than having player "move" to the same coordinates as the block they are on
	 * player, the player object
	 */
	public Maze(int dimensionx, int dimensiony, int totalPlayers) {
		MAZE_DIMENSIONX = dimensionx;
		MAZE_DIMENSIONY = dimensiony;
		mazeGenerator = new Generator();
		maze = mazeGenerator.superNewGenMaze(dimensionx, dimensiony);
		statMaze = maze;
		this.totalPlayers = totalPlayers;
		
		Random rnd = new Random();
		if(rnd.nextBoolean())
			weather = new Sun();
		else
			weather = new Rain();
		
		players = new ArrayList<Player>();
		if(totalPlayers>0)
		players.add(new Player(new Coordinate(0,0)));
		if(totalPlayers>1)
		players.add(new Player(new Coordinate( 0, (dimensiony-2))));
		if(totalPlayers>2)
		players.add(new Player(new Coordinate((dimensionx-1),(dimensiony-2))));
		if(totalPlayers>3)
		players.add(new Player(new Coordinate((dimensionx-1), 0)));
		
		currPlayer=0;
		getCurrPlayer().switchTurn();
	}
	
	public static Block[][] getMaze(){
		return statMaze;
	}
	
	public ArrayList<Player> getPlayersList() {
		return players;
	}
	
	public Player getCurrPlayer() {
		return getPlayersList().get(currPlayer);
	}
	
	public int getCurrPlayerVal() {
		return currPlayer;
	}
	
	public int getTotalPlayers() {
		return totalPlayers;
	}

	
	public void nextPlayer() {
		getCurrPlayer().switchTurn();			//end current players turn
		getCurrPlayer().switchRolled();
		currPlayer++;							//increment to next player
		currPlayer = currPlayer%totalPlayers;
		
		getCurrPlayer().switchTurn();			//start next players turn
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
		
		if(coord.getX()+xDir >= 0 && coord.getY()+yDir >= 0 && coord.getX()+xDir < MAZE_DIMENSIONX && coord.getY()+yDir < MAZE_DIMENSIONY ) {
			
			nearBlocks[0] = statMaze[coord.getX()][coord.getY()];
			nearBlocks[1] = statMaze[coord.getX()+xDir][coord.getY()+yDir];
		}
		else
		{
			System.out.println("player wanted to go OOB");
			return null;
		}
		
		return nearBlocks;
	}

	
	/**
	 * Returns a Block in a set column/row. specified by parameters
	 * @param row row wanted {@link Block} is on
	 * @param column column wanted {@link Block} is on
	 * @return
	 */
	public Block getBlock(int row, int column) {
		Block b = null;
		if(row >= 0 && row <= MAZE_DIMENSIONX && column >= 0 && column <= MAZE_DIMENSIONY)
			b = maze[row][column];
		
		return b;
	}

	public Weather getWeather() {
		return weather;
	}
	
	public static Block getBlock(Coordinate coord) {
		return statMaze[coord.getX()][coord.getY()];
	}
	
	
}