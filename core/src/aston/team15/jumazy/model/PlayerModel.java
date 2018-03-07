package aston.team15.jumazy.model;

import java.util.Random;

import com.badlogic.gdx.Input;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.model.MazeModel.Weather;

public class PlayerModel {

	private MazeModel maze;
	private int row;
	private int col;
	private String playerSymbol;
	private String currentPositionSymbol;
	private int movesLeft;
	private int stamina;
	private int strength;
	private int HP;
	private int agility;
	private int luck;
	private int intelligence;
	private int[] playerstats;
	private boolean onTrap;
	private boolean canRoll = true;

	public enum CalledStat {
		HP(0), stamina(1), strength(2), agility(3), luck(4), intelligence(5);

		protected final int index;

		private CalledStat(int index) {
			this.index = index;
		}
	}

	PlayerModel(int row, int col, String playerSymbol, MazeModel maze, String charname) {
		this.row = row;
		this.col = col;
		this.maze = maze;
		this.playerSymbol = playerSymbol;

		HP = 10;
		stamina = 3;
		strength = 2;
		agility = 2;
		luck = 2;
		intelligence = 2;

		currentPositionSymbol = "O";
		movesLeft = 0;

		switch (charname) {
		case "Dr Smolder Bravestone":
			strength = strength + 2;
			break;
		case "Ruby roundhouse":
			agility = agility + 2;
			break;
		case "franklin finbar":
			luck = luck + 2;
			intelligence = intelligence + 1;
			break;
		case "professor shelly oberon":
			intelligence = intelligence + 2;
			break;
		}

		int[] playerstats = { HP, stamina, strength, agility, luck, intelligence };
		maze.setCoordinateString(row, col, playerSymbol);
	}

	private boolean checkValidMove(int newRow, int newCol) {
		return !maze.getCoordinateString(newRow, newCol).equals("*");
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

			if (currentPositionSymbol.equals("T"))
				onTrap = true;
			else
				onTrap = false;

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
		movesLeft = movesLeft + stamina;
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
		return playerstats[stat.index];
	}

	public void setStat(CalledStat stat, int modifier) {
		playerstats[stat.index] = playerstats[stat.index] + modifier;
	}
}
