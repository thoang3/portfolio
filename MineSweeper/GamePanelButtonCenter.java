import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GamePanelButtonCenter extends JLabel {
	private HeadPictureStates state;
	
	GamePanelButtonCenter() {
		setImage(state.HEAD_SMILE);
	}
	
	public void setImage(HeadPictureStates s) {
		switch(s) {
		case HEAD_DEAD:
			setIcon(new ImageIcon("images/head_dead.gif"));
			break;
		case HEAD_GLASSES:
			setIcon(new ImageIcon("images/head_glasses.gif"));		
			break;
		case HEAD_O:
			setIcon(new ImageIcon("images/head_o.gif"));
			break;
		case HEAD_SMILE:
			setIcon(new ImageIcon("images/head_smile.gif"));
			break;
		case HEAD_SMILE_PRESSED:
			setIcon(new ImageIcon("images/head_smile_pressed.gif"));
			break;
		default:
			JOptionPane.showMessageDialog(this, "ERROR: Images not found");
			System.exit(1);
		}
	}
}
