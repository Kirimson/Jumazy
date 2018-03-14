package aston.team15.jumazy.controller;

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
import aston.team15.jumazy.view.VictoryScreen;

//this follows the state design pattern, setScreen is an inherited function, but does what a setState function would do
public class JumazyController extends Game {

	private QuestionRetriever questionRetriever = new QuestionRetriever();

	public static final int WORLD_WIDTH = 1280, WORLD_HEIGHT = 720;
	public static final boolean DEBUG_ON = true;
	public static String Texturejson;
	public String Texturepath;
	private MazeModel maze;
	private Skin gameSkin;
	private TextureAtlas textures;
	
	@Override
	public void create() {
		setTexturePack("jumazyskin/current/jumazy-skin.atlas", "jumazyskin/current/jumazy-skin.json");
		textures = new TextureAtlas(Texturepath);
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);

		// using a skin, with json, png, and atlas, reduces a lot of the workload
		// needlessly put on the GPU when having to load in many individual png's
		gameSkin = new Skin(Gdx.files.internal(Texturejson));

		setScreen(new MainMenuScreen(this));

		if (DEBUG_ON)
			System.out.println("Ready.");
	}

	public void update(String Path) {
		textures = new TextureAtlas(Path + "/jumazy-skin.atlas");
		gameSkin = new Skin(Gdx.files.internal(Path + "/jumazy-skin.json"));
	}

	public void setPlayerAmountAndStartGame(int playerAmount) {

		maze = new MazeModel(4, 2, playerAmount);
		setScreen(new GameScreen(this, playerAmount, maze.getMaze(), maze.getCurrentPlayer().getStatsArray()));

		GameScreen gameScreen = (GameScreen) getScreen();

		if (maze.getWeather() != MazeModel.Weather.SUN)
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

	public void setTexturePack(String txt, String json) {
		Texturepath = txt;
		Texturejson = json;
	}

	public String getTexturePath() {
		return Texturepath;
	}

	public boolean handleGameInput(int keycode) {
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
				
				if (maze.getCurrentPlayer().isOnChest()) {
					int originalInventorySize = maze.getCurrentPlayer().getInventory().size();
					maze.getCurrentPlayer().obtainRandomItem();
					gameScreen.setCurrentPlayerStats(maze.getCurrentPlayer().getStatsArray());
					int newInventorySize = maze.getCurrentPlayer().getInventory().size();
					if (newInventorySize > originalInventorySize) {
						String newItemString = maze.getCurrentPlayer().getInventory().get(newInventorySize-1).toString();
						gameScreen.getHUD().setPlayerConsoleText("You just picked up a " + newItemString + "!");
						
					}
				}

				if (maze.getCurrentPlayer().isOnVictorySquare()) {
					setScreen(new VictoryScreen(this, gameScreen.getCurrentPlayerNumber()));
				}
				
				return true;
			} else {
				return false;
			}
		case Input.Keys.ENTER:
			if (maze.getCurrentPlayer().getMovesLeft() < 1 && !gameScreen.isRiddleOpen()) {
				gameScreen.setCurrentPlayer(maze.passTurnToNextPlayer());
				gameScreen.setCurrentPlayerStats(maze.getCurrentPlayer().getStatsArray());
				
				return true;
			} else { 
				return false;
			}
		case Input.Keys.SPACE:
			if (maze.getCurrentPlayer().canRoll()) {
				gameScreen.rollDice(maze.rollForPlayer());
			}
			return true;
		default:
			return true;
		}

	}
}
