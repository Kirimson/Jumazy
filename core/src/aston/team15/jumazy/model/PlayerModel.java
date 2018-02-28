package aston.team15.jumazy.model;

public class PlayerModel {

	private int row;
	private int col;
	private String symbol;
	private Maze maze;

	public PlayerModel(int row, int col, String symbol, Maze maze) {
		this.row = row;
		this.col = col;
		this.maze = maze;
		this.symbol = symbol;

		maze.setCoordinateString(col, row, symbol);
	}

	public boolean checkValidMove(int newRow, int newCol) {
		if (!maze.getCoordinateString(newRow, newCol).equals("*"))
			return true;
		else
			return false;
	}

	public void move(MoveDirections direction) {
		int rowDiff = 0, colDiff = 0;

		switch (direction) {
		case RIGHT:
			rowDiff = 1;
			break;
		case LEFT:
			rowDiff = -1;
			break;
		case DOWN:
			colDiff = -1;
			break;
		case UP:
			colDiff = 1;
			break;
		}

		if (checkValidMove(row + rowDiff, col + colDiff)) {
			maze.setCoordinateString(col, row, "O");
			row += rowDiff;
			col += colDiff;
			
			maze.setCoordinateString(row, col, symbol);
		}
	}

}
