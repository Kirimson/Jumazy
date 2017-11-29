package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class ActionBlock extends Block {

	protected boolean actionTriggered = false;
	
	public ActionBlock(Texture tex, Coordinate coords) {
		super(tex, coords);
	}
	
	public void actionSwitch() {
		actionTriggered = !actionTriggered;
	}
	
}
