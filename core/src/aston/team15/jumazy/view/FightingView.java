package aston.team15.jumazy.view;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aston.team15.jumazy.controller.JumazyController;

public class FightingView extends Stage {

    private Table background;
    private Table foreground;

    public FightingView (final JumazyController game){
        background = new Table();
        background.setFillParent(true);
        background.top().padTop(100f);

        foreground = new Table();
        foreground.setFillParent(true);

        background.add(new Image(new Texture("fightingbg.png")));
		background.setSize(JumazyController.WORLD_WIDTH/4*3, JumazyController.WORLD_HEIGHT/4*3);        
        foreground.top().padTop(120f);
		foreground.add(new Image(new Texture("player1.png"))).left();
		foreground.add(new Image(new Texture("skele1.png"))).right();

        this.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.F){
    				playSound(new File("../assets/snd/correct.wav"));
    				remove();
    				game.resume();
                }
                return true;
            }
        });
    }

    public void start(){
        this.addActor(background);
        this.addActor(foreground);
    }

    public void remove() {
        background.remove();
        foreground.remove();
    }

	public void playSound(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
