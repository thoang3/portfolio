import java.awt.FlowLayout;
import javax.swing.*;

public class GameScore extends JPanel {
	
	private ImageIcon iconImages[];
	private JLabel score[];
	
	private int scoreCount;
	
	GameScore() {
		super(new FlowLayout(FlowLayout.CENTER,0,5));
		
		iconImages = new ImageIcon[10];
		score = new JLabel[3];
		scoreCount = 10;
		
		
		for(int i=0; i < 10; i++) {
			iconImages[i] = new ImageIcon("images/countdown_"+i+".gif");
		}
		
		for(int i=0; i < 3; i++) {
			score[i] = new JLabel(iconImages[0]);
			add(score[i]);
		}
		updateScoreCount();
		
	}
	
	public void remove() {
		if(scoreCount == 0) {
			JOptionPane.showMessageDialog(this, "You can't place anymore flags!");
			return;
		}
		scoreCount--;
		updateScoreCount();
	}
	
	public void increment() {
		if(scoreCount == 10)
			return;
		scoreCount++;
		updateScoreCount();
	}
	
	public int getScore() {
		return scoreCount;
	}
	
	public void setScore(int a) {
		scoreCount = a;
	}
	
	private void updateScoreCount() {
		int f = scoreCount % 10;
		int s = (scoreCount/10) % 10;
		int t = (scoreCount/100) % 10;
		score[0].setIcon(iconImages[t]);
		score[1].setIcon(iconImages[s]);
		score[2].setIcon(iconImages[f]);
	}
}
