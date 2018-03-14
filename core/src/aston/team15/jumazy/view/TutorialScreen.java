package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aston.team15.jumazy.controller.JumazyController;

public class TutorialScreen extends MenuScreen {
	

	
	public TutorialScreen(JumazyController theGame) {
		super(theGame);
		Skin skin = new Skin(Gdx.files.internal("jumazyskin/jumazy-skin.json"));
		
		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);
		
		Table tutorialBG = new Table();
		tutorialBG.setFillParent(true);
		tutorialBG.center();
		tutorialBG.add(new Image(game.getSprite("pause-dialog"))).height(1200).width(1400).padTop(-300);
		
		Table controlTable = new Table();
		controlTable.setFillParent(true);
		controlTable.top().padTop(50).left().padLeft(50);
		
		Image enterKey = new Image(new Texture("enter.png"));
		controlTable.add(enterKey).width(40).height(60);
		Label enterDescription = new Label("Press Enter to switch players.", skin);
		enterDescription.setFontScale(0.75f);
		controlTable.add(enterDescription).padLeft(20);
		controlTable.row();
		
		Image spaceKey = new Image(new Texture("space.png"));
		controlTable.add(spaceKey).width(50).height(20).padTop(20);
		Label spaceDescription = new Label("Press space to roll the dice.", skin);
		spaceDescription.setFontScale(0.75f);
		controlTable.add(spaceDescription).padLeft(20).padTop(20);
		controlTable.row();
		
		Image leftKey = new Image(new Texture("left.png"));
		Image upKey = new Image(new Texture("up.png"));
		Image downKey = new Image(new Texture("down.png"));
		Image rightKey = new Image(new Texture("right.png"));
		controlTable.add(leftKey).padTop(60).size(30).padLeft(-80);
		controlTable.add(downKey).padTop(60).padLeft(-300).size(30);
		controlTable.add(upKey).size(30).padLeft(-538).padTop(-5);
		controlTable.add(rightKey).size(30).padLeft(-470).padTop(60);
		Label arrowKeysDescription = new Label("Use the arrow keys to move your character.", skin);
		arrowKeysDescription.setFontScale(0.75f);
		controlTable.add(arrowKeysDescription).padTop(40).padLeft(-200);
		
		//Stats
		Table statsTable = new Table();
		statsTable.setFillParent(true);
		statsTable.bottom().padBottom(200).left().padLeft(50);
		
		//Items
		Table itemsTable = new Table();
		itemsTable.setFillParent(true);
		itemsTable.bottom().padBottom(200).left().padLeft(50);
		
		Label itemsTitle = new Label("Items", skin);
		itemsTitle.setFontScale(1.1f);
		itemsTable.add(itemsTitle);
		itemsTable.row();
		
		Image healthPotion = new Image(game.getSprite("potion-red"));
		Label healthPotionDescription = new Label("+4 Health", skin);
		healthPotionDescription.setFontScale(0.75f);
		itemsTable.add(healthPotion).size(40);
		itemsTable.add(healthPotionDescription);
		Image staminaPotion = new Image(game.getSprite("potion-blue"));
		Label staminaPotionDescription = new Label("+2 Stamina", skin);
		staminaPotionDescription.setFontScale(0.75f);
		itemsTable.add(staminaPotion).size(40).padLeft(20);
		itemsTable.add(staminaPotionDescription);
		Image luckPotion = new Image(game.getSprite("potion-green"));
		Label luckPotionDescription = new Label("+2 Luck", skin);
		luckPotionDescription.setFontScale(0.75f);
		itemsTable.add(luckPotion).size(40).padLeft(20);
		itemsTable.add(luckPotionDescription);
		itemsTable.row();
		
		Image sword = new Image(game.getSprite("sword"));
		Label swordDescription = new Label("+2 Strength", skin);
		swordDescription.setFontScale(0.75f);
		itemsTable.add(sword).size(40).padTop(10);
		itemsTable.add(swordDescription).padTop(10);
		Image bow = new Image(game.getSprite("arrow"));
		Label bowDescription = new Label("+1 strength", skin);
		bowDescription.setFontScale(0.75f);
		itemsTable.add(bow).size(40).padLeft(20).padTop(10);
		itemsTable.add(bowDescription).padTop(10);
		itemsTable.row();
		
		
		Image apple = new Image(game.getSprite("apple"));
		Label appleDescription = new Label("+2 Health", skin);
		appleDescription.setFontScale(0.75f);
		itemsTable.add(apple).size(30).padTop(10);
		itemsTable.add(appleDescription).padTop(10);
		Image grapes = new Image(game.getSprite("grapes"));
		Label grapesDescription = new Label("+2 Health", skin);
		grapesDescription.setFontScale(0.75f);
		itemsTable.add(grapes).size(30).padTop(10);
		itemsTable.add(grapesDescription).padTop(10);
		
		
		table.row();
		stage.addActor(tutorialBG);
		table.add(backButton).bottom().right().expand().pad(70);
		stage.addActor(controlTable);
		stage.addActor(itemsTable);
		stage.addActor(table);
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
