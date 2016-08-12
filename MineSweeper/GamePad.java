import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;


public class GamePad extends JPanel {

	GamePad() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLocation(12, 50);
		setLayout(new GridLayout(10,10,1,1));
		setSize(160,160);
		
	}
}
