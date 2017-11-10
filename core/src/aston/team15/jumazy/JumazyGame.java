package aston.team15.jumazy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JumazyGame extends ApplicationAdapter {
	private SpriteBatch batch;
	
    private OrthographicCamera cam;
	private Sprite mapSprite;
	
	public static String TITLE = "Jumazy";
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	
	private SystemManager system;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		system = new SystemManager();
		system.push(new TitleSystem(system));
		
		mapSprite = new Sprite(new Texture(Gdx.files.internal("cross.jpg")));
		mapSprite.setPosition(0, 0);
		mapSprite.setSize(1,1);

		cam = new OrthographicCamera(1280, 720);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
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
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
