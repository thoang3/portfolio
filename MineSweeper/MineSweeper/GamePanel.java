import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


public class GamePanel extends JPanel{
	
	private GameTimer gameTimer;
	private GameScore gameScore;
	private GamePanelButtonCenter gamePanelButton;

	GamePanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLocation(12,12);
		setLayout(new FlowLayout(FlowLayout.CENTER,15,3));
		setSize(160,200);	
		
		gameScore = new GameScore();
		gamePanelButton = new GamePanelButtonCenter();
		gameTimer = new GameTimer();
		
		add(gameScore);
		add(gamePanelButton);
		add(gameTimer);	
	}
	
	public GameTimer getGameTimer() {
		return gameTimer;
	}
	
	public GamePanelButtonCenter getGamePanelButton() {
		return gamePanelButton;
	}
	
	public GameScore getGameScore() {
		return gameScore;
	}
}
