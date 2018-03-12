package aston.team15.jumazy.view;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import aston.team15.jumazy.controller.JumazyController;

public class HeadsUpDisplay extends Table {

	private Table statsTable;
	private Table inventoryTable;

	private Label playerLabel;

	private Label playerStatsLabel;
	private Label hpLabel;
	private Label staminaLabel;
	private Label strengthLabel;
	private Label agilityLabel;
	private Label luckLabel;
	private Label intelligenceLabel;
	private Label[] statsLabels;
	private String[] labelStrings;
	
	private Label inventoryLabel;

	public HeadsUpDisplay(Skin skin, int currentPlayerIndex, int[] currentPlayerStats) {
		super(skin);
		this.setBackground("box-clear");
		this.setHeight(170);
		this.setWidth(JumazyController.WORLD_WIDTH);
		this.defaults().pad(10);

		// Create player statistics table
		statsTable = new Table();
		playerStatsLabel = new Label("Player Stats:", skin);
		playerStatsLabel.setFontScale(0.8f);

		hpLabel = new Label("HP: ", skin);
		hpLabel.setFontScale(0.8f);

		staminaLabel = new Label("Stamina: ", skin);
		staminaLabel.setFontScale(0.8f);

		strengthLabel = new Label("Strength: ", skin);
		strengthLabel.setFontScale(0.8f);

		agilityLabel = new Label("Agility: ", skin);
		agilityLabel.setFontScale(0.8f);

		luckLabel = new Label("Luck: ", skin);
		luckLabel.setFontScale(0.8f);

		intelligenceLabel = new Label("Intelligence: ", skin);
		intelligenceLabel.setFontScale(0.8f);

		statsTable.add(playerStatsLabel).left();
		statsTable.row();
		statsTable.add(hpLabel).expand().left();
		statsTable.add(staminaLabel).expand().left();
		statsTable.add(strengthLabel).expand().left();
		statsTable.row();
		statsTable.add(agilityLabel).expand().left();
		statsTable.add(luckLabel).expand().left();
		statsTable.add(intelligenceLabel).expand().left();
		
		statsLabels = new Label[] {hpLabel, staminaLabel, strengthLabel, agilityLabel, luckLabel, intelligenceLabel};

		labelStrings = new String[statsLabels.length];
				
		for (int i = 0; i < statsLabels.length; i++) {
			labelStrings[i] = "" + statsLabels[i].getText();
		}
		
		
		// Create player inventory table
		inventoryTable = new Table();
		inventoryLabel = new Label("Inventory:", skin);
		inventoryLabel.setFontScale(0.8f);
		inventoryTable.add(inventoryLabel).expandX().align(Align.left | Align.top);
		inventoryTable.row();
		inventoryTable.add(new Label("placeholder for inventory", skin)).expand();

		playerLabel = new Label("Player " + (currentPlayerIndex + 1) + "'s turn!", skin);
		playerLabel.setFontScale(0.8f);

		this.add(playerLabel).colspan(3).expandX().padBottom(0).left();
		this.row();
		this.add(statsTable).grow().padRight(0);
		this.add(inventoryTable).grow().pad(10, 5, 10, 5);
		this.add(new Label("6", skin)).width(150).padLeft(0);

//		this.debugAll();

		update(currentPlayerIndex, currentPlayerStats);
	}

	public void update(int currentPlayerIndex, int[] currentPlayerStats) {
		playerLabel.setText("Player " + (currentPlayerIndex + 1) + "'s turn!");

		for (int i = 0; i < statsLabels.length; i++) {
			statsLabels[i].setText(labelStrings[i] + currentPlayerStats[i]);
		}
	}
}
