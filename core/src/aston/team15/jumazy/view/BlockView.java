package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlockView extends Actor {

    private Sprite sprite;

    public BlockView(float xPos, float yPos, TextureRegion texture){
        sprite = new Sprite(texture);
        sprite.setPosition(xPos, yPos);
        setBounds(sprite.getX(), sprite.getY(), sprite.getHeight(), sprite.getWidth());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }
}
