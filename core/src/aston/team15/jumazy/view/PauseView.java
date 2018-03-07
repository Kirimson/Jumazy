package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseView {

    private Table table;
    private Table background;

    public PauseView (final JumazyController game){
        table = new Table();
        table.setFillParent(true);
        table.top().padTop(300f);

        background = new Table();
        background.setFillParent(true);
        background.top().padTop(0f);

        background.add(new Image(game.getSprite("pause-dialog")));

        TextButton resumeButton = new TextButton("RESUME", game.getSkin());

        resumeButton.addListener(new ClickListener() {
             public void clicked(InputEvent event, float x, float y) {
                 table.remove();
                 background.remove();
                 game.resume();
             }
         });

        MenuScreenButton quitButton = new MenuScreenButton("QUIT", MenuScreens.MAIN_MENU_SCREEN, game);

        table.add(resumeButton).pad(10);
        table.row();
        table.add(quitButton).pad(10);
    }

    public void addPauseScreen(Stage stage){
        stage.addActor(background);
        stage.addActor(table);
    }
}
