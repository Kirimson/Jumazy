package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;

public class HeadsUpDisplay extends Table {

	private Integer[] currentPlayerStats;
	private int currentPlayerNumber;
	
	private Table statsTable;
	private Table inventoryTable;

	private Label playerLabel;

	private Label playerStatsLabel;
	private Label hpLabel;
	private Label staminaLabel;
	private Label strengthLabel;
	private Label agilityLabel;
	private Label luckLabel;
	private Label[] statsLabels;
	private Label diceLabel;
	private String[] labelStrings;

	private Label inventoryLabel;
	private String diceLabelString;
	private String playerLabelString;

	public HeadsUpDisplay(final JumazyController game, int currentPlayerNumber, Integer[] currentPlayerStats) {
		super(game.getSkin());
		this.setBackground("rpgbg");
		this.setHeight(170);
		this.setWidth(JumazyController.WORLD_WIDTH);
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = game.getSkin().getFont("game-font");
		labelStyle.fontColor = Color.WHITE;
		float fontScale = 0.4f;
		playerLabelString = "Press SPACE to roll!";

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

		statsTable.add(playerStatsLabel).left();
		statsTable.row();
		statsTable.add(hpLabel).grow().left();
		statsTable.add(staminaLabel).grow().left();
		statsTable.add(strengthLabel).grow().left();
		statsTable.row();
		statsTable.add(agilityLabel).grow().left();
		statsTable.add(luckLabel).grow().left();
		
		statsLabels = new Label[] {hpLabel, staminaLabel, strengthLabel, agilityLabel, luckLabel};

		labelStrings = new String[statsLabels.length];
				
		for (int i = 0; i < statsLabels.length; i++) {
			labelStrings[i] = "" + statsLabels[i].getText();
		}
		
		// Create player inventory table
		inventoryTable = new Table();
		inventoryLabel = new Label("Inventory:", labelStyle);
		inventoryLabel.setFontScale(fontScale);
		inventoryTable.add(inventoryLabel).expand().left().top();
		inventoryTable.row();

		playerLabel = new Label("", labelStyle);
		playerLabel.setFontScale(fontScale);
		
		TextButtonStyle pauseBtnStyle = new TextButtonStyle();
		pauseBtnStyle.font = game.getSkin().getFont("game-font");
		pauseBtnStyle.downFontColor = Color.BLACK;
		pauseBtnStyle.fontColor = Color.WHITE;
		TextButton pauseBtn = new TextButton("PAUSE", pauseBtnStyle);
		pauseBtn.getLabel().setFontScale(fontScale+0.1f);
		
		pauseBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				GameSound.playButtonSound();
				game.pause();
			}
		});
		
		diceLabel = new Label("Hit\nSpace!", labelStyle);
		diceLabel.setFontScale(fontScale-0.05f);

		this.add(playerLabel).colspan(2).expandX().left().padLeft(15).height(40).padTop(4);
		this.add(pauseBtn).padRight(2).width(150).padTop(4).fill();
		this.row();
		this.add(statsTable).width(590).growY().padLeft(15).padBottom(15).padTop(15);
		this.add(inventoryTable).grow().padBottom(15).padTop(15);
		this.add(diceLabel).center().padRight(3).padBottom(8);

		this.currentPlayerStats = currentPlayerStats;
		this.currentPlayerNumber = currentPlayerNumber;
		update(currentPlayerNumber, currentPlayerStats);
		//this.debugAll();
	}

	public void update(int newPlayerNumber, Integer[] newPlayerStats) {
		playerLabel.setText("Player " + newPlayerNumber + "'s turn! " + playerLabelString);
		diceLabel.setText(diceLabelString);

		hpLabel.setText("HP: " + newPlayerStats[1] + "/" + newPlayerStats[0]);
		if (newPlayerStats[0] != currentPlayerStats[0] || newPlayerStats[0] != currentPlayerStats[1]) {	
			if (currentPlayerNumber == newPlayerNumber)
				highlightLabel(hpLabel);
		}
			
		for (int i = 2; i < newPlayerStats.length; i++) {
			statsLabels[i-1].setText(labelStrings[i-1] + newPlayerStats[i]);
			if (newPlayerStats[i] != currentPlayerStats[i] && currentPlayerNumber == newPlayerNumber) {
				highlightLabel(statsLabels[i-1]);
			}
		}
		
		currentPlayerStats = newPlayerStats;
		currentPlayerNumber = newPlayerNumber;
	}
	
	public void highlightLabel(Label label) {
		label.setColor(Color.GREEN);
		label.addAction(Actions.sequence(Actions.color(Color.WHITE, 4f)));
	}
	
	public void updateForNewPlayer(Boolean turnPassValid) {
		if (turnPassValid) {
			setDiceLabel("Hit\nSpace!");
			playerLabelString = "Press SPACE to roll!";
		} else {
			return;
		}
	}
	
	public void setDiceLabel(String string) {
		diceLabelString = string;
	}
	
	public void setPlayerConsoleText(String string) {
		playerLabelString = string;
	}
}
