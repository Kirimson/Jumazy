package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aston.team15.jumazy.view.JumazyGame;

public class MultiplayerSystem extends MainSystem{
	

	private Button twoP;
	private Button fourP;
	private Button backButton;
	private Texture background;
	
	public MultiplayerSystem() {
		super();
		
		Texture butTex = new Texture("ButtonNormal.png");
		background= new Texture("background.jpg");

		float base = (stage.getHeight()/2);
		
		twoP = new Button(stage.getWidth()/2-butTex.getWidth()/2,base,"Two Player", true);
		twoP.setTouchable(Touchable.enabled);
		stage.addActor(twoP);
		
		fourP = new Button(stage.getWidth()/2-butTex.getWidth()/2,base-(stage.getHeight()/9),"Four Player", true);
		fourP.setTouchable(Touchable.enabled);
		stage.addActor(fourP);
		
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
		if(twoP.wasClicked()) {
			SystemManager.setNewSystem(new GameSystem(2));
			GameSound.stopMenuMusic();
		}
		
		if(fourP.wasClicked()) {
			SystemManager.setNewSystem(new GameSystem(4));
			GameSound.stopMenuMusic();
		}
		
		if(backButton.wasClicked()) {
			SystemManager.setNewSystem(new DifficultySystem());
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
