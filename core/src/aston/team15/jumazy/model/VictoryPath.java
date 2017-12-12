package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class VictoryPath extends Path {

	public VictoryPath(Coordinate coords) {
		super(new Texture("VictoryTile.png"), coords);
	}

	public String toString() {
		return "victory";
	}
	
	public void showWon(int playerNumber) {
		System.out.println("Player " + playerNumber + " wins!");
	}
	
}
