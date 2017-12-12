package aston.team15.jumazy.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A simple class which creates the GUI for the user to answer the riddle and is
 * triggered when the player steps on a trap.
 * 
 * @author Shayan
 *
 */

public class RiddleGUI {
	private JFrame questionFrame;
	private JFrame responseFrame;
	private JTextArea question;
	private JLabel responseLabel;
	private JTextField answer;
	private JButton submit;
	private JButton continueGame;
	private String[] cells = null;
	private boolean isActive;
	private boolean correct;

	public static void main(String[] args) {
		RiddleGUI r = new RiddleGUI();
	}

	public RiddleGUI() {
		isActive = true;
		createGUI();
	}

	private void createGUI() {
		questionFrame = new JFrame("Question");
		centreWindow(questionFrame);
		responseFrame = new JFrame("Response");
		centreWindow(responseFrame);
		continueGame = new JButton("Continue");
		questionFrame.setContentPane(new JLabel(new ImageIcon("../core/assets/jungleGUI.jpg")));
		responseFrame.setContentPane(new JLabel(new ImageIcon("../core/assets/jungleGUI.jpg")));
		String[] riddleText = retrieveRiddle();
		questionFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
		question = new JTextArea(riddleText[0], 2, 16);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(question.getFont().deriveFont(16.0f));
		question.setFont(question.getFont().deriveFont(3));
		question.setEditable(false);
		question.setOpaque(false);
		question.setForeground(Color.white);
		questionFrame.add(question);

		answer = new JTextField(20);
		questionFrame.add(answer);

		submit = new JButton("Submit");
		questionFrame.add(submit);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				correct = checkAnswer(answer.getText());
				isActive = false;
				questionFrame.dispose();

				File sound;
				responseLabel = new JLabel();
				responseFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

				if (correct == true) {
					responseLabel.setText("You are correct");
					sound = new File("../core/assets/correct.wav");
				} else {
					responseLabel.setText("You are not correct");
					sound = new File("../core/assets/incorrect.wav");
				}

				playSound(sound);
				responseLabel.setFont(question.getFont().deriveFont(28.0f));
				responseLabel.setForeground(Color.white);
				responseFrame.add(responseLabel);
				responseFrame.add(continueGame);
				responseFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				responseFrame.setSize(300, 175);
				responseFrame.setVisible(true);

			}
		});

		continueGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				responseFrame.dispose();
			}
		});

		questionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		questionFrame.setSize(300, 250);
		questionFrame.setVisible(true);
	}

	public void playSound(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean isAlive() {
		return isActive;
	}

	public boolean isCorrect() {
		return correct;
	}

	public String[] retrieveRiddle() {
		File file = new File("../core/assets/riddles.csv");

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
	
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - 200);
	    int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - 200);
	    frame.setLocation(x, y);
	}
}
