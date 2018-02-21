package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aston.team15.jumazy.view.JumazyGame;
import aston.team15.jumazy.view.Question;

public class MenuSystem extends MainSystem{
	
	private Texture background;
	private Button playButton, textureButton, tutorialButton, backButton;
	
	public MenuSystem() {
		super();

		Texture butTex = new Texture("ButtonNormal.png");
		background= new Texture("background.jpg");

		float base = (stage.getHeight()/2);
		
		playButton = new Button(stage.getWidth()/2-butTex.getWidth()/2,base,"Play", true);
		playButton.setTouchable(Touchable.enabled);
		stage.addActor(playButton);
		
		textureButton = new Button(stage.getWidth()/2-butTex.getWidth()/2,base-(stage.getHeight()/9),"Textures", true);
		textureButton.setTouchable(Touchable.enabled);
		stage.addActor(textureButton);
		
		tutorialButton = new Button(stage.getWidth()/2-butTex.getWidth()/2,base-(stage.getHeight()/4.5f),"Tutorial", true);
		tutorialButton.setTouchable(Touchable.enabled);
		stage.addActor(tutorialButton);
		
		backButton = new Button((stage.getWidth()/2)-(butTex.getWidth()/2)+(stage.getWidth()/(2.5f)),0+butTex.getHeight(),"Back", true);
		backButton.setTouchable(Touchable.enabled);
		stage.addActor(backButton);
		
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
			SystemManager.setNewSystem(new MultiplayerSystem());
		}
		
		if(textureButton.wasClicked()) {
			SystemManager.setNewSystem(new TextureSystem());
		}
		
		if(tutorialButton.wasClicked()) {
			SystemManager.setNewSystem(new TutorialSystem());
		}
		
		if(backButton.wasClicked()) {
			SystemManager.setNewSystem(new TitleSystem());
		}
		
	}

	@Override
	protected void setupCamera() {
		cam.setToOrtho(false);
		cam.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);
		
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}

}
