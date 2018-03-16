package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Lighting extends Image{

    private Sprite lightImage;

    public Lighting() {
        lightImage = new Sprite(new Texture("light.png"));

    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        lightImage.draw(batch);
    }

}
