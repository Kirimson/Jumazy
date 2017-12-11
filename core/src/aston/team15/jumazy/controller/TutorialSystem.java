package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import aston.team15.jumazy.model.TextureConstants;
import aston.team15.jumazy.view.JumazyGame;

public class TutorialSystem extends MainSystem{
	
	private Texture backButton4;
	private Texture background;

	public TutorialSystem(SystemManager sysMan) {
		super(sysMan);
		// TODO Auto-generated constructor stub
		backButton4=new Texture("backButton.png");
		background= new Texture("background.jpg");
		setupCamera();
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		cam.update();
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(backButton4,(JumazyGame.WIDTH/2)-(backButton4.getWidth()/2)+500,(JumazyGame.HEIGHT/2)-(backButton4.getHeight()/2)-300,150,50);
		
		Texture tutTex = TextureConstants.getTexture("Monkey Menu");
    	Sprite tutSprite = new Sprite(tutTex);
    	
    	
    	tutSprite.setRegion(tutTex);
    	tutSprite.setSize((tutTex.getWidth()*cam.zoom)/3, (tutTex.getHeight()*cam.zoom)/3);
    	tutSprite.setX(cam.position.x-(tutTex.getWidth()*cam.zoom)/6);
    	tutSprite.setY(cam.position.y-((tutTex.getHeight()*cam.zoom)/3-(JumazyGame.HEIGHT/2*cam.zoom)));
    	
    	tutSprite.draw(batch);
		
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.getX()>1059 && Gdx.input.getY()<681 && Gdx.input.getX()<1203 && Gdx.input.getY()>635) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.B)) {
				sysManager.setNewSystem(new MenuSystem(sysManager));
			}
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

