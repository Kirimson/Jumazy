package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
	public void draw(SpriteBatch batch, Maze maze, boolean updateHoles, boolean pause,Stage stage) {
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
			
			if(player == maze.getCurrPlayer()) {
				Sprite outlineSprite = new Sprite(player);
				outlineSprite.setRegion(TextureConstants.getTexture("playeroutline"));
				outlineSprite.draw(batch);
			}
			
			font.draw(batch, "Player "+(maze.getCurrPlayerVal()+1)+"'s Turn!", maze.getCurrPlayer().getX(),maze.getCurrPlayer().getY());
			
		}
		
		if(updateHoles) {
			int blockSize = maze.getBlock(0, 0).getRegionHeight();
			lighting.dispose();
			float scalex = (JumazyGame.WIDTH/stage.getWidth());
			float scaley = (JumazyGame.HEIGHT/stage.getHeight());
			int x = (int) ((maze.getWidth()*blockSize) / scalex);
			int y = (int) (((maze.getHeight()*blockSize) + (10/scaley)) / scaley);
		    int x3 = (int) (stage.getWidth());
		    int y3 = (int) (stage.getHeight());
			Pixmap overlay = new Pixmap(x,y, Pixmap.Format.RGBA8888);
			//Pixmap overlay = new Pixmap(maze.getWidth()*blockSize + 64, maze.getHeight()*blockSize + 10, Pixmap.Format.RGBA8888);
		    overlay.setColor(0, 0, 0, 0.9f);
		    int x2 = (int) ((maze.getWidth()*blockSize) / scalex );
		    int y2 = (int) ((maze.getHeight()*blockSize + (10/scaley)) / scaley);
		    overlay.fillRectangle(0, 0, x, y);

		    // Now change the settings so we are drawing transparent circles
		    overlay.setBlending(Pixmap.Blending.None);
		    overlay.setColor(1, 1, 1, 0f);
		  
		    for(Player p : maze.getPlayersList()) {
		    	int px = (int) ((p.getCoords().getX()*blockSize + (p.getWidth()/2)) / scalex);
		    	int py = (int) ((JumazyGame.HEIGHT - p.getCoords().getY()*blockSize+(p.getHeight()/2)) / scaley);
		    	overlay.fillCircle(px, py, 150);
		    }
		    overlay.setBlending(Pixmap.Blending.SourceOver);

		    // Turn it into a texture
		    lighting = new Texture(overlay);
		    overlay.dispose();
		}
		
//		Sprite lightSprite = new Sprite();
//		lightSprite.setRegion(lighting);
//		lightSprite.setSize(Gdx.graphics.getWidth()*1.2f, Gdx.graphics.getHeight()*1.2f);
//		lightSprite.setX(0);
//		lightSprite.setY(0);
	    
	    // Draw it to the screen
	    batch.draw(lighting, 0, 0);
//		lightSprite.draw(batch);
		
	    if(maze.getCurrPlayer().rolled() == false) {
			font.draw(batch, "Press Space to roll", -100,-100);
		} 
		else if(maze.getCurrPlayer().getRollSpaces() > 0) {
			maze.getCurrPlayer().getDieAnim().draw(batch, maze.getCurrPlayer().getX()+50, maze.getCurrPlayer().getY()+50);
		}
	    
	    if(maze.getCurrPlayer().rolled() == true) {
			font.draw(batch, "Weather: "+maze.getWeather().getName(), maze.getCurrPlayer().getX(), maze.getCurrPlayer().getY()-20);
		}
	    
	}
	
	public float getCurPlayerFloatXPos(Maze maze) {
		return currPlayerPosX;
	}
	
	public float getCurPlayerFloatYPos(Maze maze) {
		return currPlayerPosY;
	}

}
