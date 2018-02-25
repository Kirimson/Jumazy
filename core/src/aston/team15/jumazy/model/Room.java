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

        //x y
        switch (direction.toString()){
            case "(1,0)":
                System.out.println("WOO2");
                blocks[roomSize-1][4] = new Path(blocks[roomSize-1][4].getCoords());
                blocks[roomSize-1][5] = new Path(blocks[roomSize-1][5].getCoords());
                break;
            case "(-1,0)":
                System.out.println("WOO2");
                blocks[0][4] = new Path(blocks[0][4].getCoords());
                blocks[0][5] = new Path(blocks[0][5].getCoords());
                break;
            case "(0,1)":
                System.out.println("WOO2");
                blocks[5][roomSize-1] = new Path(blocks[5][roomSize-1].getCoords());
                blocks[4][roomSize-1] = new Path(blocks[4][roomSize-1].getCoords());
                break;
            case "(0,-1)":
                System.out.println("WOO2");
                blocks[4][0] = new Path(blocks[4][0].getCoords());
                blocks[5][0] = new Path(blocks[5][0].getCoords());
                break;
        }

    }

    public boolean hasExit(Coordinate coord){
        for(Coordinate c : exits){
            if(c.equals(coord)) return true;
        }
        return false;
    }

    public String toString(){
        return "R";
    }
}
