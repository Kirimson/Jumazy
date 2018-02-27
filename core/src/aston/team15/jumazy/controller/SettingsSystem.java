package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.TextureConstants;
import aston.team15.jumazy.view.JumazyGame;

public class SettingsSystem extends MainSystem implements Screen{

	private Texture background;
	private Texture box;
	//private Button settingsButton;
	//private Button textureButton;
	private Button backButton;
	private Slider volumeSlider;
	private TextButton settingsButton;
	private Skin sliderSkin;
	private Label volumeLevel;	

	public SettingsSystem() {
		super();
		background= new Texture("background.jpg");
		box = new Texture("OptionsMenu.png");
		
		Table table = new Table();
		
		Texture butTex = new Texture("ButtonNormal.png");
		
//		settingsButton = new Button((stage.getWidth()/4),stage.getHeight()/8+275,"Settings", true, 0.5f, 0.5f);
//		settingsButton.setTouchable(Touchable.enabled);
//		stage.addActor(settingsButton);
//		
//		textureButton = new Button((stage.getWidth()/4),stage.getHeight()/8+200,"Texture", true);
//		textureButton.setTouchable(Touchable.enabled);
//		stage.addActor(textureButton);
	
		backButton = new Button((stage.getWidth()/2)-(butTex.getWidth()/2)+(stage.getWidth()/(2.5f)),0+butTex.getHeight(),"Back", true);
		backButton.setTouchable(Touchable.enabled);
		stage.addActor(backButton);
		
		sliderSkin = new Skin(Gdx.files.internal("golden-spiral/skin/golden-ui-skin.json"));
		
		volumeSlider = new Slider(0.0f, 10.0f, 1.0f, false, sliderSkin);
		volumeSlider.setPosition((stage.getWidth()/4) + stage.getWidth()/10, (stage.getHeight()/2)-50);
		volumeSlider.setSize(450.0f, 50.0f);
		volumeSlider.setTouchable(Touchable.enabled);
		stage.addActor(volumeSlider);
		volumeLevel = new Label("Volume Level: ", sliderSkin);
		volumeLevel.setX((stage.getWidth()/4) + stage.getWidth()/10);
		volumeLevel.setY(stage.getHeight()/8+275);
		volumeLevel.setFontScale(2);
		stage.addActor(volumeLevel);

		
		table.setDebug(true);
		table.moveBy(-250f, 0f);
		table.setFillParent(true);
		settingsButton = new TextButton("Settings", sliderSkin);

//		singlePlayer.setPosition((stage.getWidth()/4) + 25, (stage.getHeight()/2)-50);
//		singlePlayer.setScale(0.5f,0.5f);
		table.add(settingsButton).fillX().uniformX().padBottom(10);

		settingsButton.setTouchable(Touchable.enabled);
		stage.addActor(table);
		
		setupCamera();
	}

	@Override
	public void draw(SpriteBatch batch) {

		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
		stage.getBatch().draw(box, stage.getWidth()/4, stage.getHeight()/8, stage.getWidth()/2, 350);
		stage.getBatch().end();
		stage.act();

		stage.draw();
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

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {

		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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

