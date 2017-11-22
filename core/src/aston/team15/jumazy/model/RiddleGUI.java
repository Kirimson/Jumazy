package aston.team15.jumazy.model;

import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RiddleGUI extends JFrame{
	private JLabel question;
	private JTextField answer;
	private JButton submit;
	
	public static void main(String[] args) {
		RiddleGUI gui = new RiddleGUI();
	}

	public RiddleGUI() {
		super("Riddle");
		
		String[] riddleText = retrieveRiddle();
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 75));
		question = new JLabel(riddleText[0]);
		question.setFont(question.getFont().deriveFont(24.0f));
		question.setFont(question.getFont().deriveFont(3));
		add(question);
		
		answer = new JTextField(25);
		add(answer);
		
		submit = new JButton("Submit");
		add(submit);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}
	
	public String[] retrieveRiddle() {
		File file = new File("../core/assets/riddles.csv");
		String[] cells = null;
		try {
			Scanner inputStream = new Scanner(file);
			
				String line = inputStream.nextLine();
				cells = line.split(","); 
		}catch (FileNotFoundException e){
			System.out.println(e);
		}
		
		return cells;
	}
}
