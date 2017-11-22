package aston.team15.jumazy.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Player in Jumazy, has a simple texture and {@link Coordinate} as of right now
 * @author kieran
 *
 */
public class Player {
	private Texture playerTexture = new Texture("player.png");
	private Coordinate coords;
	private boolean rolled;
	private int rollSpaces;
	private boolean isTrapped;
	private boolean turn;
	
	/**
	 * Creates a new {@link Player} object, using a {@link Coordinate} object to set its position
	 * @param coords {@link Coordinates} of the new Player object
	 */
	public Player(Coordinate coords) {
		this.coords = coords;
		rolled = false;
		rollSpaces = 0;
		turn = false;
		
				}
	
	public void switchTurn() {
		turn=!turn;
	
	}
	public boolean getTurnState(){
		return turn;
	}
	/**
	 * Returns the players texture
	 * @return {@link Texture} object of the player
	 */
	public Texture getTexture() {
		return playerTexture;
	}
	
	/**
	 * Returns the players coordinates
	 * @return {@link Coordinate} object of the player
	 */
	public Coordinate getCoords() {
		return coords;
	}
	
	public void newMove(String direction) {
		
		if(rollSpaces != 0)
		{
			if(!isTrapped)
			{
				System.out.println("Player moving "+direction);
				System.out.println("Player coords "+coords.toString());
				System.out.println("getting surrounding blocks...");
				
				Block[] surroundedBlock = Maze.getSurroundingBlocks(coords, direction);
				
				if(surroundedBlock != null)
				{
					if(surroundedBlock[1].toString() == "path") {
						coords.setCoordinates(surroundedBlock[1].getCoords());
						System.out.println("allowed movement");
						System.out.println("player new Coords: "+coords.toString());
						rollSpaces--;
						System.out.println("Spaces left: "+(rollSpaces));
						
						checkTrap(surroundedBlock[1]);
						
					}
					else
					{
						System.out.println("denyed movement");
					}
				}
			}
			else
			{
				isTrapped = ((Trap)Maze.getBlock(coords)).stillTrapped();
				if(!isTrapped) {
					rollSpaces = 0;
				}
			}
				System.out.println(isTrapped);
				
			if(rollSpaces == 0)
			{
				rolled = false;
			}
		}
	}
	
	private void checkTrap(Block path) {
		System.out.println("checking");
		if(path instanceof Trap) {
			System.out.println("its a trap");
			rollSpaces = 1;
			
			isTrapped = true;
			((Trap) path).createGUI();
		}
		
	}
	
	public boolean hasRolled() {
		return rolled;
	}
	
	public int getRollSpaces() {
		return rollSpaces;
	}
	
	public void roll(int movementMod) {
		rolled = true;
		
		Random rnd = new Random();
		
		rollSpaces = rnd.nextInt(6)+1;
                
                rollSpaces += movementMod; 
                
                if(rollSpaces == 0)
                {
                    rollSpaces = 0;
                }

		System.out.println("Rolled: " + rollSpaces);
                System.out.println("Weather Modifier: " + movementMod);
		
	}
	
}
