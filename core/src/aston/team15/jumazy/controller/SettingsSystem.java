package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.TextureConstants;
import aston.team15.jumazy.view.JumazyGame;

public class SettingsSystem extends MainSystem{

	private Texture background;
	private Texture black;
	private Button backButton;
	private Slider volumeSlider;
	private Skin sliderSkin;
	private BitmapFont volumeLevel;

	public SettingsSystem() {
		super();
		background= new Texture("background.jpg");
		black = new Texture("ButtonHover.png");
		Texture butTex = new Texture("ButtonNormal.png");
		
		backButton = new Button((stage.getWidth()/2)-(butTex.getWidth()/2)+(stage.getWidth()/(2.5f)),0+butTex.getHeight(),"Back", true);
		backButton.setTouchable(Touchable.enabled);
		stage.addActor(backButton);
		
		sliderSkin = new Skin(Gdx.files.internal("golden-spiral/skin/golden-ui-skin.json"));
		
		volumeSlider = new Slider(0.0f, 10.0f, 1.0f, false, sliderSkin);
		volumeSlider.setPosition((stage.getWidth()/4) + stage.getWidth()/32, (stage.getHeight()/2)-50);
		volumeSlider.setSize(500.0f, 50.0f);
		volumeSlider.setTouchable(Touchable.enabled);
		stage.addActor(volumeSlider);
		
		volumeLevel = new BitmapFont();
		volumeLevel.setColor(1, 0, 0, 1);
		
		setupCamera();
	}

	@Override
	public void draw(SpriteBatch batch) {

		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
		stage.getBatch().draw(black, stage.getWidth()/4, stage.getHeight()/8, stage.getWidth()/2, 350);
		//volumeLevel.draw(batch, "Levellllllllllllllllllllllllllllllll", 10, 10);
		//volumeLevel.draw(batch, "levellllllllllllll", 1, 1, 10, 4, false);
		stage.getBatch().end();

		stage.act();
		stage.draw();

		int x = 100;
		int y = 100;
		Pixmap overlay = new Pixmap(x,y, Pixmap.Format.RGBA8888);
		overlay.setColor(0, 0, 0, 0.8f);
		overlay.fillRectangle(0, 0, x, y);
		
		cam.update();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(backButton.wasClicked()) {
			SystemManager.setNewSystem(new TitleSystem());
		}

	}

	@Override
	protected void setupCamera() {
		// TODO Auto-generated method stub
		cam.setToOrtho(false);
		cam.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);

	}

	public OrthographicCamera getCamera() {
		return cam;
	}

}

