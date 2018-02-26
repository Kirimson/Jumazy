package aston.team15.jumazy.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A simple block class, used as a superclass to all types of blocks.
 * Contains a factory to create these subclasses
 * @author kieran
 *
 */
public class Block extends Sprite{
	
//	private Texture blockTexture;
	private Coordinate coords;
	protected Exit exits;
	
	/**
	 * Base constructor for {@link Block} sets the texture and Coordinates of the object
	 * Creates a new {@link Exit} object, according to the type of {@link Block}
	 * @param tex
	 * @param coords
	 */
	public Block(Texture tex, Coordinate coords) {
		setRegion(tex);
		this.coords = coords;
		setSize(Gdx.graphics.getHeight() *0.045f, Gdx.graphics.getHeight() *0.045f);
		
		setX(coords.getX()*getWidth());
		setY(coords.getY()*getHeight());
		exits = new Exit();
	}

	/**
	 * Creates a new {@link Block} according to type parameter
	 * @param type the type of block the factory should make
	 * @param coord a {@link Coordinate} object, to be passed into the {@link Block} the Factory will create
	 * @return a new {@link Block} object
	 */
	public static Block newFact(String type, Coordinate coord) {
		
		if(type == "path")
		{
			Random rnd = new Random();
			
			if(rnd.nextInt(25) != 0)
				return new Path(coord);
			else
				return new Trap(coord);
		} else if (type == "victory") {
			return new VictoryPath(coord);
		}
		else
			return new Wall(coord, type);
	}
	
	/**
	 * Returns the X of the {@link Coordinate} of the {@link Block}
	 * @return returns an {@link int} value of the {@link Block}
	 */
	public int getXCoord() {
		return coords.getX();
	}
	
	/**
	 * Returns the Y of the {@link Coordinate} of the {@link Block}
	 * @return returns an {@link int} value of the {@link Block}
	 */
	public int getYCoord() {
		return coords.getY();
	}
	public Coordinate getCoords() {
		return coords;
	}
	
	/**
	 * Runs the {@link Exit#checkExit(String)} method, returning if the block has an exit at that direction
	 * @param direction direction to check for the exit
	 * @return boolean true if there is an exit in passed direction
	 */
	public boolean checkExit(String direction) {
		return exits.checkExit(direction);
	}
	
	/**
	 * Runs the {@link Exit#checkEntrance(String)} method, returning if the block has an entrance at that direction
	 * @param direction direction to check for the entrance
	 * @return boolean true if there is an entrance in passed direction
	 */
	public boolean checkEntrance(String direction) {
		return exits.checkEntrance(direction);
	}
	
	/**
	 * Generates a String representation of the {@link Block}
	 * @return String representation of the {@link Block}
	 */
	public String toString() {
		return "Base block";
	}
	
	public void addExit(String exit) {
		exits.add(exit);
	}

	public void updateCoords(int x, int y){
		coords = new Coordinate(x, y);
		setX(coords.getX()*getWidth());
		setY(coords.getY()*getHeight());
	}

	public void updateCoords(Coordinate coords){
		setX(coords.getX()*getWidth());
		setY(coords.getY()*getHeight());
	}
}