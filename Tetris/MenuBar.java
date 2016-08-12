import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

// Display menu bar for the game
public class MenuBar extends JMenuBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9154524794974916122L;
	public JMenuBar menuBar;
	public JMenu gameMenu;
	public JMenu helpMenu;

	public JMenuItem menuItemRestart;
	public JMenuItem menuItemHelp;
	public JMenuItem menuItemAbout;
	public JMenuItem menuItemExit;

	MenuBar()
	{
		gameMenu = new JMenu("Game");
		helpMenu = new JMenu("Help");

		menuItemRestart = new JMenuItem("Restart | R");
		menuItemRestart.setMnemonic(KeyEvent.VK_R);

		menuItemExit = new JMenuItem("Exit | Q");
		menuItemExit.setMnemonic(KeyEvent.VK_Q);

		menuItemHelp = new JMenuItem("Help | H");
		menuItemHelp.setMnemonic(KeyEvent.VK_H);

		menuItemAbout = new JMenuItem("About | A");
		menuItemAbout.setMnemonic(KeyEvent.VK_A);

		gameMenu.add(menuItemRestart);
		gameMenu.add(menuItemExit);

		helpMenu.add(menuItemHelp);
		helpMenu.add(menuItemAbout);

		add(gameMenu);
		add(helpMenu);

		enableActionListeners();
		
	}

	private void enableActionListeners()
	{
		menuItemExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		menuItemRestart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TestGui.getInstance().start();
			}
		});

		// Shows a dialog message with information about how to play
		menuItemHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(menuItemHelp, helpMessage);

			}
		});

		// Shows a dialog message with information about the project.
		menuItemAbout.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				String messageBuilder = "CS342 Project 5 Tetris\n";
				messageBuilder += "Team Members\n Angelica Gallegos\n Tung Hoang\n Xinyu Xu";
				JOptionPane.showMessageDialog(menuItemAbout, messageBuilder);				
			}
		});
	}

	private static String helpMessage = "Tetris Game \n"
										+ "Instructions to play the game is quite clear based on the infomration at the right side.\n"
										+ "We decide not to implement the buttons as it is much more convenient to play this game using keyboard.\n"
										+ "However, we do make the labels work as buttons in case the grader(s) want to test functionalities.";

}