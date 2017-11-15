package aston.team15.jumazy.backend;

import java.util.Random;

public class Generator {
	
	private Random rnd;

	public Generator() {
		rnd = new Random();
	}
	
	public Block[][] superNewGenMaze(int dimension){
		
		Block[][] maze = new Block[dimension][dimension];
		
		int walkerX = 0;
		int walkerY = 0;
		int walkerDirection = 0;
		
		boolean walkerWalking = true;
		
		while(walkerWalking) {
			maze[walkerX][walkerY] = Block.newFact(0, new Coordinate(walkerX, walkerY));
			
			int oldX = walkerX;
			int oldY = walkerY;
			boolean validNewCoord = false;
			int times = 0;
			
			do
			{
				oldX = walkerX;
				oldY = walkerY;
				
				walkerDirection = rnd.nextInt(4);
				switch(walkerDirection) {
				case 0: walkerX++;break; //Walker goes right
				case 1: walkerX--;break; //Walker goes left
				case 2: walkerY++;break; //Walker goes up
				case 3: walkerY--;break; //Walker goes down
				}
				
				if(walkerX >= 0 && walkerX <= dimension && walkerY >= 0 && walkerY <= dimension)
				{
					validNewCoord = true;
				}
				else
				{
					validNewCoord = false;
					walkerX = oldX;
					walkerY = oldY;
				}
				
				System.out.println("WalkerX:"+walkerX+" WalkerY:"+walkerY+" valid? "+ validNewCoord );
			}while(validNewCoord == false);
			
			if(walkerX == dimension || walkerY == dimension) {
				walkerWalking = false;
			}
			times++;
		}
	
		
		for(int column = 0; column < dimension; column++) {
			for(int row = 0; row< dimension; row++) {
				
				try {
					Block tmp = maze[column][row];
					tmp.getCoords();
				}catch (NullPointerException e){
					maze[column][row] = Block.newFact(1, new Coordinate(column, row));
				}
			}
		}
		
		return maze;
	}
	
	/**
	 * Generates a new maze. Loops through each array index, creating a new Block, by using the {@link Block#blockFactory()} method to create a new {@link Block}
	 * Creates the maze in sections of MAZE_DIMENSION/4 (if maze is 16*16 chunks are 4*4)
	 */	
	public Block[][] newGenMaze(int dimension) {
		/**
		 * MAZE STRUCTURE EXAMPLE:
		 * 
		 *  	---------
		 *     3|		|
		 *     2|		|
		 * ^   1|		|
		 * |   0|		|
		 * K	---------
		 * 	I->  0 1 2 3
		 * 
		 * I is the column, K is the row of that column. Top cell of the first column would be (I=0,K=3)
		 */
		
		Block[][] maze = new Block[dimension][dimension];
		
		int mazeOffsetI = 0;
		int mazeOffsetK = 0;
		
		int column = 0;
		int row = 0;
		int blobk = 0;
		
		int edgeOfChunk = dimension/4-1; //edge = very right and very top of each chunk
		
		while(blobk != dimension*dimension)
		{
			System.out.println("New Chunk:");
			System.out.println("I: "+column+" K: "+row);
			for(column = mazeOffsetI; column < dimension/4+mazeOffsetI; column++) {
				for(row = mazeOffsetK; row < dimension/4+mazeOffsetK; row++) {
					System.out.println("Block "+(blobk+1));

					String type = "";
					
					int rand=rnd.nextInt(8);
					int orientation=rnd.nextInt(3);
					if(rand>=0 && rand<=3)
						type = "cross";
					else if(rand==4)
						type = "straight";
					else if(rand==5)
						type = "corner";
					else
						type = "tJunction";
					
					if (column == edgeOfChunk+mazeOffsetI && row == edgeOfChunk-1+mazeOffsetK)
						maze[column][row] = Block.blockFactory("cross", new Coordinate(column, row), orientation);
					else if (column == 0+mazeOffsetI && row == edgeOfChunk-1+mazeOffsetK)
						maze[column][row] = Block.blockFactory("horizontal", new Coordinate(column, row), orientation);
					else
						maze[column][row] = Block.blockFactory(type, new Coordinate(column, row), orientation);
					
					blobk++;
				}
			}
			System.out.println("I: "+column+" K: "+row);
			 if(column != dimension) {
				mazeOffsetI += dimension/4;
			}
			else
			{
				mazeOffsetK += dimension/4;
				mazeOffsetI = 0;
			}
		}
		return maze;
	}

}
