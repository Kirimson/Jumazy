package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aston.team15.jumazy.view.JumazyGame;

public class WinSystem extends MainSystem{

	private Texture background;
	private Texture playBtn;
	private BitmapFont font12;
	private int winningPlayer;
	private Button playButton;
	private float textxOffset;
	
	public WinSystem(int winner) {
		super();
		winningPlayer = winner;
		background= new Texture("winnerpage.jpg");
		Texture butTex = new Texture("ButtonNormal.png");
		
		playButton = new Button((stage.getWidth()/2)-(butTex.getWidth()/2)+(stage.getWidth()/(2.5f)),0+butTex.getHeight(),"Play Again", true);
		playButton.setTouchable(Touchable.enabled);
		stage.addActor(playButton);
		setupCamera();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Mario-Kart-DS.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 50;
		font12 = generator.generateFont(parameter); // font size 12 pixels
		
		GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font12,"Player " + winningPlayer + " wins!");
        textxOffset = glyphLayout.width/2;
		
		generator.dispose();
	}

	@Override
	public void draw(SpriteBatch batch) {
		
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		
		stage.getBatch().begin();
			stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
			font12.draw(stage.getBatch(), "Player " + winningPlayer + " wins!", (stage.getWidth()/2)-textxOffset,(stage.getHeight()/2)+textxOffset);
		stage.getBatch().end();
		
		stage.act();
		stage.draw();
		
		cam.update();
	}

	@Override
	public void handleInput() {
		if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			SystemManager.setNewSystem(new MultiplayerSystem());
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
