package aston.team15.jumazy.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generator {
	
	private Random rnd;

	public Generator() {
		rnd = new Random();
	}
	
	public Block[][] superNewGenMaze(int dimension){
		
		Block[][] maze = new Block[dimension][dimension];
		
		//start with maze full of walls
		for(int column = 0; column < dimension; column++) {
			for(int row = 0; row< dimension; row++) {
					maze[column][row] = Block.newFact("wall", new Coordinate(column, row));
			}
		}
		
		maze = dig(maze, 0, 0);
		
//		int createWall = rnd.nextInt(4); //0 = up, 1 = right, 2 = down, 3 = left
//		String exitDirection = "";
//		switch(createWall) {
//		case 0: exitDirection = "top";break;
//		case 1: exitDirection = "right";break;
//		case 2: exitDirection = "down";break;
//		case 3: exitDirection = "left";break;
//		}
//		
//		if(stillWalls(maze, dimension)){
//			currentCell.addExit(exitDirection);
//		}
		
		return maze;
	}
	
	private ArrayList<Integer> genRandDirections() {
		
		ArrayList<Integer> directions = new ArrayList<Integer>();
		
		for(int i = 0; i < 4; i++)
		{
			directions.add(i+1);
		}
		Collections.shuffle(directions);
		
		return directions;
		
	}
	
	private Block[][] dig(Block[][] maze, int x, int y) {
		
		//choose rand directions
		ArrayList<Integer> directions = genRandDirections();
		System.out.println(maze.length);
		System.out.println(y + 2 < maze.length-1);
		for( Integer i : directions) {
			switch(i) {
			case 1: //Going up
				if(y + 2 > maze.length-1)
				{
					System.out.println("Continuing");
					continue;
				}
				if(maze[x][y + 2].getName().equals("wall")) {
					System.out.println("FOUND WALL");
					maze[x][y + 2] = Block.newFact("path", new Coordinate(x, y + 2));
					maze[x][y + 1] = Block.newFact("path", new Coordinate(x, y + 1));
					dig(maze, x, y + 2);
				}
				break;
			case 2: //Going right
				if(x + 2 > maze.length-1)
				{
					System.out.println("Continuing");
					continue;
				}
				if(maze[x + 2][y].getName().equals("wall")) {
					System.out.println("FOUND WALL");
					maze[x + 2][y] = Block.newFact("path", new Coordinate(x + 2, y));
					maze[x + 1][y] = Block.newFact("path", new Coordinate(x + 1, y));
					dig(maze, x + 2, y);
				}
				break;
			case 3: //Going down
				if(y - 2 < 0)
				{
					System.out.println("Continuing");
					continue;
				}
				if(maze[x][y - 2].getName().equals("wall")) {
					System.out.println("FOUND WALL");
					maze[x][y - 2] = Block.newFact("path", new Coordinate(x, y - 2));
					maze[x][y - 1] = Block.newFact("path", new Coordinate(x, y - 1));
					dig(maze, x, y - 2);
				}
				break;
			case 4: //Going left
				if(x - 2 < 0)
				{
					System.out.println("Continuing");
					continue;
				}
				if(maze[x - 2][y].getName().equals("wall")) {
					System.out.println("FOUND WALL");
					maze[x - 2][y] = Block.newFact("path", new Coordinate(x - 2, y));
					maze[x - 1][y] = Block.newFact("path", new Coordinate(x - 1, y));
					dig(maze, x - 2, y);
				}
				break;
			}
		}

		return maze;
	}
	
	/**
	 * Generates a new maze. Loops through each array index, creating a new Block, by using the {@link Block#blockFactory()} method to create a new {@link Block}
	 * Creates the maze in sections of MAZE_DIMENSION/4 (if maze is 16*16 chunks are 4*4)
	 */	
	public Block[][] newGenMaze(int dimension) {
		/**
		 * MAZE STRUCTURE EXAMPLE:
		 * 
		 *  	---------
		 *     3|		|
		 *     2|		|
		 * ^   1|		|
		 * |   0|		|
		 * K	---------
		 * 	I->  0 1 2 3
		 * 
		 * I is the column, K is the row of that column. Top cell of the first column would be (I=0,K=3)
		 */
		
		Block[][] maze = new Block[dimension][dimension];
		
		int mazeOffsetI = 0;
		int mazeOffsetK = 0;
		
		int column = 0;
		int row = 0;
		int blobk = 0;
		
		int edgeOfChunk = dimension/4-1; //edge = very right and very top of each chunk
		
		while(blobk != dimension*dimension)
		{
			System.out.println("New Chunk:");
			System.out.println("I: "+column+" K: "+row);
			for(column = mazeOffsetI; column < dimension/4+mazeOffsetI; column++) {
				for(row = mazeOffsetK; row < dimension/4+mazeOffsetK; row++) {
					System.out.println("Block "+(blobk+1));

					String type = "";
					
					int rand=rnd.nextInt(8);
					int orientation=rnd.nextInt(3);
					if(rand>=0 && rand<=3)
						type = "cross";
					else if(rand==4)
						type = "straight";
					else if(rand==5)
						type = "corner";
					else
						type = "tJunction";
					
					if (column == edgeOfChunk+mazeOffsetI && row == edgeOfChunk-1+mazeOffsetK)
						maze[column][row] = Block.blockFactory("cross", new Coordinate(column, row), orientation);
					else if (column == 0+mazeOffsetI && row == edgeOfChunk-1+mazeOffsetK)
						maze[column][row] = Block.blockFactory("horizontal", new Coordinate(column, row), orientation);
					else
						maze[column][row] = Block.blockFactory(type, new Coordinate(column, row), orientation);
					
					blobk++;
				}
			}
			System.out.println("I: "+column+" K: "+row);
			 if(column != dimension) {
				mazeOffsetI += dimension/4;
			}
			else
			{
				mazeOffsetK += dimension/4;
				mazeOffsetI = 0;
			}
		}
		return maze;
	}

}
