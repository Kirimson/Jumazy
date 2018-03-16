package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;

public class SettingsScreen extends MenuScreen {

	private Label percentage, title, volumeTitle;
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
		title.setFontScale(2f);
		volumeTitle = new Label("Volume: ", skin);
		volumeTitle.setFontScale(1.3f);
		
		Table settingsTitle = new Table();

		settingsTitle.setFillParent(true);
		settingsTitle.setPosition(0.0f, 35.0f);
		settingsTitle.add(title);
		
		Table settings = new Table();

		settings.setFillParent(true);
		settings.setPosition(0.0f, -50.0f);
		settings.add(volumeTitle);
		settings.add(volumeSlider).width(400.0f);
		settings.add(percentage).width(50f);
		
		Table settingsScroll = new Table();

		settingsScroll.setFillParent(true);
		settingsScroll.setPosition(0.0f, -85.0f);
		settingsScroll.add(new Image(game.getSprite("scroll"))).height(355.0f);

		table.add(backButton).bottom().right().expand().pad(70);

		stage.addActor(table);
		stage.addActor(settingsScroll);
		stage.addActor(settingsTitle);
		stage.addActor(settings);
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
