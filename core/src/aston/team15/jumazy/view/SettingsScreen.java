package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.JumazyController;

public class SettingsScreen extends MenuScreen {
	
	private Label percentage;
	private Slider volumeSlider;
	
	public SettingsScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton backButton = new MenuScreenButton("BACK", MenuScreens.MAIN_MENU_SCREEN, game);
		Skin skin = new Skin(Gdx.files.internal("jumazyskin/jumazy-skin.json"));
		volumeSlider = new Slider(0, 10, 1, false, skin);
		String sliderVal = "" + volumeSlider.getValue();
		percentage = new Label(sliderVal, skin);
		
		table.debug();
		table.add(volumeSlider);
		table.row();
		table.add(percentage);
		table.row();
		table.add(backButton).bottom().right().expand().pad(70);
		
		stage.addActor(table);
	}

	public void render() {
		percentage.setText("" + volumeSlider.getValue());
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
