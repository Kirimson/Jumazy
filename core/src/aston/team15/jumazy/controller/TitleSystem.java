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
	private BitmapFont font12;
	
	public TitleSystem(SystemManager sysMan) {
		super(sysMan);
		background= new Texture("junglebg1.png");
		playBtn= new Texture("playBtn2.png");
		setupCamera();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Mario-Kart-DS.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 72;
		font12 = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
	}

	@Override
	public void draw(SpriteBatch batch) {
		cam.update();
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(playBtn,(JumazyGame.WIDTH/2)-(playBtn.getWidth()/2)+10,(JumazyGame.HEIGHT/2)-(playBtn.getHeight()/2)-120);
		font12.draw(batch, "Press " + " to roll", 100,100);
	}

	@Override
	public void handleInput() {
		if (Gdx.input.getX()>568 && Gdx.input.getY()<504 && Gdx.input.getX()<731 && Gdx.input.getY()>455) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.setNewSystem(new GameSystem(sysManager));
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
