package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;

public class QuestionUI {

	private Table table;
	private Table questionUIBG;
	private boolean correct;
	private Label lQuestion;
	private String[] questionAndAnswer;
	private final TextField tfAnswer;

	QuestionUI(final JumazyController game, HeadsUpDisplay hud) {
		Skin skin = game.getSkin();

		table = new Table();
		table.setFillParent(true);
		table.padTop(-175);
		table.center();

		questionUIBG = new Table();
		questionUIBG.setFillParent(true);
		questionUIBG.padTop(-175);
		questionUIBG.add(new Image(new Texture("scroll1.png"))).width(1300).height(550);

		final JumazyButton btnSubmit = new JumazyButton("Submit", skin);
		tfAnswer = new TextField("", skin);
		lQuestion = new Label("", skin);

		lQuestion.setFontScale(0.75f);

		table.add(lQuestion);
		table.row();
		table.add(tfAnswer).width(400).padTop(50).height(50);

		table.row();
		table.add(btnSubmit).width(250).padTop(25).height(50);

		// if enter is pressed, simulate a click on the submit button. Need to send both
		// a touchDown/Up for clicked
		tfAnswer.setTextFieldListener((textField, key) -> {
			if ((key == '\r' || key == '\n')) {
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

				if (correct) {
					GameSound.playCorrectSound();
					hud.setPlayerConsoleText("Nice! You answered correctly!");
				} else {
					GameSound.playIncorrectSound();
					hud.setPlayerConsoleText("Incorrect! Move back a turn.");
					game.incorrectRiddle();
				}
				game.resume();
			}
		});

	}

	public void displayQuestion(String[] questionAndAns) {
		questionAndAnswer = questionAndAns;
		if (questionAndAnswer != null) {
			lQuestion.setText(questionAndAnswer[0]);
		}
	}

	/**
	 * checks if the given answer by player matches a corrent answer from the list
	 * of answers given by the csv when a new question is created
	 * 
	 * @param answer
	 *            answer user gabe in textfield
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
	 * 
	 * @return true if table is not on a stage
	 */
	public boolean notActive() {
		return table.getStage() == null;
	}

	/**
	 * adds the questionUI actors to a set stage, and gives the answer TextField
	 * keyboard focus through the stage
	 * 
	 * @param stage
	 *            stage to add Actors to
	 */
	public void addToStage(Stage stage) {
		stage.addActor(questionUIBG);
		stage.addActor(table);
		stage.setKeyboardFocus(tfAnswer);
	}
}