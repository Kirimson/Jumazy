package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.QuestionRetriever;

public class PlayerAmountSelectScreen extends MenuScreen {


	public PlayerAmountSelectScreen(JumazyController theGame) {
		super(theGame);
<<<<<<< HEAD
		
		TextButton twoPlayerButton = new TextButton("2 PLAYERS", game.getSkin());
=======

		TextButton twoPlayerButton = new TextButton("2 Players", game.getSkin());
>>>>>>> 7a108bcbc00c8bed3b3b89b1c7da594933d01cf4
		twoPlayerButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				showPopUp(2);
			}
		});

		TextButton fourPlayerButton = new TextButton("4 Players", game.getSkin());
		fourPlayerButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				showPopUp(4);
			}
		});

<<<<<<< HEAD
		MenuScreenButton backButton = new MenuScreenButton("BACK", MenuScreens.START_GAME_SCREEN, game);
=======
		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);

>>>>>>> 7a108bcbc00c8bed3b3b89b1c7da594933d01cf4
		table.add(twoPlayerButton).pad(10);
		table.row();
		table.add(fourPlayerButton).pad(10);
		table.row();
		table.row();
		table.add(backButton).bottom().right().expand().pad(70);
		stage.addActor(table);
		
	}
	
	public void showPopUp(int numOfPlayers) {
		Skin skin = new Skin(Gdx.files.internal("neonskin/neon-ui.json"));
		Table questionBG = new Table();
		questionBG.setFillParent(true);
		questionBG.top().padTop(0f);
		questionBG.add(new Image(game.getSprite("pause-dialog")));
		QuestionPopUpCreator popUp = new QuestionPopUpCreator();
		Table questionTable = popUp.getTable();
	    TextButton btnPlay = new TextButton("play", skin);
		questionTable.add(btnPlay);
		
		stage.addActor(questionBG);
		stage.addActor(questionTable);
		
		btnPlay.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				popUp.populateSelections();
				String[] levels = popUp.getSelections();
				game.setQuestionType(levels);
				game.setPlayerAmountAndStartGame(numOfPlayers);
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
