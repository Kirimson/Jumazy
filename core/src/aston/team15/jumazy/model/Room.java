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

    public void addExit(Coordinate direction, int type) {
        exits.add(direction);

        int offset = (type > 4 ? 2 : -2);
        switch (direction.toString()){
            case "(1,0)":
                blocks[roomSize-1][4 + offset] = new Path(blocks[roomSize-1][4 + offset].getCoords());
                blocks[roomSize-1][5 + offset] = new Path(blocks[roomSize-1][5 + offset].getCoords());
                if(type > 7){
                    blocks[roomSize-1][4 - offset] = new Path(blocks[roomSize-1][4 - offset].getCoords());
                    blocks[roomSize-1][5 - offset] = new Path(blocks[roomSize-1][5 - offset].getCoords());
                }
                break;
            case "(-1,0)":
                blocks[0][4 + offset] = new Path(blocks[0][4 + offset].getCoords());
                blocks[0][5 + offset] = new Path(blocks[0][5 + offset].getCoords());
                if(type > 7){
                    blocks[0][4 - offset] = new Path(blocks[0][4 - offset].getCoords());
                    blocks[0][5 - offset] = new Path(blocks[0][5 - offset].getCoords());
                }
                break;
            case "(0,1)":
                blocks[5 + offset][roomSize-1] = new Path(blocks[5 + offset][roomSize-1].getCoords());
                blocks[4 + offset][roomSize-1] = new Path(blocks[4 + offset][roomSize-1].getCoords());
                if(type > 7){
                    blocks[5 - offset][roomSize-1] = new Path(blocks[5 - offset][roomSize-1].getCoords());
                    blocks[4 - offset][roomSize-1] = new Path(blocks[4 - offset][roomSize-1].getCoords());
                }
                break;
            case "(0,-1)":
                blocks[4 + offset][0] = new Path(blocks[4 + offset][0].getCoords());
                blocks[5 + offset][0] = new Path(blocks[5 + offset][0].getCoords());
                if(type > 7){
                    blocks[5 - offset][0] = new Path(blocks[5 - offset][0].getCoords());
                    blocks[4 - offset][0] = new Path(blocks[4 - offset][0].getCoords());
                }
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
