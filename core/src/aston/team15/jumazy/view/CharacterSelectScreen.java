package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.PlayerModel.CharacterName;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class CharacterSelectScreen implements Screen{

    private LinkedHashMap<String, ArrayList<Image>> stats;
    private JumazyController game;
    private Stage stage;
    private ArrayList<CharacterName> selectedPlayerOrder;
    private ArrayList<Image> selectedImages;
    private CharacterName currentSelectedPlayer;
    private TextButton selectButton;
    private Label strengthLabel;
    private Label agilityLabel;
    private Label luckLabel;
    private Label intelligenceLabel;
    private Label infoPanelTitle;
    private Label infoText;
    private Image currentSelectedImage;

    private Image strengthOne, strengthTwo;
    private Image agilityOne, agilityTwo;
    private Image luckOne, luckTwo;
    private Image intelligenceOne, intelligenceTwo;

    CharacterSelectScreen(JumazyController theGame, int numOfPlayers) {
        game = theGame;
        Skin skin = game.getSkin();
        stage = new Stage(new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT));
        selectedPlayerOrder = new ArrayList<CharacterName>();
        selectedImages = new ArrayList<Image>();

        //background image
        Image background = new Image(new Texture("playerSelectBackground.png"));
        stage.addActor(background);

        //setup table
        Table table = new Table();
        table.top();
        table.defaults().pad(10f);
        table.setFillParent(true);

        //top section
        Label titleLabel = new Label("Player 1, Please Select Your Character", skin);

        //player table
        Table playerTable = new Table();
        playerTable.top();

        //player list
        Table playerImages = new Table();
        playerImages.top();

        //large player image
        Image bigCharImage = new Image(new Texture("addtoskin/character1Box.png"));

        class CharacterImage extends Image{

            private CharacterImage(String internalPath, CharacterName name){
                super(new Texture(internalPath));
                Image image = this;

                addListener(new ClickListener(){
                    public void clicked(InputEvent event, float x, float y){
                        currentSelectedPlayer = name;
                        TextureRegionDrawable bigImage = new TextureRegionDrawable(game.getSprite("char"+name.getValue()));
                        bigImage.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                        bigImage.setMinHeight(200f);
                        bigImage.setMinWidth(200f);
                        bigCharImage.setDrawable(bigImage);
                        currentSelectedImage = image;

                        //reset stats to base
                        resetStats();

                        //add stats and info
                        setCharacterInformation(name);
                    }
                });
            }
        }

        CharacterName[] names = {CharacterName.RUBY_ROUNDHOUSE, CharacterName.SHELLY_OBERON,
                CharacterName.SMOLDER_BRAVESTONE, CharacterName.FRANKLIN_FINBAR};

        //default character needs to be made here for simulated click
        CharacterImage characterOne = new CharacterImage("addtoskin/character1Box.png", names[0]);
        playerImages.add(characterOne).align(Align.left).pad(10f);
        playerImages.add(new Label(names[0].getName(), skin)).align(Align.left);
        playerImages.row();

        //add characters to screen
        for(int i = 1; i < 4; i++){
            playerImages.add(new CharacterImage("addtoskin/character"+(i+1)+"Box.png",
                    names[i])).align(Align.left).pad(10f);
            playerImages.add(new Label(names[i].getName(), skin)).align(Align.left);
            playerImages.row();
        }

        playerTable.add(playerImages).align(Align.left).expandX();

        //second half of player table
        playerTable.add(bigCharImage).expandX();
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
                    selectedImages.get(selectedImages.size()-1).setColor(1f,1f,1f,1f);
                    selectedImages.remove(selectedImages.size()-1);
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
                titleLabel.setText("Player "+(selectedPlayerOrder.size()+1)+", Please Select Your Character");

                currentSelectedImage.setColor(0.1f, 0.1f, 0.1f, 1f);
                selectedImages.add(currentSelectedImage);
            }
        });

        //stats table
        Table statsTable = new Table();
        statsTable.top();

        Label statsLabel = new Label("Character Stats:", skin);
        statsLabel.getStyle().fontColor = Color.WHITE;
        statsTable.add(statsLabel).expandX();
        statsTable.row();

            //labels for stats
            Table labelTable = new Table();

            HashMap<String, Integer> statAmount = new LinkedHashMap<String, Integer>();
            statAmount.put("HP", 10);
            statAmount.put("Stamina", 5);
            statAmount.put("Strength", 4);
            statAmount.put("Agility", 4);
            statAmount.put("Luck", 4);
            statAmount.put("Intelligence", 4);

            stats = new LinkedHashMap<String, ArrayList<Image>>();

            for(String stat : statAmount.keySet()){
                labelTable.add(new Label(stat, skin)).expandX().align(Align.left).padLeft(10f);
                ArrayList<Image> imageList = new ArrayList<Image>();
                for(int i = 0; i < statAmount.get(stat); i++){
                    imageList.add(new Image(new Texture("addtoskin/stat-indicator.png")));
                    labelTable.add(imageList.get((i))).padLeft(5f);
                }
                stats.put(stat, imageList);
                labelTable.row();
            }

        statsTable.add(labelTable).expand().pad(10f).align(Align.left);

        //bottom section
        infoPanelTitle = new Label("", skin);
        infoPanelTitle.setAlignment(Align.center);

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
        table.add(infoText).colspan(2).fill().pad(20f);

        stage.addActor(table);

        //set default character selected
        InputEvent clickDown = new InputEvent();
        clickDown.setType(InputEvent.Type.touchDown);
        characterOne.fire(clickDown);
        InputEvent clickUp = new InputEvent();
        clickUp.setType(InputEvent.Type.touchUp);
        characterOne.fire(clickUp);

    }

    private void resetStats() {
        for(int i = 0; i < stats.get("Stamina").size(); i++)
            if(i < 3) stats.get("Stamina").get(i).setColor(1f,1f,1f,1f);
                else stats.get("Stamina").get(i).setColor(0f,0f,0f,0f);

        for(int i = 0; i < stats.get("Strength").size(); i++)
            if(i < 2) stats.get("Strength").get(i).setColor(1f,1f,1f,1f);
            else stats.get("Strength").get(i).setColor(0f,0f,0f,0f);

        for(int i = 0; i < stats.get("Agility").size(); i++)
            if(i < 2) stats.get("Agility").get(i).setColor(1f,1f,1f,1f);
            else stats.get("Agility").get(i).setColor(0f,0f,0f,0f);

        for(int i = 0; i < stats.get("Luck").size(); i++)
            if(i < 2) stats.get("Luck").get(i).setColor(1f,1f,1f,1f);
            else stats.get("Luck").get(i).setColor(0f,0f,0f,0f);

        for(int i = 0; i < stats.get("Intelligence").size(); i++)
            if(i < 2) stats.get("Intelligence").get(i).setColor(1f,1f,1f,1f);
            else stats.get("Intelligence").get(i).setColor(0f,0f,0f,0f);

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

    private void setCharacterInformation(CharacterName name){
        switch (name){
            case RUBY_ROUNDHOUSE:
                infoPanelTitle.setText("Ruby Roundhouse");
                infoText.setText("Ruby is faster than the rest, she has a greater chance of skipping over pesky traps " +
                        "letting you get through dangerous rooms and reap their rewards! If you answer a question wrong " +
                        "you get sent back to the start of your turn!");
                for(int i = 0; i < stats.get("Agility").size(); i++)
                    if(i < 4) stats.get("Agility").get(i).setColor(1f,1f,1f,1f);
                    else stats.get("Agility").get(i).setColor(0f,0f,0f,0f);
                break;
            case SHELLY_OBERON:
                infoPanelTitle.setText("Shelly Oberon");
                infoText.setText("Shelly has greater stamina than the others. This lets him move further each turn " +
                        "giving you an advantage in navigating the labyrinth! Your stamina is added to a dice roll, " +
                        "which determines how far you can move each turn");
                for(int i = 0; i < stats.get("Intelligence").size(); i++)
                    if(i < 4) stats.get("Intelligence").get(i).setColor(1f,1f,1f,1f);
                    else stats.get("Intelligence").get(i).setColor(0f,0f,0f,0f);
                break;
            case SMOLDER_BRAVESTONE:
                infoPanelTitle.setText("Smolder Bravestone");
                infoText.setText("Smolder is the strongest of the pack, he has a better chance at defeating the monsters " +
                        "you'll encounter in the labyrinth! Defeat monsters to further increase your strength to have a " +
                        "better chance against the monster guarding the Victory Room!");
                for(int i = 0; i < stats.get("Strength").size(); i++)
                    if(i < 4) stats.get("Strength").get(i).setColor(1f,1f,1f,1f);
                    else stats.get("Strength").get(i).setColor(0f,0f,0f,0f);
                break;
            case FRANKLIN_FINBAR:
                infoPanelTitle.setText("Franklin Finbar:");
                infoText.setText("Franklin is the luckiest of them all! He has a greater chance of finding useful items " +
                        "in chests, like potions and keys to increase your stats and unlock doors. Find enough keys and " +
                        "you can unlock the doors to victory!");
                for(int i = 0; i < stats.get("Luck").size(); i++)
                    if(i < 4) stats.get("Luck").get(i).setColor(1f,1f,1f,1f);
                    else stats.get("Luck").get(i).setColor(0f,0f,0f,0f);
                for(int i = 0; i < stats.get("Intelligence").size(); i++)
                    if(i < 3) stats.get("Intelligence").get(i).setColor(1f,1f,1f,1f);
                    else stats.get("Intelligence").get(i).setColor(0f,0f,0f,0f);
                break;
        }
    }

}
