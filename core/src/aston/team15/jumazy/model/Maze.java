package aston.team15.jumazy.model;

import java.io.IOException;
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
	private static int mazeRoomsAcross;
	private static int mazeRoomsDown;
	private static int roomSize; 
	private ArrayList<Player> players;
	private int currPlayer;
	private int totalPlayers;
	private Generator mazeGenerator;
	private Weather weather;
	
	/**
	 * Creates a new maze
	 * stopBlock is a {@link Block} to stop movement OOB, rather than having player "move" to the same coordinates as the block they are on
	 * player, the player object
	 * @throws IOException 
	 */
	public Maze(int roomsAcross, int roomsDown, int totalPlayers, int roomSize) {
		mazeRoomsAcross = roomsAcross;
		mazeRoomsDown = roomsDown;
		this.totalPlayers = totalPlayers;
		this.roomSize = roomSize;
		
		mazeGenerator = new Generator();
		maze = mazeGenerator.genMaze(mazeRoomsAcross, mazeRoomsDown, roomSize);
		
		Random rnd = new Random();
		if(rnd.nextBoolean())
			weather = new Sun();
		else
			weather = new Rain();
		
		players = new ArrayList<Player>();
		
		if(totalPlayers>0)
			players.add(new Player(new Coordinate(1,1)));
		if(totalPlayers>1)
			players.add(new Player(new Coordinate((Maze.getBlocksAcross()-2),(Maze.getBlocksDown()-2))));
		if(totalPlayers>2)
			players.add(new Player(new Coordinate(1,(Maze.getBlocksDown()-2))));
		if(totalPlayers>3)
			players.add(new Player(new Coordinate((Maze.getBlocksAcross()-2),1)));
		
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
		int roomX = coord.getX() / roomSize;
		int roomY = coord.getY() / roomSize;

		int blockX = coord.getX() %roomSize;
		int blockY = coord.getY() %roomSize;

		Coordinate blockCoord = new Coordinate(blockX, blockY);
		return maze[roomX][roomY].getBlock(blockCoord);
	}

	public Weather getWeather() {
		return weather;
	}
	
	public static int getBlocksAcross() {
		return mazeRoomsAcross * roomSize;
	}

	public static int getBlocksDown() {
		return mazeRoomsDown * roomSize;
	}

	public static int getMazeRoomsAcross() {
		return mazeRoomsAcross;
	}

	public static void setMazeRoomsAcross(int mazeRoomsAcross) {
		Maze.mazeRoomsAcross = mazeRoomsAcross;
	}

	public static int getMazeRoomsDown() {
		return mazeRoomsDown;
	}

	public static void setMazeRoomsDown(int mazeRoomsDown) {
		Maze.mazeRoomsDown = mazeRoomsDown;
	}

	public static int getRoomSize() {
		return roomSize;
	}

	public static void setRoomSize(int roomSize) {
		Maze.roomSize = roomSize;
	}
}