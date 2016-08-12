//The tetri of shape I

public class TetriI extends Tetri {
	public TetriI(int ori){
		//set the orientation
		orientation = ori;
		regularOri();
		
		//Change the center
		if (orientation == 0)
			center.setY(20);
		else
			center.setY(22);
		
		
		getCors();
	}
	
	public void getCors(){
		regularOri();
		int X = center.getX();
		int Y = center.getY();
		if (orientation == 0)
		{
			cors[0].setX(X-2);
			cors[0].setY(Y);
			cors[1].setX(X-1);
			cors[1].setY(Y);
			cors[2].setX(X);
			cors[2].setY(Y);
			cors[3].setX(X+1);
			cors[3].setY(Y);
		}
		else
		{
			cors[0].setX(X);
			cors[0].setY(Y+1);
			cors[1].setX(X);
			cors[1].setY(Y);
			cors[2].setX(X);
			cors[2].setY(Y-1);
			cors[3].setX(X);
			cors[3].setY(Y-2);
		}
	}
	
	public void regularOri(){
		orientation = orientation%2;
	}
	
	public int getType(){
		return 1;
	}
}
