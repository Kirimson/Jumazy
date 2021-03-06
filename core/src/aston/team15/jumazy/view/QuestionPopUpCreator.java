package aston.team15.jumazy.view;



import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;

/**
 * Graphical class allowing player to choose what question types to have in the game
 *
 * @author Shayan
 */
public class QuestionPopUpCreator {
	
	private Skin skin;
	private Table questionTable;
	private CheckBox maths;
	private CheckBox geography;
	private CheckBox history;
	private String[] geoObjects;
	private String[] mathObjects;
	private String[] histoObjects;
	private JumazySelectBox<String> geoSB;
	private JumazySelectBox<String> mathSB;
	private JumazySelectBox<String> histoSB;
	private JumazyButton playButton;

	QuestionPopUpCreator(JumazyController game, int numOfPlayers) {
		 skin = game.getSkin();
		 
		 createCheckBoxes();
		 createSelectBoxes();
		 createTables();
		
		 maths.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					
					mathObjects[0] = "Maths Difficulty: Easy";
					mathSB.setItems(mathObjects);
					mathSB.setDisabled(false);
					mathSB.setTouchable(Touchable.enabled);

					if(!maths.isChecked()) {
						mathSB.setDisabled(true);
						mathObjects[0] = "Maths: Off";
						mathSB.setItems(mathObjects);
					}
					
					
					
				}
			});
			
			geography.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					
					geoObjects[0] = "Geography Difficulty: Easy";
					geoSB.setItems(geoObjects);
					geoSB.setDisabled(false);
					geoSB.setTouchable(Touchable.enabled);
					
					if(!geography.isChecked()) {
						geoSB.setDisabled(true);
						geoObjects[0] = "Geography: Off";
						geoSB.setItems(geoObjects);
					}
					
				}
			});
			
			history.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					
					histoObjects[0] = "Hitory Difficulty: Easy";
					histoSB.setItems(histoObjects);
					histoSB.setDisabled(false);
					histoSB.setTouchable(Touchable.enabled);

					if(!history.isChecked()) {
						histoSB.setDisabled(true);
						histoObjects[0] = "History: Off";
						histoSB.setItems(histoObjects);
					}
					
				}
			});

		playButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {

				if(!histoSB.isDisabled() || !mathSB.isDisabled() || !geoSB.isDisabled()) {
					HashMap<String, String> levels = getSelections();
					game.setQuestionType(levels);
					game.setScreen(new CharacterSelectScreen(game, numOfPlayers));
				}
			}
		});

	}
	
	
	private void createTables() {
		questionTable = new Table();
		questionTable.top().padTop(-400);
		questionTable.setFillParent(true);

		Table questionBG = new Table();
		questionBG.setFillParent(true);
		questionBG.top().padTop(0f);
		
		questionTable.add(geography).padTop(-30).left().row();
		questionTable.add(maths).left().row();
		questionTable.add(history).left().row();
		
		questionTable.add(geoSB).padTop(20).padTop(10).width(320).height(40).row();
		questionTable.add(mathSB).width(320).height(40).row();
		questionTable.add(histoSB).width(320).height(40).row();

		geoSB.setDisabled(true);
		mathSB.setDisabled(true);
		histoSB.setDisabled(true);

		playButton = new JumazyButton("Play", skin);

		questionTable.add(playButton).padTop(20);
	}
	
	private void createCheckBoxes() {
		geography = new CheckBox("Geography", skin);
	    maths = new CheckBox("Maths", skin);
	    history = new CheckBox("History", skin);
	}
	
	private void createSelectBoxes() {
	    geoObjects = new String[3];
	    geoObjects[0] = "Geography: Off";
	    geoObjects[1] = "Geography Difficulty: Medium";
	    geoObjects[2] = "GeographyDifficulty: Hard";
	    
	    mathObjects = new String[3];
	    mathObjects[0] = "Maths: Off";
	    mathObjects[1] = "Maths Difficulty: Medium";
	    mathObjects[2] = "Maths Difficulty: Hard";
	    
	    histoObjects = new String[3];
	    histoObjects[0] = "History: Off";
	    histoObjects[1] = "History Difficulty: Medium";
	    histoObjects[2] = "History Difficulty: Hard";
	   
	    geoSB = new JumazySelectBox<String>(skin);
		mathSB = new JumazySelectBox<String>(skin);
		histoSB = new JumazySelectBox<String>(skin);
		
		geoSB.setItems(geoObjects);
		mathSB.setItems(mathObjects);
		histoSB.setItems(histoObjects);
		
		mathSB.setTouchable(Touchable.disabled);
		geoSB.setTouchable(Touchable.disabled);
		histoSB.setTouchable(Touchable.disabled);
	}
	
	private HashMap<String, String> getSelections() {
		HashMap<String, String> selections = new HashMap<>();
		//gives the last word of the selected checkbox, being the difficulty
		if(!geoSB.isDisabled())
			selections.put("geography", geoSB.getSelected().substring(geoSB.getSelected().lastIndexOf(" ") + 1));

		if(!mathSB.isDisabled())
			selections.put("maths", mathSB.getSelected().substring(mathSB.getSelected().lastIndexOf(" ")+1));

		if(!histoSB.isDisabled()){
			selections.put("history", histoSB.getSelected().substring(histoSB.getSelected().lastIndexOf(" ")+1));
		}


		return selections;
	}
	
	public Table getTable() {
		return questionTable;
	}

}
