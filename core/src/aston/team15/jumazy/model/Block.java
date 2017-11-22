package aston.team15.jumazy.model;

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
	protected int orientation;
	protected Exit exits;
	
	/**
	 * Base constructor for {@link Block} sets the texture and Coordinates of the object
	 * Creates a new {@link Exit} object, according to the type of {@link Block}
	 * @param tex
	 * @param coords
	 */
	public Block(Texture tex, Coordinate coords, int orientation) {
		this.blockTexture = tex;
		this.coords = coords;
		this.orientation = orientation;
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
			return new Path(coord, 0);
		else
			return new Wall(coord, 0, type);
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
	 * Returns the orientation of the {@link Block}
	 * @return returns an int from 0 to 3, denoting the the {@link Block} orientation
	 */
	public int getOrientation() {
		return orientation;
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
}