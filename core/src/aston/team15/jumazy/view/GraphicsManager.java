package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aston.team15.jumazy.controller.Button;
import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.Player;
import aston.team15.jumazy.model.TextureConstants;

public class GraphicsManager {
	
	private BitmapFont font;
	private float currPlayerPosX;
	private float currPlayerPosY;
	private Texture lighting = new Texture("path.png");
	
	private Stage stage;
	
	public GraphicsManager() {
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
		Viewport view = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(view);
		Gdx.input.setInputProcessor(stage);
		
		Button testButton = new Button(100,100,"Test");
		testButton.setTouchable(Touchable.enabled);
        stage.addActor(testButton);
	}
	
	
	
	/**
	 * Draws the maze to the given {@link SpriteBatch} object
	 * @param batch the {@link SpriteBatch} you want to draw to
	 * @return returns the {@link SpriteBatch} passed, with maze set to draw
	 */
	public void draw(SpriteBatch batch, Maze maze, boolean updateHoles, boolean pause, OrthographicCamera cam) {
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		
		//draw maze
		for(int i = 0; i < Maze.getMaze().length; i++) {
			for(int k = 0; k <  Maze.getMaze()[0].length; k++) {
				maze.getBlock(i, k).draw(batch);
			}
		}
		
		//draw player
		for (int i = 0; i < maze.getTotalPlayers(); i++) {
			Player player = maze.getPlayersList().get(i);
			
			player.draw(batch);
			
			if(player == maze.getCurrPlayer())
			{
				Sprite outlineSprite = new Sprite(player);
				outlineSprite.setRegion(TextureConstants.getTexture("playeroutline"));
				outlineSprite.draw(batch);
			}
			
			font.draw(batch, "Player "+(maze.getCurrPlayerVal()+1)+"'s Turn!", maze.getCurrPlayer().getX(),maze.getCurrPlayer().getY());
			
		}
		
		if(updateHoles)
		{
			int blockSize = maze.getBlock(0, 0).getTexture().getHeight();
			lighting.dispose();
			Pixmap overlay = new Pixmap(maze.getWidth()*blockSize + 64, maze.getHeight()*blockSize + 10, Pixmap.Format.RGBA8888);
		    overlay.setColor(0, 0, 0, 0.9f);
		    overlay.fillRectangle(0, 0, maze.getWidth()*blockSize + 22, maze.getHeight()*blockSize +10);

		    // Now change the settings so we are drawing transparent circles
		    overlay.setBlending(Pixmap.Blending.None);
		    overlay.setColor(1, 1, 1, 0f);
		  
		    for(Player p : maze.getPlayersList()) {
		    	overlay.fillCircle(p.getCoords().getX()*blockSize, JumazyGame.HEIGHT - p.getCoords().getY()*blockSize, 150);
		    }
		    overlay.setBlending(Pixmap.Blending.SourceOver);

		    // Turn it into a texture
		    lighting = new Texture(overlay);
		    overlay.dispose();
		}
		
	    
	    // Draw it to the screen
	    batch.draw(lighting, 0, 0);
		
	    if(maze.getCurrPlayer().rolled() == false) {
			font.draw(batch, "Press Space to roll", -100,-100);
		} 
		else if(maze.getCurrPlayer().getRollSpaces() > 0){
			maze.getCurrPlayer().getDieAnim().draw(batch, maze.getCurrPlayer().getX()+50, maze.getCurrPlayer().getY()+50);
		}
	    
	    if(maze.getCurrPlayer().rolled() == true) {
			font.draw(batch, "Weather: "+maze.getWeather().getName(), maze.getCurrPlayer().getX(), maze.getCurrPlayer().getY()-20);
		}
	    
	    if(pause) {
	    	Texture pauseTex = TextureConstants.getTexture("pausepage");
	    	Sprite pauseSprite = new Sprite(pauseTex);
	    	
	    	pauseSprite.setRegion(pauseTex);
	    	pauseSprite.setSize((pauseTex.getWidth()*cam.zoom)/3, (pauseTex.getHeight()*cam.zoom)/3);
	    	pauseSprite.setX(cam.position.x-(pauseTex.getWidth()*cam.zoom)/6);
	    	pauseSprite.setY(cam.position.y-((pauseTex.getHeight()*cam.zoom)/3-(JumazyGame.HEIGHT/2*cam.zoom)));
	    	
	    	pauseSprite.draw(batch);
	    }
	    
	    stage.act(Gdx.graphics.getDeltaTime());
	    stage.draw();
	}
	
	public float getCurPlayerFloatXPos(Maze maze) {
		return currPlayerPosX;
	}
	
	public float getCurPlayerFloatYPos(Maze maze) {
		return currPlayerPosY;
	}

}
