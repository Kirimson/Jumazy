package aston.team15.jumazy.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.model.PlayerModel;
import aston.team15.jumazy.view.GameScreen;
import aston.team15.jumazy.view.MainMenuScreen;

//this follows the state design pattern, setScreen is an inherited function, but does what a setState function would do
public class JumazyController extends Game {

	public static final int WORLD_WIDTH = 1280, WORLD_HEIGHT = 720;
	private boolean debugOn;

	private MazeModel maze;
	private Skin gameSkin;
	private TextureAtlas textures;
	private int playerAmount = 4;

	public JumazyController() {
		super();
		maze = new MazeModel(4, 2, playerAmount);
		System.out.println(maze.toString());
	}

	@Override
	public void create() {
		textures = new TextureAtlas("jumazy-skin.atlas");
		Gdx.gl.glClearColor(1, 0, 0, 1);

		// using a skin, with json, png, and atlas, reduces a lot of the workload
		// needlessly put on the GPU when having to load in many individual png's
		gameSkin = new Skin(Gdx.files.internal("jumazy-skin.json"));

		setScreen(new MainMenuScreen(this));

		debugOn = true;
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}

	public TextureAtlas getAtlas() {
		return textures;
	}

	public Skin getSkin() {
		return gameSkin;
	}

	public void handleGameInput(int keycode) {
		GameScreen gameScreen = (GameScreen) getScreen();

		switch (keycode) {
		case Input.Keys.RIGHT:
			gameScreen.moveCurrentPlayerView(maze.moveCurrentPlayerModel(PlayerModel.MoveDirection.RIGHT));
			break;
		case Input.Keys.LEFT:
			gameScreen.moveCurrentPlayerView(maze.moveCurrentPlayerModel(PlayerModel.MoveDirection.LEFT));
			break;
		case Input.Keys.UP:
			gameScreen.moveCurrentPlayerView(maze.moveCurrentPlayerModel(PlayerModel.MoveDirection.UP));
			break;
		case Input.Keys.DOWN:
			gameScreen.moveCurrentPlayerView(maze.moveCurrentPlayerModel(PlayerModel.MoveDirection.DOWN));
			break;
		case Input.Keys.ENTER:
			gameScreen.setPlayerFocus(maze.passTurnToNextPlayer());
			break;
		default:
			break;
		}

		if (debugOn) {
			System.out.println(maze.toString());
		}
	}
}
