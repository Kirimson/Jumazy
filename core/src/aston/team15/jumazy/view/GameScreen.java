package aston.team15.jumazy.view;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

	private ArrayList<PlayerView> players;
	private int currentPlayerIndex;
	private FitViewport viewport;

	public GameScreen(JumazyController aGame, int playerAmount, String[][] maze) {
		game = aGame;
		viewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		stage = new Stage(viewport);
		players = new ArrayList<PlayerView>();

		for (int mazeX = 0; mazeX < maze.length; mazeX++) {
			for (int mazeY = 0; mazeY < maze[0].length; mazeY++) {
				Actor newActor;

				switch(maze[mazeX][mazeY]){
					case "*":
						newActor = new BlockView(mazeY * 32, mazeX * 32, game.getSprite(findWallType(maze, mazeX, mazeY)));break;
					case "T":newActor = new BlockView(mazeY * 32, mazeX * 32, game.getSprite("floor-trap-spikes"));break;
					case "1":
					case "2":
					case "3":
					case "4":
						players.add(new PlayerView(mazeY * 32, mazeX * 32, game.getSprite("char"+maze[mazeX][mazeY])));
					default:newActor = new BlockView(mazeY * 32, mazeX * 32, game.getSprite(randomFloorTexture()));break;
				}

				stage.addActor(newActor);
			}
		}

		for (PlayerView player : players) {
			stage.addActor(player);
		}

		currentPlayerIndex = 0;

		stage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				game.handleGameInput(keycode);
				return true;
			}
		});
	}

	private String randomFloorTexture() {
		float floorType = new Random().nextFloat();

		if(floorType < 0.1)
			return "floor-squares-missing";
		else if(floorType < 0.2)
			return "floor-squares-cracked";
		else
			return "floor-squares";
	}

	private String findWallType(String[][] maze, int xPos, int yPos) {

		return "wall-plain";
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
  
	public void setPlayerFocus(int newPlayerIndex) {
		currentPlayerIndex = newPlayerIndex;
	}

	public void moveCurrentPlayerView(boolean canMove, int keycode) {
		if (canMove)
			players.get(currentPlayerIndex).act(Gdx.graphics.getDeltaTime(), keycode);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//update camera position
		float newCameraX = players.get(currentPlayerIndex).getX();
		float newCameraY = players.get(currentPlayerIndex).getY();
		viewport.getCamera().position.set(new Vector3(newCameraX, newCameraY, 1f));

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

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