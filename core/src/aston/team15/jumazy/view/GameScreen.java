package aston.team15.jumazy.view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Jumazy;

public class GameScreen implements Screen {

	private Jumazy game;
	private Stage stage;
	private ArrayList<Actor> players;
	private int currPlayer;
 
	public GameScreen(Jumazy aGame) {
		game = aGame;
		stage = new Stage(new FitViewport(Jumazy.WORLD_WIDTH, Jumazy.WORLD_HEIGHT));
		players = new ArrayList<Actor>();

		Player player1 = new Player(300, 100, game.getAtlas().findRegion("chest-silver"));
		Player player2 = new Player(400, 100, game.getAtlas().findRegion("char2"));
		Player player3 = new Player(500, 100, game.getAtlas().findRegion("mummy"));

		players.add(player1);
		players.add(player2); 
		players.add(player3);

		stage.addActor(player1); 
		stage.addActor(player2);
		stage.addActor(player3);
		stage.setKeyboardFocus(player1);

		// for every player in players, add a listener that calls nextPlayer when you
		// press Enter
		for (Actor player : players) {
			player.addListener(new InputListener() {
				public boolean keyDown(InputEvent event, int keycode) {
					if (keycode == Input.Keys.ENTER) {
						nextPlayer();
					}
					return true;
				}
			});
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	public void nextPlayer() {
		if (currPlayer == (players.size() - 1)) {
			currPlayer = 0;
		} else {
			currPlayer++;
		}

		stage.setKeyboardFocus(players.get(currPlayer));
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