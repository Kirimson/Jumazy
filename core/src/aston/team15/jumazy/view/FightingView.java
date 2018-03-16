package aston.team15.jumazy.view;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

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
        fightBar.bottom();
        fightBar.setFillParent(true);

        background = new Table();
        background.bottom();
        background.setFillParent(true);
        
        background.add(new Image(new Texture("fightbarPNG.png"))).growX().height(124f);

		health1 = new Image(new Texture("health.png"));
		health2 = new Image(new Texture("health.png"));
		
		health1.setScaleX(scale);
		health2.setScaleX(scale);
		
		System.out.println(health1Percent);
		System.out.println(health2Percent);

		dice = new DiceView((JumazyController.WORLD_WIDTH/2) - game.getSprite("number1").originalWidth - 9, 23, game);
		dice2 = new DiceView((JumazyController.WORLD_WIDTH/2) + 7 , 23, game);
		
		fightBar.add(health1).left().expandX().height(124f).padLeft(37f);
		fightBar.add(health2).right().expandX().height(124f).left().padLeft(260f);
		
    }

    public void resume(JumazyController game) {
    	Timer.schedule(new Task() {
			@Override
		    public void run() {
				remove();
				game.resume();
		    }
		}, 1);
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
		if(dice.isRollFinished()) {
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
	}

	public void winnerAttack() {
		if (value1>value2) {
			currhp2 -= (value1 - value2);
		} else if (value2 > value1) {
			currhp1 -= (value2 - value1);
		}
		
		health1Percent = currhp1/maxhp1;
		health2Percent = currhp2/maxhp2;

//		System.out.println("player1 percentage "+ health1Percent);
//		System.out.println("player2 percentage "+health2Percent);
//		
//		System.out.println("player1 current "+currhp1);
//		System.out.println("player1 max "+maxhp1);
//
//		System.out.println("player2 current "+currhp2);
//		System.out.println("player2 max"+maxhp2);
	}

	public void show() {
		// TODO Auto-generated method stub
	   
		this.addActor(background);
		this.addActor(fightBar);
	}
	
	@Override
	public void draw() {
		super.draw();
		if(dice.isRollFinished()) {
			if(currhp1<=0) {
				health1Percent = 0;
				resume(game);
			}
			if(currhp2<=0) {
				health2Percent = 0;
				resume(game);
			}
			health1.setScaleX(scale*health1Percent);
			health2.setScaleX(scale*health2Percent);
		}
	}
	
	public void remove() {
		background.remove();
		fightBar.remove();
		dice.remove();
		dice2.remove();
	}
		
}
