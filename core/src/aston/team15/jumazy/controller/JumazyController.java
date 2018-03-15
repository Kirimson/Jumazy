package aston.team15.jumazy.controller;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aston.team15.jumazy.model.Item;
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
	private MazeModel maze;
	private Skin gameSkin;
	private TextureAtlas textures;

	@Override
	public void create() {
		textures = new TextureAtlas("jumazyskin/current/jumazy-skin.atlas");
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);

		// using a skin, with json, png, and atlas, reduces a lot of the workload
		// needlessly put on the GPU when having to load in many individual png's
		gameSkin = new Skin(Gdx.files.internal("jumazyskin/current/jumazy-skin.json"));

		setScreen(new MainMenuScreen(this));

		if (DEBUG_ON)
			System.out.println("Ready.");
	}

	public void setPlayerAmountAndStartGame(int playerAmount) {

		maze = new MazeModel(4, 2, playerAmount);
		setScreen(new GameScreen(this, playerAmount, maze.getMaze(), maze.getCurrentPlayer().getStatsArray(), maze.getWeather()));

//		GameScreen gameScreen = (GameScreen) getScreen();
//
//		if (maze.getWeather() != MazeModel.Weather.SUN)
//			gameScreen.setWeather(maze.getWeather(), maze.getMaze()[0].length, maze.getMaze().length);
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

	public void update(String path) {
		textures = new TextureAtlas(path + "/jumazy-skin.atlas");
		gameSkin = new Skin(Gdx.files.internal(path + "/jumazy-skin.json"));
	}

	public boolean handleGameInput(int keycode) {

		// protects from some dumb error I found but cant replicate. Crashed when trying
		// to cast VictoryScreen to
		// GameScreen, checking instance now as a safeguard
		GameScreen gameScreen;
		if (getScreen() instanceof GameScreen)
			gameScreen = (GameScreen) getScreen();
		else
			return false;

		switch (keycode) {
		case Input.Keys.RIGHT:
		case Input.Keys.LEFT:
		case Input.Keys.UP:
		case Input.Keys.DOWN:
			if (gameScreen.riddleIsntOpen() && maze.getCurrentPlayer().getMovesLeft() > 0) {
				boolean canMove = maze.moveCurrentPlayerModel(keycode);
				gameScreen.moveCurrentPlayerView(canMove, keycode);

				if (canMove && maze.getCurrentPlayer().isOnTrap()) {
					questionRetriever.selectFile();
					String[] questionAndAns = questionRetriever.retrieveRiddle();
					gameScreen.createQuestion(questionAndAns);
				}

				if (maze.getCurrentPlayer().isOnChest()) {
					if (new Random().nextDouble() < (0.3 + maze.getCurrentPlayer().getLuck()/10)) {
						int originalInventorySize = maze.getCurrentPlayer().getInventory().size();
						maze.getCurrentPlayer().obtainRandomItemFromChest();
						gameScreen.setCurrentPlayerStats(maze.getCurrentPlayer().getStatsArray());
						int newInventorySize = maze.getCurrentPlayer().getInventory().size();
						if (newInventorySize > originalInventorySize) {
							Item newItem = maze.getCurrentPlayer().getInventory().get(newInventorySize - 1);
							if (newItem != Item.KEY) {
								gameScreen.getHUD().setPlayerConsoleText("You just picked up a " + newItem.toString() + "! "
										+ newItem.getStatEffected() + " increased by " + newItem.getValue() + "!");
							} else {
								gameScreen.getHUD()
										.setPlayerConsoleText("You just picked up a key! Which door will you open?");
							}
						}
					} else {
						gameScreen.getHUD().setPlayerConsoleText("Seems like there's nothing inside this chest.");
					}
					
					gameScreen.openChest(maze.getCurrentPlayer().getPosition());
				}

				if (maze.getCurrentPlayer().isOnVictorySquare()) {
					setScreen(new VictoryScreen(this, gameScreen.getCurrentPlayerNumber()));
				}

				if (maze.getCurrentPlayer().isOnDoor()) {
					gameScreen.unlockDoor(maze.getDoorPositions(maze.getCurrentPlayer()));
				}

				return true;
			} else {
				return false;
			}
		case Input.Keys.ENTER:
			if (maze.getCurrentPlayer().getMovesLeft() < 1 && gameScreen.riddleIsntOpen()) {
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

	public void incorrectRiddle() {
		GameScreen gameScreen = (GameScreen) getScreen();
		gameScreen.movePlayerToStartOfMove(maze.getCurrentPlayer().moveToStartOfTurn());
	}
}
