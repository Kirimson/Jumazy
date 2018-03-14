package aston.team15.jumazy.view;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aston.team15.jumazy.controller.JumazyController;

public class TutorialScreen extends MenuScreen {

	public TutorialScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);
		Table tutorialBG = new Table();
		tutorialBG.setFillParent(true);
		tutorialBG.top().padTop(-30);
		tutorialBG.add(new Image(game.getSprite("pause-dialog")));
		table.row();
		table.add(backButton).bottom().right().expand().pad(70);
		stage.addActor(tutorialBG);
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
