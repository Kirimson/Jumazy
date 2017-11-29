package aston.team15.jumazy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generator {
	
	private Random rnd;
	private Block[][] maze;
	private int xMiddle;
	private int yMiddle;

	public Generator() {
		rnd = new Random();
	}
	
	public Block[][] superNewGenMaze(int dimensionx, int dimensiony){
		
		maze = new Block[dimensionx][dimensiony];
		dig(0, 0);		
		xMiddle = dimensionx/2;
		yMiddle = dimensiony/2;
		System.out.println("the middle of the maze:" + xMiddle + "," + yMiddle);
		refineMaze(dimensionx, dimensiony);
		
		
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
	
	private void dig(int x, int y) {
		
		//choose rand directions
		ArrayList<Integer> directions = genRandDirections();
		for( Integer i : directions) {
			switch(i) {
			case 1: //Going up
				//check if block 2 in this direction is out of bounds
				if(y + 2 > maze[0].length-1)
				{
					continue;
				}
				//check if block 2 in this direction is a wall
				if(maze[x][y + 2] == null) {
					//make that block, and the block before it a new path
					maze[x][y + 2] = Block.newFact("path", new Coordinate(x, y + 2));
					maze[x][y + 1] = Block.newFact("path", new Coordinate(x, y + 1));
					dig(x, y + 2);
				}
				break;
				
			case 2: //Going right
				if(x + 2 > maze.length-1)
				{
					continue;
				}
				if(maze[x + 2][y] == null) {
					maze[x + 2][y] = Block.newFact("path", new Coordinate(x + 2, y));
					maze[x + 1][y] = Block.newFact("path", new Coordinate(x + 1, y));
					dig(x + 2, y);
				}
				break;
				
			case 3: //Going down
				if(y - 2 < 0)
				{
					continue;
				}
				if(maze[x][y - 2] == null) {
					maze[x][y - 2] = Block.newFact("path", new Coordinate(x, y - 2));
					maze[x][y - 1] = Block.newFact("path", new Coordinate(x, y - 1));
					dig(x, y - 2);
				}
				break;
				
			case 4: //Going left
				if(x - 2 < 0)
				{
					continue;
				}
				if(maze[x - 2][y] == null) {
					maze[x - 2][y] = Block.newFact("path", new Coordinate(x - 2, y));
					maze[x - 1][y] = Block.newFact("path", new Coordinate(x - 1, y));
					dig(x - 2, y);
				}
				break;
			}
		}
	}
	
	private void refineMaze(int x, int y) {
		
		for (int i = -1; i<=1; i++) {			
			for (int a = -1; a<=1; a++) {
				maze[xMiddle+i][yMiddle+a] = Block.newFact("victory", new Coordinate(xMiddle+i,yMiddle+a));
			}
		}

		
		for(int row = 0; row < maze.length; row++) {
			for(int column = 0; column < maze[0].length; column++) {
				if(maze[row][column] == null)
				{
					String wallType = genWallType(row, column);
					maze[row][column] = Block.newFact(wallType, new Coordinate(row, column));
				}
			}
		}
		
	}
	
	private String genWallType(int row, int column) {
		String type = "Both";
		
		//order inside the if. up, down left right
		
		//if up wall and all other path exist
		try {
			if(isWall(row, column + 1) && !isWall(row, column - 1) &&
					!isWall(row + 1, column) && !isWall(row - 1, column))
				return "Both";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if all sides
		try {
			if(isWall(row, column + 1) && isWall(row, column - 1) &&
					isWall(row + 1, column) && isWall(row - 1, column))
				return "TopBlank";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if all sides are path
		try {
			if(!isWall(row, column + 1) && !isWall(row, column - 1) &&
					!isWall(row + 1, column) && !isWall(row - 1, column))
				return "BothTop";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if right and up are wall, other sides are path
		try {
			if(isWall(row, column + 1) && !isWall(row, column - 1) &&
					!isWall(row + 1, column) && isWall(row - 1, column))
				return "Right";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if left side wall, all other path
		try {
			if(isWall(row, column + 1) && isWall(row, column - 1) &&
					!isWall(row + 1, column) && isWall(row - 1, column))
				return "TopStraightLeft";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if right side wall all other path
		try {
			if(isWall(row, column + 1) && isWall(row, column - 1) &&
					isWall(row + 1, column) && !isWall(row - 1, column))
				return "TopStraightRight";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if all but down sides
		try {
			if(isWall(row, column + 1) && !isWall(row, column - 1) &&
					isWall(row + 1, column) && isWall(row - 1, column))
				return "Middle";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if but down & right sides
		try {
			if(isWall(row, column + 1) && !isWall(row, column - 1) &&
					isWall(row + 1, column) && !isWall(row - 1, column))
				return "Left";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if all but up sides
		try {
			if(isWall(row, column - 1) &&
					isWall(row + 1, column) && isWall(row - 1, column))
				return "TopBoth";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if up and down wall exist
		try {
			if(isWall(row, column + 1) && isWall(row, column - 1))
				return "TopStraight";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if down and left wall exist
		try {
			if(isWall(row, column - 1) && isWall(row - 1, column))
				return "TopRight";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if down and right wall exist
		try {
			if(isWall(row, column - 1) && isWall(row + 1, column))
				return "TopLeft";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if down is wall and up is path
		try {
			if(!isWall(row, column + 1) && isWall(row, column - 1))
				return "TopEnd";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if left and right is wall
		try {
			if(isWall(row + 1, column) && isWall(row - 1, column))
				return "MiddleTop";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if left is wall
		try {
			if(isWall(row + 1, column))
				return "LeftTop";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if right is wall
		try {
			if(isWall(row - 1, column))
				return "RightTop";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		return type;
	}

	private boolean isWall(int row, int column) {
		
		try {
			if(maze[row][column].toString().equals("wall"))
				return true;
		}catch(NullPointerException e) {
			return true;
		}
		
		return false;
	}

}
