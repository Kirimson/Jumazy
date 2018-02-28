package aston.team15.jumazy.model;

public class Maze {

	private String[][] maze;

	public Maze(int roomsAcross, int roomsDown, int players) {
		maze = genMaze(roomsAcross, roomsDown, players);

		PlayerModel player1 = new PlayerModel(1, 1, "1", this);
		player1.move(MoveDirections.DOWN);

		maze[maze.length - 2][maze[0].length - 2] = "2";

		if (players == 4) {
			maze[maze.length - 2][1] = "3";
			maze[1][maze[0].length - 2] = "4";
		}
		;
	}

	/**
	 * Creates a maze based on rooms connected to each other
	 * 
	 * @param roomsAcross
	 *            amount of rooms in maze
	 * @param roomsDown
	 *            the size of each room (currently just a square)
	 * @return the maze
	 */
	public String[][] genMaze(int roomsAcross, int roomsDown, int players) {

		// create maze with enough space to store all cells of all rooms in a square
		// shape
		String[][] mazeString = new String[10 * roomsDown][10 * roomsAcross];

		// create roomAmount rooms
		for (int i = 0; i < roomsAcross * roomsDown; i++) {
			String[][] room = genRoom(10);

			// set the xoffset to start putting cells into the maze
			// room 0 will have offset 0, room 3 will also have offset 0 in this example (a
			// 30*30 maze)
			int xoffset = (i * 10 % (10 * roomsDown)); // xoffset

			// y offset for placing cells of room into maze
			// when over the limit of the length, move cells down
			int yoffset = (i * 10 / (10 * roomsDown) * 10); // xoffset

			// add cells into maze using offsets
			for (int mazeY = 0; mazeY < room.length; mazeY++) {
				for (int mazeX = 0; mazeX < room[0].length; mazeX++) {
					mazeString[xoffset + mazeX][yoffset + mazeY] = room[mazeY][mazeX];
				}
			}
		}

		createDoors(roomsAcross, roomsDown, mazeString);
		return mazeString;
	}

	private void createDoors(int roomsAcross, int roomsDown, String[][] maze) {
		for (int x = 9; x < (roomsAcross * 10) - 1; x += 10) {
			for (int y = 1; y < (roomsDown * 10) - 1; y += 4) {
				if (y % 9 != 0) {
//					float randomFloat = new Random().nextFloat();

					// if (randomFloat < 0.4) {
					maze[y][x] = "O";
					maze[y + 1][x] = "O";
					maze[y][x + 1] = "O";
					maze[y + 1][x + 1] = "O";
					// }
				} else {
					y -= 2;
				}
			}
		}
	}

	/**
	 * Creates a new room, surrounded will walls, and filled with floors
	 * 
	 * @param size
	 *            width/height of room
	 * @return
	 */
	private String[][] genRoom(int size) {

		String[][] room = new String[size][size];

		for (int i = 0; i < size; i++) {
			for (int k = 0; k < size; k++) {
				if (i == 0 || i == size - 1 || k == 0 || k == size - 1) {
					room[i][k] = "*";
				} else {
					room[i][k] = "O";
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

	public void setCoordinateString(int row, int col, String symbol) {
		maze[row][col] = symbol;
	}

	public String getCoordinateString(int row, int col) {
		return maze[row][col];
	}
}
