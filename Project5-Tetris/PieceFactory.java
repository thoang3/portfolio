import java.util.Random;

//This is a piece factory to create a new piece

public class PieceFactory {
	Random rn;
	
	public PieceFactory(){
		rn = new Random(System.currentTimeMillis());
	}
	
	public Tetri createPiece(){
		Tetri piece = null;
		
		int type = rn.nextInt(7);
		int ori = rn.nextInt(4);
		
		if (type == 0)
			piece = new TetriO(ori);
		else if (type == 1)
			piece = new TetriI(ori);
		else if (type == 2)
			piece = new TetriS(ori);
		else if (type == 3)
			piece = new TetriZ(ori);
		else if (type == 4)
			piece = new TetriL(ori);
		else if (type == 5)
			piece = new TetriJ(ori);
		else if (type == 6)
			piece = new TetriT(ori);
		
		return piece;
	}
}
