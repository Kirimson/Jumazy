package aston.team15.jumazy;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Maze {
	
	private static Block[][] statMaze;
	private Block[][] maze;
	private Random rnd;
	private static Block stopBlock;
	private static int MAZE_DIMENSION =12;
	Player player;
	
	
	public Maze() {
		stopBlock = new Block(null, null);
		maze = new Block[MAZE_DIMENSION][MAZE_DIMENSION];
		player = new Player(new Coordinate(0,0));
		genMaze();
	}
	
	public static int getDimensions(){
		return MAZE_DIMENSION;
	}
	
	public static Block[] getSurroundedBlocks(Coordinate coord, String direction) {
		int xDir = 0;
		int yDir = 0;
		
		switch(direction) {
			case "up": yDir = 1;break;
			case "down": yDir = -1;break;
			case "left": xDir = -1;break;
			case "right": xDir = 1;break;
		}
		
		Block[] temp = new Block[2];
		temp[0] = statMaze[coord.getX()][coord.getY()];
		temp[1] = stopBlock;
		
		System.out.println("block player is on: "+statMaze[coord.getX()][coord.getY()].toString());
		if(coord.getX()+xDir >= 0 && coord.getY()+yDir >= 0 && coord.getX()+xDir < MAZE_DIMENSION && coord.getY()+yDir < MAZE_DIMENSION ) {
			
			System.out.println("block player wants to move to: "+statMaze[coord.getX()+xDir][coord.getY()+yDir].toString());
			temp[0] = statMaze[coord.getX()][coord.getY()];
			temp[1] = statMaze[coord.getX()+xDir][coord.getY()+yDir];
		}
		else
		{
			System.out.println("player wanted to go OOB");
		}
		
		return temp;
		
	}
	
	private void genMaze() {
		rnd = new Random();
		
		for(int i = 0; i < MAZE_DIMENSION; i++) {
			for(int k = 0; k < MAZE_DIMENSION; k++) {
				maze[i][k] = Block.blockFactory(rnd.nextInt(6), i, k);
			}
		}
		statMaze = maze;
	}
	
	public Block getBlock(int row, int column) {
		Block b = null;
		if(row >= 0 && row <= MAZE_DIMENSION && column >= 0 && column <= MAZE_DIMENSION)
			b = maze[row][column];
		
		return b;
	}
	
	public SpriteBatch drawMaze(SpriteBatch batch) {
		for(int i = 0; i < MAZE_DIMENSION; i++) {
			for(int k = 0; k < MAZE_DIMENSION; k++) {
				batch.draw(maze[i][k].getTexture(), 32*i,32*k);
			}
		}
		batch = player.drawPlayer(batch);
		return batch;
	}
	
	public void actions() {
		String direction = "";
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			direction = "right";
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			direction = "left";
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			direction = "up";
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			direction = "down";
		}
		if(direction != "")
			player.move(direction);
	}
}
