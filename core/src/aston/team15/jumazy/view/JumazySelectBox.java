package aston.team15.jumazy.view;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.lang.reflect.Array;

/**
 * SelectBox that has extra left padding
 *
 * @author Kieran
 * @param <T>
 */
public class JumazySelectBox<T> extends SelectBox<T> {

    public JumazySelectBox(Skin skin){
        super(skin);
        getStyle().background.setLeftWidth(20f);
    }

    @Override
    public void setItems (T... newItems) {
        super.setItems(newItems);
        getList().getStyle().selection.setLeftWidth(20f);
    }

    @Override
    public void setItems (com.badlogic.gdx.utils.Array<T> newItems) {
        super.setItems(newItems);
        getList().getStyle().selection.setLeftWidth(20f);
        getList().getStyle().selection.setBottomHeight(10f);
    }

}
