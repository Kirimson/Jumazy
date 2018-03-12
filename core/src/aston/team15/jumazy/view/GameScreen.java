package aston.team15.jumazy.view;

import java.util.ArrayList;
import java.util.Random;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.model.MazeModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aston.team15.jumazy.controller.JumazyController;

public class GameScreen implements Screen {

	private JumazyController game;
	private Stage gameStage;
	private Stage uiStage;

	private ArrayList<PlayerView> players;
	private int currentPlayerIndex;
	private FitViewport viewport;
	private FitViewport uiViewport;
	private int blockSpriteDimensions = 32;
	private QuestionUI questionUI;
	private PauseView pauseStage;
	private HeadsUpDisplay hud;
	private int[] currentPlayerStats;
	private InputMultiplexer multiplexer;

	private DiceView dice;

	public GameScreen(JumazyController aGame, int playerAmount, String[][] maze, int[] firstPlayerStats) {
		game = aGame;
		viewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		gameStage = new Stage(viewport);
		uiViewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		uiStage = new Stage(uiViewport);
		players = new ArrayList<PlayerView>();
		questionUI = new QuestionUI(game);
		pauseStage = new PauseView(game);
		currentPlayerStats = firstPlayerStats;
		multiplexer = new InputMultiplexer();

		GameSound.playGameStartMusic();
		GameSound.stopMenuMusic();

		for (int mazeX = 0; mazeX < maze.length; mazeX++) {
			for (int mazeY = 0; mazeY < maze[0].length; mazeY++) {
				Actor newActor;

				switch (maze[mazeX][mazeY]) {
				case "O":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite(randomFloorTexture()));
					break;
				case "#":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite(randomWallTexture()));
					break;
				case "^":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("wall-no-edge")); //(findWallType(maze, mazeX, mazeY)));
					break;
				case "T":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("floor-trap-spikes"));
					break;
				case "V":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("victory-statue"));
					break;
				case "W":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("floor-single-water"));
					break;
				case "a":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("floor-left-water"));
					break;
				case "b":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("floor-middle-water"));
					break;
				case "c":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("floor-right-water"));
					break;
				case "1":
				case "2":
				case "3":
				case "4":
					players.add(new PlayerView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("char" + maze[mazeX][mazeY])));
				default:
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite(randomFloorTexture()));
					break;
				}

				gameStage.addActor(newActor);
			}
		}

		for (PlayerView player : players) {
			gameStage.addActor(player);
		}

		currentPlayerIndex = 0;

		dice = new DiceView(players.get(0).getX() + 32f, players.get(0).getY() + 32f, game.getSprite("number1"));

		hud = new HeadsUpDisplay(game, currentPlayerIndex, currentPlayerStats);
		uiStage.addActor(hud);

		gameStage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {

				switch (keycode) {
				case Input.Keys.P:
					pause();
					break;
				default:
					game.handleGameInput(keycode);
				}

				return true;
			}
		});
		
		multiplexer.addProcessor(gameStage);
		multiplexer.addProcessor(pauseStage);
		multiplexer.addProcessor(uiStage);
	}

	public void setCurrentPlayerStats(int[] playerStats) {
		currentPlayerStats = playerStats;
	}
	
	private String randomWallTexture() {
		float wallType = new Random().nextFloat();
		
		if (wallType < 0.6)
			return "wall-plain";
		else if (wallType < 0.95)
			return "wall-brick-missing";
		else
			return "wall-leaves";
	}

	private String randomFloorTexture() {
		float floorType = new Random().nextFloat();

		if (floorType < 0.05)
			return "floor-squares-cracked";
		else if (floorType < 0.1)
			return "floor-squares-missing";
		else
			return "floor-squares";
	}

	public void createQuestion(String[] questionAndAns) {
		Table questionUIBG = new Table();
		questionUIBG.setFillParent(true);
		questionUIBG.top();
		questionUIBG.add(new Image(game.getSprite("scroll")));
		
		questionUI.displayQuestion(questionAndAns);
		uiStage.addActor(questionUIBG);
		uiStage.addActor(questionUI.getTable());
	}

	/**
	 * Check if question UI Actor is on a stage, if the actor returns null, riddle
	 * isn't open, otherwise, it is open
	 * 
	 * @return boolean if riddle is open
	 */
	public boolean isRiddleOpen() {
		return questionUI.getTable().getStage() != null;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
	}

	public void setCurrentPlayer(int newPlayerIndex) {
		currentPlayerIndex = newPlayerIndex;
		dice.setPosition(players.get(currentPlayerIndex).getX(), players.get(currentPlayerIndex).getY());
	}

	public void moveCurrentPlayerView(boolean canMove, int keycode) {
		if (canMove) {
			players.get(currentPlayerIndex).act(Gdx.graphics.getDeltaTime(), keycode);
			dice.decreaseRoll();

			int rollsLeft = dice.getRoll();
			if (rollsLeft > 0) {
				dice.updateSprite(game.getSprite("number" + rollsLeft));
				dice.act(keycode);
			} else
				dice.remove();
		}
	}

	public void setWeather(MazeModel.Weather weather, int width, int height){
		WeatherAnimation weatherAnimation = new WeatherAnimation(weather, width * blockSpriteDimensions, height * blockSpriteDimensions);
		gameStage.addActor(weatherAnimation.getAnimation());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// update camera position if needed
		panCameraTo(new Vector3(players.get(currentPlayerIndex).getX(), players.get(currentPlayerIndex).getY(), 1f));

		// draw stage
		if (!dice.isRollFinished()) {
			int number = dice.roll();
			dice.updateSprite(game.getSprite("number" + number));
		} else {
			dice.setPosition(players.get(currentPlayerIndex).getX(), players.get(currentPlayerIndex).getY());
		}

		gameStage.act(Gdx.graphics.getDeltaTime());
		gameStage.draw();

		// draw all UI
		hud.update(currentPlayerIndex, currentPlayerStats);
		uiStage.act(Gdx.graphics.getDeltaTime());
		uiStage.draw();

		pauseStage.act(Gdx.graphics.getDeltaTime());
		pauseStage.draw();
	}

	private void panCameraTo(Vector3 target) {
		if (viewport.getCamera().position != target) {
			Vector3 camPosition = viewport.getCamera().position;
			final float speed = 0.1f, invertSpeed = 1.0f - speed;
			camPosition.scl(invertSpeed);
			target.scl(speed);
			camPosition.add(target);
			viewport.getCamera().position.set(camPosition);
		}
	}

	public void rollDice(int finalDie) {
		gameStage.addActor(dice);
		dice.setDie(finalDie);
	}

	public int getCurrentPlayerNumber() {
		return currentPlayerIndex + 1;
	}

	@Override
	public void resize(int width, int height) {
		gameStage.getViewport().update(width, height, true);
		uiStage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		pauseStage.pause();
	}

	@Override
	public void resume() {
		pauseStage.remove();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}