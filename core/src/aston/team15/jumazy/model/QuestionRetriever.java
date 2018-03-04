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
	private boolean geo = true;
	private String geoLevel = "Medium";
	private boolean maths = true;
	private String mathsLevel = "Easy";

	public String selectFile() {
		Random rand = new Random();
		int n = rand.nextInt(2) + 1;
		String fileName = "";

		if (geo == true && n == 1) {
			fileName = "../core/assets/questions/geography" + geoLevel + ".csv";
		} else if (maths == true && n == 2) {
			fileName = "../core/assets/questions/maths" + mathsLevel + ".csv";
		}

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

	public boolean checkAnswer(String answer) {
		int i = 1;
		while (i < cells.length) {
			if (answer.toLowerCase().equals(cells[i].toLowerCase())) {
				System.out.println("correct");
				return true;
			}
			i++;
		}
		return false;
	}
}