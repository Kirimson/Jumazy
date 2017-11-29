package aston.team15.jumazy.model;

import java.awt.Color;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.badlogic.gdx.Gdx;

/**
 * A simple class which creates the GUI for the user to answer the riddle and is triggered when the player steps on a trap.
 * @author Shayan
 *
 */

public class RiddleGUI extends JFrame{
	private JLabel background;
	private JTextArea question;
	private JTextField answer;
	private JButton submit;
	private String[] cells = null;
	private boolean isActive;
	
	
	public RiddleGUI() {
		super("Riddle");
		isActive = true;
		createGUI();
	}
	
	private void createGUI() {
		setContentPane(new JLabel(new ImageIcon("../core/assets/jungleGUI.jpg")));
		String[] riddleText = retrieveRiddle();
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
		question = new JTextArea(riddleText[0], 3, 16);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(question.getFont().deriveFont(16.0f));
		question.setFont(question.getFont().deriveFont(3));
		question.setEditable(false);
		question.setOpaque(false);
		question.setForeground(Color.white);
		add(question);
		
		
		answer = new JTextField(20);
		add(answer);
		
		
		submit = new JButton("Submit");
		add(submit);
		
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAnswer(answer.getText());
				isActive = false;
				dispose();
			}
		});
			
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(300, 275);
		getContentPane().setBackground(Color.green);
		setVisible(true);
		
		
	}
	
	public boolean isAlive() {
		return isActive;
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
