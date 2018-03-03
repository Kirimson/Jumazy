package aston.team15.jumazy.model;

public class PlayerModel {

	public enum MoveDirection {
		RIGHT, LEFT, DOWN, UP
	}

	private Maze maze;
	private int row;
	private int col;
	private String playerSymbol;
	private String currentPositionSymbol;

	PlayerModel(int row, int col, String playerSymbol, Maze maze) {
		this.row = row;
		this.col = col;
		this.maze = maze;
		this.playerSymbol = playerSymbol;

		currentPositionSymbol = "O";

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

		if (checkValidMove(row + rowDiff, col + colDiff)) {
			maze.setCoordinateString(row, col, currentPositionSymbol);
			row += rowDiff;
			col += colDiff;
			currentPositionSymbol = maze.getCoordinateString(row, col);
			maze.setCoordinateString(row, col, playerSymbol);
			
			return true;
		} else {
			return false;
		}

	}

}
