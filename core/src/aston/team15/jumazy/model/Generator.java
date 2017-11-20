package aston.team15.jumazy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generator {
	
	private Random rnd;
	private Block[][] maze;

	public Generator() {
		rnd = new Random();
	}
	
	public Block[][] superNewGenMaze(int x, int y){
		
		maze = new Block[x][y];
		
		//start with maze full of walls
		
		
		maze = dig(0, 0);
		
		maze = refineMaze(x, y);
		
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
	
	private Block[][] dig(int x, int y) {
		
		//choose rand directions
		ArrayList<Integer> directions = genRandDirections();
		for( Integer i : directions) {
			switch(i) {
			case 1: //Going up
				if(y + 2 > maze[0].length-1)
				{
					continue;
				}
				if(maze[x][y + 2] == null) {
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

		return maze;
	}
	
	private Block[][] refineMaze(int x, int y) {
		
		for(int row = 0; row < maze.length; row++) {
			for(int column = 0; column < maze[0].length; column++) {
				if(maze[row][column] == null)
				{
					String type = genWallType(row, column);
					
					maze[row][column] = Block.newFact(type, new Coordinate(column, row));
				}
			}
		}
		
		return maze;
	}
	
	private String genWallType(int row, int column) {
		String type = "Both";
		
		//order inside the if. up, down left right
		
		//if only up wall exist
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
		
		//if no sides
		try {
			if(!isWall(row, column + 1) && !isWall(row, column - 1) &&
					!isWall(row + 1, column) && !isWall(row - 1, column))
				return "BothTop";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if all but down & left sides
		try {
			if(isWall(row, column + 1) && !isWall(row, column - 1) &&
					!isWall(row + 1, column) && isWall(row - 1, column))
				return "Right";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if all but left sides
		try {
			if(isWall(row, column + 1) && isWall(row, column - 1) &&
					!isWall(row + 1, column) && isWall(row - 1, column))
				return "TopStraightLeft";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if all but right sides
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
		
		//if down and no up wall exist
		try {
			if(!isWall(row, column + 1) && isWall(row, column - 1))
				return "TopEnd";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if left and right
		try {
			if(isWall(row + 1, column) && isWall(row - 1, column))
				return "MiddleTop";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if left
		try {
			if(isWall(row + 1, column))
				return "LeftTop";
		} catch(ArrayIndexOutOfBoundsException e) {}
		
		//if right
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
