package aston.team15.jumazy.view;

import java.util.ArrayList;
import java.util.Random;

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
import com.badlogic.gdx.utils.viewport.FitViewport;

import aston.team15.jumazy.controller.JumazyController;

public class GameScreen implements Screen {

	private JumazyController game;
	private Stage stage;
	private Stage uiStage;

	private ArrayList<PlayerView> players;
	private int currentPlayerIndex;
	private FitViewport viewport;
	private int blockSpriteDimensions = 32;
	private QuestionUI questionUI;
	private PauseView pause;

	private DiceView dice;

	public GameScreen(JumazyController aGame, int playerAmount, String[][] maze) {
		game = aGame;
		viewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		stage = new Stage(viewport);
		uiStage = new Stage();
		players = new ArrayList<PlayerView>();
		questionUI = new QuestionUI();
		pause = new PauseView(game);

		for (int mazeX = 0; mazeX < maze.length; mazeX++) {
			for (int mazeY = 0; mazeY < maze[0].length; mazeY++) {
				Actor newActor;

				switch (maze[mazeX][mazeY]) {
				case "*":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite(findWallType(maze, mazeX, mazeY)));
					break;
				case "T":
					newActor = new BlockView(mazeY * blockSpriteDimensions, mazeX * blockSpriteDimensions,
							game.getSprite("floor-trap-spikes"));
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

				stage.addActor(newActor);
			}
		}

		for (PlayerView player : players) {
			stage.addActor(player);
		}

		currentPlayerIndex = 0;

		dice = new DiceView(players.get(0).getX() + 32f, players.get(0).getY() + 32f, game.getSprite("number1"));

		stage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {

				switch (keycode){
					case Input.Keys.P : pause();break;
					default: game.handleGameInput(keycode);
				}
				return true;
			}
		});
	}

	private String randomFloorTexture() {
		float floorType = new Random().nextFloat();

		if (floorType < 0.1)
			return "floor-squares-missing";
		else if (floorType < 0.2)
			return "floor-squares-cracked";
		else
			return "floor-squares";
	}

	private String findWallType(String[][] maze, int xPos, int yPos) {

		return "wall-plain";
	}

	public void createQuestion(String[] questionAndAns) {
		questionUI.displayQuestion(questionAndAns);
		for (Actor a : questionUI.getActors())
			uiStage.addActor(a);

		InputMultiplexer multiplexer = new InputMultiplexer(stage, uiStage);
		Gdx.input.setInputProcessor(multiplexer);
	}

	/**
	 * Check if question UI Actor is on a stage, if the actor returns null, riddle
	 * isn't open, otherwise, it is open
	 * 
	 * @return boolean if riddle is open
	 */
	public boolean isRiddleOpen() {
		return questionUI.getActors().get(0).getStage() != null;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
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

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		// draw all UI
		uiStage.act(Gdx.graphics.getDeltaTime());
		uiStage.draw();
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
		stage.addActor(dice);
		dice.setDie(finalDie);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		uiStage.addActor(pause.addPauseScreen());
		Gdx.input.setInputProcessor(uiStage);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

		InputMultiplexer multiplexer = new InputMultiplexer(stage, uiStage);
		Gdx.input.setInputProcessor(multiplexer);
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