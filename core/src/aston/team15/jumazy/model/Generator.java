package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class Generator {
	
	private Random rnd;
	private Block[][] maze;
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
	 *
	 * @param roomsAcross
	 * @param roomsDown
	 * @return
	 */
	public Room[][] genMaze(int roomsAcross, int roomsDown){

		Room[][] maze = new Room[roomsAcross][roomsDown];

		for(int roomX = 0; roomX < roomsAcross; roomX++){
			for(int roomY = 0; roomY < roomsDown; roomY++){
				maze[roomX][roomY] = new Room(new Coordinate(roomX, roomY));
				System.out.println(maze[roomX][roomY].toString());
			}
		}

//		//currently has a hard set size for each room (10*10)
//		int roomsAcross = 3;
//		for(int i = 1; i <= 10; i++){
//			if(roomAmount % i == 0 && i !=roomAmount)
//				roomsAcross = i;
//		}
//
//		int roomsDown = roomAmount / roomsAcross;
//
//		//create maze with enough space to store all cells of all rooms in a square shape
//		maze = new Block[ROOM_SIZE*roomsDown][ROOM_SIZE*roomsAcross];
//
//		//create roomAmount rooms
//		for (int i = 0; i < roomAmount; i++) {
//			Block[][] room = genRoom(ROOM_SIZE);
//
//			//set the xoffset to start putting cells into the maze
//			// room 0 will have offset 0, room 3 will also have offset 0 in this example (a 30*30 maze)
//			int xoffset=(i*ROOM_SIZE % (ROOM_SIZE*roomsDown)); //xoffset
//
//			//y offset for placing cells of room into maze
//			//when over the limit of the length, move cells down
//			int yoffset=(i*ROOM_SIZE / (ROOM_SIZE*roomsDown)*ROOM_SIZE); //xoffset
//
//			//add cells into maze using offsets
//			for(int mazerow = 0; mazerow < room.length; mazerow++){
//				for (int mazecol = 0; mazecol < room[0].length; mazecol++){
//					maze[xoffset+mazecol][yoffset+mazerow] = room[mazecol][mazerow];
//				}
//			}
//
//		}
//
//		for(int mazerow = 0; mazerow < maze.length; mazerow++) {
//			for (int mazecol = 0; mazecol < maze[0].length; mazecol++) {
//				Block current = maze[mazerow][mazecol];
//				current.updateCoords(mazerow, mazecol);
//				if (current != null){
//					System.out.print(current.toString());
//				}
//				System.out.println();
//			}
//		}


					return maze;
	}

	/**
	 * Creates a new room, surrounded will walls, and filled with floors
	 * @param size width/height of room
	 * @return
	 */
	private Block[][] genRoom(int size) {

		Block[][] room = new Block[size][size];
//
//		for(int i = 0; i < size; i++){
//			for (int k = 0; k < size; k++){
//				if(i == 0 || i == size-1 || k == 0 || k == size-1){
//					room[i][k] = new Wall(new Coordinate(0,0), "Right");
//				}
//				else{
//					room[i][k] = new Path(new Coordinate(0,0));
//				}
//			}
//		}

		return room;
	}


}
