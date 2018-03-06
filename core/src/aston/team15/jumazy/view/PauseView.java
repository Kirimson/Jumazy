package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseView {

    private Table table;

    public PauseView (final JumazyController theGame){
        table = new Table();
        table.setFillParent(true);
        table.center().padTop(330);

        Image pauseBackground = new Image(theGame.getSprite("pause-dialog"));

        TextButton resumeButton = new TextButton("Resume", theGame.getSkin());

        resumeButton.addListener(new ClickListener() {
             public void clicked(InputEvent event, float x, float y) {
                 table.remove();
                 pauseBackground.remove();
                 theGame.resume();
             }
         });

        MenuScreenButton quitButton = new MenuScreenButton("QUIT", MenuScreens.MAIN_MENU_SCREEN, theGame);

        table.add(resumeButton).pad(10);
        table.row();
        table.add(quitButton).pad(10);
    }

    public Table addPauseScreen(){
        return table;
    }
}
