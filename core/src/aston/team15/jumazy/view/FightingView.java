package aston.team15.jumazy.view;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import aston.team15.jumazy.controller.JumazyController;

public class FightingView extends Stage {

    private Table fightBar;
    private Table background;
    float maxhp1;
	float maxhp2;
	float currhp1;
	float currhp2;
	float health1Percent=1;
	float health2Percent=1;
	DiceView dice;
	DiceView dice2;
	Random rng;
	int value1;
	int value2;
	float scale = 1.5f;
	Image health1;
	Image health2;
	private JumazyController game;
	
    public FightingView (final JumazyController game) {
    	this.game = game;
    	rng = new Random();
    	
        fightBar = new Table();
//        fightBar.debugAll();
        fightBar.bottom();
        fightBar.setFillParent(true);

        background = new Table();
        background.bottom();
        background.setFillParent(true);
        
        background.add(new Image(new Texture("fightbarPNG.png"))).growX().height(124f);

		health1 = new Image(new Texture("health.png"));
		health2 = new Image(new Texture("health.png"));
		
		health1.setScaleX(scale);
//		health1.setScaleX(health1Percent*scale);
		health2.setScaleX(scale);
//		health2.setScaleX(health2Percent*scale);
		
		System.out.println(health1Percent);
		System.out.println(health2Percent);
//		health1.setFillParent(true);

//		fightBar.add(health1);
//		fightBar.add(health2);

		dice = new DiceView((JumazyController.WORLD_WIDTH/2) - game.getSprite("number1").originalWidth - 9, 23, game);
		dice2 = new DiceView((JumazyController.WORLD_WIDTH/2) + 7 , 23, game);
		
//		l = new Label("tets", game.getSkin());
		
//		fightBar.add(contents);
//		contents.add(dice);
//		contents.add(dice2);
//		fightBar.add(contents);

//		fightBar.debugAll();
		
		fightBar.add(health1).left().expandX().height(124f).padLeft(37f);
		fightBar.add(health2).right().expandX().height(124f).left().padLeft(260f);
		
        if(health1Percent <= 0||health2Percent <= 0) {
        }
    }

    public void resume(JumazyController game) {
        remove();
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
	
	public void setHealth(int health1, int health2) {
		maxhp1 = health1;
		currhp1= maxhp1;
		maxhp2 = health2;
		currhp2= maxhp2;
		
		health1Percent = currhp1/maxhp1;
		health2Percent = currhp2/maxhp2;
	}

	public void rollDice() {
		
		if(dice.getStage() == null) {
			this.addActor(dice);
			this.addActor(dice2);
		}
		
		value1 = rng.nextInt(12)+1;
		dice.setDie(value1);
		dice.roll();
				
		value2 = rng.nextInt(10)+1;
		dice2.setDie(value2);
		dice2.roll();
		System.out.println("rolled: " + value1 + " and " + value2);
	}

	public void winnerAttack() {
		if (value1>value2) {
			currhp2 -= (value1 - value2);
		} else if (value2 > value1) {
			currhp1 -= (value2 - value1);
		}
		
		health1Percent = currhp1/maxhp1;
		health2Percent = currhp2/maxhp2;
		
		if(currhp1<0) {
			currhp1 = 0;
			resume(game);
		}
		if(currhp2<0) {
			currhp2 = 0;
			resume(game);
		}
		
//		health1.setScaleX(health1Percent);
//		health2.setScaleX(health2Percent);

		System.out.println("player1 percentage "+ health1Percent);
		System.out.println("player2 percentage "+health2Percent);
		
		System.out.println("player1 current "+currhp1);
		System.out.println("player1 max "+maxhp1);

		System.out.println("player2 current "+currhp2);
		System.out.println("player2 max"+maxhp2);
	}

	public void show() {
		// TODO Auto-generated method stub
	   
		this.addActor(background);
		this.addActor(fightBar);
	}
	
	public void remove() {
		background.remove();
		fightBar.remove();
		dice.remove();
		dice2.remove();
	}
		
}
