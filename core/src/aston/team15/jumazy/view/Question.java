package aston.team15.jumazy.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Question extends ApplicationAdapter{

	
	private Stage stage;
	private Skin skin;
	private String[] cells = null;
	private boolean isActive;
	private boolean correct;
	
	private boolean geo = true;
	private String geoLevel = "Medium";
	private boolean maths = true;
	private String mathsLevel = "Easy";
	
	
	public void create () {
		
		isActive = true;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("../core/assets/neon/skin/neon-ui.json"));
		TextButton btnSubmit = new TextButton("submit", skin);
		TextField tfAnswer = new TextField("", skin);
		String[] riddleText = retrieveRiddle();
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
				correct = checkAnswer(tfAnswer.getText());
				isActive = false;
				
				if (correct == true) {
					System.out.println("Yeaaaaah bitchh");
				} 
				else {
					System.out.println("Nooooooo bitchh");
				}
			}
		});
		
	}


	public String selectFile() {
		Random rand = new Random();
		int  n = rand.nextInt(2) + 1;
		String fileName = "";
		
		if(geo == true && n == 1) {
			fileName = "../core/assets/questions/geography" + geoLevel + ".csv";
		}
		else if(maths == true && n == 2) {
			fileName =  "../core/assets/questions/maths" + mathsLevel + ".csv";
		}
		
		return fileName;
	}
	
	public String[] retrieveRiddle() {
		String fileName = selectFile();
		File file = new File(fileName);

		try {
			Scanner inputStream = new Scanner(file);
			String line;
			List<String> lines = new ArrayList<>();
			while (inputStream.hasNext()) {
				line = inputStream.nextLine();
				lines.add(line);

			}

			Collections.shuffle(lines);

			cells = lines.get(0).split(",");
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

		return cells;
	}

	public boolean checkAnswer(String answer) {
		int i = 1;
		while (i < cells.length) {
			if (answer.toLowerCase().equals(cells[i].toLowerCase())) {
				System.out.println("correct");
				return true;
			}
			i++;
		}
		return false;
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

	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}

}
