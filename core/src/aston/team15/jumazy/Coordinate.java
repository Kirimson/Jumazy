package aston.team15.jumazy;

public class Coordinate {
	private int x, y;

	public Coordinate(int xVal, int yVal) {
		x = xVal;
		y = yVal;
	}
	
	public void setCoordinates(Coordinate newCoords) {
		x = newCoords.getX();
		y = newCoords.getY();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int[] getCoords() {
		int[] coords = {x,y};
		return coords;
	}
	
	public String toString()
	{
		return "("+x+","+y+")";
	}
}
