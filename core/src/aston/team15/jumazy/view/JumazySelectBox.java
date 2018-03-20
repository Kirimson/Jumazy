package aston.team15.jumazy.view;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Kieran
 * @param <T>
 */
public class JumazySelectBox<T> extends SelectBox<T> {

    JumazySelectBox(Skin skin){
        super(skin);
        getStyle().background.setLeftWidth(20f);
    }

    @Override
    public void setItems (T... newItems) {
        super.setItems(newItems);
        getList().getStyle().selection.setLeftWidth(20f);
    }

}
