package aston.team15.jumazy;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

/**
 * A simple block class, used as a superclass to all types of blocks.
 * Contains a factory to create these subclasses
 * @author kieran
 *
 */
public class Block {
	
	private Texture blockTexture;
	private Coordinate coords;
	protected Exit exits;
	
	/**
	 * Base constructor for {@link Block} sets the texture and Coordinates of the object
	 * Creates a new {@link Exit} object, according to the type of {@link Block}
	 * @param tex
	 * @param coords
	 */
	public Block(Texture tex, Coordinate coords) {
		this.blockTexture = tex;
		this.coords = coords;
		exits = new Exit(false, false, false, false);
	};

	/**
	 * Creates a new {@link Block} according to type parameter
	 * @param type the type of block the factory should make
	 * @param coord a {@link Coordinate} object, to be passed into the {@link Block} the Factory will create
	 * @return a new {@link Block} object
	 */
	public static Block blockFactory(String type, Coordinate coord) {
		Block tempBlock = null;
		
		switch(type) {
			case "downLeft": tempBlock = new CornerDownLeft(coord);break;
			case "downRight": tempBlock = new CornerDownRight(coord);break;
			case "vertical": tempBlock = new StraightVertical(coord);break;
			case "horizontal": tempBlock = new StraightHorizontal(coord);break;
			case "upLeft": tempBlock = new CornerUpLeft(coord);break;
			case "upRight": tempBlock = new CornerUpRight(coord);break;
			case "cross": tempBlock = new Cross(coord);break;
			default: tempBlock = new Cross(coord);break;
		}
		
		return tempBlock;
	}
	
	public static Block blockFactory(int type, Coordinate coord) {
		String typeString;
		switch(type) {
			case 0: typeString = "downLeft";break;
			case 1: typeString = "downRight";break;
			case 2: typeString = "vertical";
			case 3: typeString = "horizontal";break;
			case 4: typeString = "upLeft";break;
			case 5: typeString = "upRight";break;
			case 6: typeString = "cross";break;
			default: typeString = "cross";break;
		}
		return blockFactory(typeString, coord);
	}
	
	/**
	 * Returns the {@link Texture} of the {@link Block}
	 * @return {@link Texture} object for the {@link Block}
	 */
	public Texture getTexture()
	{
		return blockTexture;
	}
	
	/**
	 * Returns the {@link Coordinates} of the {@link Block}
	 * @return returns a {@link Coordinate} object of the {@link Block}
	 */
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
	@Override
	public String toString() {
		return "Base block. 4 walls";
	}
}