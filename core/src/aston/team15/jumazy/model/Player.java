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
	private boolean trapped;
	private boolean turn;
	private boolean trappedLast;
	
	/**
	 * Creates a new {@link Player} object, using a {@link Coordinate} object to set its position
	 * @param coords {@link Coordinates} of the new Player object
	 */
	public Player(Coordinate coords) {
		this.coords = coords;
		rolled = false;
		rollSpaces = 0;
		turn = false;
		trappedLast = false;
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
			if(!trapped)
			{
				System.out.println("Player moving "+direction);
				System.out.println("Player coords "+coords.toString());
				
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
				trapped = ((Trap)Maze.getBlock(coords)).stillTrapped();
				if(!trapped) {
					System.out.println("woo2");
				}
			}
		
			System.out.println(trapped);
				
			if(rollSpaces == 0)
			{
				rolled = false;
				switchTurn();
			}
		}
	}
	
	private void checkTrap(Block path) {
		
		System.out.println("checking if on a trap");
		if(path instanceof Trap) {
			System.out.println("on a trap");
			rollSpaces = 0;
			
			trapped = true;
			((Trap) path).createGUI();
		}
	}
	
	public void checkStillTrapped() {
		trapped = ((Trap)Maze.getBlock(coords)).stillTrapped();
		if(!trapped) {
		System.out.println("no longer trapped");
			rollSpaces = 0;
		}
	}
	
	public boolean isTrapped() {
		
		return trapped;
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
            rollSpaces = 1;
        }

		System.out.println("Rolled: " + rollSpaces);
                System.out.println("Weather Modifier: " + movementMod);
		
	}
	
}
