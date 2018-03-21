package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EditBlockView extends BlockView {
    private int xCoord, yCoord;
    private boolean clickedIn;

    public EditBlockView(float xPos, float yPos, TextureRegion texture, int xCoord, int yCoord) {
        super(xPos, yPos, texture);
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public EditBlockView(float xPos, float yPos, TextureRegion texture, TextureRegion backgroundTexture , int xCoord, int yCoord) {
        super(xPos, yPos, texture, backgroundTexture);
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public boolean isClickedIn() {
        return clickedIn;
    }

    public void setClickedIn(boolean clickedIn) {
        this.clickedIn = clickedIn;
    }
}
