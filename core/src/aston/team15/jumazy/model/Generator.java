package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class Generator {
	
	private Random rnd;
	private String[][] maze;
	private int xMiddle;
	private int yMiddle;

	Generator() {
		rnd = new Random();
	}

	public static void main(String[] args) {
		Generator gen = new Generator();
		gen.genMaze(4,2);
	}

	/**
	 * Creates a maze based on rooms connected to each other
	 * @param roomsAcross amount of rooms in maze
	 * @param roomsDown the size of each room (currently just a square)
	 * @return the maze
	 */
	public String[][] genMaze(int roomsAcross, int roomsDown){

		//create maze with enough space to store all cells of all rooms in a square shape
		maze = new String[10*roomsDown][10*roomsAcross];

		//create roomAmount rooms
		for (int i = 0; i < roomsAcross*roomsDown; i++) {
			String[][] room = genRoom(10);

			//set the xoffset to start putting cells into the maze
			// room 0 will have offset 0, room 3 will also have offset 0 in this example (a 30*30 maze)
			int xoffset=(i*10 % (10*roomsDown)); //xoffset

			//y offset for placing cells of room into maze
			//when over the limit of the length, move cells down
			int yoffset=(i*10 / (10*roomsDown)*10); //xoffset

			//add cells into maze using offsets
			for(int mazerow = 0; mazerow < room.length; mazerow++){
				for (int mazecol = 0; mazecol < room[0].length; mazecol++){
					maze[xoffset+mazecol][yoffset+mazerow] = room[mazecol][mazerow];
				}
			}
		}

		//prints out rooms
//		for (Block[] mazeRow : maze) {
//			for (Block current : mazeRow) {
//				if (current != null)
////					current.updateCoords(maz);
//					System.out.print(current.toString());
//			}
//			System.out.println();
//		}

		for(int mazerow = 0; mazerow < maze.length; mazerow++) {
			for (int mazecol = 0; mazecol < maze[0].length; mazecol++) {
				String current = maze[mazerow][mazecol];
//				current.updateCoords(mazerow, mazecol);
				if (current != null){
					System.out.print(current.toString());
				}
			}
			System.out.println();
		}

		return maze;
	}

	/**
	 * Creates a new room, surrounded will walls, and filled with floors
	 * @param size width/height of room
	 * @return
	 */
	private String[][] genRoom(int size) {

		String[][] room = new String[size][size];

		for(int i = 0; i < size; i++){
			for (int k = 0; k < size; k++){
				if(i == 0 || i == size-1 || k == 0 || k == size-1){
					room[i][k] = "*";
				}
				else{
					room[i][k] = "O";
				}
			}
		}

		return room;
	}


}
