import java.awt.event.*;

import javax.swing.Timer;

//This class is for all tetrominos
public abstract class Tetri {
	protected Pair center;
	private Timer timer;
	protected int orientation;
	protected Pair cors[];

	private int running = 1;

	public Tetri()
	{
		//Start with a place above the play area;
		center = new Pair(5,22);
		int delay = getDelay();
		timer = new Timer(delay, new TimerHandler());
		orientation = 0;
		cors = new Pair[4];
		for (int i = 0; i<4; i++)
		{
			cors[i] = new Pair(0,0);
		}
	}

	//get coordinates of the tetri
	public abstract void getCors();
	//make the orientation regular
	public abstract void regularOri();

	//rotate this tetri
	public int rotate()
	{
		if (running <= 0)
			return 0;

		timer.stop();
		timer.start();

		int flag = 1;
		clearTetri();
		orientation++;
		getCors();
		Game g = Game.getInstance();
		//Check if rotation is legal
		for (int i = 0; i<4; i++)
		{
			if (g.getPlayArea(cors[i]) == 0)
				continue;
			else
			{
				flag = 0;
				break;
			}
		}

		if (flag == 0)
		{
			orientation+=3;
			getCors();
		}

		showTetri();
		return flag;
	}

	//move a piece left, check before the move
	public int moveLeft()
	{
		if (running <= 0)
			return 0;

		timer.stop();
		timer.start();

		int flag = 1;
		Game g = Game.getInstance();
		clearTetri();
		//Check if this move is legal
		for (int i = 0; i<4; i++)
		{
			if (g.getPlayArea(cors[i].getX()-1,cors[i].getY()) == 0)
				continue;
			else
			{
				flag = 0;
				break;
			}
		}

		//Move if allowed
		if(flag == 1)
		{
			center.moveLeft();
			getCors();
		}
		showTetri();
		return flag;
	}

	//move a piece right, check before the move
	public int moveRight()
	{
		if (running <= 0)
			return 0;

		timer.stop();
		timer.start();

		int flag = 1;
		Game g = Game.getInstance();
		clearTetri();
		//Check if this move is legal
		for (int i = 0; i<4; i++)
		{
			if (g.getPlayArea(cors[i].getX()+1,cors[i].getY()) == 0)
				continue;
			else
			{
				flag = 0;
				break;
			}
		}

		//Move if allowed
		if(flag == 1)
		{
			center.moveRight();
			getCors();
		}
		showTetri();
		return flag;
	}

	//move a piece down, check before the move
	public int moveDown()
	{
		if (running <= 0)
			return 0;

		int flag = 1;
		Game g = Game.getInstance();
		clearTetri();
		//Check if this move is legal
		for (int i = 0; i<4; i++)
		{
			if (g.getPlayArea(cors[i].getX(),cors[i].getY()-1) == 0)
				continue;
			else
			{
				flag = 0;
				break;
			}
		}

		//Move if allowed
		if(flag == 1)
		{
			center.moveDown();
			getCors();
		}
		showTetri();
		return flag;
	}



	//get the delay of this tetri from Game class
	private int getDelay(){
		int currentLevel = Game.getInstance().getLevel();
		if (currentLevel > 24) 
			currentLevel = 24;
		int delay = (50 - 2*currentLevel)*1000/60;
		return delay;
	}

	//Clear this tetromino from play area
	public void clearTetri(){
		Game g = Game.getInstance();
		for (int i = 0; i<4; i++)
		{
			g.setPlayArea(cors[i], 0);
		}
	}

	//Show this tetromino to play area
	public void showTetri(){
		Game g = Game.getInstance();
		for (int i = 0; i<4; i++)
		{
			g.setPlayArea(cors[i], 2);
			System.out.println("X: "+cors[i].getX()+" Y: "+cors[i].getY());
		}
	}

	//Show this tetromino to play area as landed
	public void showTetriL(){
		Game g = Game.getInstance();
		for (int i = 0; i<4; i++)
		{
			g.setPlayArea(cors[i], 1);
		}

		for (int i = 0; i<4; i++)
		{
			//When a tetri lands, check if there is a part of this tetri
			//is above the play area. If so, the game is over
			//Should call hw5.getInstance().gameFail() here
			if (cors[i].checkY() == false)
			{
				TestGui.getInstance().gameFail();
				break;
			}

		}
	}

	public void start(){
		timer.start();
	}

	public void stop(){
		running = -1;
		timer.stop();
	}

	//used when pause that game
	public void pause(){
		if (running == 1)
		{
			running = 0;
			timer.stop();
		}
		else if (running == 0)
		{
			running = 1;
			timer.start();
		}
	}

	// inner class for timer event handling
	private class TimerHandler implements ActionListener {
		//
		public void actionPerformed( ActionEvent event )
		{

			if (moveDown() ==1){
				//Should call hw5.getInstance().displayPlayArea() here
				TestGui.getInstance().displayPlayArea();
			}
			else{
				timer.stop();
				showTetriL();
				//Should call hw5.getInstance().displayPlayArea() here
				TestGui.getInstance().displayPlayArea();
				//Should call hw5.getInstance().pieceFinish() here
				TestGui.getInstance().pieceFinish();
			}

		}
	}
}
