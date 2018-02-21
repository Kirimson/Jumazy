package aston.team15.jumazy.model;

public class Room {
    private Coordinate coords;
    private Block[][] blocks;
    private int roomSize = 10;

    public Room(Coordinate coords){
        blocks = new Block[roomSize][roomSize];
        this.coords = coords;
        makeRoom();
    }

    private void makeRoom(){
        for(int i = 0; i < roomSize; i++){
			for (int k = 0; k < roomSize; k++){
				int blockX =(roomSize*coords.getX())+i;
				int blockY =(roomSize*coords.getY())+i;
				if(i == 0 || i == roomSize-1 || k == 0 || k == roomSize-1){
					blocks[i][k] = new Wall(new Coordinate(blockX,blockY), "Right");
				}
				else{
					blocks[i][k] = new Path( new Coordinate( blockX,blockY ));
				}
//                System.out.println(coords.toString());
			}
		}
    }

    public Block getBlock(Coordinate coord) {
        return blocks[coord.getX()-1][coord.getY()-1];
    }

//    public String toString(){
//        return "";
//    }
}
