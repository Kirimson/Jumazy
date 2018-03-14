package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import aston.team15.jumazy.controller.JumazyController;

public class SettingsScreen extends MenuScreen {
	
	private String sliderVal;
	
	public SettingsScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton backButton = new MenuScreenButton("BACK", MenuScreens.MAIN_MENU_SCREEN, game);
		Skin skin = new Skin(Gdx.files.internal("jumazyskin/jumazy-skin.json"));
		Slider volumeSlider = new Slider(0, 10, 1, false, skin);
		Label percentage = new Label(sliderVal, skin);
		
		table.debug();
		table.add(volumeSlider);
		table.add(sliderVal);
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
