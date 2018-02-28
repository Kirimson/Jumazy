package aston.team15.jumazy.model;

public class Room {

	private int roomSize = 10;
	private int[][] tiles;
	
	public Room() {
		tiles = createRoom();
	}
	
	public int[][] createRoom() {
		int[][] roomTiles = new int[roomSize][roomSize];
		
		for (int x = 0; x < roomSize; x++) {
			for (int y = 0; y < roomSize; y++) {
				int thisTile = 0;
				
				if (x == 0 || x == roomSize-1 || y == 0 || y == roomSize -1) {
					thisTile = 1;
				}
				 
				roomTiles[x][y] = thisTile;
			}
		}
		
		return roomTiles;
	}
	
	public int[][] getTiles(){
		return tiles;
	}
	
	public String toString(){
		String roomString = "";
		
		for (int x = 0; x < roomSize; x++) {
			roomString += "\n";
			
			for (int y = 0; y < roomSize; y++) {
				roomString += tiles[x][y] + " ";
			}
		}
		
		return roomString;
	}
	
	public int[] getFirstLine() {
		return tiles[0];
	}
	
}
