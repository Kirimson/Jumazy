package aston.team15.jumazy.model;

public class PlayerModel {

	public enum MoveDirection {
		RIGHT, LEFT, DOWN, UP
	}
	
	private int row;
	private int col;
	private String playerSymbol;
	private String currentSymbol;
	private Maze maze;

	PlayerModel(int row, int col, String symbol, Maze maze) {
		this.row = row;
		this.col = col;
		this.maze = maze;
		playerSymbol = symbol;
		currentSymbol = maze.getCoordinateString(row, col);

		maze.setCoordinateString(row, col, symbol);
	}

	private boolean checkValidMove(int newRow, int newCol) {
		return !maze.getCoordinateString(newRow, newCol).equals("*");
	}

	public void move(MoveDirection direction) {
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
			maze.setCoordinateString(row, col, currentSymbol);
			row += rowDiff;
			col += colDiff;
			maze.setCoordinateString(row, col, playerSymbol);
		}
	}

}
