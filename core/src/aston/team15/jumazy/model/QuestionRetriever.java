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
	private String geoLevel;
	private String mathsLevel;
	private String histoLevel;
	private ArrayList<String> questionRandomiser = new ArrayList<>();
	
	public void chosenFiles(String questionType, String level) {
		if(questionType.equals("geography") && level != null) {
			geoLevel = level;
			questionRandomiser.add("geography");
		}
		else if(questionType.equals("maths") && level != null) {
			mathsLevel = level;
			questionRandomiser.add("maths");
		}
		else if(questionType.equals("history") && level != null) {
			histoLevel = level;
			questionRandomiser.add("history");
		}
	}
	
	public String selectFile() {
		Collections.shuffle(questionRandomiser);
		String selectedType = questionRandomiser.get(0);
		String fileName = "";

		if (selectedType.equals("geography")) {
			fileName = "../assets/questions/geography" + geoLevel + ".csv";
		} else if (selectedType.equals("maths")) {
			fileName = "../assets/questions/maths" + mathsLevel + ".csv";
		}
		/* } else if(selectedType.equals("history")) {
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