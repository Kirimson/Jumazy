package aston.team15.jumazy.view;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import aston.team15.jumazy.controller.JumazyController;

public class SettingsScreen extends MenuScreen {

	public SettingsScreen(JumazyController theGame) {
		super(theGame);

		Slider volumeSlider = new Slider(0, 10, 1, false, game.getSkin());
		MenuScreenButton backButton = new MenuScreenButton("BACK", MenuScreens.MAIN_MENU_SCREEN, game);
		//float percentF = volumeSlider
		//String percentS = percentF + "%";
		Label percentage = new Label("percentS", game.getSkin());

		table.debug();
		table.add(volumeSlider).pad(10).width(500);
		table.add(percentage);
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
