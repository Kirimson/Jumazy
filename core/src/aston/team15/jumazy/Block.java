package aston.team15.jumazy;

import com.badlogic.gdx.graphics.Texture;

public class Block {
	
	private Texture blockTexture;
	private Coordinate coords;
	protected Exit exits;
	
	public Block(Texture tex, Coordinate coords) {
		this.blockTexture = tex;
		this.coords = coords;
		exits = new Exit(false, false, false, false);
	};

	public static Block blockFactory(int type, int x, int y) {
		Block tempBlock = null;
		
		switch(type) {
		case 0: tempBlock = new CornerDownLeft(new Coordinate(x,y));break;
		case 1: tempBlock = new CornerDownRight(new Coordinate(x,y));break;
		case 2: tempBlock = new StraightVertical(new Coordinate(x,y));break;
		case 3: tempBlock = new StraightHorizontal(new Coordinate(x,y));break;
		case 4: tempBlock = new CornerUpLeft(new Coordinate(x,y));break;
		case 5: tempBlock = new CornerUpRight(new Coordinate(x,y));break;
		case 6: tempBlock = new Cross(new Coordinate(x,y));break;
		default: tempBlock = new Cross(new Coordinate(x,y));break;
		}
		
		return tempBlock;
	}
	
	public Texture getTexture()
	{
		return blockTexture;
	}
	
	public Coordinate getCoords() {
		return coords;
	}
	
	public boolean checkExit(String direction) {
		return exits.checkExit(direction);
	}
	
	public boolean checkEntrance(String direction) {
		return exits.checkEntrance(direction);
	}
	
	public String toString() {
		return "im lazy, havent done this one yet";
	}
}