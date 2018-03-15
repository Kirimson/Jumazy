package aston.team15.jumazy.view;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.MazeModel;

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
	private Integer[] currentPlayerStats;
	private InputMultiplexer multiplexer;
	private boolean isPaused;

	private DiceView dice;

	/**
	 * Creates a new GameScreen object. Comes with multiple stages for the game, ui
	 * and pause layers. Creates the maze in a graphical form, using the text format
	 * as a key for graphics
	 * 
	 * @param aGame
	 *            {@link JumazyController} object
	 * @param playerAmount
	 *            amount of players
	 * @param maze
	 *            strin array representation of the maze
	 * @param integers
	 *            current players stats
	 */
	public GameScreen(JumazyController aGame, int playerAmount, String[][] maze, Integer[] integers) {
		game = aGame;
		viewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		gameStage = new Stage(viewport);
		uiViewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		uiStage = new Stage(uiViewport);
		players = new ArrayList<PlayerView>();
		questionUI = new QuestionUI(game);
		pauseStage = new PauseView(game);
		currentPlayerStats = integers;
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
							game.getSprite(generateWallTexture(maze, mazeX, mazeY)));
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
							game.getSprite(generateWaterTexture(maze, mazeX, mazeY)));
					break;
				case "C":
					// newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX *
					// blockSpriteDimensions,
					// game.getSprite("chest-gold-closed"), game.getSprite(randomFloorTexture()));
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							new Sprite(new Texture(Gdx.files.internal("Chest-Gold-Closed.png"))),
							game.getSprite(randomFloorTexture()));
					break;
				case "D":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite(generateLockedDoorTexture(maze, mazeX, mazeY)),
							game.getSprite(randomFloorTexture()));
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

		hud = new HeadsUpDisplay(game, currentPlayerIndex, currentPlayerStats);
		hud.setDiceLabel("Hit\nSpace!");
		uiStage.addActor(hud);

		dice = new DiceView(JumazyController.WORLD_WIDTH - game.getSprite("number1").originalWidth - 29, 23,
				game.getSprite("number1"));

		gameStage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {

				switch (keycode) {
				case Input.Keys.ESCAPE:
					pause();
					break;
				case Input.Keys.ENTER:
					hud.updateForNewPlayer(game.handleGameInput(keycode));
					break;
				default:
					game.handleGameInput(keycode);
				}

				return true;
			}
		});

		multiplexer.addProcessor(gameStage);
		multiplexer.addProcessor(uiStage);
	}

	/**
	 * Sets the vies stat field to reflect the current players stats
	 * 
	 * @param playerStats
	 *            array of stats
	 */
	public void setCurrentPlayerStats(Integer[] playerStats) {
		currentPlayerStats = playerStats;
	}

	/**
	 * Creates the correct sprite for a locked door (right/left and top/bottom door)
	 * 
	 * @param maze
	 *            the maze string
	 * @param mazeX
	 *            x position
	 * @param mazeY
	 *            y position
	 * @return texture string
	 */
	private String generateLockedDoorTexture(String[][] maze, int mazeX, int mazeY) {
		// right door
		if (maze[mazeX - 1][mazeY].equals("D"))
			return "arrow";
		// left door
		if (maze[mazeX + 1][mazeY].equals("D"))
			return "apple";
		// top door
		if (maze[mazeX][mazeY - 1].equals("D"))
			return "arrow";
		// bottom door
		if (maze[mazeX][mazeY + 1].equals("D"))
			return "apple";

		return "arrow";
	}

	private String generateWaterTexture(String[][] maze, int mazeX, int mazeY) {

		if (mazeY > 0 && mazeY < maze[0].length - 1) {
			if (maze[mazeX][mazeY - 1].equals("W") && maze[mazeX][mazeY + 1].equals("W"))
				return "floor-middle-water";
			else if (maze[mazeX][mazeY - 1].equals("W") && !maze[mazeX][mazeY + 1].equals("W"))
				return "floor-right-water";
			else if (!maze[mazeX][mazeY - 1].equals("W") && maze[mazeX][mazeY + 1].equals("W"))
				return "floor-left-water";
		}

		return "floor-single-water";
	}

	/**
	 * generates correct type of wall depending on walls relative to this wall
	 * 
	 * @return string for wall texture
	 * @param maze
	 *            the maze string
	 * @param mazeY
	 *            y position in maze
	 * @param mazeX
	 *            x position in maze
	 */
	private String generateWallTexture(String[][] maze, int mazeX, int mazeY) {

		// very bottom corners
		if ((mazeY == 0 && mazeX == 0) || (mazeY == maze[0].length - 1 && mazeX == 0))
			return "wall-no-edge";

		// bottom of pillar wall
		if (mazeY > 0 && mazeY < maze[0].length - 1 && mazeX > 0) {
			if (maze[mazeX][mazeY - 1].equals("O") && maze[mazeX][mazeY + 1].equals("O")
					&& !maze[mazeX - 1][mazeY].equals("#")) {
				return randomWallTexture();
			}
		}

		// wall going across
		if (mazeY > 0 && mazeY < maze[0].length - 1) {
			if (maze[mazeX][mazeY - 1].equals("#") || maze[mazeX][mazeY + 1].equals("#")) {
				if (mazeX > 0) {
					if (!maze[mazeX - 1][mazeY].equals("#"))
						return randomWallTexture();
				}
				return "wall-no-edge";
			}
		}

		// wall going up
		if (mazeX > 0 && mazeX < maze.length - 1) {
			if (maze[mazeX - 1][mazeY].equals("#") || maze[mazeX + 1][mazeY].equals("#"))
				return "wall-no-edge";
		}

		// very corner
		return "wall-plain";
	}

	/**
	 * generates random types of walls, 90% of normal wall, 5% of leaf wall and 5%
	 * of missing brick
	 * 
	 * @return type of wall
	 */
	private String randomWallTexture() {
		float wallType = new Random().nextFloat();
		if (wallType < 0.8)
			return "wall-plain";
		else if (wallType < 0.95)
			return "wall-brick-missing";
		else
			return "wall-leaves";
	}

	/**
	 * generates a random floor texture, 5% chance of cracked floor/square missing
	 * 90% chance of normal tile,
	 * 
	 * @return string for floor texture
	 */
	private String randomFloorTexture() {
		float floorType = new Random().nextFloat();

		if (floorType < 0.05)
			return "floor-squares-cracked";
		else if (floorType < 0.1)
			return "floor-squares-missing";
		else
			return "floor-squares";
	}

	/**
	 * Creates a new question and adds QuestionUI to the uiStage
	 * 
	 * @param questionAndAns
	 */
	public void createQuestion(String[] questionAndAns) {
		questionUI.displayQuestion(questionAndAns);
		questionUI.addToStage(uiStage);
	}

	/**
	 * Check if question UI Actor isnt on the stage
	 * 
	 * @return boolean if riddle isnt open
	 */
	public boolean riddleIsntOpen() {
		return questionUI.notActive();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
	}

	/**
	 * sets the current player to a new player, adjusts the dice's position for when
	 * it's next going to be drawn
	 * 
	 * @param newPlayerIndex
	 *            new player number
	 */
	public void setCurrentPlayer(int newPlayerIndex) {
		currentPlayerIndex = newPlayerIndex;
		// dice.setPosition(players.get(currentPlayerIndex).getX(),
		// players.get(currentPlayerIndex).getY());
	}

	/**
	 * Moves the current player in the view, calls the current players act method,
	 * passing the keycode to move the player on screen Then decreases/removes dice
	 * actors sprite accordingly
	 * 
	 * @param canMove
	 * @param keycode
	 */
	public void moveCurrentPlayerView(boolean canMove, int keycode) {
		if (canMove) {
			players.get(currentPlayerIndex).act(Gdx.graphics.getDeltaTime(), keycode);
			dice.decreaseRoll();

			int rollsLeft = dice.getRoll();
			if (rollsLeft > 0) {
				dice.updateSprite(game.getSprite("number" + rollsLeft));
				dice.act(keycode);
				hud.setPlayerConsoleText("You have " + dice.getRoll() + " moves left, use the ARROW KEYS to move.");
			} else {
				dice.remove();
				hud.setDiceLabel("No\nMoves\nLeft!");
				hud.setPlayerConsoleText("No moves left, press ENTER to pass your turn to the next player.");
			}
		}
	}

	/**
	 * handles moving the player view back to the start position of their turn, also
	 * removes the dice from the stage
	 * 
	 * @param position
	 */
	public void movePlayerToStartOfMove(int[] position) {
		players.get(currentPlayerIndex).moveToStartOfTurn(position[0], position[1]);
		dice.remove();
	}

	/**
	 * Applies the current weather affect to the maze, overlays the entire maze,
	 * rather than the entire screen.
	 * 
	 * @param weather
	 *            enum type of weather in the maze
	 * @param width
	 *            width of the maze, got from the maze model
	 * @param height
	 *            height of the maze, got from the maze model
	 */
	public void setWeather(MazeModel.Weather weather, int width, int height) {
		WeatherAnimation weatherAnimation = new WeatherAnimation(weather, width * blockSpriteDimensions,
				height * blockSpriteDimensions);
		gameStage.addActor(weatherAnimation.getAnimation());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// update camera position if needed
		panCameraTo(new Vector3(players.get(currentPlayerIndex).getX(), players.get(currentPlayerIndex).getY(), 1f));

		// draw stage
		if (!dice.isRollFinished()) {
			Gdx.input.setInputProcessor(pauseStage);
			int number = dice.roll();
			dice.updateSprite(game.getSprite("number" + number));
		} else if (dice.isRollFinished() && (dice.getRoll() == dice.getRollResult())) {
			if (!isPaused)
				Gdx.input.setInputProcessor(multiplexer);

			if (dice.getRoll() == 8)
				hud.setPlayerConsoleText("You rolled an 8, use the ARROW KEYS to move.");
			else
				hud.setPlayerConsoleText("You rolled a " + dice.getRoll() + ", use the ARROW KEYS to move.");
		}

		gameStage.act(Gdx.graphics.getDeltaTime());
		gameStage.draw();

		// draw all UI
		hud.update(currentPlayerIndex + 1, currentPlayerStats);
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
		uiStage.addActor(dice);
		dice.setDie(finalDie);
	}

	public int getCurrentPlayerNumber() {
		return currentPlayerIndex + 1;
	}

	public HeadsUpDisplay getHUD() {
		return hud;
	}

	@Override
	public void resize(int width, int height) {
		gameStage.getViewport().update(width, height, true);
		uiStage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		Gdx.input.setInputProcessor(pauseStage);
		pauseStage.pause();
		isPaused = true;
	}

	@Override
	public void resume() {
		Gdx.input.setInputProcessor(multiplexer);
		pauseStage.remove();
		isPaused = false;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void openChest(int[] pos) {
		for (Actor a : gameStage.getActors()) {
			if (a instanceof BlockView) {
				if (a.getName().equals(pos[1] + "," + pos[0]))
					((BlockView) a).changeSprite(new Sprite(new Texture(Gdx.files.internal("Chest-Gold-Open.png"))));
			}
		}
	}

	/**
	 * Finds the door actor that the player stepped on, then updates its sprite,
	 * casting the Actor to a BlockView
	 * 
	 * @param pos
	 *            door's position for both blocks
	 */
	public void unlockDoor(int[] pos) {
		hud.setPlayerConsoleText("You just unlocked a Door!");

		for (Actor a : gameStage.getActors()) {
			if (a instanceof BlockView) {
				if (a.getName().equals(pos[1] + "," + pos[0]))
					((BlockView) a)
							.changeSprite(game.getSprite(generateUnlockedDoorSprite(pos[1], pos[0], pos[3], pos[2])));

				if (a.getName().equals(pos[3] + "," + pos[2]))
					((BlockView) a)
							.changeSprite(game.getSprite(generateUnlockedDoorSprite(pos[3], pos[2], pos[1], pos[0])));
			}
		}
	}

	/**
	 * generates the correct texture for an unlocked door. Works differently to
	 * other texture generators as it edits existing actors and needs to compare
	 * itself against another position within the maze that has been pre-defined
	 * 
	 * @param thisRow
	 *            this door part's row
	 * @param thisCol
	 *            this door part's column
	 * @param otherRow
	 *            the other door part's row
	 * @param otherCol
	 *            the other door part's column
	 * @return string for the door's texture
	 */
	private String generateUnlockedDoorSprite(int thisRow, int thisCol, int otherRow, int otherCol) {

		// right door
		if (thisRow - 1 == otherRow)
			return "checkbox-on";
		// left door
		if (thisRow + 1 == otherRow)
			return "checkbox-off";
		// top door
		if (thisCol - 1 == otherCol)
			return "checkbox-on";
		// bottom door
		if (thisCol + 1 == otherCol)
			return "checkbox-off";
		return "arrow";
	}
}