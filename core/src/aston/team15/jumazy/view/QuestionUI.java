package aston.team15.jumazy.view;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import aston.team15.jumazy.model.QuestionRetriever;

public class QuestionUI implements Screen{

	private Stage stage;
	private Skin skin;
	private boolean isActive;
	private boolean correct;
	private QuestionRetriever question = new QuestionRetriever();
	
	
	public void create () {
		isActive = true;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("../core/assets/neon/skin/neon-ui.json"));
		TextButton btnSubmit = new TextButton("submit", skin);
		TextField tfAnswer = new TextField("", skin);
		String[] riddleText = question.retrieveRiddle();
		Label lQuestion = new Label(riddleText[0], skin);
		
		lQuestion.setPosition(350, 450);
		lQuestion.setSize(500, 100);
		stage.addActor(lQuestion);
		tfAnswer.setPosition(350, 400);
		tfAnswer.setSize(500, 100);
		stage.addActor(tfAnswer);
		btnSubmit.setPosition(350, 250);
		btnSubmit.setSize(500, 100);
		stage.addActor(btnSubmit);
		Gdx.input.setInputProcessor(stage);
		
		btnSubmit.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				correct = question.checkAnswer(tfAnswer.getText());
				isActive = false;
				File sound;
				if (correct == true) {
					
					sound = new File("../core/assets/correct.wav");
				} else {
					
					sound = new File("../core/assets/incorrect.wav");
				}
				playSound(sound);
			}
		});
		
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


	public boolean isAlive() {
		return isActive;
	}

	public boolean isCorrect() {
		return correct;
	}
	
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void dispose() {
		stage.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}