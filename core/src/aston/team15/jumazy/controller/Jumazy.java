package aston.team15.jumazy.controller;

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
	private Skin gameSkin;
	private TextureAtlas textures;

	public Jumazy() {
		super();
		maze = new Maze(4, 2, 4);
		
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

	public void moveCurrentPlayer(MoveDirection direction) {
		maze.getPlayer(1).move(direction);
		System.out.println(maze.toString());
	}

	public void passTurnToNextPlayer() {

	}
}
