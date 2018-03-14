package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;

public class SettingsScreen extends MenuScreen {
	
	private Label percentage, title;
	private Slider volumeSlider;
	
	public SettingsScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton backButton = new MenuScreenButton("BACK", MenuScreens.MAIN_MENU_SCREEN, game);
		Skin skin = new Skin(Gdx.files.internal("jumazyskin/jumazy-skin.json"));
		volumeSlider = new Slider(0, 100, 1, false, skin);
		volumeSlider.setValue(GameSound.getVolumePercent());
		String sliderVal = "" + volumeSlider.getValue();
		percentage = new Label(sliderVal, skin);
		title = new Label("SETTINGS", skin);
		
		table.debug();
		table.add(title);
		table.row();
		table.add(volumeSlider);
		table.row();
		table.add(percentage);
		table.row();
		table.add(backButton).bottom().right().expand().pad(70);
		
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		percentage.setText((int) volumeSlider.getValue()+ "%");
		GameSound.setVolume(volumeSlider.getValue()/100);
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
