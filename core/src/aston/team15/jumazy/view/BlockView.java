package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BlockView extends Actor {

	private Sprite sprite;
	private Sprite bgSprite;

	public BlockView(float xPos, float yPos, TextureRegion texture) {
		bgSprite = null;
		sprite = new Sprite(texture);
		sprite.setPosition(xPos, yPos);
		setBounds(sprite.getX(), sprite.getY(), sprite.getHeight(), sprite.getWidth());
		setName(""+(int)xPos/32+","+(int)yPos/32);
	}
	
	public BlockView(float xPos, float yPos, TextureRegion texture, TextureRegion backgroundTexture) {
		bgSprite = new Sprite(backgroundTexture);
		bgSprite.setPosition(xPos, yPos);
		sprite = new Sprite(texture);
		sprite.setPosition(xPos, yPos);
		setName(""+(int)xPos/32+","+(int)yPos/32);
		setBounds(sprite.getX(), sprite.getY(), sprite.getHeight(), sprite.getWidth());
	}

	public void unlockDoor(TextureRegion newTexture){
		sprite = new Sprite(newTexture);
		sprite.setPosition(bgSprite.getX(), bgSprite.getY());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (bgSprite != null) {
			bgSprite.draw(batch, parentAlpha);
		}
		sprite.draw(batch, parentAlpha);
	}
}
