package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aston.team15.jumazy.model.DieAnimation;
import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.Player;
import aston.team15.jumazy.model.TextureConstants;

public class GraphicsManager {
	
	private BitmapFont font;
	private static Texture die1 = new Texture("number1.png");
	private static Texture die2 = new Texture("number2.png");
	private static Texture die3 = new Texture("number3.png");
	private static Texture die4 = new Texture("number4.png");
	private static Texture die5 = new Texture("number5.png");
	private static Texture die6 = new Texture("number6.png");
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
	public void draw(SpriteBatch batch, Maze maze, boolean updateHoles) {
		
		//draw maze
		int blockSize = maze.getBlock(0, 0).getTexture().getHeight();
		
		for(int i = 0; i < Maze.getMaze().length; i++) {
			for(int k = 0; k <  Maze.getMaze()[0].length; k++) {
				batch.draw(maze.getBlock(i, k).getTexture(), blockSize*i, blockSize*k);
			}
		}
		
		//draw player
		Player player  = maze.getPlayersList().get(0);
		
		int playerOffset = 10;
		currPlayerPosX= maze.getCurrPlayer().getCoords().getX()*blockSize+playerOffset;
		currPlayerPosY= maze.getCurrPlayer().getCoords().getY()*blockSize+playerOffset;
		
		for (int i = 0; i < maze.getTotalPlayers(); i++) {
			player  = maze.getPlayersList().get(i);
			
			float playerWidth = player.getTexture().getWidth();
			float playerHeight = player.getTexture().getHeight();
			float playerXPos = player.getCoords().getX()*blockSize+playerOffset;
			float playerYPos = player.getCoords().getY()*blockSize+playerOffset;
			
			batch.draw(player.getTexture(), playerXPos,playerYPos, playerWidth/2, playerHeight/2);
			
			batch.draw(TextureConstants.getTexture("outline"),currPlayerPosX, currPlayerPosY, playerWidth/2, playerHeight/2);

			font.draw(batch, "Player "+(maze.getCurrPlayerVal()+1)+"'s Turn!", 10,100);
			
			Texture[] pictureArray = {die1, die2, die3, die4, die5, die6};
			
			
		}
		
		if(updateHoles)
		{
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
			maze.getCurrPlayer().getDieAnim().draw(batch, currPlayerPosX, currPlayerPosY);
		}
	    
	    if(maze.getCurrPlayer().rolled() == true) {
			font.draw(batch, "Weather: "+maze.getWeather().getName(), currPlayerPosX-JumazyGame.WIDTH/3, currPlayerPosY-JumazyGame.HEIGHT/3);
		}
	}
	
	public float getCurPlayerFloatXPos(Maze maze) {
		return currPlayerPosX;
	}
	
	public float getCurPlayerFloatYPos(Maze maze) {
		return currPlayerPosY;
	}

}
