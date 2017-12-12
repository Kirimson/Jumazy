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

/**
 * Manages the title screen of the games title screen, sub class of {@link MainSystem}
 * @author kieran
 *
 */
public class TitleSystem extends MainSystem{
	
	private Texture background;
	private Texture playBtn;
	private Texture exitButton;
	
	public TitleSystem(SystemManager sysMan) {
		super(sysMan);
		background= new Texture("background.jpg");
		playBtn= new Texture("startButton.png");
		exitButton=new Texture("exitButton.png");
		setupCamera();
	}

	@Override
	public void draw(SpriteBatch batch) {
		cam.update();
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(playBtn,(JumazyGame.WIDTH/2)-(playBtn.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(playBtn.getHeight()/2),250,80);
		batch.draw(exitButton,(JumazyGame.WIDTH/2)-(exitButton.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(exitButton.getHeight()/2)-100,250,80);
	}

	@Override
	public void handleInput() {
		if (Gdx.input.getX()>529 && Gdx.input.getY()<382 && Gdx.input.getX()<777 && Gdx.input.getY()>304) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.P)) {
				sysManager.setNewSystem(new MenuSystem(sysManager));
			}
		}else if (Gdx.input.getX()>529 && Gdx.input.getY()<479 && Gdx.input.getX()<775 && Gdx.input.getY()>406) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
				Gdx.app.exit();
			}
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
