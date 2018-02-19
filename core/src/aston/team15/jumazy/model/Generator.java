package aston.team15.jumazy.model;

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
		gen.genMaze(10,11);
	}

	/**
	 * Creates a maze based on rooms connected to each other
	 * @param roomAmount amount of rooms in maze
	 * @param roomSize the size of each room (currently just a square)
	 * @return the maze
	 */
	public String[][] genMaze(int roomAmount, int roomSize){

		//currently has a hard set size for each room (10*10)
		int roomsAcross = 3;
		for(int i = 1; i <= 10; i++){
			if(roomAmount % i == 0 && i !=roomAmount)
				roomsAcross = i;
		}
		System.out.println(roomsAcross);
		//loop from 1-10, mod roomAmount with it, first to produce 0 is roomsAcross. simples

		//amount of rooms that will be across and up the maze


		int extraRoom = (roomAmount % roomsAcross == 0 ? 0 : 1);

		int roomsDown = roomAmount / roomsAcross + extraRoom;

		//total amount of rooms in maze
//		int roomAmount = roomlength * roomlength; //9

		//create maze with enough space to store all cells of all rooms in a square shape
		maze = new String[roomSize*roomsDown][roomSize*roomsAcross];

		//create roomAmount rooms
		for (int i = 0; i < roomAmount; i++) {
			String[][] room = genRoom(roomSize);

			//set the xoffset to start putting cells into the maze
			// room 0 will have offset 0, room 3 will also have offset 0 in this example (a 30*30 maze)
			int xoffset=(i*roomSize % (roomSize*roomsDown)); //xoffset

			//y offset for placing cells of room into maze
			//when over the limit of the length, move cells down
			int yoffset=(i*roomSize / (roomSize*roomsDown)*roomSize); //xoffset

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
				String current = maze[mazerow][mazecol];
				if(current != null)
					System.out.print(current);
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
					room[i][k] = "o";
				}
			}
		}

		return room;
	}


}
