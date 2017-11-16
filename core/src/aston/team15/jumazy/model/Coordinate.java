package aston.team15.jumazy.model;

/**
 * Implementation of a Coordinate system for the maze and its inhabitants, such as {@link Block} and {@link Player}
 * @author kieran
 *
 */
public class Coordinate {
	private int x, y;

	/**
	 * Creates a new {@link Coordinate} object, setting x and y to values passed
	 * @param xVal x value of the object
	 * @param yVal y value of the object
	 */
	public Coordinate(int xVal, int yVal) {
		x = xVal;
		y = yVal;
	}
	
	/**
	 * Overwrite the old x and y values with ones in another {@link Coordinate} object
	 * @param newCoords a {@link Coordinate} object to overwrite this objects values
	 */
	public void setCoordinates(Coordinate newCoords) {
		x = newCoords.getX();
		y = newCoords.getY();
	}
	
	/**
	 * Returns the x value of this {@link Coordinate}
	 * @return int of x value of {@link Coordinate}
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y value of this {@link Coordinate}
	 * @return int of y value of {@link Coordinate}
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns the x and y value of this {@link Coordinate}
	 * @return int[] of x and y value of {@link Coordinate}
	 */
	public int[] getCoords() {
		int[] coords = {x,y};
		return coords;
	}
	
	/**
	 * Generates a String representation of this {@link Coordinate}
	 * @return String representation of {@link Coordinate}
	 */
	public String toString()
	{
		return "("+x+","+y+")";
	}
	
	public void setX(int val) {
		x = val;
	}
	
	public void setY(int val) {
		y = val;
	}
}
