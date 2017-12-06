package aston.team15.jumazy.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aston.team15.jumazy.view.GraphicsManager;

public class DieAnimation {

	private Texture die1 = new Texture("number1.png");
	private Texture die2 = new Texture("number2.png");
	private Texture die3 = new Texture("number3.png");
	private Texture die4 = new Texture("number4.png");
	private Texture die5 = new Texture("number5.png");
	private Texture die6 = new Texture("number6.png");
	private int imageConstantAppearances = 24;
	private int imageExponentialAppearances = 8;
	private float shortTime = 0.03125f;
	//private float longestTime = 0.1f;
	private float nextTime = shortTime;
	private int count = 1;
	private float m = 1.125f;
	private int currentPicIndex = -1;
	private float currentElapsedTime = 0.0f;

	private Random gen = new Random();
	Texture[] pictureArray = {die1, die2, die3, die4, die5, die6};
	SpriteBatch batch = new SpriteBatch();
	private boolean animationFinished = false;

	private int finalDie;
	
	//batch.draw(pictureArray[gen.nextInt(5)+1], gs.currPlayerPosX + 20, gs.currPlayerPosY-20, 100, 100);

	public DieAnimation() {
		getNewIndex();
	}

	public int getNewIndex() {
		boolean found = false;
		int index = currentPicIndex;
		while(!found) {
			index = gen.nextInt(pictureArray.length);
			if(index != currentPicIndex) {
				found = true;
				currentPicIndex = index;
			}
		}
		return index;
	}

	public void getNextTime() {
		if(count > imageConstantAppearances) {
			nextTime = shortTime * m * m * (count - imageConstantAppearances);
		}
	}
	
	public void setFinalDie(int finalDie) {
		this.finalDie = finalDie;
	}

	public void draw(SpriteBatch batch) {
		if(currentElapsedTime > nextTime && !animationFinished ) {
			batch.draw(pictureArray[getNewIndex()], 100, 100, 100, 100);
			currentElapsedTime = 0.0f;
			count++;
			getNextTime();
			if(count == (imageConstantAppearances + imageExponentialAppearances)) {
				animationFinished = true;
			}

		} else if(animationFinished) { 
			batch.draw(pictureArray[finalDie - 1], 100, 100, 100, 100);
		} else {
			batch.draw(pictureArray[currentPicIndex], 100, 100, 100, 100);
			System.out.println(Gdx.graphics.getDeltaTime());
			currentElapsedTime += Gdx.graphics.getDeltaTime();
			System.out.println(Gdx.graphics.getDeltaTime() + " : " + currentElapsedTime + " : " + nextTime);
		}
	}
	
	public void decrease() {
		if(finalDie > 1) {
		finalDie--;
		}
	}
}
