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

public class MenuSystem extends MainSystem{
	
	private Texture playButton;
	private Texture texturePackButton;
	private Texture tutorialButton;
	private Texture backButton1;
	private Texture background;

	public MenuSystem(SystemManager sysMan) {
		super(sysMan);
		// TODO Auto-generated constructor stub
		playButton=new Texture("playButton.png");
		texturePackButton=new Texture("texturesButton.png");
		tutorialButton=new Texture("tutorialButton.png");
		backButton1=new Texture("backButton.png");
		background= new Texture("background.jpg");
		setupCamera();
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		cam.update();
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(playButton,(JumazyGame.WIDTH/2)-(playButton.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(playButton.getHeight()/2),250,80);
		batch.draw(texturePackButton,(JumazyGame.WIDTH/2)-(texturePackButton.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(texturePackButton.getHeight()/2)-100,250,80);
		batch.draw(tutorialButton,(JumazyGame.WIDTH/2)-(tutorialButton.getWidth()/2)-30,(JumazyGame.HEIGHT/2)-(tutorialButton.getHeight()/2)-200,250,80);
		batch.draw(backButton1,(JumazyGame.WIDTH/2)-(backButton1.getWidth()/2)+500,(JumazyGame.HEIGHT/2)-(backButton1.getHeight()/2)-300,150,50);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.getX()>529 && Gdx.input.getY()<380 && Gdx.input.getX()<775 && Gdx.input.getY()>300) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.setNewSystem(new MultiplayerSystem(sysManager));
			}
		}else if (Gdx.input.getX()>529 && Gdx.input.getY()<477 && Gdx.input.getX()<775 && Gdx.input.getY()>407) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.setNewSystem(new TextureSystem(sysManager));
			}
		}else if (Gdx.input.getX()>529 && Gdx.input.getY()<581 && Gdx.input.getX()<775 && Gdx.input.getY()>506) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.setNewSystem(new TutorialSystem(sysManager));
			}
		}else if (Gdx.input.getX()>1059 && Gdx.input.getY()<681 && Gdx.input.getX()<1203 && Gdx.input.getY()>635) {
			if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				sysManager.setNewSystem(new TitleSystem(sysManager));
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
