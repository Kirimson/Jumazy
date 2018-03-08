package aston.team15.jumazy.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import aston.team15.jumazy.controller.JumazyController;

public class MazeModel {

	// row is y and maze[0].length
	// col is x and maze.length
	// array goes (y,x)/(row,col)

	protected enum Weather {
		RAIN, SUN
	}

	private String[][] maze;
	private ArrayList<PlayerModel> players;
	private int currentPlayerIndex;
	private Weather weather;
	private ArrayList<String[][]> allRoomLayouts;

	public MazeModel(int roomsAcross, int roomsDown, int playerAmount) {
		float weatherDiscriminant = new Random().nextFloat();
		if (weatherDiscriminant <= 0.5) {
			weather = Weather.SUN;
		} else {
			weather = Weather.RAIN;
		}
		
		maze = genMaze(roomsAcross, roomsDown, playerAmount);

		players = new ArrayList<PlayerModel>();

		if (playerAmount == 2) {
			players.add(new PlayerModel(1, 1, "1", this));
			players.add(new PlayerModel(maze.length - 2, maze[0].length - 2, "2", this));
		}

		if (playerAmount == 4) {
			players.add(new PlayerModel(1, 1, "1", this));
			players.add(new PlayerModel(1, maze[0].length - 2, "2", this));
			players.add(new PlayerModel(maze.length - 2, 1, "3", this));
			players.add(new PlayerModel(maze.length - 2, maze[0].length - 2, "4", this));
		}

		currentPlayerIndex = 0;

		if (JumazyController.DEBUG_ON) {
			String initialState = "Maze initialized to a " + playerAmount + " player game.\n";
			if (weather == Weather.SUN) {
				initialState += "It is sunny.";
			} else if (weather == Weather.RAIN) {
				initialState += "It is raining.";
			}
			initialState += "\nPlayer 1 will start.";
			System.out.println(initialState);
			System.out.println(toString());
		}

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
	private String[][] genMaze(int roomsAcross, int roomsDown, int players) {
		String currentLine;
		String currentChar;
		int roomSize=8;
		BufferedReader reader;
		
		String filename = "roomlayouts/RoomLayoutsSize"+roomSize+".txt";
		allRoomLayouts = new ArrayList<String[][]>();
		try {
			reader = new BufferedReader(new FileReader(filename));
			while(reader.ready()) {
				currentLine = reader.readLine();
				if(!currentLine.startsWith("/")) {
					String[][] newLayout = new String[roomSize][roomSize];
					for(int j=0;j<roomSize;j++) {
						for(int i=0;i<roomSize;i++) {
							currentChar = currentLine.substring(i, i+1);
								newLayout[i][j]=currentChar;
						}
						currentLine = reader.readLine();
					}
					allRoomLayouts.add(newLayout);
				}
			}
		} catch (IOException e1) {
			System.out.println("Failed to parse room layout file.");
		}
		// create maze with enough space to store all cells of all rooms in a square
		// shape
		String[][] mazeString = new String[10 * roomsDown][10 * roomsAcross];

		// create roomAmount rooms
		for (int i = 0; i < roomsAcross * roomsDown; i++) {
			String[][] room = genRoom(roomSize+2);

			// set the xoffset to start putting cells into the maze
			// room 0 will have offset 0, room 3 will also have offset 0 in this example (a
			// 30*30 maze)
			int yoffset = (i * 10 % (10 * roomsDown)); // xoffset

			// y offset for placing cells of room into maze
			// when over the limit of the length, move cells down
			int xoffset = (i * 10 / (10 * roomsDown) * 10); // xoffset

			// add cells into maze using offsets
			for (int mazeX = 0; mazeX < room.length; mazeX++) {
				for (int mazeY = 0; mazeY < room[0].length; mazeY++) {
					mazeString[yoffset + mazeY][xoffset + mazeX] = room[mazeX][mazeY];
				}
			}
		}

		createDoors(roomsAcross, roomsDown, mazeString);

		mazeString[2][2] = "V";

		return mazeString;
	}

	private void createDoors(int roomsAcross, int roomsDown, String[][] maze) {

		for (int x = 9; x < (roomsAcross * 10) - 1; x += 10) {
			for (int y = 2; y < (roomsDown * 10) - 1; y += 10) {
				if (y % 9 != 0) {
					float randomFloat = new Random().nextFloat();
					if (randomFloat < 0.65)
						makeCommonDoors(y, x, maze);

					if (randomFloat > 0.35) {
						maze[y + 4][x] = "O";
						maze[y + 5][x] = "O";
						maze[y + 4][x + 1] = "O";
						maze[y + 5][x + 1] = "O";
					}
				} else
					y -= 2;
			}
		}

		for (int x = 2; x < (roomsAcross * 10) - 1; x += 10) {
			for (int y = 9; y < (roomsDown * 10) - 1; y += 10) {
				if (x % 9 != 0) {
					float randomFloat = new Random().nextFloat();
					if (randomFloat < 0.65)
						makeCommonDoors(y, x, maze);

					if (randomFloat > 0.35) {
						maze[y][x + 4] = "O";
						maze[y + 1][x + 4] = "O";
						maze[y][x + 5] = "O";
						maze[y + 1][x + 5] = "O";
					}
				} else
					x -= 2;
			}
		}
	}

	private void makeCommonDoors(int y, int x, String[][] maze) {
		maze[y][x] = "O";
		maze[y + 1][x] = "O";
		maze[y][x + 1] = "O";
		maze[y + 1][x + 1] = "O";
	}

	/**
	 * Creates a new room, surrounded will walls, and filled with floors
	 * 
	 * @param size
	 *            width/height of room
	 * @return 2D array of String, with wall and path representations
	 */
	private String[][] genRoom(int roomSize) {
		
		String[][] room = new String[roomSize][roomSize];
		Random rng = new Random();
		int randLayoutIndex = rng.nextInt(allRoomLayouts.size());
		
		for (int j = 0; j < roomSize; j++) {
			for (int i = 0; i < roomSize; i++) {
				if (i == 0 || i == roomSize - 1 || j == 0 || j == roomSize - 1) {
					room[i][j] = "#";
				} else {
					room[i][j] = allRoomLayouts.get(randLayoutIndex)[i-1][j-1];;
				}
			}
		}
		return room;
	}

	public String[][] getMaze() {
		return maze;
	}

	public String toString() {
		String str = "";

		for (int mazerow = maze.length - 1; mazerow >= 0; mazerow--) {
			for (int mazecol = 0; mazecol < maze[0].length; mazecol++) {
				String current = maze[mazerow][mazecol];
				if (current != null) {
					str += current;
				}
			}
			str += "\n";
		}

		return str;
	}

	public int passTurnToNextPlayer() {
		getCurrentPlayer().setCanRoll(true);
		currentPlayerIndex = (currentPlayerIndex + 1) % (players.size());
		if (JumazyController.DEBUG_ON)
			System.out.println("It is now Player " + (currentPlayerIndex + 1) + "'s turn.");

		return currentPlayerIndex;
	}

	public int rollForPlayer() {
		return getCurrentPlayer().rollDie(weather);
	}

	public PlayerModel getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public boolean moveCurrentPlayerModel(int direction) {
		return getCurrentPlayer().move(direction);
	}

	public void setCoordinateString(int row, int col, String symbol) {
		maze[row][col] = symbol;
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
