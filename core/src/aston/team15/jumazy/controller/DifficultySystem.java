package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aston.team15.jumazy.view.JumazyGame;

public class DifficultySystem extends MainSystem{
	

	private Button easy;
	private Button medium;
	private Button hard;
	private Button backButton;
	private Texture background;
	
	public DifficultySystem() {
		super();
		
		Texture butTex = new Texture("ButtonNormal.png");
		background= new Texture("background.jpg");

		float base = (stage.getHeight()/2);
		
		easy = new Button(stage.getWidth()/2-butTex.getWidth()/2,base,"Easy", true);
		easy.setTouchable(Touchable.enabled);
		stage.addActor(easy);
		
		medium = new Button(stage.getWidth()/2-butTex.getWidth()/2,base-(stage.getHeight()/10),"Medium", true);
		medium.setTouchable(Touchable.enabled);
		stage.addActor(medium);
		
		hard = new Button(stage.getWidth()/2-butTex.getWidth()/2,base-(stage.getHeight()/5),"Hard", true);
		hard.setTouchable(Touchable.enabled);
		stage.addActor(hard);
		
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
		if(easy.wasClicked()) {
			SystemManager.setNewSystem(new MultiplayerSystem());
		}
		
		if(medium.wasClicked()) {
			SystemManager.setNewSystem(new MultiplayerSystem());
		}
		
		if(hard.wasClicked()) {
			SystemManager.setNewSystem(new MultiplayerSystem());
		}
		
		if(backButton.wasClicked()) {
			SystemManager.setNewSystem(new MenuSystem());
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
