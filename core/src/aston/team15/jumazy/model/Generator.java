package aston.team15.jumazy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generator {
	
	private Random rnd;
	private Room[][] maze;
	private int xMiddle;
	private int yMiddle;

	Generator() {
		rnd = new Random();
	}

	/**
	 *
	 * @param roomsAcross how many rooms across there are
	 * @param roomsDown how many rooms down there are
	 * @return
	 */
	public Room[][] genMaze(int roomsAcross, int roomsDown){
		System.out.println(roomsAcross+" and "+roomsDown);
		maze = new Room[roomsAcross][roomsDown];

		for(int roomX = 0; roomX < roomsAcross; roomX++){
			for(int roomY = 0; roomY < roomsDown; roomY++){
				maze[roomX][roomY] = new Room(new Coordinate(roomX, roomY));
			}
		}
		createLinks();
		return maze;
	}

	private void createLinks() {
		for(int row = 0; row < maze.length; row++){
			for(int col = 0; col < maze[0].length; col++)
			{
				genRandDirections(row, col);
			}
		}
	}

	/**
	 * Creates an ArrayList of two valid directions given a column and row of the maze
	 * @param row current column
	 * @param col current row
	 * @return ArrayList of directions
	 */
	private void genRandDirections(int row, int col) {

		ArrayList<Coordinate> directions = new ArrayList<>();

		//check up, right, down, left dirs. if allowed, add coord direction to list
		if(!(col + 1 > maze[0].length-1))
			directions.add(new Coordinate(0,1));
		if(!(row + 1 > maze.length-1))
			directions.add(new Coordinate(1,0));
		if(col != 0)
			directions.add(new Coordinate(0,-1));
		if(row != 0)
			directions.add(new Coordinate(-1,0));

		Collections.shuffle(directions);

		for (Coordinate coord : directions){
			System.out.println("New Exit: "+coord.toString());

			Room thisRoom = maze[row][col];
			Room linkRoom = maze[row + coord.getX()][col + coord.getY()];
			//check if exit doesn't exist already
			if(!thisRoom.hasExit(coord)){
				int type = new Random().nextInt(10);
				//add this coordinate to room
				thisRoom.addExit(coord,type);
				//add inverse coordinate to other room
				linkRoom.addExit(new Coordinate((coord.getX() * -1),(coord.getY() * -1)),type);
			}
		}
	}

	/**
	 * Creates a new room, surrounded will walls, and filled with floors
	 * @param size width/height of room
	 * @return a new room
	 */
	private Block[][] genRoom(int size) {

		Block[][] room = new Block[size][size];

		return room;
	}
}
