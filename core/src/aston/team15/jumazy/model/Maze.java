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
	
	private static Room[][] statMaze;
	private Room[][] maze;
	private static Block stopBlock;
	public static int MAZE_ROOMS_ACROSS;
	public static int MAZE_ROOMS_DOWN;
	private ArrayList<Player> players;
	private int currPlayer;
	private int totalPlayers;
	private Generator mazeGenerator;
	public static int ROOM_SIZE = 10;
	private Weather weather;
	
	/**
	 * Creates a new maze
	 * stopBlock is a {@link Block} to stop movement OOB, rather than having player "move" to the same coordinates as the block they are on
	 * player, the player object
	 */
	public Maze(int roomsAcross, int roomsDown, int totalPlayers) {
		mazeGenerator = new Generator();
		maze = mazeGenerator.genMaze(roomsAcross, roomsDown);
		MAZE_ROOMS_ACROSS = maze.length;
		MAZE_ROOMS_DOWN = maze[0].length;

//		statMaze = maze;
		this.totalPlayers = totalPlayers;
		
		Random rnd = new Random();
		if(rnd.nextBoolean())
			weather = new Sun();
		else
			weather = new Rain();
		
		players = new ArrayList<Player>();
		
		if(totalPlayers>0)
			players.add(new Player(new Coordinate(1,1)));
		if(totalPlayers>1)
			players.add(new Player(new Coordinate((roomsAcross-1),(roomsDown-2))));
		if(totalPlayers>2)
			players.add(new Player(new Coordinate( 0, (roomsDown-2))));
		if(totalPlayers>3)
			players.add(new Player(new Coordinate((roomsAcross-1), 0)));
		
		currPlayer=0;
		getCurrPlayer().switchTurn();
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
		if(getCurrPlayer().hasRolled())
			getCurrPlayer().switchRolled();

		currPlayer++;							//increment to next player
		if(currPlayer == totalPlayers)
			currPlayer = 0;
		
		System.out.println(currPlayer);
		getCurrPlayer().switchTurn();			//start next players turn
		
	}
	
	/**
	 * Returns a Block in a set column/row. specified by parameters
	 * @param coord {@Link Coordinate} wanted {@link Block} is on
	 * @return
	 */
	public Block getBlock(Coordinate coord) {
//		Block b = null;
		int roomX = coord.getX() / ROOM_SIZE;
		int roomY = coord.getY() / ROOM_SIZE;
		Room room = maze[roomX][roomY];

		return room.getBlock(coord);
	}

	public Weather getWeather() {
		return weather;
	}
	
	public static int getBlocksAcross() {
		return MAZE_ROOMS_ACROSS * ROOM_SIZE;
	}

	public static int getBlocksDown() {
		return MAZE_ROOMS_DOWN * ROOM_SIZE;
	}
}