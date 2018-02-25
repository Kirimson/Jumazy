package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

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

	Generator(int roomsAcross, int roomsDown) {
		rnd = new Random();
		maze = new Room[roomsAcross][roomsDown];
	}

	public static void main(String[] args) {
		Generator gen = new Generator(3,3);
//		gen.genRandDirections(2,2);
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
//		MAZE_ROOMS_ACROSS = maze.length;
//		MAZE_ROOMS_DOWN = maze[0].length;
		for(int row = 0; row < maze.length; row++){
			for(int col = 0; col < maze[0].length; col++)
			{
				System.out.println("New Room: "+row+", "+col);
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

		for (int i = directions.size()-1; i > 1; i--)
			directions.remove(i);

		for (Coordinate c : directions){
			System.out.println("New Exit: "+c.toString());

			Room thisRoom = maze[row][col];
			Room linkRoom = maze[row + c.getX()][col + c.getY()];
			//check if exit doesn't exist already
			if(!thisRoom.getExits().contains(c)){
				//check that each room doesn't have more than 2 exits
				if(thisRoom.getExits().size() < 2 && linkRoom.getExits().size() < 2){
					//add this coordinate to room
					thisRoom.addExit(c);
					//add inverse coordinate to other room
					linkRoom.addExit(new Coordinate((c.getX() * -1),(c.getY() * -1)));
				} else {
					System.out.println("skipped");
				}
			} else {
				System.out.println("skipped");
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
