package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aston.team15.jumazy.controller.JumazyController;

public class TutorialScreen extends MenuScreen {
	
	private Skin skin;
	private Table controlTable;
	private Table itemsTable;
	private Table gameplayTable;
	private Table statsTable;
	private Label description;
	
	public TutorialScreen(JumazyController theGame) {
		super(theGame, theGame.getTexturePackName().equals("oldenglish") ? "backgrounds/TutorialBackBW.png" :
				"backgrounds/TutorialBack.png");
		
		skin = game.getSkin();

		controlsSection();
		itemsSection();
		gameplaySection();
		statsSection();
		
		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);
		table.add(backButton).bottom().right().expand().pad(70);
		stage.addActor(table);
	}
	
	private void controlsSection() {
		
		controlTable = new Table();
		controlTable.setFillParent(true);
		controlTable.top().padTop(50).left().padLeft(100);
		
		Image enterKey = new Image(game.getSprite("enter"));
		controlTable.add(enterKey).width(40).height(60);
		description = new Label("Press enter to switch players.", skin);
		description.setFontScale(0.75f);
		controlTable.add(description).padLeft(40);
		controlTable.row();
		
		Image spaceKey = new Image(game.getSprite("space"));
		controlTable.add(spaceKey).width(90).height(20).padTop(20);
		description = new Label("Press space to roll the dice.", skin);
		description.setFontScale(0.75f);
		controlTable.add(description).padLeft(30).padTop(20);
		controlTable.row();
		
		Image leftKey = new Image(game.getSprite("left"));
		Image upKey = new Image(game.getSprite("up"));
		Image downKey = new Image(game.getSprite("down"));
		Image rightKey = new Image(game.getSprite("right"));
		controlTable.add(leftKey).padTop(60).size(30).padLeft(-60);
		controlTable.add(downKey).padTop(60).padLeft(-343).size(30);
		controlTable.add(upKey).size(30).padLeft(-600).padTop(-5);
		controlTable.add(rightKey).size(30).padLeft(-535).padTop(60);
		description = new Label("Use the arrow keys to move your character.", skin);
		description.setFontScale(0.75f);
		controlTable.add(description).padTop(40).padLeft(-215);
		controlTable.row();
		
		Image escapeKey = new Image(game.getSprite("Esc"));
		controlTable.add(escapeKey).width(40).height(30).padTop(20);
		description = new Label("Press escape to pause.", skin);
		description.setFontScale(0.75f);
		controlTable.add(description).padTop(20);
		
		stage.addActor(controlTable);
		description.getStyle().fontColor = Color.WHITE;
	}
	
	public void itemsSection() {
		
		itemsTable = new Table();
		itemsTable.setFillParent(true);
		itemsTable.right().padRight(120).top().padTop(50);
		
		description = new Label("Items", skin);
		description.setFontScale(1.1f);
		itemsTable.add(description).padLeft(20);
		itemsTable.row();
		
		Image healthPotion = new Image(game.getSprite("potion-red"));
		description = new Label("+4 Health", skin);
		description.setFontScale(0.75f);
		itemsTable.add(healthPotion).size(40);
		itemsTable.add(description);
		Image staminaPotion = new Image(game.getSprite("potion-blue"));
		description = new Label("+2 Stamina", skin);
		description.setFontScale(0.75f);
		itemsTable.add(staminaPotion).size(40).padLeft(20);
		itemsTable.add(description);
		Image luckPotion = new Image(game.getSprite("potion-green"));
		description = new Label("+2 Luck", skin);
		description.setFontScale(0.75f);
		itemsTable.add(luckPotion).size(40).padLeft(20);
		itemsTable.add(description);
		itemsTable.row();
		
		Image sword = new Image(game.getSprite("sword"));
		description = new Label("+2 Strength", skin);
		description.setFontScale(0.75f);
		itemsTable.add(sword).size(40).padTop(10);
		itemsTable.add(description).padTop(10).padLeft(10);
		Image bow = new Image(game.getSprite("arrow"));
		description = new Label("+1 strength", skin);
		description.setFontScale(0.75f);
		itemsTable.add(bow).size(40).padLeft(20).padTop(10);
		itemsTable.add(description).padTop(10);
		itemsTable.row();
		
		
		Image apple = new Image(game.getSprite("apple"));
		description = new Label("+2 Health", skin);
		description.setFontScale(0.75f);
		itemsTable.add(apple).size(30).padTop(10).padLeft(-10);
		itemsTable.add(description).padTop(10);
		Image grapes = new Image(game.getSprite("grapes"));
		description = new Label("+2 Health", skin);
		description.setFontScale(0.75f);
		itemsTable.add(grapes).size(30).padTop(10);
		itemsTable.add(description).padTop(10);
		itemsTable.row();
		
		Image key = new Image(game.getSprite("key"));
		description = new Label("Unlocks doors", skin);
		description.setFontScale(0.75f);
		itemsTable.add(key).size(30).padTop(10);
		itemsTable.add(description).padTop(10).padLeft(30);
		
		stage.addActor(itemsTable);
	}
	
	public void statsSection() {
		statsTable = new Table();
		statsTable.setFillParent(true);
		statsTable.right().padRight(40).bottom().padBottom(130);
		
		description = new Label("Stats", skin);
		description.setFontScale(1.1f);
		statsTable.add(description).padLeft(-525);
		statsTable.row();
		
		description = new Label("HP: This is your health and if it hits 0 you will be spawned back where you started."
				+ "\nSTAMINA: The amount of stamina you have is added onto your dice roll."
				+ "\nLUCK: This effects your chances of recieving an item from a chest."
				+ "\nSTRENGTH: This increases your chances of winning fights against monsters."
				+ "\nAGILITY: This increases your chance of skipping a trap"
				+ "\nINTELLIGENCE: This increases your chances of opening doors without keys by lock picking.", skin);
		description.setFontScale(0.65f);
		statsTable.add(description);
		statsTable.row();
		stage.addActor(statsTable);
	}
	
	public void gameplaySection() {
		gameplayTable = new Table();
		gameplayTable.setFillParent(true);
		gameplayTable.left().padLeft(0).bottom().padBottom(50);
		
		description = new Label("Gameplay", skin);
		description.setFontScale(1.1f);
		gameplayTable.add(description).padLeft(50);
		gameplayTable.row();
		
		Image chest = new Image(new Texture("chest-gold.png"));
		gameplayTable.add(chest).size(50).padTop(10);
		description = new Label("You can recieve special items to help you towards vitory", skin);
		description.setFontScale(0.65f);
		gameplayTable.add(description).padTop(10).padLeft(-200);
		gameplayTable.row();
		
		Image trap = new Image(game.getSprite("floor-trap-spikes"));
		gameplayTable.add(trap).size(30).padTop(10);
		description = new Label("These are traps! if stepped on a question will have to be answered."
				+ "\nGet it correct, you may pass. Get it wrong and you will be punished.", skin);
		description.setFontScale(0.65f);
		gameplayTable.add(description).padTop(10).padLeft(-120);
		gameplayTable.row();
		
		Image monster = new Image(game.getSprite("skeleton"));
		gameplayTable.add(monster).size(30).padTop(10);
		description = new Label("You can recieve more strength by fighting monsters. You and the monster both"
				+ "\nroll a dice and if you roll 6 and the monster rolls 2, the monster will lose 4 hp."
				+ "\nThe amount of strength you have is added on to what you roll.", skin);
		description.setFontScale(0.65f);
		gameplayTable.add(description).padTop(10).padLeft(-50);
		gameplayTable.row();
		
		Image victory = new Image(game.getSprite("victory-statue"));
		gameplayTable.add(victory).size(30).padTop(10);
		description = new Label("To win the game you must get to this victory treasure", skin);
		description.setFontScale(0.65f);
		gameplayTable.add(description).padTop(10).padLeft(-200);
		
		stage.addActor(gameplayTable);
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
