package aston.team15.jumazy.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import aston.team15.jumazy.controller.SystemManager;
import aston.team15.jumazy.controller.TitleSystem;

public class JumazyGame extends ApplicationAdapter {
	private SpriteBatch batch;
	
    private OrthographicCamera cam;
	private Sprite mapSprite;
	private Texture lightSprite;
	
	public static String TITLE = "Jumazy";
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	public FrameBuffer lightBuffer;
	public TextureRegion lightBufferRegion;
	public SpriteBatch light_batch;
	private SystemManager system;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		system = new SystemManager();
		system.push(new TitleSystem(system));
		
		mapSprite = new Sprite(new Texture(Gdx.files.internal("path.png")));
		mapSprite.setPosition(0, 0);
		mapSprite.setSize(1,1);
		
		lightSprite = new Texture("light.png");

		
		
		lightBuffer = new FrameBuffer(Format.RGBA8888, WIDTH,HEIGHT, false);

		lightBuffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(),0,lightBuffer.getHeight()-HEIGHT,WIDTH,HEIGHT);
	}

	@Override
	public void render () {

		OrthographicCamera cam = system.peek().getCamera();
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.252f, 0.448f, 0.287f, 1);
		
		
		
		system.handleInput();
		
		batch.begin();
			batch = system.draw(batch);
		batch.end();
		
	if(system.getGameRunning() == true){
		batch.begin();
		 Pixmap overlay = new Pixmap(WIDTH,HEIGHT, Pixmap.Format.RGBA8888);
		    overlay.setColor(0, 0, 0, 0.4f);
		    overlay.fillRectangle(0, 0, WIDTH + 100, HEIGHT);

		    // Now change the settings so we are drawing transparent circles
		    overlay.setBlending(Pixmap.Blending.None);
		    overlay.setColor(1, 1, 1, 0f);
		  
		    overlay.fillCircle(100, 100, 100);
		    overlay.fillCircle(400, 100, 100);
		    overlay.fillCircle(100, 600, 100);
		    overlay.fillCircle(500, 500, 100);
		    overlay.setBlending(Pixmap.Blending.SourceOver);

		    // Turn it into a texture
		    Texture lighting = new Texture(overlay);
		    overlay.dispose();
		    
		    // Draw it to the screen
		    batch.draw(lighting, 0, 0);
		batch.end();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
