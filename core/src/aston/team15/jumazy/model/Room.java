package aston.team15.jumazy.model;

import java.util.ArrayList;

public class Room {
    private Coordinate coords;
    private Block[][] blocks;
    private int roomSize = 10;
    private ArrayList<Coordinate> exits;

    public Room(Coordinate coords){
        blocks = new Block[roomSize][roomSize];
        exits = new ArrayList<>();
        this.coords = coords;
        makeRoom();
    }

    private void makeRoom(){
        for(int i = 0; i < roomSize; i++){
			for (int k = 0; k < roomSize; k++){
				int blockX =(roomSize*coords.getX())+i;
				int blockY =(roomSize*coords.getY())+k;
				if(i == 0 || i == roomSize-1 || k == 0 || k == roomSize-1){
					blocks[i][k] = new Wall(new Coordinate(blockX,blockY), "Right");
				}
				else{
					blocks[i][k] = new Path( new Coordinate( blockX,blockY ));
				}
			}
		}
    }

    public Block getBlock(Coordinate coord) {
        return blocks[coord.getX()][coord.getY()];
    }

    public ArrayList<Coordinate> getExits() {
        return exits;
    }

    public void addExit(Coordinate direction) {
        System.out.println("Room "+coords.toString()+" now has an exit in "+direction.toString());
        exits.add(direction);
    }

    public String toString(){
        return "R";
    }
}
