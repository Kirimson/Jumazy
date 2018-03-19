package aston.team15.jumazy.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class PlayerView extends Actor {
	private Sprite sprite;

	public PlayerView(float xPos, float yPos, TextureRegion texture) {
		this.sprite = new Sprite(texture);
		sprite.setPosition(xPos, yPos);
		setBounds(sprite.getX(), sprite.getY(), sprite.getHeight(), sprite.getWidth());
		setTouchable(Touchable.enabled);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}

	@Override
	protected void positionChanged() {
		sprite.setPosition(getX(), getY());
		super.positionChanged();
	}

	public void act(float delta, int keycode, int style) {
		super.act(delta);
		MoveByAction move = new MoveByAction();

		switch (keycode) {
		case Input.Keys.RIGHT:
			move.setAmount(32f, 0);
			break;
		case Input.Keys.LEFT:
			move.setAmount(-32f, 0);
			break;
		case Input.Keys.UP:
			move.setAmount(0, 32f);
			break;
		case Input.Keys.DOWN:
			move.setAmount(0, -32f);
			break;
		}
		
		if (style==2) {
			move.setReverse(true);
		}

		move.setDuration(0.05f);
		this.addAction(move);
	}

	public void moveToStartOfTurn(int row, int col) {
//		setX(col*32);
//		setY(row*32);
		addAction(Actions.sequence(Actions.moveTo(col*32, row*32, 0.5f)));
	}

//	public void moveTo

}
