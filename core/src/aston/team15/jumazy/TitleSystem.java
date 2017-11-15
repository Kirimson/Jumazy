package aston.team15.jumazy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Manages the title screen of the games title screen, sub class of {@link MainSystem}
 * @author kieran
 *
 */
public class TitleSystem extends MainSystem{
	
	private Texture background;
	private Texture playBtn;

	public TitleSystem(SystemManager sysMan) {
		super(sysMan);
		background= new Texture("junglebg1.png");
		playBtn= new Texture("playBtn2.png");
	}

	@Override
	public SpriteBatch draw(SpriteBatch batch) {
		batch.draw(background, 0, 0, JumazyGame.WIDTH, JumazyGame.HEIGHT);
		batch.draw(playBtn,(JumazyGame.WIDTH/2)-(playBtn.getWidth()/2)+10,(JumazyGame.HEIGHT/2)-(playBtn.getHeight()/2)-120);
		return batch;
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			
			sysManager.setNewSystem(new GameSystem(sysManager));
		}
	}

}
