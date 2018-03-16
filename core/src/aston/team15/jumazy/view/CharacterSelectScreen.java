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


public class CharacterSelectScreen implements Screen{

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
                agilityLabel.setText("Agility:");
                intelligenceLabel.setText("Intelligence:");
                strengthLabel.setText("Strength:");
                luckLabel.setText("Luck:");
                titleLabel.setText("Player "+(selectedPlayerOrder.size()+1)+", Please Select Your Character");

                currentSelectedImage.setColor(0.1f, 0.1f, 0.1f, 1f);
                selectedImages.add(currentSelectedImage);
            }
        });

        //stats table
        Table statsTable = new Table();
        statsTable.top();

        Label statsLabel = new Label("Character Stats:", skin);
        statsTable.add(statsLabel).expandX();
        statsTable.row();

            //labels for stats
            Table labelTable = new Table();
            Label hpLabel = new Label("HP:", skin);
            labelTable.add(hpLabel).expandX().align(Align.left).padLeft(10f);
            for(int i = 0; i < 10; i++)
                labelTable.add(new Image(new Texture("addtoskin/stat-indicator.png"))).padLeft(5f);

            labelTable.row();

            Label staminaLabel = new Label("Stamina:", skin);
            labelTable.add(staminaLabel).expandX().align(Align.left).padLeft(10f);
            for(int i = 0; i < 3; i++)
                labelTable.add(new Image(new Texture("addtoskin/stat-indicator.png"))).padLeft(5f);
            labelTable.row();

            strengthLabel = new Label("Strength:", skin);
            labelTable.add(strengthLabel).expandX().align(Align.left).padLeft(10f);
            for(int i = 0; i < 2; i++)
                labelTable.add(new Image(new Texture("addtoskin/stat-indicator.png"))).padLeft(5f);
            labelTable.add(strengthOne = new Image(new Texture("addtoskin/stat-indicator.png")));
            labelTable.add(strengthTwo = new Image(new Texture("addtoskin/stat-indicator.png")));
            labelTable.row();

            agilityLabel = new Label("Agility:", skin);
            labelTable.add(agilityLabel).expandX().align(Align.left).padLeft(10f);
            for(int i = 0; i < 2; i++)
                labelTable.add(new Image(new Texture("addtoskin/stat-indicator.png"))).padLeft(5f);
            labelTable.add(agilityOne = new Image(new Texture("addtoskin/stat-indicator.png")));
            labelTable.add(agilityTwo = new Image(new Texture("addtoskin/stat-indicator.png")));
            labelTable.row();

            luckLabel = new Label("Luck:", skin);
            labelTable.add(luckLabel).expandX().align(Align.left).padLeft(10f);
            for(int i = 0; i < 2; i++)
                labelTable.add(new Image(new Texture("addtoskin/stat-indicator.png"))).padLeft(5f);
            labelTable.add(luckOne = new Image(new Texture("addtoskin/stat-indicator.png")));
            labelTable.add(luckTwo = new Image(new Texture("addtoskin/stat-indicator.png")));
            labelTable.row();

            intelligenceLabel = new Label("Intelligence:", skin);
            labelTable.add(intelligenceLabel).expandX().align(Align.left).padLeft(10f);
            for(int i = 0; i < 2; i++)
                labelTable.add(new Image(new Texture("addtoskin/stat-indicator.png"))).padLeft(5f);
            labelTable.add(intelligenceOne = new Image(new Texture("addtoskin/stat-indicator.png")));
            labelTable.add(intelligenceTwo = new Image(new Texture("addtoskin/stat-indicator.png")));
            labelTable.row();

        resetStats();

        statsTable.add(labelTable).expand().pad(10f).align(Align.left);

        //bottom section
        infoPanelTitle = new Label("", skin);
        infoPanelTitle.setAlignment(Align.center);

        infoText = new Label("", skin);
        infoText.setWrap(true);

//        table.debugAll();
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
        strengthOne.setColor(0f, 0f, 0f, 0f);
        strengthTwo.setColor(0f,0f,0f,0f);
        agilityOne.setColor(0f, 0f, 0f, 0f);
        agilityTwo.setColor(0f,0f,0f,0f);
        luckOne.setColor(0f, 0f, 0f, 0f);
        luckTwo.setColor(0f,0f,0f,0f);
        intelligenceOne.setColor(0f, 0f, 0f, 0f);
        intelligenceTwo.setColor(0f,0f,0f,0f);
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
                infoPanelTitle.setText("Ruby Roundhouse:");
                infoText.setText("Ruby is faster than the rest, she has a greater chance of skipping over pesky traps " +
                        "letting you get through dangerous rooms and reap their rewards! If you answer a question wrong " +
                        "you get sent back to the start of your turn!");
                agilityOne.setColor(1f,1f,1f,1f);
                agilityTwo.setColor(1f,1f,1f,1f);
                break;
            case SHELLY_OBERON:
                infoPanelTitle.setText("Shelly Oberon:");
                infoText.setText("Shelly has greater stamina than the others. This lets him move further each turn " +
                        "giving you an advantage in navigating the labyrinth! Your stamina is added to a dice roll, " +
                        "which determines how far you can move each turn");
                intelligenceOne.setColor(1f,1f,1f,1f);
                intelligenceTwo.setColor(1f,1f,1f,1f);
                break;
            case SMOLDER_BRAVESTONE:
                infoPanelTitle.setText("Smolder Bravestone:");
                infoText.setText("Smolder is the strongest of the pack, he has a better chance at defeating the monsters " +
                        "you'll encounter in the labyrinth! Defeat monsters to further increase your strength to have a " +
                        "better chance against the monster guarding the Victory Room!");
                strengthOne.setColor(1f,1f,1f,1f);
                strengthTwo.setColor(1f,1f,1f,1f);
                break;
            case FRANKLIN_FINBAR:
                infoPanelTitle.setText("Franklin Finbar:");
                infoText.setText("Franklin is the luckiest of them all! He has a greater chance of finding useful items " +
                        "in chests, like potions and keys to increase your stats and unlock doors. Find enough keys and " +
                        "you can unlock the doors to victory!");
                luckOne.setColor(1f,1f,1f,1f);
                luckTwo.setColor(1f,1f,1f,1f);
                intelligenceOne.setColor(1f,1f,1f,1f);
                break;
        }
    }

}
