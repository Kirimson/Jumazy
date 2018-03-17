package aston.team15.jumazy.view;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.JumazyController;


public class PlayerAmountSelectScreen extends MenuScreen {


	public PlayerAmountSelectScreen(JumazyController theGame) {
		super(theGame);


		TextButton twoPlayerButton = new TextButton("2 Players", game.getSkin());
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
		questionBG.top().padTop(-400);
		questionBG.add(new Image(game.getSprite("pause-dialog")));
		
		QuestionPopUpCreator popUp = new QuestionPopUpCreator(game, numOfPlayers);
		Table questionTable = popUp.getTable();
		
		MoveToAction questionMA = new MoveToAction();
		MoveToAction backgroundMA = new MoveToAction();
		MoveToAction questionMA2 = new MoveToAction();
		MoveToAction backgroundMA2 = new MoveToAction();
		MoveToAction questionMA3 = new MoveToAction();
		MoveToAction backgroundMA3 = new MoveToAction();
		questionMA.setPosition(0f, -625f);
		questionMA.setDuration(0.4f);
		questionMA2.setPosition(0f, -575f);
		questionMA2.setDuration(0.2f);
		questionMA3.setPosition(0f, -625f);
		questionMA3.setDuration(0.2f);
		backgroundMA.setPosition(0f, -375f);
		backgroundMA.setDuration(0.4f);
		backgroundMA2.setPosition(0f, -325f);
		backgroundMA2.setDuration(0.2f);
		backgroundMA3.setPosition(0f, -375f);
		backgroundMA3.setDuration(0.2f);
		SequenceAction backgroundSA = new SequenceAction(backgroundMA,backgroundMA2,backgroundMA3);
		SequenceAction questionSA = new SequenceAction(questionMA,questionMA2,questionMA3);
		questionTable.addAction(questionSA);
		questionBG.addAction(backgroundSA);
		
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
