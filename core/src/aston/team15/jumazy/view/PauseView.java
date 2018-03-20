package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseView extends Stage {

    private Table table;
    private Table background;
    MoveToAction tableMA;
    MoveToAction backgroundMA;

    public PauseView (final JumazyController game){
        table = new Table();
        table.setFillParent(true);
        table.top().padTop(-400);

        background = new Table();
        background.setFillParent(true);
        background.top().padTop(-400);

		tableMA = new MoveToAction();
		backgroundMA = new MoveToAction();
		tableMA.setPosition(0f, -525f);
		tableMA.setDuration(0.30f);
		backgroundMA.setPosition(0f, -300f);
		backgroundMA.setDuration(0.3f);
		table.addAction(tableMA);
		background.addAction(backgroundMA);

        background.add(new Image(game.getSprite("pause-dialog")));

        Skin skin = game.getSkin();
        
        Label title = new Label("PAUSE", skin);
		title.setColor(Color.WHITE);
		title.setFontScale(2f);
		
        JumazyButton resumeButton = new JumazyButton("Resume", game.getSkin());

        resumeButton.addListener(new ClickListener() {
             public void clicked(InputEvent event, float x, float y) {
            	 GameSound.playButtonSound();
            	
                 remove();
                 game.resume();
             }
         });

        this.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.ESCAPE){
                    remove();
                    game.resume();
                }
                return true;
            }
        });

        MenuScreenButton quitButton = new MenuScreenButton("Quit", MenuScreens.MAIN_MENU_SCREEN, game);

		table.add(title).padBottom(40);
		table.row();
        table.add(resumeButton).pad(20);
        table.row();
        table.add(quitButton).pad(20);
    }

    public void pause(){
        this.addActor(background);
        this.addActor(table);
    }

    public void remove() {

    	background.remove();
        table.remove();
    }
}
