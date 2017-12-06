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

public class PauseSystem extends MainSystem{

	private Texture background;
	private BitmapFont font12;
	private Texture playBtn;

	public PauseSystem(SystemManager sysMan) {
		super(sysMan);
		background= new Texture("background.jpg");
		setupCamera();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Mario-Kart-DS.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 72;
		font12 = generator.generateFont(parameter); // font size 12 pixels
		playBtn= new Texture("startButton.png");
		generator.dispose();
	}

	@Override
	public void draw(SpriteBatch batch) {
		cam.update();
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(playBtn,(JumazyGame.WIDTH/2)-(playBtn.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(playBtn.getHeight()/2)-170,250,80);
		font12.draw(batch, "Paused! Press P to unpause", 100,100);
	}

	@Override
	public void handleInput() {
		if (Gdx.input.getX()>529 && Gdx.input.getY()<552 && Gdx.input.getX()<777 && Gdx.input.getY()>474) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.pop();
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
