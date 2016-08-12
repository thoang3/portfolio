//The Game object keeps track of all information needed to know the state of the current game.
//The three main data here are score, line and the level.
//This class has getters and setters for these three.
//Another important data is the information of play area.
//Here 0 means an empty block, 1 means an occupied block, 2 means occupied by the current tetromino.

public class Game {
	//Singleton pattern
	private static Game instance = null;
	private int playArea[][];
	private int score;
	private int line;
	private int level;
	
	
	private Game(){
		
		//Set the play area 0
		playArea = new int[10][20];
		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 20; y++){
				playArea[x][y] = 0;
			}
		}
		
		setScore(0);
		setLine(0);
		setLevel(1);
	}
	
	public static synchronized Game getInstance(){
		if (instance == null)
			instance = new Game();

		return instance;
	}
	
	//use for restart the game
	public void clearGame() {
		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 20; y++){
				playArea[x][y] = 0;
			}
		}
		
		setScore(0);
		setLine(0);
		setLevel(1);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
	
	public int getPlayArea(int x, int y){
		if (x<0 || x>=10 || y<0 || y>=24)
			return -1;		//Illegal input
		if (y>=20)
			return 0;
		return playArea[x][y];
	}
	
	public int getPlayArea(Pair p){
		int x = p.getX();
		int y = p.getY();
		
		return getPlayArea(x,y);
	}
	
	public void setPlayArea(int x, int y, int z){
		if (x<0 || x>=10 || y<0 || y>=20 || z<0 || z >2)
			;		//Illegal input
		else
			playArea[x][y] = z;
	}
	
	public void setPlayArea(Pair p, int z){
		int x = p.getX();
		int y = p.getY();
		
		setPlayArea(x,y,z);
	}
	
	//This is a function to check if there is full lines.
	//This function should be called after a tetromino lands.
	//So it is assumed that blocks of current tetrimono are all 1.
	//The return is int[2].
	//The first number is the number of the first full line.
	//The second number is how many full lines there are.
	//Return [-1,0] if no full lines.
	public int[] checkLines(){
		int[]ans = new int[2];
		boolean flag = false;
		int firstline = 0;
		int lines = 0;
		for (int y = 0; y<20;y++){
			flag = true;
			for(int x = 0;x<10;x++){
				if(playArea[x][y] != 1){
					flag = false;
					break;
				}
			}
			if (flag == false && lines > 0)
			{
				ans[0] = firstline;
				ans[1] = lines;
				break;
			}
			else if(flag == true && lines == 0)
			{
				firstline = y;
				lines++;
			}
			else if(flag == true && lines >0)
			{
				lines++;
			}
		}
		return ans;
	}
	
	//clear the lines
	//input should be the return of checkLines
	//Using naive gravity
	public void clearLines(int[] input){
		int y = input[0];
		int lines = input[1];
		int points = 0;
		if (y == -1)
			return;
		
		
		for (; y<20;y++){
			for (int x =0;x<10;x++)
			{
				if(y+lines >= 20)
					playArea[x][y] = 0;
				else
					playArea[x][y] = playArea[x][y+lines];
			}
		}
		
		if (lines == 1)
			points = 40;
		if (lines == 2)
			points = 100;
		if (lines == 3)
			points = 300;
		if (lines == 4)
			points = 1200;
		
		line += lines;
		score += points*level;
		
		if (line >= level*10)
			level++;
	}

}
