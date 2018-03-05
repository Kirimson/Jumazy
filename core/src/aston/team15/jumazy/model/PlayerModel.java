package aston.team15.jumazy.model;

import com.badlogic.gdx.Input;

import aston.team15.jumazy.model.MazeModel.Weather;

import java.util.Random;

public class PlayerModel {

	private MazeModel maze;
	private int row;
	private int col;
	private String playerSymbol;
	private String currentPositionSymbol;
	private int movesLeft;
	private boolean canRoll = true;

	PlayerModel(int row, int col, String playerSymbol, MazeModel maze) {
		this.row = row;
		this.col = col;
		this.maze = maze;
		this.playerSymbol = playerSymbol;

		currentPositionSymbol = "O";
		movesLeft = 0;

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

			if (maze.getDebugOn())
				System.out.println("Player " + playerSymbol + " just moved successfully. They have " + movesLeft
						+ " moves left.\n" + maze.toString());

			return true;
		} else {
			if (maze.getDebugOn())
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

		if (maze.getDebugOn())
			System.out.println("Player " + playerSymbol + " just rolled a " + movesLeft + ".");

		return movesLeft;
	}

	public int getMovesLeft() {
		return movesLeft;
	}

	public void setCanRoll(boolean canRoll) {
		this.canRoll = canRoll;
	}

	public boolean canRoll() {
		return canRoll;
	}
}
