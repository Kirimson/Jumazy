package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Manages the title screen of the games title screen, sub class of {@link MainSystem}
 * @author kieran
 *
 */
public class TitleSystem extends MainSystem{

	private Texture background;
	//	private Texture playBtn;
	//	private Texture exitButton;
	private Button playButton, quitButton, settingsButton;

	public TitleSystem() {
		super();

		Texture butTex = new Texture("ButtonNormal.png");
		background= new Texture("background.jpg");

		float base = (stage.getHeight()/2);

		playButton = new Button((stage.getWidth()/2-butTex.getWidth()/2),base,"Play", true);
		playButton.setTouchable(Touchable.enabled);
		stage.addActor(playButton);

		settingsButton = new Button((stage.getWidth()/2-butTex.getWidth()/2),base-(stage.getHeight()/9),"Options", true);
		settingsButton.setTouchable(Touchable.enabled);
		stage.addActor(settingsButton);

		quitButton = new Button(stage.getWidth()/2-butTex.getWidth()/2,base-(stage.getHeight()/4.5f),"Quit", true);
		quitButton.setTouchable(Touchable.enabled);
		stage.addActor(quitButton);

		setupCamera();
	}

	@Override
	public void draw(SpriteBatch batch) {

		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
		stage.getBatch().end();

		stage.act();
		stage.draw();

		cam.update();
	}
	
	@Override
	public void handleInput() {

		if(playButton.wasClicked()) {
			SystemManager.setNewSystem(new MenuSystem());
		}

		if(settingsButton.wasClicked()) {
			SystemManager.setNewSystem(new SettingsSystem());
		}

		if(quitButton.wasClicked()) {
			Gdx.app.exit();
		}

	}

	protected void setupCamera() {
		cam.setToOrtho(false);
		cam.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);
	}

	public OrthographicCamera getCamera() {
		return cam;
	}
}
