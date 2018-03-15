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

    private Table fightBar;
    private Table contents;

    public FightingView (final JumazyController game){
        fightBar = new Table();
//        fightBar.setFillParent(true);
//        fightBar.top().padTop(100f);
		fightBar.setBackground(new Image(new Texture("fightbar.png")).getDrawable());
		fightBar.setSize(JumazyController.WORLD_WIDTH, 124); 

        contents = new Table();
        contents.setFillParent(true);    
        contents.top().padTop(120f);
		contents.add(new Image(new Texture("player1.png"))).left();
		contents.add(new Image(new Texture("skele1.png"))).right();

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
        this.addActor(fightBar);
        this.addActor(contents);
    }

    public void remove() {
        fightBar.remove();
        contents.remove();
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
