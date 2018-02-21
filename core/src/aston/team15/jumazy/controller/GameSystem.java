package aston.team15.jumazy.controller;

import aston.team15.jumazy.model.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import aston.team15.jumazy.view.GraphicsManager;
import aston.team15.jumazy.view.JumazyGame;
/**
 * Subclass of {@link MainSystem}. The main class containing the maze and gameplay elements of the game.
 * Allows drawing of the maze, player and other UI elements
 * @author kieran
 *
 */
public class GameSystem extends MainSystem{
	
	private Maze maze;
	private GraphicsManager gMan;
	private boolean playerMoved = true;
	private boolean focusCam = false;
	private Sound ambientMusic;
	private boolean pause = false;
	
	private Button resumeButton;
	private Button quitButton;

	private DieAnimation dieAnimation;

	public GameSystem(int players) {
		super();
		maze = new Maze(4, 2, players);
		gMan = new GraphicsManager();
		setupCamera();
		ambientMusic = Gdx.audio.newSound(Gdx.files.internal("Creepy Music.mp3"));
		ambientMusic.play();
		dieAnimation = new DieAnimation();

		//UI
		Texture butTex = new Texture("ButtonNormal.png");
		Texture pauseTex = TextureConstants.getTexture("pausepageNew");
		
		quitButton = new Button(stage.getWidth()/2-butTex.getWidth()/2,stage.getHeight()-pauseTex.getHeight()+100,"Exit", true);
		quitButton.setTouchable(Touchable.enabled);
		
		resumeButton = new Button(stage.getWidth()/2-butTex.getWidth()/2,stage.getHeight()-pauseTex.getHeight()+200,"Resume", true);
		resumeButton.setTouchable(Touchable.enabled);
        
        UIComponent pauseUI = new UIComponent(stage.getWidth()/2-pauseTex.getWidth()/2, stage.getHeight()-pauseTex.getHeight(), pauseTex);
        stage.addActor(pauseUI);
        stage.addActor(resumeButton);
        stage.addActor(quitButton);
	}
	
	/**
	 * Sends needed parameters to the {@link GraphicsManager} to draw all needed textures
	 */
	public void draw(SpriteBatch batch) {
		gMan.draw(batch, maze, playerMoved, pause, stage, dieAnimation);
		
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		if(pause) {
		    stage.draw();
		    
		    //check pause actor buttons
		    if(resumeButton.wasClicked()) {
		    	pause = false;
		    }
		    if(quitButton.wasClicked()) {
		    	SystemManager.setNewSystem(new MenuSystem());
		    }
		    
	    }
		stage.act();
	}
	
	/**
	 * Any action that happens on the maze will be called through this method
	 * As of now. it is just listening for player movement
	 * Called each frame by {@link JumazyGame#render()}
	 */
	@Override
	public void handleInput() {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
			
			pause = !pause;
			System.out.println("pause "+pause);
		}
		
		if(!pause) {
			Player currPlayer = maze.getCurrPlayer();
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
			&& !maze.getCurrPlayer().rolled()
			&& !maze.getCurrPlayer().isTrapped()) {
				focusCam = true;
				currPlayer.setStartOfMove(currPlayer.getCoords());
				maze.getCurrPlayer().switchRolled();

				currPlayer.roll(maze.getWeather().getMovementMod());

				dieAnimation.setFinalDie(currPlayer.getRollSpaces());
			}
//			System.out.println(currPlayer.getRollSpaces());
			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) 
			&& maze.getCurrPlayer().getRollSpaces() == 0
			&& !maze.getCurrPlayer().isTrapped()) {
				focusCam = false;
				maze.nextPlayer();
				System.out.println("CHANGE TURN");
			}

			if(currPlayer.getRollSpaces() > 0 && dieAnimation.getAnimationFinished())
			{
				if(!currPlayer.isTrapped()){
					Block movingBlock = findDirection(currPlayer);
//					System.out.println(movingBlock.toString());
					if(movingBlock instanceof Path && movingBlock != null){
						System.out.println("here");
						currPlayer.updateCoords(movingBlock.getCoords());
						currPlayer.reduceRolls();
						currPlayer.checkTrap(movingBlock);
						currPlayer.checkVictory(movingBlock);
						dieAnimation.decrease();
					}
				}
				else
				{
					Trap current = (Trap)maze.getBlock(currPlayer.getCoords());
					if(!current.stillTrapped()){
						if(!current.wasCorrect()){
							System.out.println("boo2");
							currPlayer.moveToStartOfTurn();
						}
					}
				}
			}
			
			focusCamera();
		}
	}

	private Block findDirection(Player currPlayer){
		int xDir = 0, yDir = 0;

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
			xDir++;

		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
			xDir--;

		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
			yDir++;

		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
			yDir--;

		Coordinate movingBlock;
		if(!(xDir == 0 && yDir == 0))
			movingBlock = new Coordinate(currPlayer.getCoords().getX() + xDir, currPlayer.getCoords().getY() + yDir);
		else return null;

		return maze.getBlock(movingBlock);
	}

	/**
	 * Sets the camera position and updates it to a target Vertex3
	 */
	private void focusCamera() {
		//if camera needs to be focused on one player
		if(focusCam) {
			cam.zoom = 0.75f;
			//get players coordinates
			Vector3 target = new Vector3(
					maze.getCurrPlayer().getX(),
					maze.getCurrPlayer().getY(), 0);
			//zoom to that players coordinates
				cameraZoom(target);
			
		}else{//camera needs to be set to default location
			cam.zoom = 1.0f;
			Vector3 target = new Vector3(
					stage.getWidth()/2,
					 stage.getHeight()/2, 0);
			cameraZoom(target);
		}
		
	}
	
	/**
	 * smoothly zoom to set coordinates
	 * @param target the target coordinates camera should zoom to
	 */
	private void cameraZoom(Vector3 target){
		Vector3 camPosition = cam.position;
		final float speed=0.1f,invertSpeed=1.0f-speed;
		camPosition.scl(invertSpeed);
		target.scl(speed);
		camPosition.add(target);
		cam.position.set(camPosition);
	}
	
	protected void setupCamera() {
		cam.setToOrtho(false,stage.getWidth()*1.2f, stage.getHeight()*1.2f);
		cam.position.set(stage.getWidth()/2, stage.getHeight()/2, 0);
	}
}