package aston.team15.jumazy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JumazyGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Maze maze;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		maze = new Maze();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
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
