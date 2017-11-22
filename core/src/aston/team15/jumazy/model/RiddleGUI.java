package aston.team15.jumazy.model;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.badlogic.gdx.Gdx;

public class RiddleGUI extends JFrame{
	private JLabel question;
	private JTextField answer;
	private JButton submit;
	private String[] cells = null;
	private boolean isActive;
	
	
	public static void main(String[] args) {
		RiddleGUI gui = new RiddleGUI();
	}

	public RiddleGUI() {
		super("Riddle");
		isActive = true;
		createGUI();
	}
	
	private void createGUI() {
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
		
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAnswer(answer.getText());
				isActive = false;
			}
		});
			
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
		
		
	}
	
	public boolean isAlive() {
		return isActive;
	}
	
	public void closeRiddle() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public String[] retrieveRiddle() {
		File file = new File("../core/assets/riddles.csv");
		
		try {
			Scanner inputStream = new Scanner(file);
			String line;
			List<String> lines = new ArrayList<>();
			while(inputStream.hasNext()) 
			{
				line = inputStream.nextLine();
				lines.add(line);
				 
			}
			
			Collections.shuffle(lines);
			
			cells = lines.get(0).split(",");
		}catch (FileNotFoundException e){
			System.out.println(e);
		}
		
		return cells;
	}
	
	public String checkAnswer(String answer) {
		if(answer.equals(cells[1])) {
			System.out.println("correct");
			return "correct";
		}
		return "incorrect";
	}
}
