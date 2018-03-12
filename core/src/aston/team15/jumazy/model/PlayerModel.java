package aston.team15.jumazy.model;

import java.util.Random;

import com.badlogic.gdx.Input;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.MazeModel.Weather;

public class PlayerModel {
	
	public enum CharacterName {
		SMOLDER_BRAVESTONE, RUBY_ROUNDHOUSE, FRANKLIN_FINBAR, SHELLY_OBERON;
	}

	public enum CalledStat {
		HP(0), STAMINA(1), STRENGTH(2), AGILITY(3), LUCK(4), INTELLIGENCE(5);

		protected final int index;

		private CalledStat(int index) {
			this.index = index;
		}
	}

	private MazeModel maze;
	private int row;
	private int col;
	private String playerSymbol;
	private String currentPositionSymbol;
	private int movesLeft;
	private int stamina, strength, hp, agility, luck, intelligence;
	private int[] playerStats;
	private boolean onTrap;
	private boolean canRoll = true;
	String[] walls = new String[] {"#","^","W","a","b","c"};
	String[] enemies = new String[] {"E","X"};

	PlayerModel(int row, int col, String playerSymbol, MazeModel maze, CharacterName charName) {
		this.row = row;
		this.col = col;
		this.maze = maze;
		this.playerSymbol = playerSymbol;

		// health points:
		// combat is based on dice rolls. If the opponents roll result is larger than
		// this players roll result, subtract the difference from hp
		hp = 10;
		
		// stamina:
		// added to movement dice roll result
		stamina = 3;
		
		// strength:
		// added to combat dice roll result
		strength = 2;
		
		// agility:
		// chance to avoid a trap
		agility = 2;
		
		// luck:
		// added chance to find an item in a chest
		luck = 2;
		
		// intelligence:
		// added chance a player can pick door locks
		intelligence = 2;

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
			intelligence += 1;
			break;
		case SHELLY_OBERON:
			intelligence += 2;
			break;
		}
		
		playerStats = new int[] { hp, stamina, strength, agility, luck, intelligence };
		maze.setCoordinateString(row, col, playerSymbol);
	}

	private boolean checkValidMove(int newRow, int newCol) {
		for(String wall : walls) {
			if(maze.getCoordinateString(newRow, newCol).equals(wall))
				return false;
			}
		if(checkForEnemy(newRow, newCol))
			return false;
		
		return true;
	}

	private boolean checkForEnemy(int newRow, int newCol) {
		for(String enemy : enemies) {
			if(maze.getCoordinateString(newRow, newCol).equals(enemy))
				return true;
			}
		return false;
	}

	public int move(int inputDirection) {
		int rowDiff = 0, colDiff = 0;

		switch (inputDirection) {
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
		
		int newRow = row + rowDiff;
		int newCol = col + colDiff;
		
		if (checkValidMove(newRow, newCol) && movesLeft > 0) {

			if (JumazyController.DEBUG_ON)
				System.out.println("Player " + playerSymbol + " just moved successfully onto a " + maze.getCoordinateString(newRow, newCol) + " block.\nThey have " + movesLeft
						+ " move" + (movesLeft == 1 ? "" : "s") + " left.\n" + maze.toString());
			
			maze.setCoordinateString(row, col, currentPositionSymbol);
			currentPositionSymbol = maze.getCoordinateString(newRow, newCol);
			movesLeft--;

			if (currentPositionSymbol.equals("T"))
				onTrap = true;
			else
				onTrap = false;
			
			maze.setCoordinateString(newRow, newCol, playerSymbol);
			row = newRow;
			col = newCol;

			return 1;
			
		} else if (checkForEnemy(newRow, newCol) && movesLeft > 0) {
			
			if (JumazyController.DEBUG_ON)
				System.out.println("Player " + playerSymbol + " just moved into a " + maze.getCoordinateString(newRow, newCol) + " enemy.\nThey have " + movesLeft
						+ " move" + (movesLeft == 1 ? "" : "s") + " left.\n" + maze.toString());
			
			maze.setCoordinateString(row, col, "O");
			
			return 2;
			
		} else {
			if (JumazyController.DEBUG_ON)
				System.out.println("Player " + playerSymbol + " tried to move onto a " + maze.getCoordinateString(newRow, newCol) + " block, but failed.\nThey have " + movesLeft
						+ " move" + (movesLeft == 1 ? "" : "s") + " left.\n" + maze.toString());
			return 0;
		}

	}

	public int rollDie(Weather weather) {
		canRoll = false;
		int rollResult = new Random().nextInt(6) + 1;

		switch (weather) {
		case SUN:
			movesLeft = rollResult;
			break;
		case RAIN:
			movesLeft = rollResult + 1;
			break;
		}
		movesLeft += stamina;
		if (JumazyController.DEBUG_ON)
			System.out.println("Player " + playerSymbol + " just rolled a " + movesLeft + ".");

		return movesLeft;
	}

	public int getMovesLeft() {  
		return movesLeft;
	}

	public boolean isOnTrap() {
		return onTrap;
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

	public int getStat(CalledStat stat) {
		return playerStats[stat.index];
	}
	
	public int[] getStatsArray() {
		return playerStats;
	}

	public void setStat(CalledStat stat, int newValue) {
		playerStats[stat.index] = playerStats[stat.index] + newValue;
	}
}
