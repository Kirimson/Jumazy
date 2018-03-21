package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EditTool extends Actor {
    private String tool;
    private Sprite sprite;
    private boolean highlighted;

    public EditTool(TextureRegion texture, String tool) {
        sprite = new Sprite(texture);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
        setBounds(sprite.getX(), sprite.getY(), sprite.getHeight(), sprite.getWidth());
        this.tool = tool;
    }

    public void setHighlighted(){
        highlighted = true;
    }

    public String getTool() {
        return tool;
    }

    public void unHighlight() {
        highlighted = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(highlighted)
            batch.setColor(0.4f, 0.4f, 0.4f, parentAlpha);
        else
            batch.setColor(1f, 1f, 1f, parentAlpha);
        batch.draw(sprite, getX(), getY());

    }
}
