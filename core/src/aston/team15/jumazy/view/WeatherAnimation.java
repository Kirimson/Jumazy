package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.MazeModel.Weather;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WeatherAnimation{

    private WeatherTile weatherAnimation;

    public WeatherAnimation(Weather weather, JumazyController game, float width, float height) {

        TextureAtlas animation = new TextureAtlas("JumazyAnimations/pack.atlas");

        switch(weather){
            case RAIN:
                weatherAnimation = new WeatherTile( new Animation<TextureRegion>(0.05f, animation.findRegions("Rain"), Animation.PlayMode.LOOP), width, height);
                break;
        }
    }

    public WeatherTile getAnimation() {
        return weatherAnimation;
    }

}
