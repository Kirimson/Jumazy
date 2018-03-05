package aston.team15.jumazy.model;

import java.util.Random;

public class DiceModel {

	private int imageConstantAppearances = 24; // how often to display the dice at a constant rate before slowing down
	// comes to a stop
	private float shortTime = 0.03125f; //
	private float nextTime = shortTime; //
	private int count = 1; //
	private int currentRollNumber = 1; // which picture to display
	private float currentElapsedTime = 0.0f; //
	private Random gen = new Random(); // random number generator
	private boolean rollFinished = false; // true if animation is finished
	private boolean canRoll = true;

	private long lastTime = System.nanoTime();

	private int finalDie = -1;  

	public DiceModel() {

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

	public void setDie(int maxRoll) {
		reset();
		this.finalDie = new Random().nextInt(maxRoll) + 1;
		getNewIndex();
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

	public int getRoll() {
		return finalDie;
	}
}
