package aston.team15.jumazy.view;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.Item;
import aston.team15.jumazy.model.MazeModel;
import aston.team15.jumazy.model.MazeModel.Weather;

public class GameScreen implements Screen {

	private Lighting light;
	private JumazyController game;
	private Stage gameStage;
	private Stage uiStage;

	private Weather weather;
	private ArrayList<PlayerView> players;
	private int currentPlayerIndex;
	private FitViewport viewport;
	private FitViewport uiViewport;
	private int blockSpriteDimensions = 32;
	private QuestionUI questionUI;
	private PauseView pauseStage;
	private FightingView fightingStage;
	private HeadsUpDisplay hud;
	private LinkedHashMap<String, Integer> currentPlayerStats;
	private InputMultiplexer multiplexer;
	private boolean inFight;
	private boolean isPaused;
	private ArrayList<Item> currentPlayerInventory;

	private DiceView dice;
	private boolean lighttest;

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
	 *            string array representation of the maze
	 * @param playerStats
	 *            current players stats
	 */
	public GameScreen(JumazyController aGame, int playerAmount, String[][] maze, LinkedHashMap<String, Integer> playerStats,
			Weather weather) {
		Pixmap cursor = new Pixmap(Gdx.files.internal("mouse.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));

		game = aGame;
		viewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		gameStage = new Stage(viewport);
		uiViewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		uiStage = new Stage(uiViewport);
		players = new ArrayList<PlayerView>();
		questionUI = new QuestionUI(game);
		pauseStage = new PauseView(game);
		fightingStage = new FightingView(game);
		currentPlayerStats = playerStats;
		multiplexer = new InputMultiplexer();
		currentPlayerInventory = new ArrayList<Item>();
		light = new Lighting();

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
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							new Sprite(new Texture(Gdx.files.internal("Chest-Gold-Closed.png"))),
							game.getSprite(randomFloorTexture()));
					break;
				case "D":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							new Sprite(new Texture(generateLockedDoorTexture(maze, mazeX, mazeY))),
							game.getSprite(randomFloorTexture()));
					break;
				case "E":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("skeleton"),game.getSprite(randomFloorTexture()));
					break;
				case "X":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("mummy"),game.getSprite(randomFloorTexture()));
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

		this.weather = weather;
		if (weather != MazeModel.Weather.SUN)
			setWeather(weather, maze[0].length, maze.length);

		uiStage.addActor(light);

		hud = new HeadsUpDisplay(game, currentPlayerIndex, currentPlayerStats);
		hud.setDiceLabel("Hit\nSpace!");
		uiStage.addActor(hud);
		

		dice = new DiceView(JumazyController.WORLD_WIDTH - game.getSprite("number1").originalWidth - 29, 23,
				game);

		gameStage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				switch (keycode) {
				case Input.Keys.ESCAPE:
					pause();
					break;
				case Input.Keys.ENTER:
					game.handleGameInput(keycode);
					lighttest = !lighttest;
					light.increaseLightSize(lighttest);
					break;
				default:
					game.handleGameInput(keycode);
				}

				return true;
			}
		});
		
		fightingStage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
            	if(inFight) {
	                if(keycode == Input.Keys.SPACE){
	                	
	                	fightingStage.rollDice();
	                	fightingStage.winnerAttack();
	                	
	                }
	
	                if(keycode == Input.Keys.F){
	
	                	fightingStage.remove();
	                	resume();
	                	inFight = false;
	
//	        			playSound(new File("../assets/snd/correct.wav"));
//	        			resume(game);
	                }
	                
	                return true;
	            }
            	return false;
            }
        });
		
		multiplexer.addProcessor(gameStage);
		multiplexer.addProcessor(uiStage);
	}

	public void updateCurrentInventoryAndStats(ArrayList<Item> playerInventory, boolean pickup) {
		ArrayList<Item> inventory = playerInventory;
		
		if (pickup) {
			Item lastItem = inventory.get(inventory.size()-1);
			if (lastItem.getStatEffected() != null) {
				hud.setPlayerConsoleText("You just picked up a " + lastItem.toString() + "! " + lastItem.getStatEffected()
				+ " increased by " + lastItem.getValue() + "!");
				hud.updateItemStat(lastItem);
			} else if (lastItem == Item.KEY) {
				hud.setPlayerConsoleText("You just picked up a key! Which door will you open?");
			}
		}
	}
	
	public void renderInventory(ArrayList<Item> inventory) {
		ArrayList<Item> inventoryView = new ArrayList<Item>();
		
		for (Item item : inventory) {
			if (item.getType().equals("held")) {
				inventoryView.add(item);
			}
		}
		
		int xPos = 610;
		for (Item item : inventoryView) {			
			ItemView itemView = new ItemView(game.getSprite(item.getAtlasString()));
			itemView.setVisible(true);
			
			itemView.setPosition(xPos, 35);
			xPos += 70;
			uiStage.addActor(itemView);
		}
	}
	
	public void clearInventory() {
		for (Actor actor : uiStage.getActors()) {
			if (actor instanceof ItemView) {
				actor.setVisible(false);
			}
		}
	}
	
	public void openChest(int[] pos, Item item) {
		for (Actor a : gameStage.getActors()) {
			if (a instanceof BlockView) {
				if (a.getName().equals(pos[1] + "," + pos[0])) {
					((BlockView) a).changeSprite(new Sprite(new Texture(Gdx.files.internal("Chest-Gold-Open.png"))));
										
					if (item != null) {
						gameStage.addAction(Actions.sequence(Actions.alpha(1)));
						
						Actor tempItemActor = new Actor() {
							private Sprite sprite = new Sprite(game.getSprite(item.getAtlasString()));
							
							public void setPosition(float x, float y) {
								sprite.setScale(1.6f);
								sprite.setPosition(x + blockSpriteDimensions/6, y);
								
								MoveByAction move = new MoveByAction();
								move.setAmount(0, 50f);
								move.setDuration(1f);
								setBounds(sprite.getX(), sprite.getY(), sprite.getHeight(), sprite.getWidth());
								addAction(move);
								addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(1f)));
							}
							
							public void draw(Batch batch, float parentAlpha) {
								sprite.setPosition(getX() + blockSpriteDimensions/6, getY());
							    sprite.setColor(getColor());
								sprite.draw(batch, parentAlpha);
							}
						};
						
						tempItemActor.setPosition(a.getX(), a.getY());
						
						gameStage.addActor(tempItemActor);
					}
				}
			}
		}
		
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

		// left door
		if (maze[mazeX][mazeY - 1].equals("D"))
			return "addtoskin/door.png";
		// right door
		if (maze[mazeX][mazeY + 1].equals("D"))
			return "addtoskin/door.png";

		// top door
		if (maze[mazeX - 1][mazeY].equals("D"))
			return "addtoskin/door-vertical.png";
		// bottom door
		if (maze[mazeX + 1][mazeY].equals("D"))
			return "addtoskin/door-vertical.png";

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
			if (!maze[mazeX][mazeY - 1].equals("#") && !maze[mazeX][mazeY + 1].equals("#")
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
	 * @param questionAndAns string containing both question and answer for question
	 */
	public void createQuestion(String[] questionAndAns) {
		questionUI.displayQuestion(questionAndAns);
		questionUI.addToStage(uiStage);
	}

	/**
	 * Check if question UI Actor isn't on the stage
	 * 
	 * @return boolean if riddle isn't open
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
	public void updateCurrentPlayer(int newPlayerIndex, LinkedHashMap<String, Integer> currentPlayerStats) {
		currentPlayerIndex = newPlayerIndex;
		hud.updateForNewPlayer(newPlayerIndex, currentPlayerStats);
	}

	/**
	 * Moves the current player in the view, calls the current players act method,
	 * passing the keycode to move the player on screen Then decreases/removes dice
	 * actors sprite accordingly
	 * 
	 * @param moveStyle int of player move style. 1 is normal, 2 reversed 0 invalid
	 * @param keycode direction the player moved
	 */
	public void moveCurrentPlayerView(int moveStyle, int keycode) {
		if (moveStyle==1) {
			players.get(currentPlayerIndex).act(Gdx.graphics.getDeltaTime(), keycode, moveStyle);
			dice.decreaseRoll();
		} else if (moveStyle==2) {
			players.get(currentPlayerIndex).act(Gdx.graphics.getDeltaTime(), keycode, moveStyle);
			inFight = true;
			startFight(currentPlayerStats.get("Health"), 10, keycode);
		}

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


	/**
	 * handles moving the player view back to the start position of their turn, also
	 * removes the dice from the stage
	 * 
	 * @param position position to move player to
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
		gameStage.addActor(weatherAnimation);
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
		hud.update(currentPlayerIndex + 1);
		uiStage.act(Gdx.graphics.getDeltaTime());
		uiStage.draw();

		if(inFight) {
			fightingStage.act(Gdx.graphics.getDeltaTime());
			fightingStage.draw();
		}
		
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
		pauseStage.getViewport().update(width, height);
	}

	private void startFight(int health1, int health2, int keycode) {
		fightingStage.setHealth(health1, health2, players.get(currentPlayerIndex), keycode);

		fightingStage.show();
		Gdx.input.setInputProcessor(fightingStage);
		
		System.out.println("p1 h:"+health1+" p2: h:"+health2);
		inFight = true;
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
							.changeSprite(new Sprite(new Texture(generateUnlockedDoorSprite(pos[1], pos[0], pos[3], pos[2]))));

				if (a.getName().equals(pos[3] + "," + pos[2]))
					((BlockView) a)
							.changeSprite(new Sprite(new Texture(generateUnlockedDoorSprite(pos[3], pos[2], pos[1], pos[0]))));
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
			return "addtoskin/door-open.png";
		// left door
		if (thisRow + 1 == otherRow)
			return "addtoskin/door-open.png";
		// top door
		if (thisCol - 1 == otherCol)
			return "addtoskin/door-vertical-open.png";
		// bottom door
		if (thisCol + 1 == otherCol)
			return "addtoskin/door-vertical-open.png";
		return "arrow";
	}

	public void stopFight() {
		inFight = false;
		resume();
	}
}