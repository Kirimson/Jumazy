package aston.team15.jumazy.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuestionRetriever {
	private String[] cells = null;
	private boolean geo;
	private String geoLevel;
	private boolean maths;
	private String mathsLevel;
	private boolean history;
	private String histoLevel;
	private int amountOfTypes;
	
	public void chosenFiles(String questionType, String level) {
		if(questionType.equals("geography") && level != null) {
			geo = true;
			geoLevel = level;
			amountOfTypes = amountOfTypes + 1;
		}
		else if(questionType.equals("maths") && level != null) {
			maths = true;
			mathsLevel = level;
			amountOfTypes = amountOfTypes + 1;
		}
		else if(questionType.equals("history") && level != null) {
			history = true;
			histoLevel = level;
			amountOfTypes = amountOfTypes + 1;
		}
	}
	
	public String selectFile() {
		Random rand = new Random();
		
		int n = rand.nextInt(amountOfTypes) + 1;
		String fileName = "";

		if (geo == true && n == 1) {
			fileName = "../assets/questions/geography" + geoLevel + ".csv";
		} else if (maths == true && n == 2) {
			fileName = "../assets/questions/maths" + mathsLevel + ".csv";
		}
		/* } else if(history == true && n == 3) {
			fileName = "../assets/questions/history" + histoLevel + ".csv";
		} */

		return fileName;
	}

	public String[] retrieveRiddle() {
		String fileName = selectFile();
		File file = new File(fileName);
		
		try {
			Scanner inputStream = new Scanner(file);
			String line;
			List<String> lines = new ArrayList<>();
			while (inputStream.hasNext()) {
				line = inputStream.nextLine();
				lines.add(line);
				
			}

			Collections.shuffle(lines);
			cells = lines.get(0).split(",");
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

		return cells;
	}

}