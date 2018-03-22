package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.PlayerModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class EditorScreen implements Screen {

    JumazyController game;

    private Stage editorStage;
    private Stage uiStage;
    private Table editorTable;
    private InputMultiplexer multiplexer;
    private Sprite background;

    private ArrayList<EditBlockView> blocks;
    private String[][] room;
    private String currentRoomName;
    private int roomSize = 10;
    private String currentTool = "O";
    private LinkedHashMap<String, String[][]> roomLayouts;
    private JumazySelectBox<String> layoutList;
    private Array<String> names;

    public EditorScreen(JumazyController game) {
        Pixmap cursor = new Pixmap(Gdx.files.internal("mouse.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));

        this.game = game;

        background = new Sprite(game.getSprite("settingsback"));
        background.setSize(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);

        //create stages
        FitViewport viewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
        editorStage = new Stage(viewport);
        uiStage = new Stage(viewport);
        Table uiTable = new Table(game.getSkin());
        uiTable.setFillParent(true);
        uiTable.top();

        multiplexer = new InputMultiplexer(editorStage, uiStage);
        layoutList = new JumazySelectBox<String>(game.getSkin());

        //read the rooms
        readRooms(false);

        //make a 10x10 room, feed the 8x8 layout into it
        room = new String[roomSize][roomSize];
        loadRoom();

        //add blocks from layout to stage
        blocks = new ArrayList<EditBlockView>();
        drawRoom();

        //ui stuff
        Label editorLabel = new Label("Jumazy Room Editor", game.getSkin());

        uiTable.add(editorLabel).top().colspan(2).padTop(10f);
        uiTable.row();

        editorTable = new Table(game.getSkin());

        Label layoutLabel = new Label("Current Layout",game.getSkin());
        editorTable.add(layoutLabel).padBottom(10f);
        editorTable.row();
        editorTable.add(layoutList).width(300);
        editorTable.row();

        //new room
        JumazyButton newLayoutButton = new JumazyButton("New Room", game.getSkin());
        newLayoutButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                createNewRoom();
            }
        });
        editorTable.add(newLayoutButton).pad(10);
        editorTable.row();

        drawTools();

        uiTable.add(editorTable).expand().left().padLeft(70f);

        Table buttonTable = new Table();

        //clear button
        JumazyButton clearButton = new JumazyButton("Clear Room", game.getSkin());
        clearButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                roomLayouts.replace(currentRoomName, blankLayout());
                loadRoom();
                drawRoom();
            }
        });
        buttonTable.add(clearButton).bottom().expandX().pad(10);
        buttonTable.row();

        //remove room
        JumazyButton removeButton = new JumazyButton("Remove Room", game.getSkin());
        removeButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                boolean victoryRoom = false;
                for(String[] row : roomLayouts.get(currentRoomName)){
                    for(String cell : row){
                        if(cell.equals("V"))
                            victoryRoom = true;
                    }
                }

                if(names.size > 1 && !victoryRoom) {
                    String oldName = currentRoomName;
                    roomLayouts.remove(currentRoomName);
                    layoutList.clearItems();

                    names = new Array<String>();

                    for (String s : roomLayouts.keySet())
                        names.add(s);

                    layoutList.setItems(names);

                    for (String s : roomLayouts.keySet()) {
                        currentRoomName = s;
                        layoutList.setSelected(s);
                        break;
                    }

                    loadRoom();
                    drawRoom();

                    popUp("Room " + oldName + " has been deleted");
                } else popUp(victoryRoom ? "You can't delete the victory Room!" : "You can't delete the last room!");
            }
        });
        buttonTable.add(removeButton).pad(10);
        buttonTable.row();

        //reset to custom
        JumazyButton resetButton = new JumazyButton("Reset to Last Save", game.getSkin());
        resetButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                layoutList.clearItems();
                readRooms(false);
                loadRoom();
                drawRoom();

                popUp("Rooms have been reset to your last export");
            }
        });
        buttonTable.add(resetButton).pad(10);
        buttonTable.row();

        //reset to internal default
        JumazyButton defaultButton = new JumazyButton("Reset to Default", game.getSkin());
        defaultButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                layoutList.clearItems();
                readRooms(true);
                layoutList.setSelected("1: Pillars");
                loadRoom();
                drawRoom();

                popUp("Rooms have been reset to default");
            }
        });
        buttonTable.add(defaultButton).pad(10);
        buttonTable.row();

        //export button
        JumazyButton exportButton = new JumazyButton("Export to File", game.getSkin());
        exportButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){

                //make the entire roomLayout arrayList into a string
                StringBuffer roomFile = new StringBuffer();

                for(String name : roomLayouts.keySet()){
                    roomFile.append("/"+name+"\n");

                    String[][] layout = roomLayouts.get(name);

                    for(int i = 0; i < 8; i++){
                        for(int j = 0; j < 8; j++){
                            roomFile.append(layout[j][i]);
                        }
                        roomFile.append("\n");
                    }
                }

                FileHandle file = Gdx.files.local("RoomLayoutsSize8.txt");
                file.writeString(roomFile.toString(), false);

                popUp("Rooms were successfully exported!");

            }
        });
        buttonTable.add(exportButton).pad(10);
        buttonTable.row();

        MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);
        buttonTable.add(backButton).pad(10);


        uiTable.add(buttonTable).expand().right().padRight(70f);

        uiStage.addActor(uiTable);
    }

    private void popUp(String popupText) {

        Label popupLabel = new Label(popupText, game.getSkin());
        popupLabel.setFontScale(2f);

        Table popupBackgroundTable = new Table(game.getSkin());
        popupBackgroundTable.setFillParent(true);
        Image bg = new Image(game.getSprite("settingsback"));
        popupBackgroundTable.add(bg);

        Table popupTable = new Table(game.getSkin());
        popupTable.setFillParent(true);
        popupTable.center();
        popupTable.add(popupLabel);

        uiStage.addActor(popupBackgroundTable);
        uiStage.addActor(popupTable);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {

                RunnableAction removeTable = new RunnableAction();
                removeTable.setRunnable(popupTable::remove);

                RunnableAction removeBG = new RunnableAction();
                removeBG.setRunnable(popupBackgroundTable::remove);

                popupTable.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.5f), removeTable));
                popupBackgroundTable.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.5f), removeBG));

            }
        }, 1f);

    }

    /**
     * Reads rooms from the layout file
     */
    private void readRooms(boolean reset) {
        //initialise room
        String currentLine;
        String currentChar;
        int layoutSize=8;

        FileHandle file;
        //creates a list of room layouts from provided file. ArrayList of String[][]
        //check if custom layout is there
        file = Gdx.files.local("RoomLayoutsSize8.txt");

        //fallback to internal
        if(!file.exists() || reset)
            file = Gdx.files.internal("roomlayouts/RoomLayoutsSize8.txt");


        String[] lines = file.readString().split("\r\n|\r|\n");
        String name;
        names = new Array<String>();
        roomLayouts = new LinkedHashMap<String, String[][]>();

        for(int currentLineIndex = 0; currentLineIndex < lines.length-1; currentLineIndex++){
            currentLine = lines[currentLineIndex];
            if(!currentLine.startsWith("/")){
                name = lines[currentLineIndex-1].substring(1);
                names.add(name);

                String[][] newLayout = new String[layoutSize][layoutSize];
                for(int j = 0; j < layoutSize; j++) {
                    for(int i = 0; i < layoutSize; i++) {
                        currentChar = currentLine.substring(i, i+1);
                        newLayout[i][j]=currentChar;
                    }
                    currentLineIndex = currentLineIndex+1;
                    if(currentLineIndex < lines.length)
                        currentLine = lines[currentLineIndex];
                }
                roomLayouts.put(name, newLayout);
            }
        }

        currentRoomName = names.get(0);

        //list of room layout from read file
        layoutList.setItems(names);
    }

    private void createNewRoom() {

        Gdx.input.setInputProcessor(uiStage);

        //background
        Table newRoomBG = new Table();
        newRoomBG.setFillParent(true);
        newRoomBG.add(new Image(game.getSprite("settingsback"))).height(355.0f).width(600);
        uiStage.addActor(newRoomBG);

        Table newRoomTable = new Table();
        newRoomTable.setFillParent(true);
        newRoomTable.center();

        //Title
        Label createTitleLabel = new Label("Create a new room", game.getSkin());
        newRoomTable.add(createTitleLabel).pad(10).colspan(2);
        newRoomTable.row();

        //text input
        TextField nameField = new TextField("Untitled Room", game.getSkin());
        nameField.setAlignment(Align.center);
        newRoomTable.add(nameField).width(400).height(50).pad(10).colspan(2);
        newRoomTable.row();

        //create button
        JumazyButton createButton = new JumazyButton("Create", game.getSkin());
        newRoomTable.add(createButton).bottom().pad(10);

        JumazyButton cancelButton = new JumazyButton("Cancel", game.getSkin());
        newRoomTable.add(cancelButton).bottom().pad(10);

        cancelButton.addListener(new ClickListener() {
             public void clicked(InputEvent event, float x, float y) {
                 newRoomBG.remove();
                 newRoomTable.remove();
                 Gdx.input.setInputProcessor(multiplexer);
             }
         });

        createButton.addListener(new ClickListener(){
           public void clicked(InputEvent event, float x, float y){
               if(!nameField.getText().isEmpty()) {
                   if (!names.contains(nameField.getText(), false)) {
                       String name = nameField.getText();

                       names.add(name);
                       roomLayouts.put(name, blankLayout());

                       layoutList.clearItems();
                       layoutList.setItems(names);
                       layoutList.setSelected(name);

                       newRoomBG.remove();
                       newRoomTable.remove();
                       Gdx.input.setInputProcessor(multiplexer);
                   } else popUp("Room with that name exists!");
               } else popUp("Room name can't be empty!");
           }
        });


        nameField.setTextFieldListener((textField, key) -> {
            if ((key == '\r' || key == '\n')) {
                InputEvent clickDown = new InputEvent();
                clickDown.setType(InputEvent.Type.touchDown);
                createButton.fire(clickDown);
                InputEvent clickUp = new InputEvent();
                clickUp.setType(InputEvent.Type.touchUp);
                createButton.fire(clickUp);
            }
        });

        uiStage.addActor(newRoomTable);
        uiStage.setKeyboardFocus(nameField);
    }

    private String[][] blankLayout() {
        String[][] empty = new String[8][8];

        for(int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                empty[i][j] = "O";
            }
        }
        return empty;
    }

    private void loadRoom() {
        for(int j = 0; j < roomSize; j++) {
            for (int i = 0; i < roomSize; i++) {

                if(i == 0 || i == roomSize-1) {
                    if(j == 2 || j == 3 || j == 6 ||j == 7)
                        room[i][j] = "O";
                    else
                        room[i][j] = "#";
                }else if(j == 0 || j == roomSize-1) {
                    if(i == 2 || i == 3 || i == 6 || i == 7)
                        room[i][j] = "O";
                    else
                        room[i][j] = "#";
                }
                else room[i][j] = roomLayouts.get(currentRoomName)[i-1][j-1]; //minus one as we're already 1 in
            }
        }
    }

    private void drawTools() {

        String[] tools = {"O", "#", "W", "T", "C", "E", "X"};
        String[] textures = {"floor-squares", "wall-plain", "floor-single-water", "floor-trap-spikes",
                "chest-closed", "skeleton", "mummy"};

        Table toolTable = new Table(game.getSkin());

        Label toolLabel = new Label("Tools:",game.getSkin());
        toolTable.add(toolLabel).padTop(10f).center();
        toolTable.row();

        ArrayList<EditTool> toolImages = new ArrayList<EditTool>();
        for(int i = 0; i < tools.length; i++){
            EditTool tool = new EditTool(game.getSprite(textures[i]), tools[i]);

            toolImages.add(tool);

            tool.addListener(new ClickListener(){
               public void clicked(InputEvent event, float x, float y){
                   GameSound.playStepSound();
                   currentTool = tool.getTool();
                   for(EditTool t : toolImages)
                       t.unHighlight();

                   tool.setHighlighted();
               }
            });

            toolTable.add(tool).padLeft(4f).padRight(4f);
        }
        editorTable.add(toolTable).padBottom(50f);
        editorTable.row();

        //set floor to be highlighted
        toolImages.get(0).setHighlighted();
    }

    private void drawRoom(){

        if(!blocks.isEmpty()) {
            for (EditBlockView b : blocks)
                b.remove();
        }

        int blockDimension = 32;
        //center blocks in middle of screen
        int roomBottom = (JumazyController.WORLD_HEIGHT/2) - ((blockDimension*roomSize)/2);

        //for far rooms starts from left of screen
        int roomLeft = (JumazyController.WORLD_WIDTH/2) - ((blockDimension*roomSize)/2);

        for(int roomY = 0; roomY < roomSize; roomY++){
            for(int roomX = 0; roomX < roomSize; roomX++) {
                EditBlockView block;

                switch (room[roomX][(roomSize - 1) - roomY]) {
                    case "#":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite(generateWallTexture(room, roomX, roomSize - 1 - roomY)), roomY, roomX);
                        break;
                    case "W":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite(generateWaterTexture(room, roomX, roomSize - 1 - roomY)), roomY, roomX);
                        break;
                    case "T":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite("floor-trap-spikes"), roomY, roomX);
                        break;
                    case "C":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite("chest-closed"), game.getSprite("floor-squares"), roomY, roomX);
                        break;
                    case "E":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite("skeleton"), game.getSprite("floor-squares"), roomY, roomX);
                        break;
                    case "X":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite("mummy"), game.getSprite("floor-squares"), roomY, roomX);
                        break;
                    case "Z":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite(generateBossSprite(room, roomX, roomY)), game.getSprite("floor-squares"), roomY, roomX);
                        break;
                    case "D":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite("door"), game.getSprite("floor-squares"), roomY, roomX);
                        break;
                    case "V":
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite("victory-statue"), game.getSprite("floor-squares"), roomY, roomX);
                        break;
                    default:
                        //floor
                        block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                game.getSprite("floor-squares"), roomY, roomX);
                }
                blocks.add(block);
                editorStage.addActor(block);

                if (roomX != 0 && roomX != roomSize - 1 && roomY != 0 && roomY != roomSize - 1) {
                    block.addListener(new ClickListener() {
                        public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            String thisBlock = room[block.getYCoord()][roomSize - 1 - block.getXCoord()];
                            if(pointer == 0 && !thisBlock.equals(currentTool)){
                                if(!thisBlock.equals("V") && !thisBlock.equals("D") && !thisBlock.equals("Z")) {
                                    GameSound.playStepSound();
                                    room[block.getYCoord()][roomSize - 1 - block.getXCoord()] = currentTool;
                                    drawRoom();

                                    roomLayouts.get(currentRoomName)[block.getYCoord() - 1][roomSize - 1 - block.getXCoord() - 1] = currentTool;
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private String generateBossSprite(String[][] room, int roomX, int roomY) {
        if (room[roomX][roomY - 1].equals("Z") && room[roomX - 1][roomY].equals("Z"))
            return "bossUR";

        //upperleft
        if (room[roomX][roomY + 1].equals("Z") && room[roomX - 1][roomY].equals("Z"))
            return "bossLR";

        //lowerright
        if (room[roomX][roomY - 1].equals("Z") && room[roomX + 1][roomY].equals("Z"))
            return "bossUL";

        //lowerleft
        if (room[roomX][roomY + 1].equals("Z") && room[roomX + 1][roomY].equals("Z"))
            return "bossLL";
        return "bossLL";
    }

    /**
     * generates correct type of wall depending on walls relative to this wall
     *
     * @return string for wall texture
     * @param maze
     *            the maze string
     * @param mazeX
     *            y position in maze
     * @param mazeY
     *            x position in maze
     */
    private String generateWallTexture(String[][] maze, int mazeX, int mazeY) {

        //if there is not a wall below this tile
        if(mazeY < roomSize-1){
            if (!maze[mazeX][mazeY + 1].equals("#"))
                return "wall-plain";
        }

        return "wall-no-edge";
    }

    private String generateWaterTexture(String[][] maze, int mazeX, int mazeY) {

        if (mazeX > 0 && mazeX < maze[0].length - 1) {
            if (maze[mazeX- 1][mazeY].equals("W") && maze[mazeX + 1][mazeY].equals("W"))
                return "floor-middle-water";
            else if (maze[mazeX - 1][mazeY].equals("W") && !maze[mazeX + 1][mazeY].equals("W"))
                return "floor-right-water";
            else if (!maze[mazeX - 1][mazeY].equals("W") && maze[mazeX+ 1][mazeY ].equals("W"))
                return "floor-left-water";

        }

        return "floor-single-water";
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(currentRoomName != layoutList.getSelected()){
            currentRoomName = layoutList.getSelected();
            loadRoom();
            drawRoom();
        }

        editorStage.getBatch().begin();
        background.draw(editorStage.getBatch());
        editorStage.getBatch().end();

        editorStage.act();
        editorStage.draw();

        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        editorStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
