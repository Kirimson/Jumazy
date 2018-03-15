package aston.team15.jumazy.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import com.badlogic.gdx.Input;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.MazeModel.Weather;

public class PlayerModel {

	public enum CharacterName {
		SMOLDER_BRAVESTONE, RUBY_ROUNDHOUSE, FRANKLIN_FINBAR, SHELLY_OBERON;
	}

	private int[] startOfTurnPosition;
	private MazeModel maze;
	private int row;
	private int col;
	private String playerSymbol;
	private String currentPositionSymbol;
	private int movesLeft;
	private boolean onChest;
	private boolean canRoll = true;
	private ArrayList<Item> inventory;
	private LinkedHashMap<String, Integer> playerStats;
	private boolean onDoor;

	PlayerModel(int row, int col, String playerSymbol, MazeModel maze, CharacterName charName) {
		this.row = row;
		this.col = col;
		this.maze = maze;
		this.playerSymbol = playerSymbol;

		startOfTurnPosition = new int[2];

		inventory = new ArrayList<Item>();

		// health points:
		// combat is based on dice rolls. If the opponents roll result is larger than
		// this players roll result, subtract the difference from hp
		int hp = 10;

		// stamina:
		// added to movement dice roll result
		int stamina = 3;

		// strength:
		// added to combat dice roll result
		int strength = 2;

		// agility:
		// chance to avoid a trap
		int agility = 2;

		// luck:
		// added chance to find an item in a chest
		int luck = 2;

		// intelligence:
		// added chance a player can pick door locks
//		int intelligence = 2;

		currentPositionSymbol = "O";
		movesLeft = 0;

		switch (charName) {
		case SMOLDER_BRAVESTONE:
			strength += 2;
			break;
		case RUBY_ROUNDHOUSE:
			agility += 2;
			break;
		case FRANKLIN_FINBAR:
			luck += 2;
//			intelligence += 1;
			break;
		case SHELLY_OBERON:
//			intelligence += 2;
			stamina += 1;
			break;
		}

		playerStats = new LinkedHashMap<String, Integer>();
		playerStats.put("Max Health", hp);
		playerStats.put("Health", hp);
		playerStats.put("Stamina", stamina);
		playerStats.put("Strength", strength);
		playerStats.put("Agility", agility);
		playerStats.put("Luck", luck);

		// playerStats = new int[] { hp, stamina, strength, agility, luck, intelligence
		// };
		maze.setCoordinateString(row, col, playerSymbol);
	}

	private boolean checkValidMove(int newRow, int newCol) {
		String[] walls = new String[] { "#", "^", "W" };
		boolean valid = true;
		for (String wall : walls) {
			if (maze.getCoordinateString(newRow, newCol).equals(wall))
				valid = false;
		}
		

		//if Door
		if(maze.getCoordinateString(newRow, newCol).equals("D")){
			System.out.println("HERE");
			if(inventory.contains(Item.KEY)){
				inventory.remove(Item.KEY);
				valid = true;
			}
		}

		return valid;
	}

	public boolean move(int direction) {
		int rowDiff = 0, colDiff = 0;

		switch (direction) {
		case Input.Keys.RIGHT:
			colDiff = 1;
			break;
		case Input.Keys.LEFT:
			colDiff = -1;
			break;
		case Input.Keys.DOWN:
			rowDiff = -1;
			break;
		case Input.Keys.UP:
			rowDiff = 1;
			break;
		}

		if (checkValidMove(row + rowDiff, col + colDiff) && movesLeft > 0) {
			maze.setCoordinateString(row, col, currentPositionSymbol);
			row += rowDiff;
			col += colDiff;
			currentPositionSymbol = maze.getCoordinateString(row, col);
			maze.setCoordinateString(row, col, playerSymbol);
			movesLeft--;

			if(currentPositionSymbol.equals("D")) {
				onDoor = true;
				currentPositionSymbol = "d";
				maze.unlockDoor(row, col);
			} else onDoor = false;

			if (currentPositionSymbol.equals("C")) {
				onChest = true;
				currentPositionSymbol = "c";
			} else onChest = false;

			if (JumazyController.DEBUG_ON)
				System.out.println("Player " + playerSymbol + " just moved successfully. They have " + movesLeft
						+ " moves left.\n" + maze.toString());

			return true;
		} else {
			if (JumazyController.DEBUG_ON)
				System.out.println("Player " + playerSymbol + " tried to move, but failed. They have " + movesLeft
						+ " moves left.\n" + maze.toString());
			return false;
		}

	}

	public void obtainRandomItemFromChest() {
		Random randGen = new Random();
		Item item = Item.values()[randGen.nextInt(Item.values().length)];
		inventory.add(item);

		if (JumazyController.DEBUG_ON)
			System.out.println("Player " + playerSymbol + " just picked up a " + item.toString());

		if (item != Item.KEY) {
			int stat = playerStats.get(item.getStatEffected());
			playerStats.replace(item.getStatEffected(), stat + item.getValue());
			if (playerStats.get("Health") > playerStats.get("Max Health")) {
				playerStats.replace("Health", playerStats.get("Max Health"));
			}
		}
		
		
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public int rollDie(Weather weather) {
		canRoll = false;
		int rollResult = new Random().nextInt(6) + 1;

		startOfTurnPosition[0] = row;
		startOfTurnPosition[1] = col;

		switch (weather) {
		case SUN:
			movesLeft = rollResult;
			break;
		case RAIN:
			movesLeft = rollResult + 1;
			break;
		}
		movesLeft += playerStats.get("Stamina");
		if (JumazyController.DEBUG_ON)
			System.out.println("Player " + playerSymbol + " just rolled a " + movesLeft + ".");

		return movesLeft;
	}

	/**
	 * moves the player to the start of their turn
	 * 
	 * @return the coordinates in an integer array they moved to
	 */
	public int[] moveToStartOfTurn() {
		movesLeft = 0;
		maze.setCoordinateString(row, col, currentPositionSymbol);
		row = startOfTurnPosition[0];
		col = startOfTurnPosition[1];

		currentPositionSymbol = maze.getCoordinateString(row, col);

		maze.setCoordinateString(row, col, playerSymbol);
		return startOfTurnPosition;
	}

	public boolean isOnDoor() {
		return onDoor;
	}

	public int[] getPosition() {
		return new int[]{row, col};
	}

	public int getMovesLeft() {  
		return movesLeft;
	}

	public boolean isOnTrap() {
		return currentPositionSymbol.equals("T");
	}

	public boolean isOnChest() {
		return onChest;
	}

	public void setCanRoll(boolean canRoll) {
		this.canRoll = canRoll;
	}

	public boolean canRoll() {
		return canRoll;
	}

	public boolean isOnVictorySquare() {
		return currentPositionSymbol.equals("V");
	}

	public Integer[] getStatsArray() {
		return playerStats.values().toArray(new Integer[0]);
	}
	
	public int getLuck() {
		return playerStats.get("Luck");
	}
}
