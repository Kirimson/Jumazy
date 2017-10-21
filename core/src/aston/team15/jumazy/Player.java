package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	private Texture playerTexture = new Texture("player.jpg");
	private Coordinate coords;
	
	public Player(Coordinate coords) {
		this.coords = coords;
	}
	
	public SpriteBatch drawPlayer(SpriteBatch batch) {
		batch.draw(playerTexture, coords.getX()*32+12, coords.getY()*32+12);
		
		return batch;
	}
	
	public void move(String direction) {
		
		System.out.println("Player moving "+direction);
		System.out.println("Player coords "+coords.toString());
		System.out.println("getting surrounding blocks...");
		
		Block[] surroundedBlock = Maze.getSurroundedBlocks(coords, direction);
		
		if(surroundedBlock != null)
		{
			if(surroundedBlock[0].checkExit(direction) && surroundedBlock[1].checkEntrance(direction))
			{
				coords.setCoordinates(surroundedBlock[1].getCoords());
				System.out.println("allowed movement");
				System.out.println("player new Coords: "+coords.toString());
			}
			else
			{
				System.out.println("denyed movement");
			}
		}
	}
	
}
