package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class EditorScreen implements Screen {

    JumazyController game;

    //for the actual room, only contains the roomTiles. will be cleared when edited, then redrawn
    private Stage editorStage;

    private ArrayList<EditBlockView> blocks;
    private String[][] room;

    private String currentTool = "O";

    private FitViewport viewport;

    public EditorScreen(JumazyController game) {
        Pixmap cursor = new Pixmap(Gdx.files.internal("mouse.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));

        this.game = game;

        //create stages
        viewport = new FitViewport(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
        editorStage = new Stage(viewport);

        //initialise room
        room = new String[8][8];
        for(int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                room[i][j] = "O";
            }
        }
        blocks = new ArrayList<EditBlockView>();

        drawRoom();

        drawTools();

        editorStage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {

                switch (keycode) {
                    case Input.Keys.E:
                        currentTool = "#";
                        break;
                    case Input.Keys.W:
                        currentTool = "W";
                        break;
                    default:
                }

                return true;
            }
        });

    }

    private void drawTools() {

        String[] tools = {"O", "#", "W", "T", "C", "E", "X"};
        String[] textures = {"floor-squares", "wall-plain", "floor-single-water", "floor-trap-spikes",
                "chest-closed", "skeleton", "mummy"};

        for(int i = 0; i < tools.length; i++){
            EditTool tool = new EditTool(0, JumazyController.WORLD_HEIGHT-((i+1)*36),
                    game.getSprite(textures[i]), tools[i]);

            tool.addListener(new ClickListener(){
               public void clicked(InputEvent event, float x, float y){
                   currentTool = tool.getTool();
                   System.out.println("update tool to"+ currentTool);
               }
            });

            editorStage.addActor(tool);
        }

    }

    private void drawRoom(){

        if(!blocks.isEmpty()) {
            for (EditBlockView b : blocks)
                b.remove();
        }

        int blockDimension = 32;
        //center blocks in middle of screen
        int roomBottom = (JumazyController.WORLD_HEIGHT/2) - ((blockDimension*8)/2);

        //for far rooms starts from left of screen
        int roomLeft = 600;

        for(int roomY = 0; roomY < 8; roomY++){
            for(int roomX = 0; roomX < 8; roomX++){
                EditBlockView block;

                System.out.print(room[roomX][roomY]);

                 switch (room[roomX][7-roomY]){
                     case "#":
                         block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                 game.getSprite(generateWallTexture(room, roomX, 7-roomY)), roomY, roomX);
                         break;
                     case "W":
                         block = new EditBlockView(roomLeft + roomX * blockDimension, roomBottom + roomY * blockDimension,
                                 game.getSprite(generateWaterTexture(room, roomX, 7-roomY)), roomY, roomX);
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

                block.addListener(new ClickListener(){
                    public void clicked(InputEvent event, float x, float y) {
                        if(isPressed()) {
                            if(!room[block.getYCoord()][7 - block.getXCoord()].equals(currentTool))
                                room[block.getYCoord()][7 - block.getXCoord()] = currentTool;
                            else room[block.getYCoord()][7 - block.getXCoord()] = "O";
                            drawRoom();
                        }
                    }
                });
            }
            System.out.println();
        }
        System.out.println();
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
        if(mazeY < 7){
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
        Gdx.input.setInputProcessor(editorStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        editorStage.act();
        editorStage.draw();
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
