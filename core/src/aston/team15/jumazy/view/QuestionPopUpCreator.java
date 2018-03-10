package aston.team15.jumazy.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class QuestionPopUpCreator {
	Skin skin;
	Table questionTable;
	Table questionBG;
	CheckBox maths;
	CheckBox geography;
	CheckBox history;
	String[] geoObjects;
	String[] mathObjects;
	String[] histoObjects;
	TextButton btnPlay;
	SelectBox<String> geoSB;
	SelectBox<String> mathSB;
	SelectBox<String> histoSB;
	String[] selections;
	public QuestionPopUpCreator() {
		skin = new Skin(Gdx.files.internal("jumazyskin/jumazy-skin.json"));
		questionTable = new Table();
		questionBG = new Table();
		questionBG.setFillParent(true);
		questionBG.top().padTop(0f);
		questionTable.center();
		questionTable.setFillParent(true);
		
	    geography = new CheckBox("Geography", skin);
	    maths = new CheckBox("Maths", skin);
	    history = new CheckBox("History", skin);
	    
	    geoObjects = new String[4]; 
	    geoObjects[0] = new String("Please Select a Geography level"); 
	    geoObjects[1] = new String("Easy"); 
	    geoObjects[2] = new String("Medium"); 
	    geoObjects[3] = new String("Hard"); 
	    
	    mathObjects = new String[4]; 
	    mathObjects[0] = new String("Please Select a Maths level"); 
	    mathObjects[1] = new String("Easy"); 
	    mathObjects[2] = new String("Medium"); 
	    mathObjects[3] = new String("Hard"); 
	    
	    histoObjects = new String[4]; 
	    histoObjects[0] = new String("Please Select a History level"); 
	    histoObjects[1] = new String("Easy"); 
	    histoObjects[2] = new String("Medium"); 
	    histoObjects[3] = new String("Hard"); 
	   
	    //////
		geoSB = new SelectBox<String>(skin);
		mathSB = new SelectBox<String>(skin);
		histoSB = new SelectBox<String>(skin);
		geoSB.setItems(geoObjects);
		mathSB.setItems(mathObjects);
		histoSB.setItems(histoObjects);
		
		
		questionTable.add(geography).left().row();;
		questionTable.add(maths).left().row();;
		questionTable.add(history).left().row();
		
		questionTable.add(geoSB).width(320).height(50).row();
		questionTable.add(mathSB).width(320).height(50).row();
		questionTable.add(histoSB).width(320).height(50).row();
		
	}
	
	public void populateSelections() {
		selections = new String[3];
		if(geography.isChecked()) {
			selections[0] = geoSB.getSelected();
		}
		
		if(maths.isChecked()) {
			selections[1] = mathSB.getSelected();
		}
		
		if(history.isChecked()) {
			selections[2] = histoSB.getSelected();
		}
	}
	
	public String[] getSelections(){
		return selections;
	}
	
	public Table getTable() {
		return questionTable;
	}

}
