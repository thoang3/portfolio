/*
 * GameTimer class. Display timer on the gui.
 */

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameTimer extends JPanel {
	
	private Timer timeClock;
	private ImageIcon iconImages[];
	private JLabel time[];
	private int count;
	
	private static final int TIME_DELAY = 1000;
	
	GameTimer() 
	{
		super(new FlowLayout(FlowLayout.CENTER,0,5));
		
		
		count = 0;
		time = new JLabel[3];
		iconImages = new ImageIcon[10];
		timeClock = new Timer(TIME_DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTimer();
				incrementTimer();
				if(count == 1000) {
					stopTimer();
				}
			}
			
		});
		
		for(int i=0; i < 10; i++) {
			iconImages[i] = new ImageIcon("images/countdown_"+i+".gif");
		}
		
		for(int i=0; i < 3; i++) {
			time[i] = new JLabel(iconImages[0]);
			add(time[i]);
		}
		
	}
	
	private void updateTimer() {
		int f = count % 10;
		int s = (count / 10) % 10;
		int t = (count / 100) % 10;
		time[0].setIcon(iconImages[t]);
		time[1].setIcon(iconImages[s]);
		time[2].setIcon(iconImages[f]);
	}
	
	public int getTime() {
		return count;
	}
	
	public void resetTimer() {
		count = 0;
	}
	
	private void incrementTimer() {
		count++;
	}
	
	public void startTimer() {
		timeClock.start();
	}
	
	public void stopTimer() {
		timeClock.stop();
	}
}
