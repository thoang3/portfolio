//The tetri of shape Z

public class TetriZ extends Tetri {
	public TetriZ(int ori){
		//set the orientation
		orientation = ori;
		regularOri();
		
		//Change the center
		center.setY(21);
		
		
		getCors();
	}
	
	public void getCors(){
		regularOri();
		int X = center.getX();
		int Y = center.getY();
		if (orientation == 0)
		{
			cors[0].setX(X-1);
			cors[0].setY(Y);
			cors[1].setX(X);
			cors[1].setY(Y);
			cors[2].setX(X);
			cors[2].setY(Y-1);
			cors[3].setX(X+1);
			cors[3].setY(Y-1);
		}
		else
		{
			cors[0].setX(X+1);
			cors[0].setY(Y+1);
			cors[1].setX(X);
			cors[1].setY(Y);
			cors[2].setX(X+1);
			cors[2].setY(Y);
			cors[3].setX(X);
			cors[3].setY(Y-1);
		}
	}
	
	public void regularOri(){
		orientation = orientation%2;
	}
	
	public int getType(){
		return 3;
	}
}
