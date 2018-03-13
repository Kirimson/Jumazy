package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class QuestionUI {

	private Table table;
	private Table questionUIBG;
	private boolean correct;
	private Label lQuestion;
	private String[] questionAndAnswer;
	private final TextField tfAnswer;

	public QuestionUI(final JumazyController game) {
		Skin skin = game.getSkin();
		
		table = new Table();
		table.setFillParent(true);
		table.padTop(-100);
		table.center();
		
		questionUIBG = new Table();
		questionUIBG.setFillParent(true);
		questionUIBG.padTop(-100);
		questionUIBG.add(new Image(game.getSprite("scroll")));
		
		final TextButton btnSubmit = new TextButton("Submit", skin);
		tfAnswer = new TextField("", skin);
		lQuestion = new Label("", skin);
		
		lQuestion.setFontScale(0.6f);
		
		table.add(lQuestion);
		table.row();
		table.add(tfAnswer).width(400).padTop(50).height(50);

		table.row();
		table.add(btnSubmit).width(250).padTop(25).height(50);

		btnSubmit.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				
				correct = checkAnswer(tfAnswer.getText());

				table.remove();
				questionUIBG.remove();
				tfAnswer.setText("");

				File sound;
				if (correct) {
					sound = new File("../assets/snd/correct.wav");
				} else {
					sound = new File("../assets/snd/incorrect.wav");
					game.incorrectRiddle();
				}
				playSound(sound);
				game.resume();
			}
		});

	}

	public void displayQuestion(String[] questionAndAns) {
		questionAndAnswer = questionAndAns;
		if(questionAndAnswer != null) {
			lQuestion.setText(questionAndAnswer[0]);
		}
	}

	/**
	 * checks if the given answer by player matches a corrent answer from the list of answers given by the csv when a
	 * new question is created
	 * @param answer answer user gabe in textfield
	 * @return true if answer is correct
	 */
	private boolean checkAnswer(String answer) {
		int i = 1;
		while (i < questionAndAnswer.length) {
			if (answer.toLowerCase().equals(questionAndAnswer[i].toLowerCase())) {
				if (JumazyController.DEBUG_ON)
					System.out.println("Correct");
				return true;
			}
			i++;
		}
		if (JumazyController.DEBUG_ON)
			System.out.println("Incorrect");
		return false;
	}

	/**
	 * plays a given sound, catches any file Exceptions
	 * @param sound sound to play
	 */
	private void playSound(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * checks if questionUI is not active, returns true if table has no stage
	 * @return true if table is not on a stage
	 */
	public boolean notActive() {
		return table.getStage() == null;
	}

	/**
	 * adds the questionUI actors to a set stage, and gives the answer TextField keyboard focus through the stage
	 * @param stage stage to add Actors to
	 */
	public void addToStage(Stage stage) {
		stage.addActor(questionUIBG);
		stage.addActor(table);
		stage.setKeyboardFocus(tfAnswer);
	}
}