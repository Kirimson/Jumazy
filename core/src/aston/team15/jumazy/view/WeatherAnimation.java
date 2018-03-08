package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.MazeModel.Weather;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class WeatherAnimation{

    private Table table;

    public WeatherAnimation(Weather weather, JumazyController game) {

        table = new Table();
        table.setFillParent(true);
        table.center();
        table.debugAll();
        TextureAtlas animation = new TextureAtlas("JumazyAnimations/pack.atlas");

        WeatherTile weatherAnimation;
        switch(weather){
            default:
//                weatherAnimation = new WeatherTile( new Animation<TextureRegion>(0.05f, animation.findRegions("Rain"), Animation.PlayMode.LOOP));
                weatherAnimation = new WeatherTile( new Animation<TextureRegion>(0.05f, animation.findRegions("Rain"), Animation.PlayMode.LOOP));
                break;
        }

        table.add(weatherAnimation);
//        table.row();
//        table.add(weatherAnimation);
    }

    public Table getTable () {
        return table;
    }

}
