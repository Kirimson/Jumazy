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
	private Coordinate startOfMove;
	private Coordinate lastMove;
	private boolean victoryState;
	private static int playerCount = 1;
	private int playerNumber;
	
	
	/**
	 * Creates a new {@link Player} object, using a {@link Coordinate} object to set its position
	 * @param coords {@link Coordinates} of the new Player object
	 */
	public Player(Coordinate coords) {
		this.coords = coords;
		rolled = false;
		rollSpaces = 0;
		turn = false;
		lastMove=coords;
		playerNumber = playerCount;
		playerCount++;
		startOfMove= new Coordinate(coords.getX(), coords.getY());
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
				Block[] surroundedBlock = Maze.getSurroundingBlocks(coords, direction);
				if(surroundedBlock != null)
				{
					if(surroundedBlock[1].toString() == "path" && surroundedBlock[1].getCoords()!=lastMove) {
						lastMove=coords;
						coords.setCoordinates(surroundedBlock[1].getCoords());
						rollSpaces--;
						
						checkTrap(surroundedBlock[1]);
						checkVictory(surroundedBlock[1]);
					}
				}
			}
			else
				trapped = ((Trap)Maze.getBlock(coords)).stillTrapped();
		}
	}
	
	private void checkTrap(Block path) {
		
		if(path instanceof Trap) {
			trapped = true;
			((Trap) path).createGUI();
		}
	}
	
	private void checkVictory(Block path) {
		if(path instanceof VictoryPath) {
			victoryState = true;
			((VictoryPath) path).showWon(playerNumber);
		}
	}
	
	public void checkStillTrapped() {
		trapped = ((Trap)Maze.getBlock(coords)).stillTrapped();
		if(!trapped) {
		System.out.println("no longer trapped");
			if(((Trap)Maze.getBlock(coords)).wasCorrect() == false)
			{
				rollSpaces = 0;
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
		Random rnd = new Random();
		rolled = true;
		rollSpaces = rnd.nextInt(6) + 1 + movementMod;

		if(rollSpaces == 0)
            rollSpaces = 1;
	}
	
	public boolean isVictor() {
		return victoryState;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
}
