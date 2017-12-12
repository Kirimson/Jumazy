package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import aston.team15.jumazy.model.Maze;
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

	public GameSystem(int players) {
		super();
		maze = new Maze(41, 24, players);
		gMan = new GraphicsManager();
		setupCamera();
		ambientMusic = Gdx.audio.newSound(Gdx.files.internal("Creepy Music.mp3"));
		ambientMusic.play();
	}
	
	/**
	 * Sends needed parameters to the {@link GraphcisManager} to draw all needed textures
	 */
	public void draw(SpriteBatch batch) {
		gMan.draw(batch, maze, playerMoved, pause, cam);
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
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
			&& maze.getCurrPlayer().rolled() == false 
			&& maze.getCurrPlayer().isTrapped() == false) {
				focusCam = true;
				maze.getCurrPlayer().setStartOfMove(maze.getCurrPlayer().getCoords());
				maze.getCurrPlayer().switchRolled();
				maze.getCurrPlayer().roll(maze.getWeather().getMovementMod());
				System.out.println("Current player " + maze.getCurrPlayerVal());
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) 
			&& maze.getCurrPlayer().getRollSpaces() == 0
			&& maze.getCurrPlayer().isTrapped() == false) {
				focusCam = false;
				maze.nextPlayer();
				System.out.println("CHANGE TURN");
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.B)
			&& maze.getCurrPlayer().isTrapped() == false) {
				maze.getCurrPlayer().moveToStartOfTurn();
			}
			
			if(maze.getCurrPlayer().rolled())
			{
				String direction = "";
				if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
					direction = "right";
				}
				else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
					direction = "left";
				}
				else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
					direction = "up";
				}
				else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
					direction = "down";
				}
				
				if(direction != "")
				{
					maze.getCurrPlayer().newMove(direction);
					playerMoved = true;
				}
				else
				{
					playerMoved = false;
				}
			}
			
			focusCamera();
			
			if(maze.getCurrPlayer().isTrapped()) {
				maze.getCurrPlayer().checkStillTrapped();
			}
			
			if(maze.getCurrPlayer().isVictor()) {
				int winner = maze.getCurrPlayer().getPlayerNumber();
				SystemManager.setNewSystem(new WinSystem(winner));
			}
		}

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
					GAME_WIDTH/2,
					 GAME_HEIGHT/2, 0);
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
		cam.setToOrtho(false,GAME_WIDTH*1.2f, GAME_HEIGHT*1.2f);
		cam.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);
	}
}