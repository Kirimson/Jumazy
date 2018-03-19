package aston.team15.jumazy.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.*;

public class QuestionRetriever {
	private String[] cells = null;
	private ArrayList<String> questionRandomiser;
	private HashMap<String, String> categoryLevels;
	private ArrayList<String> lastQuestion = new ArrayList<>();
	private Scanner inputStream;
	private List<String> lines = new ArrayList<>();

	public void chosenTypes(HashMap<String, String> levels) {
		questionRandomiser = new ArrayList<String>();
		// adds to a HashMap the categories and level if they are checked
		categoryLevels = new HashMap<String, String>();
		for (String category : levels.keySet()) {
			System.out.println(category);
			switch (levels.get(category)) {
			case "Easy":
			case "Medium":
			case "Hard":
				categoryLevels.put(category, levels.get(category));
				break;
			}
		}

	}



	/**
	 * Get a question from a selected question file. Checks current question against
	 * lastQuestion so no duplicates occur
	 * 
	 * @return String array for contents of question
	 */
	public void retrieveFromFile() {
		int index = 0;
		questionRandomiser.addAll(categoryLevels.keySet());
		String line;
		
		while(index <= questionRandomiser.size() - 1) {
			
			String selectedType = questionRandomiser.get(index);
			String fileName = "questions/" + selectedType.toLowerCase() + categoryLevels.get(selectedType) + ".csv";
			
			FileHandle csv = Gdx.files.internal(fileName);
			
			inputStream = new Scanner(csv.read());
			
			
			while (inputStream.hasNext()) {
				line = inputStream.nextLine();
				lines.add(line);
			}
			index = index + 1;
			
		}
		inputStream.close();
	}
	
	public String[] questionSelector() {
		boolean questionUsedBefore;
		do {
			questionUsedBefore = false;
			Collections.shuffle(lines);
			cells = lines.get(0).split("_");
			
			for (String ques : lastQuestion) {
				
				if (cells[0].equals(ques)) {
					questionUsedBefore = true;
				}
				
			}
			
		} while (questionUsedBefore == true);

		lastQuestion.add(cells[0]);
		if(lastQuestion.size() == lines.size()) {
			lastQuestion.clear();
		}
		return cells;
	}

}