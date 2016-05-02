/*	CS 342 Spring 2016 UIC
 *  Project 5 Tetris
 *  Team Members: Angelica Gallegos
 *  			  Xinyu Xu
 *  			  Tung Hoang
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// GUI for the game
public class TestGui implements KeyListener {
	private static TestGui instance = null;
	
	private JFrame frame;
	private GridLayout grid;
	private JLabel labels[][];

	private int running = 1;
	private Tetri current = null;
	
	private JPanel boardPanel;
	private SidePanel sidePanel;
	private MenuBar menuBar;
	
	private int seconds = 0;
	private Timer timer;
	
	private final static String icons[] =
		{"whitesquare.jpg", "brownsquare.jpg", "bluesquare.jpg"};
	
	public TestGui()
	{
		frame = new JFrame("Tetris");
		menuBar = new MenuBar();
		frame.setJMenuBar(menuBar);
		
		frame.setLayout(new BorderLayout());
		//set up layout
		grid = new GridLayout(20,10);
		
		boardPanel = new JPanel();
		sidePanel = new SidePanel(boardPanel.getSize().height);
		
		boardPanel.setLayout(grid);

		labels = new JLabel[10][20];
		
		//Using this sequence of adding labels
		for (int y = 19; y>=0;y--){
			for (int x = 0; x <10; x++){
				labels[x][y] = new JLabel("[" + x + "] [" + y +"]");
				labels[x][y].setText("");
				labels[x][y].setIcon(new ImageIcon(icons[0]));
				boardPanel.add(labels[x][y]);
			}
		}
		
		frame.add(boardPanel, BorderLayout.CENTER);
		frame.add(sidePanel, BorderLayout.EAST);
		frame.addKeyListener(this);
		
		frame.requestFocus();
		
		frame.pack();
		frame.setVisible(true);
		
		timer = new Timer(1000, new TimerHandler());
		

	}
	
	public static synchronized TestGui getInstance(){
		if (instance == null)
			instance = new TestGui();

		return instance;
	}
	
	public static void main( String args[] )
	{
		TestGui application = TestGui.getInstance();
		application.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//		Game.getInstance().setLevel(20);
		application.start();
		
	}
	
	public void start()
	{
		if (current != null)
			current.stop();
		Game.getInstance().clearGame();
		seconds = 0;
		sidePanel.updateTime(seconds);
		timer.start();
		

		current = sidePanel.getPiece();
		current.start();
		displayPlayArea();
		
		running = 1;
	}
	
	public void displayPlayArea()
	{
		Game g = Game.getInstance();
		for (int x=0;x<10;x++)
		{
			for (int y=0;y<20;y++)
			{
				labels[x][y].setIcon(new ImageIcon(icons[g.getPlayArea(x, y)]));
			}
		}

	}
	
	public void pieceFinish(){
		if (running == 1)
		{
			Game g = Game.getInstance();
			while (true)
			{
				int result[] = g.checkLines();
				if (result[1]  == 0)
					break;
				g.clearLines(result);
			}
			current = sidePanel.getPiece();
			current.start();
		}
		else
		{
			current.stop();
			
			Object[] options = { "Restart" , "Exit Game" };
			int choice = JOptionPane.showOptionDialog(frame, "Game Over!","Game Over",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			
			if (choice == 0) 
			{
				start();
			}
			else 
			{
				System.exit(0);
			}
		}
		
	}
	
	public void gameFail(){
		running = 0;
		timer.stop();
		
		
		
		
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT)
			current.moveRight();
		else if (key == KeyEvent.VK_LEFT)
			current.moveLeft();
		else if (key == KeyEvent.VK_UP)
			current.rotate();
		else if (key == KeyEvent.VK_DOWN)
			current.moveDown();
		else if (key == KeyEvent.VK_SPACE)
			current.pause();
		else if (key == KeyEvent.VK_S)
			start();
		
		displayPlayArea();
	}
	
	public void buttonAction(int type) {
		if (type == 0)
			current.moveRight();
		else if (type == 1)
			current.moveLeft();
		else if (type == 2)
			current.rotate();
		else if (type == 3)
			current.moveDown();
		else if (type == 4)
			current.pause();
		else if (type == 5)
			start();
		
		displayPlayArea();
	}

	public void keyReleased(KeyEvent e) {
		;
	}

	public void keyTyped(KeyEvent e) {
		;		
	}
	
	// inner class for timer event handling
	private class TimerHandler implements ActionListener {
		//
		public void actionPerformed( ActionEvent event )
		{
			seconds++;
			sidePanel.updateTime(seconds);
		}
	}
	
	

}
