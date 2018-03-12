package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

	public HeadsUpDisplay(final JumazyController game, int currentPlayerIndex, int[] currentPlayerStats) {
		super(game.getSkin());
		this.setBackground("rpgbg");
		this.setHeight(170);
		this.setWidth(JumazyController.WORLD_WIDTH);
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = game.getSkin().getFont("game-font");
		labelStyle.fontColor = Color.WHITE;
		float fontScale = 0.4f;

		// Create player statistics table
		statsTable = new Table();
		hpLabel = new Label("HP: ", labelStyle);
		hpLabel.setFontScale(fontScale);

		staminaLabel = new Label("Stamina: ", labelStyle);
		staminaLabel.setFontScale(fontScale);

		strengthLabel = new Label("Strength: ", labelStyle);
		strengthLabel.setFontScale(fontScale);

		agilityLabel = new Label("Agility: ", labelStyle);
		agilityLabel.setFontScale(fontScale);

		luckLabel = new Label("Luck: ", labelStyle);
		luckLabel.setFontScale(fontScale);

		intelligenceLabel = new Label("Intelligence: ", labelStyle);
		intelligenceLabel.setFontScale(fontScale);

		statsTable.add(playerStatsLabel).left();
		statsTable.row();
		statsTable.add(hpLabel).grow().left();
		statsTable.add(staminaLabel).grow().left();
		statsTable.add(strengthLabel).grow().left();
		statsTable.row();
		statsTable.add(agilityLabel).grow().left();
		statsTable.add(luckLabel).grow().left();
		statsTable.add(intelligenceLabel).grow().left();
		
		statsLabels = new Label[] {hpLabel, staminaLabel, strengthLabel, agilityLabel, luckLabel, intelligenceLabel};

		labelStrings = new String[statsLabels.length];
				
		for (int i = 0; i < statsLabels.length; i++) {
			labelStrings[i] = "" + statsLabels[i].getText();
		}
		
		// Create player inventory table
		inventoryTable = new Table();
		inventoryLabel = new Label("Inventory:", labelStyle);
		inventoryLabel.setFontScale(fontScale);
		inventoryTable.add(inventoryLabel).expandX().align(Align.left | Align.top);
		inventoryTable.row();

		playerLabel = new Label("Player " + (currentPlayerIndex + 1) + "'s turn!", labelStyle);
		playerLabel.setFontScale(fontScale);
		
		TextButtonStyle pauseBtnStyle = new TextButtonStyle();
		pauseBtnStyle.font = game.getSkin().getFont("game-font");
		pauseBtnStyle.downFontColor = Color.BLACK;
		pauseBtnStyle.fontColor = Color.WHITE;
		TextButton pauseBtn = new TextButton("PAUSE", pauseBtnStyle);
		pauseBtn.getLabel().setFontScale(fontScale+0.1f);
		
		pauseBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.pause();
				System.out.println("hi");
			}
		});
			

		this.add(playerLabel).colspan(2).expandX().left().padTop(15).padLeft(15).padBottom(0);
		this.add(pauseBtn).expandY().fill().padRight(2).padTop(10);
		this.row();
		this.add(statsTable).width(590).growY().padLeft(15).padBottom(15).padTop(15);
		this.add(inventoryTable).grow().padBottom(15).padTop(15);
		this.add(new Label("6", labelStyle)).width(150).height(105).growY().padBottom(15).padRight(2);

		update(currentPlayerIndex, currentPlayerStats);
	}

	public void update(int currentPlayerIndex, int[] currentPlayerStats) {
		playerLabel.setText("Player " + (currentPlayerIndex + 1) + "'s turn!");

		for (int i = 0; i < statsLabels.length; i++) {
			statsLabels[i].setText(labelStrings[i] + currentPlayerStats[i]);
		}
	}
}
