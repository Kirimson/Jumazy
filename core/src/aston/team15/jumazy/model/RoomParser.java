package aston.team15.jumazy.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RoomParser{

	private int roomSize;
	private String filename;
	private BufferedReader reader;
	private ArrayList<ArrayList<Block>> allRoomLayouts;
	
	public RoomParser(int roomSize) throws FileNotFoundException {
		this.roomSize = roomSize-2;
		filename = "../core/assets/RoomLayoutsS"+(roomSize-2)+".txt";
		reader = new BufferedReader(new FileReader(filename));
		allRoomLayouts = new ArrayList<ArrayList<Block>>();
	}

	public ArrayList<ArrayList<Block>> generateRoomLayouts() throws IOException {
		
		String currentLine;
		String currentChar;
		
		while(reader.ready()) {
			currentLine = reader.readLine();
			if(!currentLine.startsWith("/")) {
				ArrayList<Block> newLayout = new ArrayList<Block>(); 
				for(int i=0;i<roomSize;i++) {
					for(int k=0;k<roomSize;k++) {
						currentChar = currentLine.substring(k, k+1);
						if(currentChar.equals("^"))
							newLayout.add(new Wall("TopEnd"));		
						else if(currentChar.equals("#"))
							newLayout.add(new Wall("BothTop"));	
						else if(currentChar.equals("O"))
							newLayout.add(new Path());	
						else if(currentChar.equals("V"))
							newLayout.add(new VictoryPath());	
						else if(currentChar.equals("T"))
							newLayout.add(new Trap());
						else
							newLayout.add(new Wall("Void"));
					}
					currentLine = reader.readLine();					
				}
				allRoomLayouts.add(newLayout);
			}
		}
		return allRoomLayouts;
	}

	public ArrayList<ArrayList<Block>> getAllRoomLayouts() {
		return allRoomLayouts;
	}
}
