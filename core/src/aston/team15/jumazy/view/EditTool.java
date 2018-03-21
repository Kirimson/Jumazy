package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EditTool extends BlockView {
    private String tool;

    public EditTool(float xPos, float yPos, TextureRegion texture, String tool) {
        super(xPos, yPos, texture);
        this.tool = tool;
    }

    public String getTool() {
        return tool;
    }
}
