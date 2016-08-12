//The tetri of shape O

public class TetriO extends Tetri {
	public TetriO(int ori){
		//set the orientation
		orientation = 0;
		
		//Change the center
		center.setY(21);
		
		
		getCors();
	}
	
	public void getCors(){
		int X = center.getX();
		int Y = center.getY();
		cors[0].setX(X-1);
		cors[0].setY(Y);
		cors[1].setX(X);
		cors[1].setY(Y);
		cors[2].setX(X-1);
		cors[2].setY(Y-1);
		cors[3].setX(X);
		cors[3].setY(Y-1);
	}
	
	public void regularOri(){
		orientation = 0;
	}
	
	public int getType(){
		return 0;
	}
}
