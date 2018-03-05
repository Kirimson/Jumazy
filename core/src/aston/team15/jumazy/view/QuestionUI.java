package aston.team15.jumazy.view;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class QuestionUI {

	private Skin skin;
	private boolean isActive;
	private boolean correct;
	private ArrayList<Actor> questionActors;
	private Label lQuestion;
	private String[] questionAndAnswer;

	
	public QuestionUI() {
		isActive = false;
		questionActors = new ArrayList<Actor>();
		skin = new Skin(Gdx.files.internal("neonskin/neon-ui.json"));
		final TextButton btnSubmit = new TextButton("submit", skin);
		final TextField tfAnswer = new TextField("", skin);
		lQuestion = new Label("", skin);

		tfAnswer.setPosition(350, 400);
		tfAnswer.setSize(500, 100);

		btnSubmit.setPosition(350, 250);
		btnSubmit.setSize(500, 100);

		questionActors.add(btnSubmit);
		questionActors.add(tfAnswer);
		

		btnSubmit.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				correct = checkAnswer(tfAnswer.getText());
				isActive = false;
				questionActors.remove(questionActors.size()-1);
				//remove actors
				lQuestion.remove();
				tfAnswer.setText("");
				tfAnswer.remove();
				btnSubmit.remove();
				lQuestion.remove();

				File sound;
				if (correct) {
					sound = new File("../assets/snd/correct.wav");
					System.out.println("Correct");
				} else {
					sound = new File("../assets/snd/incorrect.wav");
					System.out.println("Incorrect");
				}
				playSound(sound);
				
				
			}
		});
		
	}
	
	public void displayQuestion(String[] questionAndAns) {
		questionAndAnswer = questionAndAns;
		lQuestion = new Label(questionAndAnswer[0], skin);
		lQuestion.setPosition(350, 450);
		lQuestion.setSize(500, 100);
		
		questionActors.add(lQuestion);
	}
	
	public boolean checkAnswer(String answer) {
		int i = 1;
		while (i < questionAndAnswer.length) {
			if (answer.toLowerCase().equals(questionAndAnswer[i].toLowerCase())) {
				System.out.println("correct");
				return true;
			}
			i++;
		}
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

	public ArrayList<Actor> getActors(){
		return questionActors;
	}

	public boolean isAlive() {
		return isActive;
	}

	public boolean isCorrect() {
		return correct;
	}
	
//	public void resize (int width, int height) {
//		stage.getViewport().update(width, height, true);
//	}

//	public void dispose() {
//		stage.dispose();
//	}

//	@Override
//	public void show() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void render(float delta) {
//		// TODO Auto-generated method stub
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		stage.act(Gdx.graphics.getDeltaTime());
//		stage.draw();
//	}
//
//	@Override
//	public void pause() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void resume() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void hide() {
//		// TODO Auto-generated method stub
//
//	}

}