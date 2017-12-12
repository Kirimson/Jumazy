package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aston.team15.jumazy.model.TextureConstants;
import aston.team15.jumazy.view.JumazyGame;

public class TutorialSystem extends MainSystem{
	
	private Texture backButton4;
	private Texture background;
	private Button backButton;

	public TutorialSystem() {
		super();
		background= new Texture("background.jpg");
		Texture butTex = new Texture("ButtonNormal.png");
		
		Texture tutTex = new Texture("Monkey Menu.png");
		
		UIComponent tutorialUI = new UIComponent(stage.getWidth()/2-tutTex.getWidth()/2, stage.getHeight()-tutTex.getHeight(), tutTex);
		stage.addActor(tutorialUI);
		
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
//		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
//		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
//		batch.draw(backButton4,(JumazyGame.WIDTH/2)-(backButton4.getWidth()/2)+500,(JumazyGame.HEIGHT/2)-(backButton4.getHeight()/2)-300,150,50);
//		
//		
//    	Sprite tutSprite = new Sprite(tutTex);
//    	
//    	tutSprite.setRegion(tutTex);
//    	tutSprite.setSize((tutTex.getWidth()*cam.zoom)/3, (tutTex.getHeight()*cam.zoom)/3);
//    	tutSprite.setX(cam.position.x-(tutTex.getWidth()*cam.zoom)/6);
//    	tutSprite.setY(cam.position.y-((tutTex.getHeight()*cam.zoom)/3-(JumazyGame.HEIGHT/2*cam.zoom)));
//    	
//    	tutSprite.draw(batch);
		
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(backButton.wasClicked()) {
			SystemManager.setNewSystem(new MenuSystem());
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

