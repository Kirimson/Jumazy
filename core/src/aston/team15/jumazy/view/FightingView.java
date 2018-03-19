package aston.team15.jumazy.view;

import java.util.LinkedHashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FightingView extends Stage {

    private Table fightBar;
    private int playerMaxHP;
	private int playerCurrentHP;
	private int playerStrength;

	private int enemyMaxHP;
	private int enemyCurrentHP;
	private int enemyStrength;

	private float playerHealthPercent =1;
	private float enemyHealthPercent =1;
	private DiceView dice;
	private DiceView dice2;
	private Random rng;
	private int playerRoll;
	private int enemyRoll;
	private float scale = 1.5f;
	private Image playerHealth;
	private Image enemyHealth;
	private float timeSinceFightDone = 0f;
	private JumazyController game;
	private PlayerView player;
	private int fightDirection;
	private int reward;

	FightingView(final JumazyController game, Viewport view) {
		super(view);
    	this.game = game;
    	rng = new Random();

        fightBar = new Table(game.getSkin());

        fightBar.setBackground(new TextureRegionDrawable(game.getSprite("fight-bar")));
		fightBar.setHeight(124);
		fightBar.setWidth(JumazyController.WORLD_WIDTH);


		playerHealth = new Image(game.getSprite("health"));
		enemyHealth = new Image(game.getSprite("health"));
		
		playerHealth.setScaleX(scale);
		enemyHealth.setScaleX(scale);

		dice = new DiceView((JumazyController.WORLD_WIDTH/2) - game.getSprite("number1").originalWidth - 9, 23, game);
		dice2 = new DiceView((JumazyController.WORLD_WIDTH/2) + 7 , 23, game);
		
		fightBar.add(playerHealth).left().expandX().height(124f).padLeft(37f);
		fightBar.add(enemyHealth).right().expandX().height(124f).left().padLeft(260f);
    }

    private void resumeGame() {
		remove();
		timeSinceFightDone = 0f;
		boolean won = !(playerCurrentHP <= 0);
		game.stopFight(won, playerCurrentHP, reward);
    }
	
	public void setupNewFight(LinkedHashMap<String, Integer> playerStats,
							  LinkedHashMap<String, Integer> enemyStats, PlayerView player, int direction) {
    	this.player = player;
    	fightDirection = direction;
		playerMaxHP = playerStats.get("Max Health");
		playerCurrentHP = playerStats.get("Health");
		playerStrength = playerStats.get("Strength");

		enemyMaxHP = enemyStats.get("Max Health");
		enemyCurrentHP = enemyStats.get("Health");
		enemyStrength = enemyStats.get("Strength");
		reward = enemyStats.get("Reward");

		playerHealthPercent = (float)playerCurrentHP / (float)playerMaxHP;
		enemyHealthPercent = (float)enemyCurrentHP / (float)enemyMaxHP;

		playerHealth.setScaleX(scale * playerHealthPercent);
		enemyHealth.setScaleX(scale * enemyHealthPercent);
		GameSound.playPreFightSound();
	}

	public void rollDice() {
	    if(fightNotFinished()) {
            if (dice.isRollFinished()) {
                if (dice.getStage() == null) {
                    this.addActor(dice);
                    this.addActor(dice2);
                }
                player.act(Gdx.graphics.getDeltaTime(), fightDirection, 2);
                playerRoll = rng.nextInt(5) + 1 + playerStrength;

				System.out.println("player strength: "+ playerStrength);

                if(playerRoll > 13)
                	playerRoll = 13;

                dice.setDie(playerRoll);
                dice.roll();

                enemyRoll = rng.nextInt(5) + 1 + enemyStrength;

				if(enemyRoll > 13)
					enemyRoll = 13;

                dice2.setDie(enemyRoll);
                dice2.roll();
                System.out.println("rolled: " + playerRoll + " and " + enemyRoll);
            }
        }
	}

    private boolean fightNotFinished() {
	    return (playerCurrentHP > 0) && (enemyCurrentHP > 0);
    }

    public void winnerAttack() {
		if (playerRoll > enemyRoll) {
			enemyCurrentHP -= (playerRoll - enemyRoll);
		} else if (enemyRoll > playerRoll) {
			playerCurrentHP -= (enemyRoll - playerRoll);
		}
		
		playerHealthPercent = (float)playerCurrentHP / (float)playerMaxHP;
		enemyHealthPercent = (float)enemyCurrentHP / (float)enemyMaxHP;
	}

	public void show() {
		// TODO Auto-generated method stub

		this.addActor(fightBar);
	}
	
	@Override
	public void draw() {
		super.draw();

		if(dice.isRollFinished()) {
			if(playerCurrentHP <=0) {
				playerHealthPercent = 0;
				timeSinceFightDone += Gdx.graphics.getRawDeltaTime();
			}
			if(enemyCurrentHP <=0) {
				enemyHealthPercent = 0;
				timeSinceFightDone += Gdx.graphics.getRawDeltaTime();
			}

			playerHealth.setScaleX(scale* playerHealthPercent);
			enemyHealth.setScaleX(scale* enemyHealthPercent);

			if(timeSinceFightDone > 1f)
				resumeGame();
		}
	}
	
	private void remove() {
		fightBar.remove();
		dice.remove();
		dice2.remove();
	}
		
}
