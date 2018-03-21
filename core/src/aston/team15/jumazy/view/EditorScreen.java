package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class EditorScreen implements Screen {

    JumazyController game;

    private Stage editorStage;
    private Stage uiStage;
    private Table uiTable;
    private InputMultiplexer multiplexer;

    private ArrayList<EditBlockView> blocks;
    private String[][] room;
    private int roomSize = 10;
    private String currentTool = "O";

    public EditorScreen(JumazyController game) {
        Pixmap cursor = new Pixmap(Gdx.files.internal("mouse.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));

        this.game = game;

        //create stages
        FitViewport viewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
        editorStage = new Stage(viewport);
        uiStage = new Stage(viewport);
        uiTable = new Table(game.getSkin());
        uiTable.setFillParent(true);

        multiplexer = new InputMultiplexer(editorStage, uiStage);

        //initialise room
        room = new String[roomSize][roomSize];
        clearRoom();

        blocks = new ArrayList<EditBlockView>();

        drawRoom();

        Label editorLabel = new Label("Jumazy Room Editor", game.getSkin());

        uiTable.add(editorLabel).top().colspan(3);
        uiTable.row();

        drawTools();

        JumazyButton clearButton = new JumazyButton("Clear", game.getSkin());

        clearButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                clearRoom();
                drawRoom();
            }
        });

        MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);

        uiTable.add(clearButton).bottom().expand().pad(70);
        uiTable.add(backButton).bottom().right().pad(70).expand();
        uiStage.addActor(uiTable);
    }

    private void clearRoom() {
        for(int j = 0; j < roomSize; j++) {
            for (int i = 0; i < roomSize; i++) {
                room[i][j] = "O";

                if(i == 0 || i == roomSize-1) {
                    if(j == 2 || j == 3 || j == 6 ||j == 7)
                        room[i][j] = "O";
                    else
                        room[i][j] = "#";
                }

                if(j == 0 || j == roomSize-1) {
                    if(i == 2 || i == 3 || i == 6 || i == 7)
                        room[i][j] = "O";
                    else
                        room[i][j] = "#";
                }

            }
        }
    }

    private void drawTools() {

        String[] tools = {"O", "#", "W", "T", "C", "E", "X"};
        String[] textures = {"floor-squares", "wall-plain", "floor-single-water", "floor-trap-spikes",
                "chest-closed", "skeleton", "mummy"};

        Table toolTable = new Table();

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

        uiTable.add(toolTable).bottom().pad(70).expandX();
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

                switch (room[roomX][roomSize - 1 - roomY]) {
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
                            if(pointer == 0 && !room[block.getYCoord()][roomSize - 1 - block.getXCoord()].equals(currentTool)){
                                GameSound.playStepSound();
                                room[block.getYCoord()][roomSize - 1 - block.getXCoord()] = currentTool;
                                drawRoom();
                            }
                        }
                    });
                }
            }
        }
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
        editorStage.act();
        editorStage.draw();

        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        editorStage.getViewport().update(width, height, true);
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
