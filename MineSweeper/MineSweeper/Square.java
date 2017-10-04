import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Square extends JLabel
{
    public int rowPos;  // x-position of the square (0 -> 9 in this 10 by 10 small project)
    public int colPos;  // y-position of the square (-------------------------------------)
    public int numMines; // # of mines around the respective position (0 -> 8)
    public int isMine = 0;  // 1 -> Yes this is a mine, 0 No
    public boolean isAbleToGenerate = true; // just to check if a mine can be put at this square or not
    public boolean isExplored = false; // these last two boolean help to get the connected compoment
    public boolean isQueue = false;   // in the clearMines method
    public boolean use = true;
    public boolean isFlaged = false;
    
    private MineFieldStates state;
    
    private ImageIcon iconImages[];
    
    // constructor
    public Square(int x, int y, int posX, int posY)
    {
		super();
        // Initialize instance variables
        rowPos = x;
        colPos = y;
		setImage(state.NORMAL_BUTTON,0);
		setBounds(posX, posY, 16, 16);
		
		iconImages = new ImageIcon[9];
		for(int i=0; i < 9; i++) {
			iconImages[i] = new ImageIcon("images/button_"+i+".gif");
		}
		
		enableActionListener();
    }
    
    public void updateNumMines(int num)
    {
        numMines = num;
    }
    
	public void setImage(MineFieldStates s, int numberOfMines) {
		switch(s) {
		case BOMB:
			setIcon(new ImageIcon("images/button_bomb_pressed.gif"));
			break;
		case BOMB_X:
			setIcon(new ImageIcon("images/button_bomb_X.gif"));
			break;
		case BOMB_BLOWN:
			setIcon(new ImageIcon("images/button_bomb_blown.gif"));
			break;
		case NORMAL_BUTTON:
			setIcon(new ImageIcon("images/button_normal.gif"));
			break;
		case PRESSED_BUTTON:
			setIcon(new ImageIcon("images/button_pressed.gif"));
			break;
		case QUESTION_MARK:
			setIcon(new ImageIcon("images/button_question.gif"));
			break;
		case QUESTION_MARK_PRESSED:
			setIcon(new ImageIcon("images/button_question_pressed.gif"));
			break;
		case FLAG:
			setIcon(new ImageIcon("images/button_flag.gif"));
			break;
		case NUMBER_BUTTON:
			if(numberOfMines < 9 && numberOfMines > 0) 
				setIcon(iconImages[numberOfMines]);
			break;
		default:
			JOptionPane.showMessageDialog(this, "ERROR: images not found!");
			System.exit(1);
		}
	}
	
	public void reset() {
		setImage(state.NORMAL_BUTTON,0);
		
		 numMines = -1;
		 isMine = 0; 
		 isAbleToGenerate = true; 
		 isExplored = false; 
		 isQueue = false;  
		 use = true;
	}
	
	private void enableActionListener() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseReleased(MouseEvent e) {
				
			}
		});
	}
}
