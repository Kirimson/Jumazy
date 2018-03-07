package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class VictoryScreen extends MenuScreen {

    public VictoryScreen(JumazyController game, int winningPlayer) {
        super(game);

        Image background = new Image(new Texture("victoryBackground.jpg"));
        background.setSize(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
        stage.addActor(background);
        table.top().padTop(225f);

        Skin skin = game.getSkin();

        Label victoryString = new Label("Congratulations, Player "+winningPlayer+"!", skin);
        table.add(victoryString);
        table.row().pad(10);

        MenuScreenButton playAgainButton = new MenuScreenButton("Play Again", MenuScreens.START_GAME_SCREEN, game);
        table.add(playAgainButton);
        table.row().pad(10);

        TextButton quitButton = new TextButton("Quit", game.getSkin());
        quitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(quitButton);
        table.row().pad(10);

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
