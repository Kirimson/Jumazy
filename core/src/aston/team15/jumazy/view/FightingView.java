package aston.team15.jumazy.view;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class FightingView extends Stage {

    private Table fightBar;
    private float maxhp1;
	private float maxhp2;
	private float currhp1;
	private float currhp2;
	private float health1Percent=1;
	private float health2Percent=1;
	private DiceView dice;
	private DiceView dice2;
	private Random rng;
	private int value1;
	private int value2;
	private float scale = 1.5f;
	private Image health1;
	private Image health2;
	private float timeSinceFightDone = 0f;
	private JumazyController game;
	private PlayerView player;
	private int fightDirection;
	private HeadsUpDisplay hud;

	FightingView(final JumazyController game, HeadsUpDisplay hud) {
		this.hud = hud;
    	this.game = game;
    	rng = new Random();
    	
        fightBar = new Table(game.getSkin());
        fightBar.bottom();

        fightBar.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("fightbarPNG.png"))));
		fightBar.setHeight(124);
		fightBar.setWidth(JumazyController.WORLD_WIDTH);

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

    private void resumeGame() {
		remove();
		timeSinceFightDone = 0f;

		boolean won = !(currhp1 <= 0);

        System.out.println(won);

		game.stopFight(won);
    }
	
	public void setHealth(int health1, int health2, PlayerView player, int direction) {
    	this.player = player;
    	fightDirection = direction;
		maxhp1 = health1;
		currhp1= maxhp1;
		maxhp2 = health2;
		currhp2= maxhp2;

		System.out.println("new fight hp1 "+currhp1);
		System.out.println("new fight hp2 "+currhp2);

		health1Percent = currhp1/maxhp1;
		health2Percent = currhp2/maxhp2;
	}

	public void rollDice() {
	    if(fightNotFinished()) {
            if (dice.isRollFinished()) {
                if (dice.getStage() == null) {
                    this.addActor(dice);
                    this.addActor(dice2);
                }
                player.act(Gdx.graphics.getDeltaTime(), fightDirection, 2);
                value1 = rng.nextInt(12) + 1;
                dice.setDie(value1);
                dice.roll();

                value2 = rng.nextInt(10) + 1;
                dice2.setDie(value2);
                dice2.roll();
                System.out.println("rolled: " + value1 + " and " + value2);
            }
        }
	}

    private boolean fightNotFinished() {
	    return (currhp1 > 0) && (currhp2 > 0);
    }

    public void winnerAttack() {
		if (value1>value2) {
			currhp2 -= (value1 - value2);
		} else if (value2 > value1) {
			currhp1 -= (value2 - value1);
		}
		
		health1Percent = currhp1/maxhp1;
		health2Percent = currhp2/maxhp2;
	}

	public void show() {
		// TODO Auto-generated method stub

		this.addActor(fightBar);
	}
	
	@Override
	public void draw() {
		super.draw();

		if(dice.isRollFinished()) {
			if(currhp1<=0) {
				health1Percent = 0;
				timeSinceFightDone += Gdx.graphics.getRawDeltaTime();
			}
			if(currhp2<=0) {
				health2Percent = 0;
				timeSinceFightDone += Gdx.graphics.getRawDeltaTime();
			}

			health1.setScaleX(scale*health1Percent);
			health2.setScaleX(scale*health2Percent);

			if(timeSinceFightDone > 1f)
				resumeGame();
		}
	}
	
	public void remove() {
		fightBar.remove();
		dice.remove();
		dice2.remove();
	}
		
}
