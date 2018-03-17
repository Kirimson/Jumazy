package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Lighting extends Image{

    private Sprite lightImage;
    private float currentScale = 1f;
    private float baseScale = 1f;
    private boolean increaseSize = true;

    public Lighting() {
        lightImage = new Sprite(new Texture("light.png"));
        lightImage.setOriginCenter();
    }

    public void increaseLightSize(boolean increase){
        if(increase)
            baseScale = 1.2f;
        else
            baseScale = 1f;

        currentScale = baseScale;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){

        if(increaseSize){
            currentScale += Gdx.graphics.getRawDeltaTime()/10;
            if(currentScale > baseScale + 0.2f)
                increaseSize = false;
        }

        if(!increaseSize){
            currentScale -= Gdx.graphics.getRawDeltaTime()/10;
            if(currentScale < baseScale + 0.01f)
                increaseSize = true;
        }

        lightImage.setScale(currentScale);

        lightImage.draw(batch);
    }

}
