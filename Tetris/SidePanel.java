import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Side panel containing the instruction of how to control the game, and displaying next piece information
public class SidePanel extends JPanel {

	private static final long serialVersionUID = 2181495598854992747L;
	private static final Color DRAW_COLOR = new Color(128, 192, 128);
	//private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);
	//private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);
	private final String icons[] =
		{"whitesquare.jpg", "brownsquare.jpg", "bluesquare.jpg"};

	private GridLayout gr;
	private JLabel labels[][];

	private JPanel subPanel;
	private JLabel nextPieceLabel, scoreLabel, levelLabel, statsLabel;
	private JLabel upLabel, leftLabel, rightLabel, controlLabel, downLabel;
	private JLabel restartLabel, pauseLabel, lineLabel, timeLabel;
	private PieceFactory factory;
	private Tetri next = null;
	private Tetri current = null;

	private JPanel topPanel;
	private JPanel bottomPanel;

	// Creates a new SidePanel and sets it's display properties.
	public SidePanel(int i) 
	{
		setPreferredSize(new Dimension(300, i));
		//setBackground(Color.BLACK);
		//this.setFont(LARGE_FONT);
		//	this.setForeground(DRAW_COLOR);
		this.setLayout(new BorderLayout());

		factory = new PieceFactory();
		next = factory.createPiece();

		topPanel = new JPanel();
		bottomPanel = new JPanel();

		topPanel.setBackground(Color.BLACK);
		nextPieceLabel = new JLabel("Next Piece:     ");
		topPanel.add(nextPieceLabel);
		nextPieceLabel.setForeground(DRAW_COLOR);

		this.add(topPanel, BorderLayout.NORTH);

		gr = new GridLayout(5, 5);
		labels = new JLabel[5][5];

		subPanel = new JPanel();
		subPanel.setLayout(gr);
		topPanel.add(subPanel);

		//Using this sequence of adding labels
		for (int y = 4; y>=0;y--){
			for (int x = 0; x <5; x++){
				labels[x][y] = new JLabel("[" + x + "] [" + y +"]");
				labels[x][y].setText("");
				labels[x][y].setIcon(new ImageIcon(icons[0]));
				subPanel.add(labels[x][y]);
			}
		}

		statsLabel = new JLabel("Stats");
		scoreLabel = new JLabel("Score: " + Game.getInstance().getScore());
		levelLabel = new JLabel("Level: " + Game.getInstance().getLevel());
		lineLabel = new JLabel("Cleared lines: " + Game.getInstance().getLine());
		timeLabel = new JLabel("Game Time: 0s");
		controlLabel = new JLabel("Controls");
		leftLabel = new JLabel("Left Arrow - Move Left");
		rightLabel = new JLabel("Right Arrow - Move Right");
		upLabel = new JLabel("Up Arrow - Rotate");
		downLabel = new JLabel("Down Arrow - Drop");
		restartLabel = new JLabel("S - Start/ Restart");
		pauseLabel = new JLabel("Space - Pause");

		statsLabel.setForeground(DRAW_COLOR);
		scoreLabel.setForeground(DRAW_COLOR);
		levelLabel.setForeground(DRAW_COLOR);
		lineLabel.setForeground(DRAW_COLOR);
		timeLabel.setForeground(DRAW_COLOR);
		controlLabel.setForeground(DRAW_COLOR);
		leftLabel.setForeground(DRAW_COLOR);
		rightLabel.setForeground(DRAW_COLOR);
		upLabel.setForeground(DRAW_COLOR);
		downLabel.setForeground(DRAW_COLOR);
		restartLabel.setForeground(DRAW_COLOR);
		pauseLabel.setForeground(DRAW_COLOR);

		bottomPanel.setBackground(Color.BLACK);
		
		leftLabel.addMouseListener(new ControlListener(1));
		rightLabel.addMouseListener(new ControlListener(0));
		upLabel.addMouseListener(new ControlListener(2));
		downLabel.addMouseListener(new ControlListener(3));
		restartLabel.addMouseListener(new ControlListener(5));
		pauseLabel.addMouseListener(new ControlListener(4));

		GroupLayout layout = new GroupLayout(bottomPanel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup()
				
				.addGroup(layout.createSequentialGroup()
						.addComponent(statsLabel)
						.addGroup(layout.createParallelGroup()
								.addComponent(scoreLabel)
								.addComponent(levelLabel)  
								.addComponent(lineLabel)
								.addComponent(timeLabel)
								)
						)
				.addGroup(layout.createSequentialGroup()
						.addComponent(controlLabel)
						.addGroup(layout.createParallelGroup(
								GroupLayout.Alignment.LEADING)
								.addComponent(leftLabel)
								.addComponent(rightLabel) 
								.addComponent(upLabel) 
								.addComponent(downLabel)
								.addComponent(restartLabel) 
								.addComponent(pauseLabel) 
								)
						)
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(20)
				.addComponent(statsLabel)
				.addComponent(scoreLabel)
				.addComponent(levelLabel)
				.addComponent(lineLabel)
				.addComponent(timeLabel)
				.addGap(20)
				.addComponent(controlLabel)
				.addComponent(leftLabel)
				.addComponent(rightLabel) 
				.addComponent(upLabel) 
				.addComponent(downLabel) 
				.addComponent(restartLabel) 
				.addComponent(pauseLabel) 

				);


		bottomPanel.setLayout(layout);    

		bottomPanel.add(statsLabel);
		bottomPanel.add(scoreLabel);
		bottomPanel.add(levelLabel);
		bottomPanel.add(lineLabel);
		bottomPanel.add(timeLabel);
		bottomPanel.add(controlLabel);
		bottomPanel.add(leftLabel);
		bottomPanel.add(rightLabel);
		bottomPanel.add(upLabel);
		bottomPanel.add(downLabel);
		bottomPanel.add(restartLabel);
		bottomPanel.add(pauseLabel);

		this.add(bottomPanel);
	}

	public Tetri getPiece()
	{
		current = next;
		
		next = factory.createPiece();
		next.regularOri();
		scoreLabel.setText("Score: " + Game.getInstance().getScore());
		levelLabel.setText("Level: " + Game.getInstance().getLevel());
		lineLabel.setText("Cleared lines: " + Game.getInstance().getLine());

		for (int y = 4; y>=0;y--){
			for (int x = 0; x <5; x++)
			{
				labels[x][y].setIcon(new ImageIcon(icons[0]));
			}
		}

		for (int i = 0; i < 4; i++)
		{
			labels[next.cors[i].getX()-3][next.cors[i].getY()-19].setIcon(new ImageIcon(icons[2]));
		}

		return current;
	}
	
	public void updateTime(int time)
	{
		timeLabel.setText("Game Time: " + time + "s");
	}
	
	private class ControlListener implements MouseListener
	{
		private int type;
		
		public ControlListener(int i) {
			type = i;
		}

		public void mouseClicked(MouseEvent arg0) {
			TestGui.getInstance().buttonAction(type);
			
		}

		public void mouseEntered(MouseEvent e) {
			;
			
		}

		public void mouseExited(MouseEvent e) {
			;
			
		}

		public void mousePressed(MouseEvent e) {
			;
			
		}

		public void mouseReleased(MouseEvent e) {
			;
			
		}

		
	}

}