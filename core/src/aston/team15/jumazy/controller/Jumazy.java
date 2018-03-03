package aston.team15.jumazy.controller;

import aston.team15.jumazy.model.DiceModel;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aston.team15.jumazy.model.Maze;
import aston.team15.jumazy.model.PlayerModel;
import aston.team15.jumazy.model.PlayerModel.MoveDirection;
import aston.team15.jumazy.view.MainMenuScreen;

//this follows the state design pattern, setScreen is an inherited function, but does what a setState function would do
public class Jumazy extends Game {

	public static final int WORLD_WIDTH = 1280, WORLD_HEIGHT = 720;

	private Maze maze;
	private DiceModel dice;
	private Skin gameSkin;
	private TextureAtlas textures;

	public Jumazy() {
		super();
		maze = new Maze(4, 2, 4);
		dice = new DiceModel();
		System.out.println(maze.toString());
	}

	@Override
	public void create() {
		textures = new TextureAtlas("jumazy-skin.atlas");
		Gdx.gl.glClearColor(1, 0, 0, 1);

		// using a skin, with json, png, and atlas, reduces a lot of the workload
		// needlessly put on the GPU when having to load in many individual png's
		gameSkin = new Skin(Gdx.files.internal("jumazy-skin.json"));

		setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render() {
		super.render();

		/* game logic stuff */
		//Dice rolling
		if(!dice.isRollFinished() && dice.getRoll() != -1){
			dice.roll();

			if(dice.isRollFinished())
				System.out.println("Roll is: "+dice.getRoll());
		}
	}

	@Override
	public void dispose() {
	}

	public TextureAtlas getAtlas() {
		return textures;
	}

	public Skin getSkin() {
		return gameSkin;
	}

	public DiceModel getDice() {
		return dice;
	}

	public void moveCurrentPlayer(MoveDirection direction) {
		if(dice.isRollFinished() && dice.getRoll() > 0) {
			maze.getPlayer(1).move(direction);
			dice.decreaseRoll();
			System.out.println(maze.toString());
			System.out.println("Moves left: "+dice.getRoll());
		}
	}

	public void passTurnToNextPlayer() {

	}
}
