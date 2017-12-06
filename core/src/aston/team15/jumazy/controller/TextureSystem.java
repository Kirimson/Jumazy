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
public class TextureSystem extends MainSystem{
	
	private Texture texture1;
	private Texture texture2;
	private Texture texture3;
	private Texture backButton3;
	private Texture background;
	
	public TextureSystem(SystemManager sysMan) {
		super(sysMan);
		texture1= new Texture("texture1Button.png");
		texture2= new Texture("texture2Button.png");
		texture3= new Texture("texture3Button.png");
		backButton3 = new Texture("backButton.png");
		background= new Texture("background.jpg");
		setupCamera();
	}

	@Override
	public void draw(SpriteBatch batch) {
		cam.update();
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(texture1,(JumazyGame.WIDTH/2)-(texture1.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(texture1.getHeight()/2)+100,250,80);
		batch.draw(texture2,(JumazyGame.WIDTH/2)-(texture2.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(texture2.getHeight()/2),250,80);
		batch.draw(texture3,(JumazyGame.WIDTH/2)-(texture3.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(texture3.getHeight()/2)-100,250,80);
		batch.draw(backButton3,(JumazyGame.WIDTH/2)-(backButton3.getWidth()/2)+500,(JumazyGame.HEIGHT/2)-(backButton3.getHeight()/2)-300,150,50);
		System.out.println("playY"+Gdx.input.getY());
		System.out.println("playX"+Gdx.input.getX());
	}

	@Override
	public void handleInput() {
		if (Gdx.input.getX()>1059 && Gdx.input.getY()<681 && Gdx.input.getX()<1203 && Gdx.input.getY()>635) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.setNewSystem(new MenuSystem(sysManager));
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