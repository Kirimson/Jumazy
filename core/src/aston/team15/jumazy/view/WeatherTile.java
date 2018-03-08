package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class WeatherTile extends Image {

    private float animationTime;
    private Animation<TextureRegion> weatherAnimation;
    private float width, height;

    public WeatherTile(Animation<TextureRegion> animation, float width, float height) {
        weatherAnimation = animation;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        animationTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = weatherAnimation.getKeyFrame(animationTime);
        TiledDrawable tile = new TiledDrawable(currentFrame);

        tile.draw(batch, 0,0, width,height);
    }
}
