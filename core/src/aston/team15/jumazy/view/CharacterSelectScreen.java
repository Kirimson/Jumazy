package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.PlayerModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;


public class CharacterSelectScreen implements Screen{

    private JumazyController game;
    private Stage stage;
    private ArrayList<PlayerModel.CharacterName> selectedPlayerOrder;
    private PlayerModel.CharacterName currentSelectedPlayer;
    private TextButton selectButton;
    private Label hpLabel, staminaLabel, strengthLabel, agilityLabel, luckLabel, intelligenceLabel, infoPanelTitle, infoText;

    CharacterSelectScreen(JumazyController theGame, int numOfPlayers) {
        game = theGame;
        Skin skin = game.getSkin();
        stage = new Stage(new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT));
        selectedPlayerOrder = new ArrayList<PlayerModel.CharacterName>();

        //background image
        Image background = new Image(new Texture("playerSelectBackground.png"));
        stage.addActor(background);

        //setup table
        Table table = new Table();
        table.top();
        table.defaults().pad(10f);
        table.setFillParent(true);
//        table.debugAll();

        //top section
        Label titleLabel = new Label("Player 1, Please Select Your Character", skin);

        //player table
        Table playerTable = new Table();
        playerTable.top();
//        playerTable.debugAll();

        //player list
        Table playerImages = new Table();
        playerImages.top();
//        playerImages.debugAll();

        class CharacterImage extends Image{

            private PlayerModel.CharacterName name;

            private CharacterImage(String internalPath, PlayerModel.CharacterName name, Image changer){
                super(new Texture(internalPath));

                addListener(new ClickListener(){
                    public void clicked(InputEvent event, float x, float y){
                        currentSelectedPlayer = name;
                        changer.setDrawable(getDrawable());

                        agilityLabel.setText("Agility: XX");
                        intelligenceLabel.setText("Intelligence: XX");
                        strengthLabel.setText("Strength: XX");
                        luckLabel.setText("Luck: XX");

                        switch (name){
                            case RUBY_ROUNDHOUSE:
                                infoPanelTitle.setText("Smolder Bravestone:");
                                infoText.setText("the fast boi");
                                agilityLabel.setText(agilityLabel.getText()+"XX");
                                break;
                            case SHELLY_OBERON:
                                infoPanelTitle.setText("Ruby Roundhouse:");
                                infoText.setText("the smart boi");
                                intelligenceLabel.setText(intelligenceLabel.getText()+"XX");
                                break;
                            case SMOLDER_BRAVESTONE:
                                infoPanelTitle.setText("Frankiling Finbar:");
                                infoText.setText("the strong boi");
                                strengthLabel.setText(strengthLabel.getText()+"XX");
                                break;
                            case FRANKLIN_FINBAR:
                                infoPanelTitle.setText("Shelly Oberon:");
                                infoText.setText("the lucky boi");
                                luckLabel.setText(luckLabel.getText()+"XX");
                                intelligenceLabel.setText(intelligenceLabel.getText()+"X");
                                break;
                        }

                    }

                });
            }
        }

        //large player image
        Image ib = new Image(new Texture("addtoskin/character1Box.png"));

        CharacterImage characterOne = new CharacterImage("addtoskin/character1Box.png",
                PlayerModel.CharacterName.RUBY_ROUNDHOUSE, ib);

        playerImages.add(characterOne).align(Align.left);
        playerImages.add(new Label("Ruby Roundhouse", skin)).align(Align.left);
        playerImages.row();

        CharacterImage characterTwo = new CharacterImage("addtoskin/character2Box.png",
                PlayerModel.CharacterName.SHELLY_OBERON, ib);

        playerImages.add(characterTwo).align(Align.left);
        playerImages.add(new Label("Shelly Oberon", skin)).align(Align.left);
        playerImages.row();

        CharacterImage characterThree = new CharacterImage("addtoskin/character3Box.png",
                PlayerModel.CharacterName.SMOLDER_BRAVESTONE, ib);

        playerImages.add(characterThree).align(Align.left);
        playerImages.add(new Label("Smolder Bravestone", skin)).align(Align.left);
        playerImages.row();

        CharacterImage characterFour = new CharacterImage("addtoskin/character4Box.png",
                PlayerModel.CharacterName.FRANKLIN_FINBAR, ib);

        playerImages.add(characterFour).align(Align.left);
        playerImages.add(new Label("Franklin Finbar", skin)).align(Align.left);

        playerTable.add(playerImages).align(Align.left).expandX();

        //second half of player table
        playerTable.add(ib).expandX();
        playerTable.row();

        //buttons
        TextButton backButton = new TextButton("Back", skin);
        playerTable.add(backButton).expandY();

        backButton.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
                if(selectedPlayerOrder.isEmpty()){
                    game.setScreen(new PlayerAmountSelectScreen(game));
                } else {
                    selectedPlayerOrder.remove(selectedPlayerOrder.size()-1);
                    titleLabel.setText("Player "+(selectedPlayerOrder.size()+1)+", Please Select Your Character");
                }
           }
       });

        selectButton = new TextButton("Select", skin);
        playerTable.add(selectButton).expandY();

        selectButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                selectedPlayerOrder.add(currentSelectedPlayer);
                if(selectedPlayerOrder.size() == numOfPlayers){
                    game.setPlayerAmountAndStartGame(numOfPlayers, selectedPlayerOrder);
                }
                System.out.println("selected" + currentSelectedPlayer);
                agilityLabel.setText("Agility: XX");
                intelligenceLabel.setText("Intelligence: XX");
                strengthLabel.setText("Strength: XX");
                luckLabel.setText("Luck: XX");
                titleLabel.setText("Player "+(selectedPlayerOrder.size()+1)+", Please Select Your Character");
            }
        });

        //stats table
        Table statsTable = new Table();
        statsTable.top();
//        statsTable.debugAll();

        Label statsLabel = new Label("Character Stats:", skin);
        statsTable.add(statsLabel).expandX();
        statsTable.row();

            //labels for stats
            Table labelTable = new Table();
            hpLabel = new Label("HP:  XXXXXXXXXX", skin);
            labelTable.add(hpLabel).expandX().align(Align.left).padLeft(10f);
            labelTable.row();
            staminaLabel = new Label("Stamina: XXX", skin);
            labelTable.add(staminaLabel).expandX().align(Align.left).padLeft(10f);
            labelTable.row();
            strengthLabel = new Label("Strength: XX", skin);
            labelTable.add(strengthLabel).expandX().align(Align.left).padLeft(10f);
            labelTable.row();
            agilityLabel = new Label("Agility: XX", skin);
            labelTable.add(agilityLabel).expandX().align(Align.left).padLeft(10f);
            labelTable.row();
            luckLabel = new Label("Luck: XX", skin);
            labelTable.add(luckLabel).expandX().align(Align.left).padLeft(10f);
            labelTable.row();
            intelligenceLabel = new Label("Intelligence: XX", skin);
            labelTable.add(intelligenceLabel).expandX().align(Align.left).padLeft(10f);
            labelTable.row();

        statsTable.add(labelTable).expand().pad(10f).align(Align.left);

        //bottom section
        infoPanelTitle = new Label("Character Information", skin);

        infoText = new Label("", skin);
        infoText.setWrap(true);

        //setup the root table
        table.add(titleLabel).colspan(2).height(64f).expandX();
        table.row();
        table.add(playerTable).height(347f).width(621f);
        table.add(statsTable).height(347f).width(621f);
        table.row();
        table.add(infoPanelTitle).colspan(2).fillX();
        table.row();
        table.add(infoText).colspan(2).fill().padLeft(10f).align(Align.left);

        stage.addActor(table);

        //set default character selected
        InputEvent clickDown = new InputEvent();
        clickDown.setType(InputEvent.Type.touchDown);
        characterOne.fire(clickDown);
        InputEvent clickUp = new InputEvent();
        clickUp.setType(InputEvent.Type.touchUp);
        characterOne.fire(clickUp);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        if(selectedPlayerOrder.contains(currentSelectedPlayer)) {
            if (selectButton.isTouchable()) {
                selectButton.setTouchable(Touchable.disabled);
                selectButton.setText("Selected");
            }
        } else {
            selectButton.setTouchable(Touchable.enabled);
            selectButton.setText("Select");
        }

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
