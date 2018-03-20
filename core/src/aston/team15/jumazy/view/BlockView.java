package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * A graphical representation of a block, which is then displayed on the maze. can have a top sprite and
 * a background sprite, and includes a name of its coordinate, to allow the block to be found within the view
 *
 * @author Kieran, Abdullah
 */
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

	public Sprite getSprite(){
	    return sprite;
    }

    public Sprite getBGSprite(){
        return bgSprite;
    }
	
	public BlockView(float xPos, float yPos, TextureRegion texture, TextureRegion backgroundTexture) {
		bgSprite = new Sprite(backgroundTexture);
		bgSprite.setPosition(xPos, yPos);
		sprite = new Sprite(texture);
		sprite.setPosition(xPos, yPos);
		setName(""+(int)xPos/32+","+(int)yPos/32);
		setBounds(sprite.getX(), sprite.getY(), sprite.getHeight(), sprite.getWidth());
	}

	public void changeSprite(TextureRegion newTexture){
		System.out.println(getName());
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
