package aston.team15.jumazy.model;

public class Room {
    private Coordinate coords;
    private Block[][] blocks;
    private int roomSize = 10;

    public Room(Coordinate coords){
        blocks = new Block[roomSize][roomSize];
        this.coords = coords;
    }

    public void makeRoom(){
        for(int i = 0; i < roomSize; i++){
			for (int k = 0; k < roomSize; k++){
				if(i == 0 || i == roomSize-1 || k == 0 || k == roomSize-1){
					blocks[i][k] = new Wall(new Coordinate(i,k), "Right");
				}
				else{
					blocks[i][k] = new Path(new Coordinate(i, (coords.getY()+1) * k));
				}
			}
		}
    }
}
