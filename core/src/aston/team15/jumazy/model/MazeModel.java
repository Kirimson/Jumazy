package aston.team15.jumazy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import aston.team15.jumazy.controller.JumazyController;

public class MazeModel {
	// row is y and maze[0].length
	// col is x and maze.length
	// array goes (y,x)/(row,col)

	public enum Weather {
		RAIN("Careful! It's raining, the ground will be slippery."), 
		SUN("The weather's nice, it's sunny."), 
		SNOW("Hope you dressed warm, the snows coming down hard!");
		
		private final String desc;
		
		private Weather(String desc) {
			this.desc = desc;
		}
		
		public String toString() {
			return desc;
		}
	}

	private String[][] maze;
	private ArrayList<PlayerModel> players;
	private int currentPlayerIndex;
	private Weather weather;
	private ArrayList<String[][]> allRoomLayouts;
	private boolean makeVictory = true;

	public MazeModel(int roomsAcross, int roomsDown, int playerAmount, ArrayList<PlayerModel.CharacterName> playerOrder) {
		float weatherDiscriminant = new Random().nextFloat();
		if (weatherDiscriminant <= 0.3) {
			weather = Weather.SUN;
		} else if (weatherDiscriminant <= 0.6){
			weather = Weather.RAIN;
		} else {
			weather = Weather.SNOW;
		}
		
		maze = genMaze(roomsAcross, roomsDown);

		players = new ArrayList<PlayerModel>();

		if (playerAmount == 2) {
			players.add(new PlayerModel(1, 1, playerOrder.get(0).getValue(), this, playerOrder.get(0)));
			players.add(new PlayerModel(maze.length - 2, maze[0].length - 2, playerOrder.get(1).getValue(), this,
					playerOrder.get(1)));
		}

		if (playerAmount == 4) {
			players.add(new PlayerModel(1, 1, playerOrder.get(0).getValue(), this, playerOrder.get(0)));
			players.add(new PlayerModel(1, maze[0].length - 2, playerOrder.get(1).getValue(), this, playerOrder.get(1)));
			players.add(new PlayerModel(maze.length - 2, 1, playerOrder.get(2).getValue(), this, playerOrder.get(2)));
			players.add(new PlayerModel(maze.length - 2, maze[0].length - 2, playerOrder.get(3).getValue(), this,
					playerOrder.get(3)));
		}

		currentPlayerIndex = 0;

		if (JumazyController.DEBUG_ON) {
			String initialState = "Maze initialized to a " + playerAmount + " player game.\n";
			initialState += weather.toString();
			initialState += "\nPlayer 1 will start.";
			System.out.println(initialState);
			System.out.println(toString());
		}

	}

	public Weather getWeather() {
		return weather;
	}

	/**
	 * Creates a maze based on rooms connected to each other
	 * 
	 * @param roomsAcross
	 *            amount of rooms across
	 * @param roomsDown
	 *            amount of rooms down
	 * @return the maze
	 */
	private String[][] genMaze(int roomsAcross, int roomsDown) {
	    String currentLine;
		String currentChar;
		int roomSize=8;
		int roomBorder = 2;

		//creates a list of room layouts from provided file. ArrayList of String[][]
		String filename = "roomlayouts/RoomLayoutsSize"+roomSize+".txt";

		FileHandle file = Gdx.files.internal("roomlayouts/RoomLayoutsSize"+roomSize+".txt");

        String[] lines = file.readString().split("\r\n|\r|\n");

        allRoomLayouts = new ArrayList<String[][]>();

        for(int currentLineIndex = 0; currentLineIndex < lines.length; currentLineIndex++){
            currentLine = lines[currentLineIndex];
            if(!currentLine.startsWith("/")){
                String[][] newLayout = new String[roomSize][roomSize];
                for(int j = 0; j < roomSize; j++) {
                    for(int i = 0; i < roomSize; i++) {
                        currentChar = currentLine.substring(i, i+1);
                        newLayout[i][(roomSize-1)-j]=currentChar;
                    }
                    currentLineIndex = currentLineIndex+1;
                    if(currentLineIndex < lines.length)
                        currentLine = lines[currentLineIndex];
                }
                allRoomLayouts.add(newLayout);
            }
        }

		// create maze with enough space to store all cells of all rooms in a square
		// shape
		String[][] mazeString = new String[10 * roomsDown][10 * roomsAcross];

		ArrayList<String[][]> rooms = new ArrayList<String[][]>();
		for (int i = 0; i < roomsAcross * roomsDown; i++) {
			rooms.add(genRoom(roomSize + roomBorder));
		}

		//ensure that there is definitely a victory room
		if(makeVictory) {
			do {
				rooms.remove(0);
				rooms.add(genRoom(roomSize + roomBorder));
			} while (makeVictory);
		}

		Collections.shuffle(rooms);

		// create roomAmount rooms
		for (int i = 0; i < roomsAcross * roomsDown; i++) {
			// set the xoffset to start putting cells into the maze
			// room 0 will have offset 0, room 3 will also have offset 0 in this example (a
			// 30*30 maze)
			int yoffset = (i * 10 % (10 * roomsDown)); // xoffset

			// y offset for placing cells of room into maze
			// when over the limit of the length, move cells down
			int xoffset = (i * 10 / (10 * roomsDown) * 10); // xoffset

			// add cells into maze using offsets
			for (int mazeX = 0; mazeX < rooms.get(i).length; mazeX++) {
				for (int mazeY = 0; mazeY < rooms.get(i)[0].length; mazeY++) {
					mazeString[yoffset + mazeY][xoffset + mazeX] = rooms.get(i)[mazeX][mazeY];
				}
			}
		}

		createDoors(roomsAcross, roomsDown, mazeString);

		return mazeString;
	}

	private void createDoors(int roomsAcross, int roomsDown, String[][] maze) {

		float lockedDoorProbability = 0.5f;
		ArrayList<Boolean> lockedDoors = new ArrayList<Boolean>();

		//make doors down the maze columns
		for (int x = 9; x < (roomsAcross * 10) - 1; x += 10) {
			boolean locked = false;
			int count = 1;
			for (int y = 2; y < (roomsDown * 10) - 1; y += 10) {
				if (y % 9 != 0) {

					String symbol = (new Random().nextFloat() > lockedDoorProbability ? "O" : "D");


					if(symbol.equals("D") && !locked)
						locked = true;
					else if(locked)
						symbol = "O";

					float randomFloat = new Random().nextFloat();
					if (randomFloat < 0.65) {
						maze[y][x] = symbol;
						maze[y + 1][x] = symbol;
						maze[y][x + 1] = "O";
						maze[y + 1][x + 1] = "O";
					}

					//make doors at offset area, with more added to column
					if (randomFloat > 0.35) {
						maze[y + 4][x] = symbol;
						maze[y + 5][x] = symbol;
						maze[y + 4][x + 1] = "O";
						maze[y + 5][x + 1] = "O";
					}

					System.out.println(locked);
					lockedDoors.add(locked);
				} else
					y -= 2;
				count++;
			}
		}
		lockedDoors.add(true);

		int currentCol = 0;
		int currentRow = 0;
		//make doors across the maze rows
		for (int x = 2; x < (roomsAcross * 10) - 1; x += 10) {
			for (int y = 9; y < (roomsDown * 10) - 1; y += 10) {
				if (x % 9 != 0) {

					String symbol = "O";

					if(!lockedDoors.get((currentCol * 4)  + ((currentRow *4) % 3)))
						symbol = (new Random().nextFloat() < lockedDoorProbability ? "D" : "O");

					float randomFloat = new Random().nextFloat();
					if (randomFloat < 0.65) {
						maze[y][x] = symbol;
						maze[y + 1][x] = "O";
						maze[y][x + 1] = symbol;
						maze[y + 1][x + 1] = "O";
					}

					//make doors at offset area, with more added to row
					if (randomFloat > 0.35) {
						maze[y][x + 4] = symbol;
						maze[y][x + 5] = symbol;
						maze[y + 1][x + 4] = "O";
						maze[y + 1][x + 5] = "O";
					}

					currentCol++;
				} else
					x -= 2;
			}
			currentCol = 0;
			currentRow++;
		}
	}

	/**
	 * Creates a new room, surrounded will walls, and filled with floors
	 * 
	 * @param roomSize
	 *            width/height of room
	 * @return 2D array of String, with wall and path representations
	 */
	private String[][] genRoom(int roomSize) {

		String[][] room = new String[roomSize][roomSize];
		String[][] layout;

		//make sure no more than one victory room is made
		do{
            Random rng = new Random();
            int randLayoutIndex = rng.nextInt(allRoomLayouts.size());
            layout = allRoomLayouts.get(randLayoutIndex);
        }while (!validRoom(layout));

		for (int j = 0; j < roomSize; j++) {
			for (int i = 0; i < roomSize; i++) {
				if (i == 0 || i == roomSize - 1 || j == 0 || j == roomSize - 1) {
					room[j][i] = "#";
				} else {
					room[i][j] = layout[i-1][j-1];
					if(layout[i-1][j-1].equals("V"))
                        makeVictory = false;
				}
			}
		}

		return room;
	}

	/**
     * Checks if this room should be created, EG there is already one victory room, return false
     * @return true if room should be made
     */
	private boolean validRoom(String[][] layout){
	    for(String[] row : layout){
	        for(String cell : row){
	            if(!makeVictory && cell.equals("V")){
                    return false;
                }
            }
        }
	    return true;
    }

	public String[][] getMaze() {
		return maze;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();

		for (int mazerow = maze.length - 1; mazerow >= 0; mazerow--) {
			for (int mazecol = 0; mazecol < maze[0].length; mazecol++) {
				String current = maze[mazerow][mazecol];
				if (current != null) {
					str.append(current);
				}
			}
			str.append("\n");
		}

		return str.toString();
	}

	public int passTurnToNextPlayer() {
		getCurrentPlayer().setCanRoll(true);
		currentPlayerIndex = (currentPlayerIndex + 1) % (players.size());
		if (JumazyController.DEBUG_ON) {
			System.out.println("It is now Player " + (currentPlayerIndex + 1) + "'s turn.");
			System.out
					.println("Player " + (currentPlayerIndex + 1) + " inventory: " + getCurrentPlayer().getInventory());

			String statsString = "";
			for (int i : getCurrentPlayer().getStats().values()) {
				statsString += i + ", ";
			}
			System.out.println("Player " + (currentPlayerIndex + 1) + " stats: " + statsString);
		}

		return currentPlayerIndex;
	}

	public int rollForPlayer() {
		return getCurrentPlayer().rollDie(weather);
	}

	public PlayerModel getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public int moveCurrentPlayerModel(int inputDirection) {
		return getCurrentPlayer().move(inputDirection);
	}

	public void setCoordinateString(int row, int col, String symbol) {
		maze[row][col] = symbol;
	}

	/**
	 * updates a doors pair to be set to unlocked door "d". The door the player on does not need to be updated, as the
	 * players "currentPositionSymbol" field will replace the players position to "d" when moved off of.
	 * @param row row of original door half
	 * @param col column of original door half
	 */
	public void unlockDoor(int row, int col) {

		if(maze[row][col-1].equals("D")) {
			maze[row][col - 1] = "d";
			return;
		}
		if(maze[row][col+1].equals("D")) {
			maze[row][col + 1] = "d";
		}

		if(maze[row-1][col].equals("D")) {
			maze[row - 1][col] = "d";
			return;
		}
		if(maze[row+1][col].equals("D")) {
			maze[row + 1][col] = "d";
			return;
		}
	}

	/**
	 * Finds a doors position as well as it's other halfs position, given a player that has stepped on a door
	 * @param player PlayerModel that has stepped on a door
	 * @return
	 */
	public int[] getDoorPositions(PlayerModel player){
		int row = player.getPosition()[0];
		int col = player.getPosition()[1];

		int positions[] = {row, col, 0, 0};

		if(maze[row-1][col].equals("d")){
			positions[2]=row-1;
			positions[3]=col;
		}
		if(maze[row+1][col].equals("d")){
			positions[2]=row+1;
			positions[3]=col;
		}

		if(maze[row][col-1].equals("d")){
			positions[2]=row;
			positions[3]=col-1;
		}
		if(maze[row][col+1].equals("d")){
			positions[2]=row;
			positions[3]=col+1;
		}
		return positions;
	}

	public int[] removeMonster(int[] position) {
		int row = position[0];
		int col = position[1];

		ArrayList<String> monsters = new ArrayList<String>();
		monsters.add("E");
		monsters.add("X");

		int[] monsterPos = new int[2];

		if(monsters.contains(maze[row-1][col])) {
			monsterPos[0] = row-1;
			monsterPos[1] = col;
		}
		if(monsters.contains(maze[row+1][col])) {
			monsterPos[0] = row+1;
			monsterPos[1] = col;
		}
		if(monsters.contains(maze[row][col-1])) {
			monsterPos[0] = row;
			monsterPos[1] = col-1;
		}
		if(monsters.contains(maze[row][col+1])) {
			monsterPos[0] = row;
			monsterPos[1] = col+1;
		}
		setCoordinateString(monsterPos[0], monsterPos[1], "O");

		System.out.println(toString());

		return monsterPos;
	}

	public String getCoordinateString(int row, int col) {
		return maze[row][col];
	}

	public PlayerModel getPlayer(int player) {
		return players.get(player - 1);
	}

	public ArrayList<String[][]> getAllRoomLayouts() {
		return allRoomLayouts;
	}
}
