package aston.team15.jumazy.model;

import java.util.ArrayList;
import java.util.Collections;
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
		gen.genMaze(30,30);
	}

	/**
	 * Creates a maze based on rooms connected to each other
	 * @param dimensionx x dimension of maze
	 * @param dimensiony y dimension of maze
	 * @return the maze
	 */
	public String[][] genMaze(int dimensionx, int dimensiony){

		//currently has a hard set size for each room (10*10)
		int roomsize = 10;

		//amount of rooms that will be across and up the maze
		int roomlength = dimensionx/roomsize; //3

		//total amount of rooms in maze
		int roomAmount = roomlength * roomlength; //9

		//create maze with enough space to store all cells of all rooms in a square shape
		maze = new String[roomsize*roomlength][roomsize*roomlength];

		//create roomAmount rooms
		for (int i = 0; i < roomAmount; i++) {
			String[][] room = genRoom(roomsize);

			//set the xoffset to start putting cells into the maze
			// room 0 will have offset 0, room 3 will also have offset 0 in this example (a 30*30 maze)
			int xoffset=(i*roomsize % (roomsize*roomlength)); //xoffset

			//y offset for placing cells of room into maze
			//when over the limit of the length, move cells down
			int yoffset=(i*roomsize / (roomsize*roomlength)*roomsize); //xoffset

			//add cells into maze using offsets
			for(int mazerow = 0; mazerow < room.length; mazerow++){
				for (int mazecol = 0; mazecol < room[0].length; mazecol++){
					maze[xoffset+mazecol][yoffset+mazerow] = room[mazecol][mazerow];
				}
			}

		}

		//prints out rooms
		for(int mazerow = 0; mazerow < maze.length; mazerow++){
			for (int mazecol = 0; mazecol < maze[0].length; mazecol++){
				System.out.print(maze[mazerow][mazecol]);
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

		for(int i = 0; i <= 9; i++){
			for (int k = 0; k <= 9; k++){
				if(i == 0 || i == 9 || k == 0 || k == 9){
					room[i][k] = "*";
				}
				else{
					room[i][k] = "o";
				}
			}
		}

		return room;
	}


}
