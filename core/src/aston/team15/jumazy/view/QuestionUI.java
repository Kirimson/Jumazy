package aston.team15.jumazy.view;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aston.team15.jumazy.controller.JumazyController;

public class QuestionUI {

	private Skin skin;
	private Stage stage;
	private Table table;
	private boolean isActive;
	private boolean correct;
	private ArrayList<Actor> questionActors;
	private Label lQuestion;
	private String[] questionAndAnswer;

	public QuestionUI(JumazyController game) {
		isActive = false;
		questionActors = new ArrayList<Actor>();
		skin = new Skin(Gdx.files.internal("neonskin/neon-ui.json"));
		table = new Table();
		table.setFillParent(true);
		table.center();
		
		final TextButton btnSubmit = new TextButton("submit", skin);
		final TextField tfAnswer = new TextField("", skin);
		lQuestion = new Label("", skin);
		
		lQuestion.setFontScale(1.8f);
		table.add(lQuestion);
		table.row();
		table.add(tfAnswer).width(500).height(100);;
		table.row();
		table.add(btnSubmit).width(500).height(100);;

		questionActors.add(btnSubmit);
		questionActors.add(tfAnswer);

		btnSubmit.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				
				correct = checkAnswer(tfAnswer.getText());
				isActive = false;
				
				table.remove();
				tfAnswer.setText("");

				File sound;
				if (correct) {
					sound = new File("../assets/snd/correct.wav");
				} else {
					sound = new File("../assets/snd/incorrect.wav");
				}
				playSound(sound);
				game.resume();
			}
		});

	}

	public void displayQuestion(String[] questionAndAns) {
		questionAndAnswer = questionAndAns;
		lQuestion.setText(questionAndAnswer[0]);
		questionActors.add(lQuestion);
	}

	public boolean checkAnswer(String answer) {
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

	public void playSound(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Table getTable() {
		return table;
	}

	public boolean isAlive() {
		return isActive;
	}

	public boolean isCorrect() {
		return correct;
	}


}