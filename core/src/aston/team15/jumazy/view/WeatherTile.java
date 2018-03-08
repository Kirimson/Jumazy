package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class WeatherTile extends Image {

    private float animationTime;
    private Animation<TextureRegion> weatherAnimation;

    public WeatherTile(Animation<TextureRegion> animation) {
        weatherAnimation = animation;

    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        animationTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = weatherAnimation.getKeyFrame(animationTime);
        batch.draw(currentFrame,0,0);
    }
}
