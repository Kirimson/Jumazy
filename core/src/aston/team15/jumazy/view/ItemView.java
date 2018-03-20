package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Graphical representation of an item in the game
 *
 * @author Abdullah
 */
public class ItemView extends Actor {
	
	private Sprite sprite;
	private boolean visible;

	public ItemView(TextureRegion sprite) {
		this.sprite = new Sprite(sprite);
		visible = true;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		if (visible) {
			sprite.setSize(50, 50);
			sprite.draw(batch);
		}
	}

	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
