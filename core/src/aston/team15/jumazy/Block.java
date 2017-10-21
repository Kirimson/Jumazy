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
	public static Block blockFactory(int type, Coordinate coord) {
		Block tempBlock = null;
		
		switch(type) {
			case 0: tempBlock = new CornerDownLeft(coord);break;
			case 1: tempBlock = new CornerDownRight(coord);break;
			case 2: tempBlock = new StraightVertical(coord);break;
			case 3: tempBlock = new StraightHorizontal(coord);break;
			case 4: tempBlock = new CornerUpLeft(coord);break;
			case 5: tempBlock = new CornerUpRight(coord);break;
			case 6: tempBlock = new Cross(coord);break;
			default: tempBlock = new Cross(coord);break;
		}
		
		return tempBlock;
	}
	
	/**
	 * Overload of {@link #blockFactory(int, Coordinate)}, uses a random number to generate a type value for a block.
	 * Taking in extra precautions to try and make the maze more navigable
	 * @param coord {@link Coordinate} object to be passed to the {@link Block} the Factory will create
	 * @return
	 */
	public static Block blockFactory(Coordinate coord) {
		int type = 6;
		Random rnd = new Random();
		
		if(rnd.nextInt(3) == 0) {
			int gen = rnd.nextInt(9);
			if(gen < 6) {
				type = 6;
			}
			else if(gen < 8) {
				type = 2;
			}
			else
				type = 3;
		}
		else
		{
			type = rnd.nextInt(6);
		}
		
		return blockFactory(type, coord);
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