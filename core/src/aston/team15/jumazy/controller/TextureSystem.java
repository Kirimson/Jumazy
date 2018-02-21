package aston.team15.jumazy.controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aston.team15.jumazy.view.JumazyGame;

/**
 * Manages the title screen of the games title screen, sub class of {@link MainSystem}
 * @author kieran
 *
 */
public class TextureSystem extends MainSystem{
	
	private Texture background;
	private Button tex1;
	private Button tex3;
	private Button tex2;
	private Button backButton;
	
	public TextureSystem() {
		super();
		Texture butTex = new Texture("ButtonNormal.png");
		background= new Texture("background.jpg");

		float base = (stage.getHeight()/2);
		
		tex1 = new Button(stage.getWidth()/2-butTex.getWidth()/2,base,"Texture 1", true);
		tex1.setTouchable(Touchable.enabled);
		stage.addActor(tex1);
		
		tex2 = new Button(stage.getWidth()/2-butTex.getWidth()/2,base-(stage.getHeight()/9),"Texture 2", true);
		tex2.setTouchable(Touchable.enabled);
		stage.addActor(tex2);
		
		tex3 = new Button(stage.getWidth()/2-butTex.getWidth()/2,base-(stage.getHeight()/4.5f),"Texture 3", true);
		tex3.setTouchable(Touchable.enabled);
		stage.addActor(tex3);
		
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
		if(backButton.wasClicked()) {
			SystemManager.setNewSystem(new MenuSystem());
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