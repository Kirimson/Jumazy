package aston.team15.jumazy.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.JumazyController;

public class PlayerAmountSelectScreen extends MenuScreen {

	public PlayerAmountSelectScreen(JumazyController theGame) {
		super(theGame);
		
		TextButton twoPlayerButton = new TextButton("2 PLAYERS", game.getSkin());
		twoPlayerButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setPlayerAmountAndStartGame(2);
			}
		});
		
		TextButton fourPlayerButton = new TextButton("4 PLAYERS", game.getSkin());
		fourPlayerButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setPlayerAmountAndStartGame(4);
			}
		});
		
		ScreenSwitchButton backButton = new ScreenSwitchButton("BACK", Screens.START_GAME_SCREEN, game);

		table.add(twoPlayerButton).pad(10);
		table.row();
		table.add(fourPlayerButton).pad(10);
		table.row();
		table.row();
		table.add(backButton).bottom().right().expand().pad(70);
		
		stage.addActor(table);
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
