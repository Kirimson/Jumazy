package aston.team15.jumazy.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aston.team15.jumazy.view.GraphicsManager;

public class DieAnimation {
	// the textures of the different die results
	private Texture die1 = new Texture("number1.png");
	private Texture die2 = new Texture("number2.png");
	private Texture die3 = new Texture("number3.png");
	private Texture die4 = new Texture("number4.png");
	private Texture die5 = new Texture("number5.png");
	private Texture die6 = new Texture("number6.png");
	private Texture die7 = new Texture("number7.png");

	private int imageConstantAppearances = 24; // how often to display the dice at a constant rate before slowing down
	private int imageExponentialAppearances = 8; // how often to show the die before it exponentially slows down and
													// comes to a stop
	private float shortTime = 0.03125f; //
	private float nextTime = shortTime; //
	private int count = 1; //
	private float m = 1.125f; //
	private int currentPicIndex = -1; // which picture to display
	private float currentElapsedTime = 0.0f; //
	private Random gen = new Random(); // random number generator
	Texture[] pictureArray = { die1, die2, die3, die4, die5, die6, die7 }; // array to hold all the dice textures
	SpriteBatch batch = new SpriteBatch();
	private boolean animationFinished = false; // true if animation is finished

	private int finalDie;

	// batch.draw(pictureArray[gen.nextInt(5)+1], gs.currPlayerPosX + 20,
	// gs.currPlayerPosY-20, 100, 100);

	public DieAnimation() {
		getNewIndex();
	}

	
	/**
	 * @return
	 */
	public int getNewIndex() {
		boolean found = false; // by default a picture has not been found
		int index = currentPicIndex; // stores which picture we are on into index

		// while the picture isn't found look for another random image within the array
		// that isn't the current image
		while (!found) {
			index = gen.nextInt(pictureArray.length);

			if (index != currentPicIndex) {
				found = true;
				currentPicIndex = index;
			}
		}
		return index;
	}

	public void getNextTime() {
		if (count > imageConstantAppearances) {
			nextTime = shortTime * m * m * (count - imageConstantAppearances);
		}
	}

	public void setFinalDie(int finalDie) {
		reset();
		this.finalDie = finalDie;
		getNewIndex();
	}

	public void draw(SpriteBatch batch, float x, float y) {
		if (currentElapsedTime > nextTime && !animationFinished) {
			batch.draw(pictureArray[getNewIndex()], x + 50, y + 50);
			currentElapsedTime = 0.0f;
			count++;
			getNextTime();
			if (count == (imageConstantAppearances + imageExponentialAppearances)) {
				animationFinished = true;
			}
		} else if (animationFinished) {
			batch.draw(pictureArray[finalDie - 1], x + 50, y + 50);
		} else {
			batch.draw(pictureArray[currentPicIndex], x + 50, y + 50);
			currentElapsedTime += Gdx.graphics.getDeltaTime();

		}
	}

	public void decrease() {
		if (finalDie > 1) {
			finalDie--;
		}
	}

	public void reset() {
		animationFinished = false;
		currentElapsedTime = 0.0f;
		imageConstantAppearances = 24;
		imageExponentialAppearances = 8;
		shortTime = 0.03125f;
		nextTime = shortTime;
		count = 1;
		m = 1.125f;
		currentPicIndex = -1;
	}

	public boolean getAnimationFinished() {
		return animationFinished;
	}
}
