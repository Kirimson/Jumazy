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
import com.badlogic.gdx.utils.Align;

import aston.team15.jumazy.controller.JumazyController;

public class FightingView extends Stage {

    private Table fightBar;
    private Table contents;
	float health1Percent;
	float health2Percent;
	DiceView dice;
	DiceView dice2;

    public FightingView (final JumazyController game){
    	float scale = 2f;
        fightBar = new Table();
//        fightBar.debugAll();
//        fightBar.setFillParent(true);
//        fightBar.top().padTop(100f);
		fightBar.setBackground(new Image(new Texture("fightbarPNG.png")).getDrawable());
		fightBar.setSize(JumazyController.WORLD_WIDTH, 124); 

        contents = new Table();
        contents.setFillParent(true);    
//        contents.top().padTop(120f);
//        Table health1 = new Table();
//        Table health2 = new Table();
//        
//        health1.setFillParent(true);    
//        health2.setFillParent(true);    
//		health1.setBackground(new Image(new Texture("health.png")).getDrawable());
//		health2.setBackground(new Image(new Texture("health.png")).getDrawable());
		Image health1 = new Image(new Texture("health.png"));
		Image health2 = new Image(new Texture("health.png"));
		
		health1.setScale(scale);
		health1.setScaleX(health1Percent*scale);
//		health1.setFillParent(true);

//		fightBar.add(health1);
//		fightBar.padRight(300);
//		fightBar.add(health2);

		dice2 = new DiceView((JumazyController.WORLD_WIDTH/2) - game.getSprite("number1").originalWidth - 9, 23,
						game.getSprite("number1"));
		dice = new DiceView((JumazyController.WORLD_WIDTH/2) + 7 , 23,
						game.getSprite("number2"));
		
		fightBar.add(contents);
		contents.add(dice);
		contents.add(dice2);
		fightBar.add(contents);

        this.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.SPACE){
                	
            		
                	update(health1Percent-=0.1,health2Percent);
                	fightBar.remove();
                	start();
                	System.out.println(health1Percent);

                }

                if(keycode == Input.Keys.F){


        			playSound(new File("../assets/snd/correct.wav"));
        			resume(game);
                }
                
                return true;
            }
        });
        
        if(health1Percent <= 0||health2Percent <= 0) {
        }
    }

	public void update(float health1, float health2){
		setHealth(health1,health2);
    }

	public void start(){
        this.addActor(fightBar);
    }

    public void resume(JumazyController game) {
        fightBar.remove();
		game.resume();
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
	
	public void setHealth(float health1, float health2) {
		health1Percent = health1;
		health2Percent = health2;
	}

	public void rollDice(JumazyController game, int finalDie) {
		
		contents.add(dice);
		contents.add(dice2);
		fightBar.add(contents);
		dice.setDie(finalDie);
		dice2.setDie(finalDie);
	}
		
}
