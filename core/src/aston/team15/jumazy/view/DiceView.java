package aston.team15.jumazy.view;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DiceView extends Actor {

	private int imageConstantAppearances = 24; // how often to display the dice at a constant rate before slowing down
	// comes to a stop
	private float shortTime = 0.03125f; //
	private float nextTime = shortTime; //
	private int count = 1; //
	private int currentRollNumber = 1; // which picture to display
	private float currentElapsedTime = 0.0f; //
	private Random gen = new Random(); // random number generator
	private boolean rollFinished = true; // true if animation is finished
	private boolean canRoll = true;
	private long lastTime = System.nanoTime();
	private int finalDie = -1;

	private Sprite sprite;

	public DiceView(float xPos, float yPos, TextureRegion textureRegion) {
		sprite = new Sprite(textureRegion);
		sprite.setPosition(xPos, yPos);
	}

	public void setPosition(float xPos, float yPos) {
		sprite.setPosition(xPos + 32f, yPos + 32f);
	}

	public void updateSprite(TextureRegion textureRegion) {
		float oldX = sprite.getX();
		float oldY = sprite.getY();
		sprite = new Sprite(textureRegion);

		sprite.setPosition(oldX, oldY);
	}

	/**
	 * @return index the picture which is currently being displayed
	 */
	private int getNewIndex() {
		boolean found = false; // by default a picture has not been found
		int index = currentRollNumber; // stores which picture we are on into index

		// while the picture isn't found look for another random image within the array
		// that isn't the current image
		while (!found) {
			index = gen.nextInt(7) + 1;

			if (index != currentRollNumber) {
				found = true;
				currentRollNumber = index;
			}
		}
		return index;
	}

	private void getNextTime() {
		if (count > imageConstantAppearances) {
			float m = 1.125f;
			nextTime = shortTime * m * m * (count - imageConstantAppearances);
		}
	}

	public void setDie(int finalDie) {
		reset();
		this.finalDie = finalDie;
		getNewIndex();
		rollFinished = false;
	}

	private void reset() {
		rollFinished = false;
		currentElapsedTime = 0.0f;
		shortTime = 0.03125f;
		nextTime = shortTime;
		count = 1;
		currentRollNumber = 0;
		finalDie = -1;
	}

	public void decreaseRoll() {
		if (finalDie >= 1) {

			finalDie--;
		}
	}

	public boolean isRollFinished() {
		return rollFinished;
	}

	public void setCanRoll() {
		canRoll = true;
	}

	public boolean canRoll() {
		return canRoll;
	}

	public int roll() {
		int rollNumber;

		if (currentElapsedTime > nextTime && !rollFinished) {
			rollNumber = getNewIndex();
			currentElapsedTime = 0.0f;
			count++;
			getNextTime();
			int imageExponentialAppearances = 8;
			if (count == (imageConstantAppearances + imageExponentialAppearances)) {
				rollFinished = true;
				canRoll = false;
				rollNumber = finalDie;
			}
		} else {
			rollNumber = currentRollNumber;
			long time = System.nanoTime();
			currentElapsedTime += (float) ((time - lastTime) / 1000000);
			lastTime = time;
		}
		return rollNumber;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}

	public int getRoll() {
		return finalDie;
	}
}
