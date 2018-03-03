package aston.team15.jumazy.model;

import java.util.Random;

public class PlayerModel {

	public enum MoveDirection {
		RIGHT, LEFT, DOWN, UP
	}

	private MazeModel maze;
	private int row;
	private int col;
	private String playerSymbol;
	private String currentPositionSymbol;
	private int movesLeft;

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

	public boolean move(MoveDirection direction) {
		int rowDiff = 0, colDiff = 0;

		switch (direction) {
		case RIGHT:
			colDiff = 1;
			break;
		case LEFT:
			colDiff = -1;
			break;
		case DOWN:
			rowDiff = -1;
			break;
		case UP:
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
			return true;
		} else {
			return false;
		}
	}

	public void rollDie() {
		movesLeft = new Random().nextInt(6) + 1;
		System.out.println("Player " + playerSymbol + " rolled a " + movesLeft);
	}

}
