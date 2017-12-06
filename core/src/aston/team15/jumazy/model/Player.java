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
	private Texture playerTexture = TextureConstants.getTexture("player");
	private Coordinate coords;
	private boolean rolled;
	private int rollSpaces;
	private boolean trapped;
	private boolean turn;
	private boolean trappedLast;
	private Coordinate startOfMove;
	private Coordinate lastMove;
	private boolean victoryState;
	private static int playerCount = 1;
	private int playerNumber;
	private DieAnimation dieAnimation;
	
	
	/**
	 * Creates a new {@link Player} object, using a {@link Coordinate} object to set its position
	 * @param coords {@link Coordinates} of the new Player object
	 */
	public Player(Coordinate coords) {
		dieAnimation = new DieAnimation();
		this.coords = coords;
		rolled = false;
		rollSpaces = 0;
		turn = false;
		trappedLast = false;
		lastMove=coords;
		playerNumber = playerCount;
		playerCount++;
		startOfMove= new Coordinate(coords.getX(), coords.getY());
		}
	
	public DieAnimation getDieAnim() {
		return dieAnimation;
	}
	
	public void switchTurn() {
		turn = !turn;
	}
	
	public boolean getTurnState(){
		return turn;
	}
	
	public void switchRolled() {
		rolled = !rolled;
		
	}
	
	public boolean rolled() {
		return rolled;
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
	
	public void setStartOfMove(Coordinate coord) {
		startOfMove=new Coordinate(coord.getX(), coord.getY());
	}
	
	public void moveToStartOfTurn() {
		coords.setCoordinates(startOfMove);
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
					if(surroundedBlock[1].toString() == "path" && surroundedBlock[1].getCoords()!=lastMove) {
						lastMove=coords;
						coords.setCoordinates(surroundedBlock[1].getCoords());
						System.out.println("allowed movement");
						System.out.println("player new Coords: "+coords.toString());
						rollSpaces--;
						dieAnimation.decrease();
						System.out.println("Spaces left: "+(rollSpaces));
						
						checkTrap(surroundedBlock[1]);
						checkVictory(surroundedBlock[1]);
							
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
	
	private void checkVictory(Block path) {
		System.out.println("checking for victory");
		if(path instanceof VictoryPath) {
			System.out.println("victory path reached");
			
			victoryState = true;
			((VictoryPath) path).showWon(playerNumber);
		}
	}
	
	public void checkStillTrapped() {
		trapped = ((Trap)Maze.getBlock(coords)).stillTrapped();
		if(!trapped) {
		System.out.println("no longer trapped");
			rollSpaces = 0;
			
			if(((Trap)Maze.getBlock(coords)).wasCorrect() == false)
			{
				System.out.println("answer wrong");
				moveToStartOfTurn();
			}
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
		
		int roll = rnd.nextInt(6) + 1;
		
		rollSpaces = roll + movementMod;
		
		dieAnimation.setFinalDie(rollSpaces);
		//rollSpaces = rnd.nextInt(6) + 1 + movementMod;
		
        if(rollSpaces == 0)
        {
            rollSpaces = 1;
        }

//        setStartOfMove(coords);
        
		System.out.println("Rolled: " + rollSpaces);
                System.out.println("Weather Modifier: " + movementMod);
		
	}
	
	public boolean isVictor() {
		return victoryState;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
}
