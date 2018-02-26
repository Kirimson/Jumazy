package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class VictoryPath extends Path {

	public VictoryPath() {
		super(TextureConstants.getTexture("VictoryTile"), new Coordinate(0,0));
	}

	public VictoryPath(Coordinate coords) {
		super(TextureConstants.getTexture("VictoryTile"), coords);
	}

	public String toString() {
		return "V";
	}
	
	public void showWon(int playerNumber) {
		System.out.println("Player " + playerNumber + " wins!");
	}
	
}
