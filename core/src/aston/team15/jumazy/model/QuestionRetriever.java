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
	private ArrayList<String> questionRandomiser = new ArrayList<String>();
	private HashMap<String, String> categoryLevels;

	public void chosenTypes(HashMap<String, String> levels) {

		//adds to a HashMap the categories and level if they are checked
		categoryLevels = new HashMap<String, String>();
		for(String category : levels.keySet()) {
			switch (levels.get(category)) {
				case "Easy":
				case "Medium":
				case "Hard":
					categoryLevels.put(category, levels.get(category));break;
			}
		}

	}

	/**
	 * Generates a file path for a new question based on selected categories and their difficulty
	 * @return path to a riddle file
	 */
	public String selectFile() {
		//creates an ArrayList out of the HashMap's keys to be shuffled
		questionRandomiser.addAll(categoryLevels.keySet());
		Collections.shuffle(questionRandomiser);

		String selectedType = questionRandomiser.get(0);

		//return the URI of the file. has no checks if the file inst there, as they should soon be put in
		return "../assets/questions/"+selectedType.toLowerCase()+categoryLevels.get(selectedType)+".csv";
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
			e.printStackTrace();
		}

		return cells;
	}

}