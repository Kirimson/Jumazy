package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.GameSound;
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

	QuestionUI(final JumazyController game) {
		Skin skin = game.getSkin();
		
		table = new Table();
		table.setFillParent(true);
		table.padTop(-100);
		table.center();
		
		questionUIBG = new Table();
		questionUIBG.setFillParent(true);
		questionUIBG.padTop(-100);
		questionUIBG.add(new Image(game.getSprite("scroll")));
		
		final JumazyButton btnSubmit = new JumazyButton("Submit", skin);
		tfAnswer = new TextField("", skin);
		lQuestion = new Label("", skin);

		lQuestion.setFontScale(0.6f);
		
		table.add(lQuestion);
		table.row();
		table.add(tfAnswer).width(400).padTop(50).height(50);

		table.row();
		table.add(btnSubmit).width(250).padTop(25).height(50);

		//if enter is pressed, simulate a click on the submit button. Need to send both a touchDown/Up for clicked
		tfAnswer.setTextFieldListener((textField, key) -> {
            if ((key == '\r' || key == '\n')){
				InputEvent clickDown = new InputEvent();
				clickDown.setType(InputEvent.Type.touchDown);
				btnSubmit.fire(clickDown);
				InputEvent clickUp = new InputEvent();
				clickUp.setType(InputEvent.Type.touchUp);
				btnSubmit.fire(clickUp);
            }
        });

		btnSubmit.addListener(new ClickListener() {

			public void clicked(InputEvent event, float x, float y) {
				
				correct = checkAnswer(tfAnswer.getText());

				table.remove();
				questionUIBG.remove();
				tfAnswer.setText("");

				File sound;
				if (correct) {
					GameSound.playCorrectSound();
				} else {
					GameSound.playIncorrectSound();
					game.incorrectRiddle();
				}
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