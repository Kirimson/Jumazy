package aston.team15.jumazy.view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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

	public GameScreen(JumazyController aGame, int playerAmount, String[][] maze) {
		game = aGame;
		stage = new Stage(new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT));
		players = new ArrayList<PlayerView>();

		for (int mazeX = 0; mazeX < maze.length; mazeX++) {
			for (int mazeY = 0; mazeY < maze[0].length; mazeY++) {
				Actor newActor;

				switch(maze[mazeX][mazeY]){
					case "*":newActor = new BlockView(mazeY * 32, mazeX * 32, game.getSprite("wall-plain"));break;
					case "1":
					case "2":
					case "3":
					case "4":
						newActor = new BlockView(mazeY * 32, mazeX * 32, game.getSprite("floor-squares"));
						players.add(new PlayerView(mazeY * 32, mazeX * 32, game.getSprite("char"+maze[mazeX][mazeY])));break;
					default:newActor = new BlockView(mazeY * 32, mazeX * 32, game.getSprite("floor-squares"));break;
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

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
  
	public void setPlayerFocus(int newPlayerIndex) {
//		stage.setKeyboardFocus(players.get(newPlayerIndex));
		currentPlayerIndex = newPlayerIndex;
	}

	public void moveCurrentPlayerView(boolean canMove, int keycode) {
		if (canMove)
			players.get(currentPlayerIndex).act(Gdx.graphics.getDeltaTime(), keycode);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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