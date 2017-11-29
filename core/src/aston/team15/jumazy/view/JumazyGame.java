package aston.team15.jumazy.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
		lightBuffer = new FrameBuffer(Format.RGBA8888, WIDTH,HEIGHT, false);

		lightBuffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(),0,lightBuffer.getHeight()-HEIGHT,WIDTH,HEIGHT);
	}

	@Override
	public void render () {

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
		batch.draw(lightBufferRegion, 0, 0,8,8);               
		
		
		lightBuffer.begin();

				
				Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
				Gdx.gl.glEnable(GL20.GL_BLEND);
						        
				// set the ambient color values, this is the "global" light of your scene
				// imagine it being the sun.  Usually the alpha value is just 1, and you change the darkness/brightness with the Red, Green and Blue values for best effect

				Gdx.gl.glClearColor(0.38f,0.38f,0.24f,1f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
				batch.end();
				// start rendering the lights to our spriteBatch
				batch.begin();


				// set the color of your light (red,green,blue,alpha values)
				batch.setColor(0.4f, 0.4f, 0f, 1.0f);

				// tx and ty contain the center of the light source
				float tx= (20);
				float ty= (20);

				// tw will be the size of the light source based on the "distance"
				// (the light image is 128x128)
				// and 96 is the "distance"  
				// Experiment with this value between based on your game resolution 
				// my lights are 8 up to 128 in distance
				float tw=(128/100f)*2;

				// make sure the center is still the center based on the "distance"
				tx-=(tw/2);
				ty-=(tw/2);

				
				// and render the sprite
				batch.draw(lightSprite, tx,ty,tw,tw,0,0,8,8,false,true);

				batch.end();
				lightBuffer.end();


				// now we render the lightBuffer to the default "frame buffer"
				// with the right blending !

				Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
