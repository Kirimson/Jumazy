package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aston.team15.jumazy.model.DieAnimation;
import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.Player;
import aston.team15.jumazy.model.TextureConstants;

public class GraphicsManager {
	
	private BitmapFont font;
	private float currPlayerPosX;
	private float currPlayerPosY;
	private Texture lighting = new Texture("path.png");
	
	public GraphicsManager() {
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
	}
	
	/**
	 * Draws the maze to the given {@link SpriteBatch} object
	 * @param batch the {@link SpriteBatch} you want to draw to
	 * @return returns the {@link SpriteBatch} passed, with maze set to draw
	 */
	public void draw(SpriteBatch batch, Maze maze, boolean updateHoles, boolean pause, OrthographicCamera cam) {
		
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
			Pixmap overlay = new Pixmap(maze.getWidth()*blockSize, maze.getHeight()*blockSize, Pixmap.Format.RGBA8888);
		    overlay.setColor(0, 0, 0, 0.9f);
		    overlay.fillRectangle(0, 0, maze.getWidth()*blockSize, maze.getHeight()*blockSize);

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
			font.draw(batch, "Weather: "+maze.getWeather().getName(), maze.getCurrPlayer().getX(), maze.getCurrPlayer().getY());
		}
	    
	    if(pause) {
	    	Texture pauseTex = TextureConstants.getTexture("pausepage");
	    	Sprite pauseSprite = new Sprite(pauseTex);
	    	
	    	pauseSprite.setSize(pauseTex.getWidth(), pauseTex.getHeight());
	    	pauseSprite.setRegion(pauseTex);
	    	
	    	pauseSprite.scale(0.5f);
	    	
	    	pauseSprite.setX(cam.position.x-(pauseTex.getWidth()/2));
	    	pauseSprite.setY(cam.position.y-(pauseTex.getHeight()/2));
	    	
	    	pauseSprite.draw(batch);
	    	
	    }
	    
	}
	
	public float getCurPlayerFloatXPos(Maze maze) {
		return currPlayerPosX;
	}
	
	public float getCurPlayerFloatYPos(Maze maze) {
		return currPlayerPosY;
	}

}
