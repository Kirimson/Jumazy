package aston.team15.jumazy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aston.team15.jumazy.model.Item;
import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.model.MazeModel.Weather;
import aston.team15.jumazy.model.PlayerModel;
import aston.team15.jumazy.model.QuestionRetriever;
import aston.team15.jumazy.view.GameScreen;
import aston.team15.jumazy.view.MainMenuScreen;
import aston.team15.jumazy.view.VictoryScreen;

/**
 * This class represents the controller in this applications
 * Model-View-Controller architecture. It uses the facade design pattern, as it
 * is the only class in this package used to interface with others.
 * 
 * It creates the facade classes for the view and model packages as well, who in
 * turn initialise other classes. It also initialises/declares global final
 * variables, and handles file I/O for the game skin and questions.
 * 
 * As part of its role, this class uses the state-like design pattern to manage
 * the games screens.
 * 
 * @author Abdullah, Kieran, Shayan, Jawwad
 *
 */
public class JumazyController extends Game {

	public static final int WORLD_WIDTH = 1280, WORLD_HEIGHT = 720;
	public static final boolean DEBUG_ON = true;
	private boolean questionsRetrieved = false;
	private MazeModel maze;
	private Skin gameSkin;
	private TextureAtlas textures;
	private QuestionRetriever questionRetriever;
	private String texturePack;

	/**
	 * Provides the logic for creating objects from the contents of the game skins
	 * atlas and json files. These few files contain every texture used throughout
	 * the game, therefore reducing the need to include many different files in the
	 * assets folder and applying excess stress to the GPU.
	 * 
	 * Also sets global OpenGL clear colour, the initial game screen, and
	 * initialises a {@link QuestionRetriever} object.
	 * 
	 * Calls the super constructor for the parent class.
	 */
	@Override
	public void create() {
		texturePack = "current";
		textures = new TextureAtlas("jumazyskin/current/jumazy-skin.atlas");
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);

		// using a skin, with json, png, and atlas, reduces a lot of the workload
		// needlessly put on the GPU when having to load in many individual png's
		gameSkin = new Skin(Gdx.files.internal("jumazyskin/current/jumazy-skin.json"));

		questionRetriever = new QuestionRetriever();

		setScreen(new MainMenuScreen(this));

		if (DEBUG_ON)
			System.out.println("Ready.");
	}

	/**
	 * Renders the screen, with logic that is mainly provided by the LibGDX
	 * framework.
	 */
	@Override
	public void render() {
		super.render();
	}

	/**
	 * Set player amount and start game, by creating a new
	 * {@link aston.team15.jumazy.model.MazeModel} object for the maze, and
	 * switching to a new instance of {@link aston.team15.jumazy.view.GameScreen}.
	 * 
	 * @param playerAmount
	 *            the number of players the user has chosen to play with
	 * @param playerOrder
	 *            the order in which the character for each player has been selected
	 *            in the form of an ArrayList
	 */
	public void setPlayerAmountAndStartGame(int playerAmount, ArrayList<PlayerModel.CharacterName> playerOrder) {
		maze = new MazeModel(4, 4, playerAmount, playerOrder);
		setScreen(new GameScreen(this, playerAmount, maze.getMaze(), maze.getCurrentPlayer().getStats(),
				maze.getWeather()));
	}

	/**
	 * Sets the question type and difficulty after they've been chosen by the user.
	 * 
	 * @param levels
	 *            a HashMap object that contains information representing levels and
	 *            difficulty for questions
	 */
	public void setQuestionType(HashMap<String, String> levels) {
		questionRetriever.chosenTypes(levels);
	}

	/**
	 * Returns a specified sprite from the skin files.
	 * 
	 * @param name
	 *            A String that represents the texture contained in the skin files.
	 * @return The corresponding texture as an AtlasRegion object
	 */
	public AtlasRegion getSprite(String name) {
		return textures.findRegion(name);
	}

	/**
	 * Returns the Skin object representing the current skin for the game.
	 * 
	 * @return Games Skin object.
	 */
	public Skin getSkin() {
		return gameSkin;
	}

	/**
	 * Updates the chosen skin for the game.
	 * 
	 * @param path
	 *            A String that represents the name of the new skin.
	 */
	public void updateSkin(String path) {
		textures = new TextureAtlas("jumazyskin/"+path + "/jumazy-skin.atlas");
		gameSkin = new Skin(Gdx.files.internal("jumazyskin/"+path + "/jumazy-skin.json"));
		texturePack = path;
	}

	public String getTexturePackName() {
		return texturePack;
	}

	/**
	 * Handles the logic for interfacing between the model and view when the view
	 * detects user input during gameplay. Updates the model depending on what has
	 * been detected by the view, and then updates the view according to the new
	 * model.
	 * 
	 * @param keycode
	 *            The keycode that represents which key was pressed by the user.
	 * @return True or False depending on the success of the operation that was
	 *         executed as a result of user input
	 */
	public boolean handleGameInput(int keycode) {
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
				int moveStyle = maze.moveCurrentPlayerModel(keycode);
				gameScreen.moveCurrentPlayerView(moveStyle, keycode);

				if (moveStyle != 0 && maze.getCurrentPlayer().isOnTrap()) {
					if (new Random().nextDouble() < (0.15
							+ ((double) maze.getCurrentPlayer().getStatFromHashMap("Agility")) / 100)) {
						gameScreen.getHUD().setPlayerConsoleText("Your agility helped you escape this trap!");
					} else {
						gameScreen.getHUD()
								.setPlayerConsoleText("You landed on a trap! Answer the question to keep going!");
						if(questionsRetrieved == false) {
							questionRetriever.retrieveFromFile();
							questionsRetrieved = true;
						}
						String[] questionAndAns = questionRetriever.questionSelector();
						gameScreen.createQuestion(questionAndAns);
					}
				}

				if (maze.getCurrentPlayer().isOnChest()) {
					double discriminant = new Random().nextDouble();

					if (JumazyController.DEBUG_ON) {
						System.out.println("DISC: " + discriminant);
						System.out.println("LUCK: " + (double) maze.getCurrentPlayer().getStatFromHashMap("Luck") / 10);
						System.out.println("0.6 + LUCK = "
								+ (0.4 + (double) maze.getCurrentPlayer().getStatFromHashMap("Luck") / 10));
					}

					if (discriminant < 0.4 + ((double) maze.getCurrentPlayer().getStatFromHashMap("Luck")) / 10) {
						if (maze.getCurrentPlayer().obtainRandomItemFromChest()) {
							gameScreen.updateCurrentInventoryAndStats(maze.getCurrentPlayer().getInventory(), true);
							gameScreen.openChest(maze.getCurrentPlayer().getPosition(), maze.getCurrentPlayer()
									.getInventory().get(maze.getCurrentPlayer().getInventory().size() - 1));
						} else {
							gameScreen.getHUD()
									.setPlayerConsoleText("Looks like there's no space left in your inventory!");
						}
					} else if (maze.getWeather() == Weather.SNOW && maze.getCurrentPlayer().isOnStuckChest()) {
						gameScreen.getHUD().setPlayerConsoleText("The cold has stuck this chest closed! Try again.");
					} else {
						gameScreen.getHUD().setPlayerConsoleText("Seems like there's nothing inside this chest.");
						gameScreen.openChest(maze.getCurrentPlayer().getPosition(), null);
					}
				}

				if (maze.getCurrentPlayer().isOnVictorySquare())
					setScreen(new VictoryScreen(this, gameScreen.getCurrentPlayerNumber()));
				

				if (maze.getCurrentPlayer().isOnDoor()) {
					gameScreen.unlockDoor(maze.getDoorPositions(maze.getCurrentPlayer()));
					if (maze.getCurrentPlayer().pickedDoor()) {
						gameScreen.getHUD().setPlayerConsoleText("You used your intelligence to pick this door's lock!");
						maze.getCurrentPlayer().setAlreadyPicked(true);
					}
				}

				gameScreen.renderInventory(maze.getCurrentPlayer().getInventory());
				return true;
			} else {
				return false;
			}
		case Input.Keys.ENTER:
			if (maze.getCurrentPlayer().getMovesLeft() < 1 && gameScreen.riddleIsntOpen()) {
				gameScreen.clearInventory();
				gameScreen.updateCurrentPlayer(maze.passTurnToNextPlayer(), maze.getCurrentPlayer().getStats());
				gameScreen.updateCurrentInventoryAndStats(maze.getCurrentPlayer().getInventory(), false);
				gameScreen.renderInventory(maze.getCurrentPlayer().getInventory());

				boolean bigLight;
				if(maze.getCurrentPlayer().getInventory().contains(Item.TORCH))
				    bigLight = true;
				else
				    bigLight = false;

                gameScreen.setBigLight(bigLight);

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
	
	public void resetQuestions(){
		questionsRetrieved = false;
		questionRetriever.clear();
	}

    public void stopFight(boolean won, int playerHealth, int reward) {
		GameScreen gameScreen = (GameScreen) getScreen();
		if(won){
			int newStat = maze.getCurrentPlayer().getStatFromHashMap("Strength") + reward;
			if (newStat >= 6) {
				maze.getCurrentPlayer().getStats().replace("Strength", 6);
				gameScreen.getHUD().setPlayerConsoleText("You won! Your strength is maxed out, why not try the FINAL BOSS?");
			} else {
				gameScreen.getHUD().setPlayerConsoleText("You won! Your strength increased by "+reward+"!");
				maze.getCurrentPlayer().editStat("Strength", reward, true);
			}

			if(playerHealth != maze.getCurrentPlayer().getStats().get("Health")){
				maze.getCurrentPlayer().editStat("Health", playerHealth, false);
				gameScreen.getHUD().updateItemStat(Item.APPLE);
			}

			gameScreen.getHUD().updateItemStat(Item.SWORD);
			gameScreen.removeMonster(maze.removeMonster(maze.getCurrentPlayer().getPosition()));
			gameScreen.showStatUpgrade("muscle");

		} else {
			maze.getCurrentPlayer().editStat("Health", maze.getCurrentPlayer().getStats().get("Max Health")/2,
					false);
			gameScreen.getHUD().updateItemStat(Item.APPLE);
			gameScreen.getHUD().setPlayerConsoleText("You lost! Try again when you're ready!");
			moveCurrentPlayerToStartOfTurn();
		}

        gameScreen.stopFight();
    }

	/**
	 * Handles the logic for question answering, particularly when a riddle is
	 * incorrect. Updates the maze to move player to the position they were at
	 * before this turn, and then the view to match the new state of the maze model.
	 */
	public void moveCurrentPlayerToStartOfTurn() {
		GameScreen gameScreen = (GameScreen) getScreen();
		gameScreen.movePlayerToStartOfMove(maze.getCurrentPlayer().moveToStartOfTurn());
	}

	public LinkedHashMap<String, Integer> generateMonsterStats(LinkedHashMap<String, Integer> playerstats) {
		String monsterType = maze.getMonsterType(maze.getCurrentPlayer());

		LinkedHashMap<String, Integer> monsterStats = new LinkedHashMap<String, Integer>();

		int hp, str, reward;

		switch (monsterType){
			case "Z": //big boss
				hp = 18;
				str = 15 - playerstats.get("Strength");
				reward = 0;
				break;
			case "X": //common 1
				hp = 11;
				str = 3;
				reward = 2;
				break;
			case "E": //common 2
			default :
				hp = 10;
				str = 2;
				reward = 1;
				break;
		}

		monsterStats.put("Max Health", hp);
		monsterStats.put("Health", hp);
		monsterStats.put("Strength", str);
		monsterStats.put("Reward", reward);

		return monsterStats;
	}

	public void correctRiddle() {
		int statAmount = maze.getCurrentPlayer().getStats().get("Intelligence");
		if(statAmount + 1 < 6) {
			maze.getCurrentPlayer().editStat("Intelligence", 1, true);
			GameScreen gameScreen = (GameScreen) getScreen();

			gameScreen.getHUD().setPlayerConsoleText("You did well with that trap! Your intelligence went up!");
			gameScreen.showStatUpgrade("brain");
			gameScreen.getHUD().updateIntelligence();
		}
	}
}
