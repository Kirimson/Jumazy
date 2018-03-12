package aston.team15.jumazy.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class QuestionRetriever {
	private String[] cells = null;
	private String geoLevel;
	private String mathsLevel;
	private String histoLevel;
	private ArrayList<String> questionRandomiser = new ArrayList<>();
	private ArrayList<String> keyHolder = new ArrayList<>();
	
	public void chosenFiles(HashMap<String, String> levels) {
		for (String key : levels.keySet()) {
		    keyHolder.add(key);
		}
		
		for(int i = 0; i < keyHolder.size()-1; i++) {
			String level = levels.get(keyHolder.get(i));
			if(keyHolder.get(i).equals("geography") && (level.equals("Easy") || level.equals("Medium") || level.equals("Hard"))) {
				geoLevel = level;
				questionRandomiser.add("geography");
			}
			else if(keyHolder.get(i).equals("maths") && (level.equals("Easy") || level.equals("Medium") || level.equals("Hard"))) {
				mathsLevel = level;
				questionRandomiser.add("maths");
			}
			else if(keyHolder.get(i).equals("history") && (level.equals("Easy") || level.equals("Medium") || level.equals("Hard"))) {
				histoLevel = level;
				questionRandomiser.add("history");
			}
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