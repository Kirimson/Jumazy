package aston.team15.jumazy.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.view.GameScreen;
import aston.team15.jumazy.view.MainMenuScreen;

//this follows the state design pattern, setScreen is an inherited function, but does what a setState function would do
public class JumazyController extends Game {

	public static final int WORLD_WIDTH = 1280, WORLD_HEIGHT = 720;

	private MazeModel maze;
	private Skin gameSkin;
	private TextureAtlas textures;
	private boolean debugOn;

	@Override
	public void create() {
		textures = new TextureAtlas("jumazy-skin.atlas");
		Gdx.gl.glClearColor(1, 0, 0, 1);

		// using a skin, with json, png, and atlas, reduces a lot of the workload
		// needlessly put on the GPU when having to load in many individual png's
		gameSkin = new Skin(Gdx.files.internal("jumazy-skin.json"));

		setScreen(new MainMenuScreen(this));

		debugOn = false;
		if (debugOn)
			System.out.println("Ready.");
	}

	public void setPlayerAmountAndStartGame(int playerAmount) {
		maze = new MazeModel(4, 2, playerAmount, debugOn);
		setScreen(new GameScreen(this, playerAmount, maze.getMaze()));
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
			gameScreen.moveCurrentPlayerView(maze.moveCurrentPlayerModel(keycode), keycode);
			break;
		case Input.Keys.ENTER:
			if(maze.getCurrentPlayer().getMovesLeft() < 1)
				gameScreen.setCurrentPlayer(maze.passTurnToNextPlayer());
			break;
		case Input.Keys.SPACE:
			if(maze.getCurrentPlayer().canRoll()) {
				gameScreen.rollDice(maze.rollForPlayer());
			}
			break;
		default:
			break;
		}

	}
}
