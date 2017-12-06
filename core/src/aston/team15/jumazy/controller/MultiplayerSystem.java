package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import aston.team15.jumazy.view.JumazyGame;

public class MultiplayerSystem extends MainSystem{
	
	private Texture twoP;
	private Texture fourP;
	private Texture backButton2;
	private Texture background;

	public MultiplayerSystem(SystemManager sysMan) {
		super(sysMan);
		// TODO Auto-generated constructor stub
		twoP=new Texture("2playerButton.png");
		fourP=new Texture("4playerButton.png");
		backButton2=new Texture("backButton.png");
		background= new Texture("background.jpg");
		setupCamera();
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		cam.update();
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(twoP,(JumazyGame.WIDTH/2)-(twoP.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(twoP.getHeight()/2)+75,250,80);
		batch.draw(fourP,(JumazyGame.WIDTH/2)-(fourP.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(fourP.getHeight()/2)-100,250,80);
		batch.draw(backButton2,(JumazyGame.WIDTH/2)-(backButton2.getWidth()/2)+500,(JumazyGame.HEIGHT/2)-(backButton2.getHeight()/2)-300,150,50);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.getX()>529 && Gdx.input.getY()<305 && Gdx.input.getX()<775 && Gdx.input.getY()>231) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.setNewSystem(new GameSystem(sysManager, 2));
			}
		}else if (Gdx.input.getX()>529 && Gdx.input.getY()<480 && Gdx.input.getX()<775 && Gdx.input.getY()>406) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.setNewSystem(new GameSystem(sysManager, 4));
			}
		}else if (Gdx.input.getX()>1059 && Gdx.input.getY()<681 && Gdx.input.getX()<1203 && Gdx.input.getY()>635) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
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
