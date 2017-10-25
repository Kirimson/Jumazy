package aston.team15.jumazy;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Player in Jumazy, has a simple texture and {@link Coordinate} as of right now
 * @author kieran
 *
 */
public class Player {
	private Texture playerTexture = new Texture("player.jpg");
	private Coordinate coords;
	private boolean rolled;
	private int rollSpaces;
	
	/**
	 * Creates a new {@link Player} object, using a {@link Coordinate} object to set its position
	 * @param coords {@link Coordinates} of the new Player object
	 */
	public Player(Coordinate coords) {
		this.coords = coords;
		rolled = false;
		rollSpaces = 0;
	}
	
	/**
	 * Similar to {@link Maze#drawMaze(SpriteBatch)} however it draws the player onto the batch
	 * @param batch {@link SpriteBatch} object the player will be drawn onto
	 * @return {@link SpriteBatch} object with the player set to be drawn
	 */
	public SpriteBatch drawPlayer(SpriteBatch batch) {
		int xOffset = (Gdx.graphics.getWidth()/2)-(Maze.MAZE_DIMENSION*32/2);
		int yOffset = Gdx.graphics.getHeight()/2-(Maze.MAZE_DIMENSION*32/2);
		
		batch.draw(playerTexture, xOffset+coords.getX()*32+12, yOffset+coords.getY()*32+12);
		
		return batch;
	}
	
	/**
	 * Checks players current and desired {@link Block} using {@link Maze#getSurroundingBlocks(Coordinate, String)}.
	 * Updates players {@link Coordinate} object if they are able to move to desired block
	 * @param direction String of the direction the player is moving
	 */
	public void move(String direction) {
		
		if(rollSpaces != 0)
		{
			System.out.println("Player moving "+direction);
			System.out.println("Player coords "+coords.toString());
			System.out.println("getting surrounding blocks...");
			
			Block[] surroundedBlock = Maze.getSurroundingBlocks(coords, direction);
			
			if(surroundedBlock != null)
			{
				if(surroundedBlock[0].checkExit(direction) && surroundedBlock[1].checkEntrance(direction))
				{
					coords.setCoordinates(surroundedBlock[1].getCoords());
					System.out.println("allowed movement");
					System.out.println("player new Coords: "+coords.toString());
					rollSpaces--;
					System.out.println("Spaces left: "+(rollSpaces));
				}
				else
				{
					System.out.println("denyed movement");
				}
			}
		}
		
		if(rollSpaces == 0)
		{
			rolled = false;
		}
	}
	
	public boolean hasRolled() {
		return rolled;
	}
	
	public int getRollSpaces() {
		return rollSpaces;
	}
	
	public void roll() {
		rolled = true;
		
		Random rnd = new Random();
		
		rollSpaces = rnd.nextInt(6)+1;

		System.out.println("Rolled: "+rollSpaces);
		
	}
	
}
