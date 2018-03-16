package aston.team15.jumazy.controller;

import aston.team15.jumazy.view.VictoryScreen;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.model.QuestionRetriever;
import aston.team15.jumazy.view.GameScreen;
import aston.team15.jumazy.view.MainMenuScreen;

//this follows the state design pattern, setScreen is an inherited function, but does what a setState function would do
public class JumazyController extends Game {
	
	private QuestionRetriever questionRetriever = new QuestionRetriever();

	public static final int WORLD_WIDTH = 1280, WORLD_HEIGHT = 720;
	public static final boolean DEBUG_ON = true;

	private MazeModel maze;
	private Skin gameSkin;
	private TextureAtlas textures;

	@Override
	public void create() {
		textures = new TextureAtlas("jumazyskin/jumazy-skin.atlas");
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);

		// using a skin, with json, png, and atlas, reduces a lot of the workload
		// needlessly put on the GPU when having to load in many individual png's
		gameSkin = new Skin(Gdx.files.internal("jumazyskin/jumazy-skin.json"));

		setScreen(new MainMenuScreen(this));

		if (DEBUG_ON)
			System.out.println("Ready.");
	}

	public void setPlayerAmountAndStartGame(int playerAmount) {
		maze = new MazeModel(5, 5, playerAmount);
		setScreen(new GameScreen(this, playerAmount, maze.getMaze(), maze.getCurrentPlayer().getStatsArray()));

		GameScreen gameScreen = (GameScreen) getScreen();

		if(maze.getWeather() != MazeModel.Weather.SUN)
			gameScreen.setWeather(maze.getWeather(), maze.getMaze()[0].length, maze.getMaze().length);
	}
	
	public void setQuestionType(HashMap<String, String> levels) {
			questionRetriever.chosenTypes(levels);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}

	public TextureAtlas.AtlasRegion getSprite(String name) {
		return textures.findRegion(name);
	}

	public Skin getSkin() {
		return gameSkin;
	}

	public void handleGameInput(int keycode) {
		GameScreen gameScreen = (GameScreen) getScreen();
		
		switch (keycode) {
		case Input.Keys.RIGHT:
		case Input.Keys.LEFT:
		case Input.Keys.UP:
		case Input.Keys.DOWN:
			if (!gameScreen.isRiddleOpen() && maze.getCurrentPlayer().getMovesLeft() > 0) {
				gameScreen.moveCurrentPlayerView(maze.moveCurrentPlayerModel(keycode), keycode);

				if (maze.getCurrentPlayer().isOnTrap()) {
					
					questionRetriever.selectFile();
					String[] questionAndAns = questionRetriever.retrieveRiddle();
					gameScreen.createQuestion(questionAndAns);
				}

				if (maze.getCurrentPlayer().isOnVictorySquare()) {
					setScreen(new VictoryScreen(this, gameScreen.getCurrentPlayerNumber()));
				}
			}
			break;
		case Input.Keys.ENTER:
			if (maze.getCurrentPlayer().getMovesLeft() < 1 && !gameScreen.isRiddleOpen()) {
				gameScreen.setCurrentPlayer(maze.passTurnToNextPlayer());
				gameScreen.setCurrentPlayerStats(maze.getCurrentPlayer().getStatsArray());
			}
			break;
		case Input.Keys.SPACE:
			if (maze.getCurrentPlayer().canRoll()) {
				gameScreen.rollDice(maze.rollForPlayer());
			}
			break;
		default:
			break;
		}

	}

    public void stopFight() {
        GameScreen gameScreen = (GameScreen) getScreen();
        gameScreen.stopFight();
    }
}
