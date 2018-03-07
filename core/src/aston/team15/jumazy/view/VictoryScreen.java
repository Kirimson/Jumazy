package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class VictoryScreen extends MenuScreen {

    public VictoryScreen(JumazyController game, int winningPlayer) {
        super(game);

        Image background = new Image(new Texture("victoryBackground.jpg"));
        background.setSize(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
        stage.addActor(background);

        Skin skin = game.getSkin();

        Label victoryString = new Label("Congratulations, Player"+winningPlayer+"!", skin);

        table.top().padTop(250f);

        table.add(victoryString);

        stage.addActor(table);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
