package aston.team15.jumazy.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Player in Jumazy, has a simple texture and {@link Coordinate} as of right now
 * @author kieran
 *
 */
public class Player extends Sprite{
	private Coordinate coords;
	private boolean rolled;
	private int rollSpaces;
	private boolean trapped;
	private boolean turnDone = false;
	private Coordinate startOfMove;
	private Coordinate lastMove;
	private boolean victoryState;
	private static int playerCount = 1;
	private int playerNumber;
	private DieAnimation dieAnimation;
	private boolean willContinue = false;

	/**
	 * Creates a new {@link Player} object, using a {@link Coordinate} object to set its position
	 * @param coords {@link Coordinate} of the new Player object
	 */
	public Player(Coordinate coords) {
		setRegion(TextureConstants.getTexture("player"));
		dieAnimation = new DieAnimation();
		this.coords = coords;
		
		rolled = false;
		rollSpaces = 0;
		turnDone = false;
		lastMove=coords;
		playerNumber = playerCount;
		playerCount++;
		startOfMove= new Coordinate(coords.getX(), coords.getY());
		
		setSize(Gdx.graphics.getHeight() *0.045f, Gdx.graphics.getHeight() *0.045f);
		
		setX(coords.getX()*getWidth());
		setY(coords.getY()*getHeight());
		
	}

	public DieAnimation getDieAnim() {
		return dieAnimation;
	}
	
	public boolean willContinue() {
		return willContinue;
	}

	public void switchTurn() {
		turnDone = !turnDone;
	}

	public boolean getTurnDone(){
		return turnDone;
	}

	public void switchRolled() {
		rolled = !rolled;

	}

	public boolean rolled() {
		return rolled;
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
		setX(coords.getX()*getWidth());
		setY(coords.getY()*getHeight());
	}

	public void newMove(String direction) {

		if(rollSpaces != 0 && dieAnimation.getAnimationFinished())
		{
			if(!trapped)
			{
				Block[] surroundedBlock = Maze.getSurroundingBlocks(coords, direction);
				if(surroundedBlock != null)
				{
					if(surroundedBlock[1].toString() == "path" && surroundedBlock[1].getCoords()!=lastMove)
					{
						lastMove=coords;
						coords.setCoordinates(surroundedBlock[1].getCoords());
						setX(coords.getX()*getWidth());
						setY(coords.getY()*getHeight());
						rollSpaces--;
						checkTrap(surroundedBlock[1]);
						checkVictory(surroundedBlock[1]);
						dieAnimation.decrease();
					}
				}
			}
			else
				trapped = ((Trap)Maze.getBlock(coords)).stillTrapped();
		}

		if(rollSpaces == 0)
			turnDone = true;
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
			if(((Trap)Maze.getBlock(coords)).wasCorrect() == false)
			{
				rollSpaces = 0;
				moveToStartOfTurn();
			}
			else
			{
				//chance for extra turnDone if riddle answered correctly
//				if(new Random().nextInt(9) == 0)
					willContinue = true;
					System.out.println("set to true");
//				else
//					System.out.println("did not get extra turn");
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
    
		dieAnimation.setFinalDie(rollSpaces);
	}

	public void resetTurn(){
		turnDone = false;
		willContinue = false;
	}

	public boolean isVictor() {
		return victoryState;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
}
