//This is a class of coordinate pairs.
//This class has getters and setters for each coordinate.
//For this project, the range of x should be 0~9, and the range of y should be 0~19.
//The check methods will check if the value is legal.


public class Pair {
	private int x;
	private int y;
	
	public Pair(int a, int b){
		setX(a);
		setY(b);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int a){
		x = a;
	}
	
	public void setY(int b){
		y = b;
	}
	
	public boolean checkX(){
		if (x >= 10 || x < 0)
			return false;
		else
			return true;
	}
	
	public boolean checkY(){
		if (y >= 20 || y < 0)
			return false;
		else
			return true;
	}
	
	public void moveLeft(){
		x--;
	}
	
	public void moveRight(){
		x++;
	}
	
	public void moveDown(){
		y--;
	}
}
