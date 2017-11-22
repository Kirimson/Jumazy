package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.Player;

public class GraphicsManager {
	
	private BitmapFont font;
	private static Texture moves = new Texture("moves.png");

	public GraphicsManager() {
		font = new BitmapFont();
		font.setColor(1, 1, 1, 1);
	}
	
	/**
	 * Draws the maze to the given {@link SpriteBatch} object
	 * @param batch the {@link SpriteBatch} you want to draw to
	 * @return returns the {@link SpriteBatch} passed, with maze set to draw
	 */
	public SpriteBatch draw(SpriteBatch batch, Maze maze) {
		
		//draw maze
		int xOffset = (1280-(Maze.MAZE_DIMENSIONX*32))/2;
		int yOffset = (720-(Maze.MAZE_DIMENSIONY*32))/2;
		int blockSize = maze.getBlock(0, 0).getTexture().getHeight();
		int blockXYStart = 0;
		int blockOrigin = blockSize/2;
		int scaleX = JumazyGame.WIDTH/1280;
		int scaleY = JumazyGame.HEIGHT/720;
		
		for(int i = 0; i < Maze.getMaze().length; i++) {
			for(int k = 0; k <  Maze.getMaze()[0].length; k++) {
				int blockOrientation = maze.getBlock(i, k).getOrientation();
				batch.draw(maze.getBlock(i, k).getTexture(), xOffset+blockSize*i, yOffset+blockSize*k, blockOrigin, blockOrigin, blockSize, blockSize, scaleX, scaleY, blockOrientation*-90, blockXYStart, blockXYStart, blockSize, blockSize, false, false);
			}
		}
		//draw player
		for (int i = 0; i < maze.getTotalPlayers(); i++){
			Player player  = maze.getPlayers().get(i);
			int playerOffset = 10;
			float playerWidth = player.getTexture().getWidth();
			float playerHeight = player.getTexture().getHeight();
			
			batch.draw(player.getTexture(), xOffset+player.getCoords().getX()*blockSize+playerOffset, yOffset+player.getCoords().getY()*blockSize+playerOffset, playerWidth/2, playerHeight/2);
			batch.draw(new Texture("playeroutline.png"), xOffset+maze.getCurrPlayer().getCoords().getX()*blockSize+playerOffset, yOffset+maze.getCurrPlayer().getCoords().getY()*blockSize+playerOffset, playerWidth/2, playerHeight/2);

			font.draw(batch, "Player "+(maze.getCurrPlayerVal()+1)+"'s Turn!", 10,100);
			
			if(maze.getCurrPlayer().hasRolled() == false)
			{
				font.draw(batch, "Press Space to roll", 10,60);
			}
			else
			{
				for (int a=0;a<maze.getCurrPlayer().getRollSpaces();a++) {
					batch.draw(moves,10+(a*10),20);
				}
			}
			
			if(maze.getPlayers().get(i).hasRolled() == false)
			{
				font.draw(batch, "Weather: "+maze.getWeather().getName(), 10,80);
			}
		}
		return batch;
	}

}
