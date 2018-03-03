package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Jumazy;

import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.PlayerModel;

public class MainMenuScreen extends MenuScreen {

	public MainMenuScreen(Jumazy theGame) {
		super(theGame);

		ScreenSwitchButton playButton = new ScreenSwitchButton("PLAY", Screens.START_GAME_SCREEN, game);
		ScreenSwitchButton settingsButton = new ScreenSwitchButton("SETTINGS", Screens.SETTINGS_SCREEN, game);
 
		TextButton quitButton = new TextButton("QUIT", game.getSkin());
		quitButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit(); 
			}
		});
				
		table.add(playButton).pad(10); 
		table.row();
		table.add(settingsButton).pad(10);
		table.row();
		table.add(quitButton).pad(10);
		
		table.padTop(150);

		stage.addActor(table);
		
		final Maze maze = new Maze(4, 2, 4);
		maze.setCoordinateString(2, 1, "T");
		System.out.println(maze.toString());
		
		stage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				switch (keycode) { 
				case Input.Keys.RIGHT:
					maze.getPlayer(1).move(PlayerModel.MoveDirection.RIGHT);
					break;
				case Input.Keys.LEFT:
					maze.getPlayer(1).move(PlayerModel.MoveDirection.LEFT);
					break;
				case Input.Keys.UP:
					maze.getPlayer(1).move(PlayerModel.MoveDirection.UP);
					break;
				case Input.Keys.DOWN:
					maze.getPlayer(1).move(PlayerModel.MoveDirection.DOWN);
					break;
				}				
				System.out.println(maze.toString());
				return true;
			}
		});
		
		
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
