package aston.team15.jumazy.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;


public class PlayerAmountSelectScreen extends MenuScreen {


	public PlayerAmountSelectScreen(JumazyController theGame) {
		super(theGame);


		JumazyButton twoPlayerButton = new JumazyButton("2 Players", game.getSkin());
		twoPlayerButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				GameSound.playButtonSound();
				showPopUp(2);
			}
		});

		JumazyButton fourPlayerButton = new JumazyButton("4 Players", game.getSkin());
		fourPlayerButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				GameSound.playButtonSound();
				showPopUp(4);
			}
		});


		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);
		table.add(twoPlayerButton).pad(10);
		table.row();
		table.add(fourPlayerButton).pad(10);
		table.row();
		table.row();
		table.add(backButton).bottom().right().expand().pad(70);
		stage.addActor(table);
		
	}
	
	public void showPopUp(int numOfPlayers) {
		Table questionBG = new Table();
		questionBG.setFillParent(true);
		questionBG.top().padTop(-30);
		questionBG.add(new Image(game.getSprite("pause-dialog")));
		
		QuestionPopUpCreator popUp = new QuestionPopUpCreator(game, numOfPlayers);

		stage.addActor(questionBG);
		stage.addActor(popUp.getTable());
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
