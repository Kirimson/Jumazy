package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class CharacterSelectScreen implements Screen{

    private JumazyController game;
    private Stage stage;
    private Table table;

    public CharacterSelectScreen(JumazyController theGame, int numOfPlayers) {
        game = theGame;
        Skin skin = game.getSkin();
        stage = new Stage(new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT));

        //background image
        Image background = new Image(new Texture("playerSelectBackground.png"));
        stage.addActor(background);

        //setup table
        table = new Table();
        table.top();
        table.defaults().pad(10f);
        table.setFillParent(true);
        table.debugAll();

        Label titleLabel = new Label("Character Select Screen", skin);

        Label bottomText = new Label("Bottom Text", skin);

        //player table
        Table playerTable = new Table();
        playerTable.top();
        playerTable.debugAll();

//        Label playerLabel = new Label("table", skin);
//        playerTable.add(playerLabel).expandX().colspan(2);
//        playerTable.row();

        Table playerImages = new Table();
        playerImages.top();
        playerImages.debugAll();

        Image i = new Image(new Texture("addtoskin/character1Box.png"));
        playerImages.add(i).align(Align.left);
        playerImages.add(new Label("Character 1", skin));
        playerImages.row();

        Image ii = new Image(new Texture("addtoskin/character2Box.png"));
        playerImages.add(ii).align(Align.left);
        playerImages.add(new Label("Character 2", skin));
        playerImages.row();

        Image iii = new Image(new Texture("addtoskin/character3Box.png"));
        playerImages.add(iii).align(Align.left);
        playerImages.add(new Label("Character 3", skin));
        playerImages.row();

        Image iiii = new Image(new Texture("addtoskin/character4Box.png"));
        playerImages.add(iiii).align(Align.left);
        playerImages.add(new Label("Character 4", skin));

        playerTable.add(playerImages).align(Align.left).expandX();

        Image ib = new Image(new Texture("addtoskin/character1Box.png"));
        playerTable.add(ib).fill().expandX();


        //stats table
        Table statsTable = new Table();
        statsTable.top();
        statsTable.debugAll();

        Label statsLabel = new Label("table", skin);
        statsTable.add(statsLabel).expandX();
        statsTable.row();

        table.add(titleLabel).colspan(2).height(64f).expandX();
        table.row();
        table.add(playerTable).height(347f).width(621f);
        table.add(statsTable).height(347f).width(621f);
        table.row();
        table.add(bottomText).colspan(2).expandX().align(Align.left);

        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameSound.playButtonSound();
                game.setPlayerAmountAndStartGame(numOfPlayers);
            }
        });

        stage.addActor(playButton);

        stage.addActor(table);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
