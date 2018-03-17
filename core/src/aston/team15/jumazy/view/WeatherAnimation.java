package aston.team15.jumazy.view;

import aston.team15.jumazy.model.MazeModel.Weather;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

public class WeatherAnimation extends Image{

    private float animationTime;
    private Animation<TextureRegion> animation;
    private float width, height;

    public WeatherAnimation(Weather weather, float mazeWidth, float mazeHeight) {
        this.width = mazeWidth;
        this.height = mazeHeight;

        TextureAtlas animationAtlas = new TextureAtlas("JumazyAnimations/pack.atlas");

        switch(weather){
            case RAIN:
                animation = new Animation<TextureRegion>(0.05f, animationAtlas.findRegions("Rain"), LOOP);
                break;
            case SNOW:
                animation = new Animation<TextureRegion>(0.05f, animationAtlas.findRegions("Snow"), LOOP);
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        animationTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(animationTime);
        TiledDrawable tile = new TiledDrawable(currentFrame);

        tile.draw(batch, 0,0, width,height);
    }
}
