package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Lighting extends Image{

    private Sprite lightImage;
    private float timeScale = 1f;
    boolean increaseSize = true;

    public Lighting() {
        lightImage = new Sprite(new Texture("light.png"));
        lightImage.setOriginCenter();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){

        if(increaseSize){
            timeScale += Gdx.graphics.getRawDeltaTime()/10;
            if(timeScale > 1.2f)
                increaseSize = false;
        }

        if(!increaseSize){
            timeScale -= Gdx.graphics.getRawDeltaTime()/10;
            if(timeScale < 1.01f)
                increaseSize = true;
        }

        lightImage.setScale(timeScale);

        lightImage.draw(batch);
    }

}
