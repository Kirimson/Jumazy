package aston.team15.jumazy.view;



import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class QuestionPopUpCreator {
	
	private Skin skin;
	private Table questionTable;
	private Table questionBG;
	private CheckBox maths;
	private CheckBox geography;
	private CheckBox history;
	private String[] geoObjects;
	private String[] mathObjects;
	private String[] histoObjects;
	private SelectBox<String> geoSB;
	private SelectBox<String> mathSB;
	private SelectBox<String> histoSB;
	private HashMap<String, String> selections;
	
	public QuestionPopUpCreator() {
		 skin = new Skin(Gdx.files.internal("jumazyskin/jumazy-skin.json"));
		
		 createCheckBoxes();
		 createSelectBoxes();
		 createTables();
		
		 maths.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					
					mathSB.setTouchable(Touchable.enabled);
					mathObjects[0] = new String("Maths Difficulty"); 
					mathSB.setItems(mathObjects);
					
					if(!maths.isChecked()) {
						mathSB.setTouchable(Touchable.disabled);
						mathObjects[0] = new String("Tick Maths for levels"); 
						mathSB.setItems(mathObjects);
					}
					
				}
			});
			
			geography.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					
					geoSB.setTouchable(Touchable.enabled);
					geoObjects[0] = new String("Geography Difficulty"); 
					geoSB.setItems(geoObjects);
					
					if(!geography.isChecked()) {
						geoSB.setTouchable(Touchable.disabled);
						geoObjects[0] = new String("Tick Geography for levels"); 
						geoSB.setItems(geoObjects);
					}
					
				}
			});
			
			history.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					
					histoSB.setTouchable(Touchable.enabled);
					histoObjects[0] = new String("Hitory Difficulty"); 
					histoSB.setItems(histoObjects);
					
					if(!history.isChecked()) {
						histoSB.setTouchable(Touchable.disabled);
						histoObjects[0] = new String("Tick History for levels"); 
						histoSB.setItems(histoObjects);
					}
					
				}
			});

	}
	
	
	public void createTables() {
		questionTable = new Table();
		questionTable.center();
		questionTable.setFillParent(true);
		
		questionBG = new Table();
		questionBG.setFillParent(true);
		questionBG.top().padTop(0f);
		
		questionTable.add(geography).padTop(-20).left().row();;
		questionTable.add(maths).left().row();;
		questionTable.add(history).left().row();
		
		questionTable.add(geoSB).padTop(10).width(320).height(40).row();
		questionTable.add(mathSB).width(320).height(40).row();
		questionTable.add(histoSB).width(320).height(40).row();
	}
	
	public void createCheckBoxes() {
		geography = new CheckBox("Geography", skin);
	    maths = new CheckBox("Maths", skin);
	    history = new CheckBox("History", skin);
	}
	
	public void createSelectBoxes() {
	    geoObjects = new String[4]; 
	    geoObjects[0] = new String("Tick Geography for levels"); 
	    geoObjects[1] = new String("Easy"); 
	    geoObjects[2] = new String("Medium"); 
	    geoObjects[3] = new String("Hard"); 
	    
	    mathObjects = new String[4]; 
	    mathObjects[0] = new String("Tick Maths for levels"); 
	    mathObjects[1] = new String("Easy"); 
	    mathObjects[2] = new String("Medium"); 
	    mathObjects[3] = new String("Hard"); 
	    
	    histoObjects = new String[4]; 
	    histoObjects[0] = new String("Tick History for levels"); 
	    histoObjects[1] = new String("Easy"); 
	    histoObjects[2] = new String("Medium"); 
	    histoObjects[3] = new String("Hard"); 
	   
	    geoSB = new SelectBox<String>(skin);
		mathSB = new SelectBox<String>(skin);
		histoSB = new SelectBox<String>(skin);
		
		geoSB.setItems(geoObjects);
		mathSB.setItems(mathObjects);
		histoSB.setItems(histoObjects);
		
		mathSB.setTouchable(Touchable.disabled);
		geoSB.setTouchable(Touchable.disabled);
		histoSB.setTouchable(Touchable.disabled);
	}
	
	public void populateSelections() {
		selections = new HashMap<>();
		selections.put("geography", geoSB.getSelected());
		selections.put("maths", mathSB.getSelected());
		selections.put("history", histoSB.getSelected());
	}
	
	public HashMap<String, String> getSelections(){
		return selections;
	}
	
	public Table getTable() {
		return questionTable;
	}

}
