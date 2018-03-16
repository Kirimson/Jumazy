package aston.team15.jumazy.controller;

import aston.team15.jumazy.model.PlayerModel;
import aston.team15.jumazy.view.VictoryScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aston.team15.jumazy.model.Item;
import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.model.PlayerModel;
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
		textures = new TextureAtlas("jumazyskin/current/jumazy-skin.atlas");
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);

		// using a skin, with json, png, and atlas, reduces a lot of the workload
		// needlessly put on the GPU when having to load in many individual png's
		gameSkin = new Skin(Gdx.files.internal("jumazyskin/current/jumazy-skin.json"));

		setScreen(new MainMenuScreen(this));

		if (DEBUG_ON)
			System.out.println("Ready.");
	}

	public void setPlayerAmountAndStartGame(int playerAmount, ArrayList<PlayerModel.CharacterName> playerOrder) {

		maze = new MazeModel(4, 2, playerAmount);
		setScreen(new GameScreen(this, playerAmount, maze.getMaze(), maze.getCurrentPlayer().getStats(), maze.getWeather()));

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
		
		PlayerModel currentPlayer = maze.getCurrentPlayer();
		
		Random randGen = new Random();

		switch (keycode) {
		case Input.Keys.RIGHT:
		case Input.Keys.LEFT:
		case Input.Keys.UP:
		case Input.Keys.DOWN:
			if (gameScreen.riddleIsntOpen() && currentPlayer.getMovesLeft() > 0) {
				boolean canMove = maze.moveCurrentPlayerModel(keycode);
				gameScreen.moveCurrentPlayerView(canMove, keycode);

				if (canMove && currentPlayer.isOnTrap()) {
					questionRetriever.selectFile();
					String[] questionAndAns = questionRetriever.retrieveRiddle();
					gameScreen.createQuestion(questionAndAns);
				}

				if (currentPlayer.isOnChest()) {
					if (randGen.nextDouble() < 0.6 + currentPlayer.getStatFromHashMap("Luck")/10) {
						currentPlayer.obtainRandomItemFromChest();
						gameScreen.updateCurrentInventoryAndStats(currentPlayer.getInventory());
					} else {
						gameScreen.getHUD().setPlayerConsoleText("Seems like there's nothing inside this chest.");
					}
					
					gameScreen.openChest(currentPlayer.getPosition());
				}

				if (currentPlayer.isOnVictorySquare()) {
					setScreen(new VictoryScreen(this, gameScreen.getCurrentPlayerNumber()));
				}

				if (currentPlayer.isOnDoor()) {
					gameScreen.unlockDoor(maze.getDoorPositions(currentPlayer));
				}

				return true;
			} else {
				return false;
			}
		case Input.Keys.ENTER:
			if (currentPlayer.getMovesLeft() < 1 && gameScreen.riddleIsntOpen()) {
				gameScreen.updateCurrentPlayer(maze.passTurnToNextPlayer(), maze.getCurrentPlayer().getStats() );
				gameScreen.setCurrentPlayerStats(currentPlayer.getStats());
				
				return true;
			} else {
				return false;
			}
		case Input.Keys.SPACE:
			if (currentPlayer.canRoll()) {
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
