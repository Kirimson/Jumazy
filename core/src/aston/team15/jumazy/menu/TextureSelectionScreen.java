package aston.team15.jumazy.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.view.JumazyButton;
import aston.team15.jumazy.view.MenuScreenButton;
import aston.team15.jumazy.view.MenuScreens;

/**
 * Allows players to change the currently used texture pack
 *
 * @author Dom
 */
public class TextureSelectionScreen extends MenuScreen {
	
	public TextureSelectionScreen(JumazyController theGame) {
		
		super(theGame, "backgrounds/tBackground.jpg");

		JumazyButton texture1 = new JumazyButton("Jungle", new Skin(Gdx.files.internal("jumazyskin/current/jumazy-skin.json")));
		texture1.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.updateSkin("current");
				game.setScreen(new TextureSelectionScreen(game));
				MenuScreen.setJungle();
				GameSound.setJungleMenuMusic();
			}
		});

		JumazyButton texture2 = new JumazyButton("Old English", new Skin(Gdx.files.internal("jumazyskin/oldenglish/jumazy-skin.json")));
		texture2.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.updateSkin("oldenglish");
				game.setScreen(new TextureSelectionScreen(game));
				MenuScreen.setOldEnglish();
				GameSound.setOldEnglishMenuMusic();
			}
		});
		JumazyButton texture3 = new JumazyButton("Medieval", new Skin(Gdx.files.internal("jumazyskin/medievel/jumazy-skin.json")));
		texture3.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.updateSkin("medievel");
				game.setScreen(new TextureSelectionScreen(game));
				MenuScreen.setMedieval();
				GameSound.setMedievalMenuMusic();
			}
		});



		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.SETTINGS_SCREEN, game);

		Table textureTable = new Table();

		textureTable.setFillParent(true);
		textureTable.setPosition(0.0f, 0.0f);
		textureTable.row();
		textureTable.add(texture1).pad(100);
		textureTable.add(texture2).pad(100);
		textureTable.add(texture3).pad(100);

		table.add(backButton).bottom().right().expand().pad(70);

		stage.addActor(table);
		stage.addActor(textureTable);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
