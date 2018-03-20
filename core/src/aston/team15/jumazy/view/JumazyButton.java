package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.GameSound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author Kieran
 */
public class JumazyButton extends TextButton {

    public JumazyButton(String text, Skin skin){
        super(text, skin);

        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                GameSound.playButtonSound();
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Pixmap cursor = new Pixmap(Gdx.files.internal("hand.png"));
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Pixmap cursor = new Pixmap(Gdx.files.internal("mouse.png"));
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));
            }
        });
    }
}
