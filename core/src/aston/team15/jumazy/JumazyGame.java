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
	private Texture img;
	private Maze maze;
	
    private OrthographicCamera cam;
 
	private Sprite mapSprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		maze = new Maze();
		
		mapSprite = new Sprite(new Texture(Gdx.files.internal("cross.jpg")));
		mapSprite.setPosition(0, 0);
		mapSprite.setSize(1,1);

		cam = new OrthographicCamera(1280, 720);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
	}

	@Override
	public void render () {

//		handleInput();
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		maze.actions();
		
		batch.begin();
			batch = maze.drawMaze(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
