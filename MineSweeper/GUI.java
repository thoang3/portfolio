//import javafx.scene.input.MouseButton;

import javax.swing.*;
//import javax.swing.border.*;

//import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;


public class GUI extends JFrame{
	private Square mineFieldButton[][];

	private GamePanel gamePanel;
	private GamePad gamePad;
	private GameMenuBar gameMenuBar;
	private Game game;
	private boolean firstPress;
	private boolean gameEnded;
	private ArrayList<Player> scoreList;
	
	
	GUI() {
		super("MineSweeper");
		gamePanel = new GamePanel();
		gameMenuBar = new GameMenuBar();
		gamePad = new GamePad();
		game = new Game();
		
		scoreList = new ArrayList<Player>();
		scoreList.add(new Player("Dan",43));
		scoreList.add(new Player("Tung",33));
		Collections.sort(scoreList);
		
		
		setSize(200, 300);
		setLayout(null);
		
		add(gamePad);
		add(gamePanel);
		setJMenuBar(gameMenuBar);
		firstPress = false;
		gameEnded = false;
		
		mineFieldButton = new Square[10][10];
		for(int i=0; i < 10; i++) {
			for(int j=0; j < 10; j++) {
				mineFieldButton[i][j] = new Square(i,j,i+1+16, j+1+16);
				mineFieldButton[i][j].addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e) {
						
						if(e.getButton() == MouseEvent.BUTTON1 && !gameEnded) {
							gamePanel.getGamePanelButton().setImage(HeadPictureStates.HEAD_O);
							Square s = (Square) e.getSource();
							if(!firstPress) {
								gamePanel.getGameTimer().startTimer();
								game.RandomGenerator(mineFieldButton, s.rowPos+1, s.colPos+1);
								firstPress = true;
							}
							if(game.countMines(mineFieldButton, s.rowPos, s.colPos) > 0 && s.isMine == 0) {
								s.setImage(MineFieldStates.NUMBER_BUTTON, 
											game.countMines(mineFieldButton, s.rowPos, s.colPos));
								s.use = false;
							} 
							else if(s.isMine == 1) {
								showAllBombs();
								gamePanel.getGameTimer().stopTimer();
								gamePanel.getGamePanelButton().setImage(HeadPictureStates.HEAD_DEAD);
								gameEnded = true;
								JOptionPane.showMessageDialog(gamePanel, "You Lose!");
							}
							else {
								game.updateArray(mineFieldButton);
								explorePress(s.rowPos+1, s.colPos+1);
							}
						}
						
						if(e.getButton() == MouseEvent.BUTTON3 && firstPress && !gameEnded) {
							gamePanel.getGamePanelButton().setImage(HeadPictureStates.HEAD_O);
							Square s = (Square) e.getSource();
							if(s.use) {
								if(!s.isFlaged && gamePanel.getGameScore().getScore() > 0) {
									s.setImage(MineFieldStates.FLAG, 0);
									s.isFlaged = true;
									gamePanel.getGameScore().remove();
								}
								else if(s.isFlaged && gamePanel.getGameScore().getScore() <= 10){
									s.setImage(MineFieldStates.NORMAL_BUTTON, 0);
									s.isFlaged = false;
									gamePanel.getGameScore().increment();;
								}
								else 
									System.out.println("Something went wrong");
								
								int winCounter = 0;
								for(int i=0; i< 10; i++) {
									for(int j=0; j < 10; j++) {
										if(mineFieldButton[i][j].isMine == 1 
											&& mineFieldButton[i][j].isFlaged) {
											winCounter++;
										}											
									}
								}
								
								if(winCounter == 10) {
									gamePanel.getGameTimer().stopTimer();
									gamePanel.getGamePanelButton().setImage(HeadPictureStates.HEAD_GLASSES);
									Player p = new Player("",0);
									p.name = JOptionPane.showInputDialog("You Win! Please enter your name.");
									p.score = gamePanel.getGameTimer().getTime();
									
									scoreList.add(p);
									Collections.sort(scoreList);
								}
							}
						}
					}
					
					public void mouseReleased(MouseEvent e) {
						gamePanel.getGamePanelButton().setImage(HeadPictureStates.HEAD_SMILE);
					}
				});
				gamePad.add(mineFieldButton[i][j]);
			}
		}
		
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		enableActionListener();
	}	
	
	private void resetGame() {
		for(int i=0; i < 10; i++) {
			for(int j=0; j < 10; j++) {
				mineFieldButton[i][j].reset();
			}
		}
		game = new Game();
		firstPress = false;
		gameEnded = false;
		gamePanel.getGameTimer().resetTimer();
		gamePanel.getGameScore().setScore(10);
		gamePanel.getGamePanelButton().setImage(HeadPictureStates.HEAD_SMILE);
	}
	
	private void showAllBombs() {
		for(int i=0; i < 10; i++) {
			for(int j=0; j < 10; j++) {
				if(mineFieldButton[i][j].isMine == 1) {
					mineFieldButton[i][j].setImage(MineFieldStates.BOMB_BLOWN, 0);
					mineFieldButton[i][j].use = false;
				}
			}
		}
	}
	
	private void explorePress(int x, int y) {
		game.clearMines(mineFieldButton, x, y);
		for(int i=0; i < 10; i++) {
			for(int j=0; j < 10; j++) {
				 if(mineFieldButton[i][j].isExplored) {
					 mineFieldButton[i][j].setImage(MineFieldStates.PRESSED_BUTTON, 0);
					 mineFieldButton[i][j].use = false;
				 }
				 
				 if(mineFieldButton[i][j].isMine == 0 && mineFieldButton[i][j].numMines == 7) {
					 mineFieldButton[i][j].setImage(MineFieldStates.NUMBER_BUTTON, game.countMines(mineFieldButton, i, j));
					 mineFieldButton[i][j].use = false;
				 }
			}
		}
	}
	
	private String displayScore() {
		String list = "";
		if(scoreList.size() == 0) {
			for(int i=0; i < 10; i++) {
				list += i+1 + ": \n";
			}
			return list;
		}
		
		int size = scoreList.size();
		int i;
		for(i=0 ;i < size; i++) {
			//Player p = scoreList.get(i);
			list += i+1 + ": " + scoreList.get(i).name + " " + scoreList.get(i).score + "\n";
		}
		for(i=size; i < 10; i++) {
			list += i+":\n";
		}
		return list;
	}
	
	private void resetScore() {
		scoreList.clear();
	}
	
	
	private void enableActionListener() {
		gamePanel.getGamePanelButton().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				gamePanel.getGamePanelButton().setImage(HeadPictureStates.HEAD_SMILE_PRESSED);
				resetGame();
			}
			
			public void mouseReleased(MouseEvent event) {
				gamePanel.getGamePanelButton().setImage(HeadPictureStates.HEAD_SMILE);
			}
		});
		
		gameMenuBar.menuItemReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}
		});
		
		gameMenuBar.menuItemTopTen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(gamePad,displayScore());
			}
		});
		
		gameMenuBar.menuItemTopTenReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				resetScore();
			}
		});
	}
	
	private class Player implements Comparable {
		public String name;
		public int score;
		Player(String name, int score) {
			this.name = name;
			this.score = score;
		}
		
		public int getScore() {
			return score;
		}
		
		public String getName() {
			return name;
		}

		public int compareTo(Object o) {
			Player p = (Player)o;
			return p.score-this.score;
		}
	}
}
