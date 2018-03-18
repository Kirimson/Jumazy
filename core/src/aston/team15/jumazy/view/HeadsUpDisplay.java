package aston.team15.jumazy.view;

import java.util.LinkedHashMap;

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
import aston.team15.jumazy.model.Item;

public class HeadsUpDisplay extends Table {

	private LinkedHashMap<String, Integer> currentPlayerStats;
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
	private int currentPlayerNumber;

	public HeadsUpDisplay(final JumazyController game, int currentPlayerNumber,
			LinkedHashMap<String, Integer> currentPlayerStats) {
		super(game.getSkin());
		setBackground("rpgbg");
		setHeight(170);
		setWidth(JumazyController.WORLD_WIDTH);
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

		statsLabels = new Label[] { hpLabel, staminaLabel, strengthLabel, agilityLabel, luckLabel };

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
		pauseBtn.getLabel().setFontScale(fontScale + 0.1f);

		pauseBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				GameSound.playButtonSound();
				game.pause();
			}
		});

		diceLabel = new Label("Hit\nSpace!", labelStyle);
		diceLabel.setFontScale(fontScale - 0.05f);

		add(playerLabel).colspan(2).expandX().left().padLeft(15).height(40).padTop(4);
		add(pauseBtn).padRight(2).width(150).padTop(4).fill();
		row();
		add(statsTable).width(590).growY().padLeft(15).padBottom(15).padTop(15);
		add(inventoryTable).grow().padBottom(15).padTop(15);
		add(diceLabel).center().padRight(3).padBottom(8);

		this.currentPlayerStats = currentPlayerStats;
		setStatLabels(currentPlayerStats);
		update(currentPlayerNumber);
		// this.debugAll();
	}

	public void update(int newPlayerNumber) {
		playerLabel.setText("Player " + newPlayerNumber + "'s turn! " + playerLabelString);
		diceLabel.setText(diceLabelString);
	}

	public void updateItemStat(Item item) {
		highlightLabel(item);
		setStatLabels(currentPlayerStats);
	}
	
	public void updateForNewPlayer(int newPlayerNumber, LinkedHashMap<String, Integer> newPlayerStats) {
		setDiceLabel("Hit\nSpace!");
		playerLabelString = "Press SPACE to roll!";
		setStatLabels(newPlayerStats);
	}

	public void setStatLabels(LinkedHashMap<String, Integer> newPlayerStats) {
		hpLabel.setText("HP: " + newPlayerStats.get("Health") + "/" + newPlayerStats.get("Max Health"));
		staminaLabel.setText("Stamina: " + newPlayerStats.get("Stamina") + "/6");
		strengthLabel.setText("Strength: " + newPlayerStats.get("Strength") + "/6");
		luckLabel.setText("Luck: " + newPlayerStats.get("Luck") + "/6");
		agilityLabel.setText("Agility: " + newPlayerStats.get("Agility") + "/6");
		currentPlayerStats = newPlayerStats;
	}

	public void highlightLabel(Item item) {
		switch(item) {
		case RED_POTION:
		case GRAPES:
		case APPLE:
			hpLabel.setColor(Color.RED);
			hpLabel.addAction(Actions.sequence(Actions.color(Color.WHITE, 4f)));
			break;
		case BLUE_POTION:
			staminaLabel.setColor(Color.BLUE);
			staminaLabel.addAction(Actions.sequence(Actions.color(Color.WHITE, 4f)));
			break;
		case GREEN_POTION:
			luckLabel.setColor(Color.GREEN);
			luckLabel.addAction(Actions.sequence(Actions.color(Color.WHITE, 4f)));
			break;
		case PURPLE_POTION:
			agilityLabel.setColor(Color.PURPLE);
			agilityLabel.addAction(Actions.sequence(Actions.color(Color.WHITE, 4f)));
			break;
		case SWORD:
			strengthLabel.setColor(Color.YELLOW);
			strengthLabel.addAction(Actions.sequence(Actions.color(Color.WHITE, 4f)));
			break;
		case BOWANDARROW:
			strengthLabel.setColor(Color.YELLOW);
			strengthLabel.addAction(Actions.sequence(Actions.color(Color.WHITE, 4f)));
			break;
		default:
			break;
		}
	}

	public void setDiceLabel(String string) {
		diceLabelString = string;
	}

	public void setPlayerConsoleText(String string) {
		playerLabelString = string;
	}
}
