import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.*;

import com.sun.glass.events.MouseEvent;


public class GameMenuBar extends JMenuBar{
	
	public JMenuBar menuBar;
	public JMenu gameMenu;
	public JMenu helpMenu;
	
	public JMenuItem menuItemReset;
	public JMenu menuItemTopTen;
	public JMenuItem menuItemExit;
	public JMenuItem menuItemHelp;
	public JMenuItem menuItemAbout;
	public JMenuItem menuItemTopTenScores;
	public JMenuItem menuItemTopTenReset;
	
	private ArrayList scoreList;
	private String scoreListMessage;
	
	GameMenuBar() {
		gameMenu = new JMenu("Game");
		helpMenu = new JMenu("Help");
		
		scoreListMessage = "";
		scoreList = new ArrayList<Integer>();
		
		menuItemReset = new JMenuItem("Reset | R");
		menuItemReset.setMnemonic(KeyEvent.VK_R);
		
		menuItemTopTen = new JMenu("Top Ten | T");
		menuItemTopTen.setMnemonic(KeyEvent.VK_T);
		
		menuItemTopTenScores = new JMenuItem("Scores");
		menuItemTopTenReset = new JMenuItem("Reset");
		
		
		
		menuItemExit = new JMenuItem("Exit | X");
		menuItemExit.setMnemonic(KeyEvent.VK_X);
		
		menuItemHelp = new JMenuItem("Help | H");
		menuItemHelp.setMnemonic(KeyEvent.VK_H);
		
		menuItemAbout = new JMenuItem("About | A");
		menuItemAbout.setMnemonic(KeyEvent.VK_A);
		
		
		 
		
		gameMenu.add(menuItemReset);
		gameMenu.add(menuItemTopTen);
		gameMenu.add(menuItemExit);
		menuItemTopTen.add(menuItemTopTenScores);
		menuItemTopTen.add(menuItemTopTenReset);
		helpMenu.add(menuItemHelp);
		helpMenu.add(menuItemAbout);
		
		add(gameMenu);
		add(helpMenu);
		
		enableActionListeners();
	}
	
	public void addToScoreList(int score) {
		scoreList.add(score);
	}
	
	public void resetScoreList() {
		scoreList.clear();
	}
	
	public String buildScoreList() {
		String builtMessage = "";
		
		
		
		return builtMessage;
	}
	
	private class ScoreEntity {
		int score = 0;
	}
	
	private void enableActionListeners() {
		
		
		// Resets the top ten scores for the game.
		menuItemTopTenReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Reset top ten function
			}
		});
		
		// Exit the application
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		// Shows a dialog message with information about how to play minesweeper.
		menuItemHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(menuItemHelp, helpMessage);
				
			}
		});
		
		// Shows a dialog message with information about the project.
		menuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String messageBuilder = "CS342 Project2 MineSweeper\n";
				messageBuilder += "Team Members\nDaniel Hajnos\nTung Hoang";
				JOptionPane.showMessageDialog(menuItemAbout, messageBuilder);				
			}
		});
	}
	
	private static String helpMessage = "MineSweeper Help\n\nYou have a board with squares that can contain bombs.\n" +
	                                    "You need to click figure out the locations of the boms\n" +
			                            "by using using the squares that have numbers on them. \n" +
	                                    "The number on the square correponds to the number of \n" +
			                            "bombs adjacent to that sqaure.";
}
