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

public class WinSystem extends MainSystem{

	private Texture background;
	private Texture playBtn;
	private BitmapFont font12;
	private int winningPlayer;

	public WinSystem(SystemManager sysMan, int winner) {
		super(sysMan);
		winningPlayer = winner;
		background= new Texture("winnerpage.jpg");
		playBtn= new Texture("playBtn2.png");
		setupCamera();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Mario-Kart-DS.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 50;
		font12 = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
	}

	@Override
	public void draw(SpriteBatch batch) {
		cam.update();
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(playBtn,(JumazyGame.WIDTH/2)-(playBtn.getWidth()/2)+10,(JumazyGame.HEIGHT/2)-(playBtn.getHeight()/2)-120);
		font12.draw(batch, "Player " + winningPlayer + " wins!", (JumazyGame.WIDTH/2 - 160),(JumazyGame.HEIGHT/2 + 100));
	}

	@Override
	public void handleInput() {
		if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			sysManager.setNewSystem(new MultiplayerSystem(sysManager));
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
