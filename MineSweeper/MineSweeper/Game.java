import java.util.*;
import java.lang.Math;

public class Game
{
    // instance variables - replace the example below with your own
    Square[][] arr = new Square[10][10];
    
    // constructor
    public Game()
    {
        // Initialize the square board
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                arr[i][j] = new Square(i, j,0,0);
            }
        }
    }

    // Generate 10 mines randomly on the game board, except for the one user click on and its neighbors 
    public void RandomGenerator(Square[][] array, int row, int col)
    {
        array[row-1][col-1].isAbleToGenerate = false;
        
        // Do not assign mines to any of the the squares below
        for (Square a : neighbors(array, row, col))
        {
            a.isAbleToGenerate = false;
        }
                  
        int rand;
        int count = 0;
        
        // randomly assign 10 mines to the remaining squares
        while (count < 10)
        {
            do
            {
                rand = (int) (Math.random() * 101);
            }
            while (array[rand/10][rand%10].isAbleToGenerate == false);
            
            array[rand/10][rand%10].isMine = 1;
            array[rand/10][rand%10].isAbleToGenerate = false;
            count++;
        }
        
    }
    
    // count number of mines around a square
    public int countMines(Square[][] array, int row, int col)
    {
        int count = 0;
               
        for (Square a : neighbors(array, row+1, col+1))
        {
            count += a.isMine;
        }
        
        return count;
    }
    
    // update the square board with numMines
    public void updateArray(Square[][] array)
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                array[i][j].updateNumMines(countMines(array, i,j));
            }
        }
    }
    
    // print mines map for console checking, display 1 where there is a mine
    public void printMines(Square[][] array)
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                System.out.print(array[i][j].isMine + " ");
            }
            System.out.println();
        }
    }
   
    // print number of mines map for console checking
    public void printNumberOfMines(Square[][] array)
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                System.out.print(array[i][j].numMines + " ");
            }
            System.out.println();
        }
    }
    
    // for fun (console checking)
    public boolean isInteger( String input ) 
    {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( NumberFormatException e ) {
            return false;
        }
    }
    
    // main method, click left-mouse button on a square to clear mines
    public void clearMines(Square[][] array, int row, int col)
    {
        if (array[row-1][col-1].isMine == 1)
        {
            System.out.println("BOOM! You click on mine!"); // for console checking, check if user click on mine
        }
        
        if (array[row-1][col-1].isMine == 0)
        {
            if (array[row-1][col-1].numMines != 0)
            {
                // for console checking, if user click on a non-mine button with
                // non-zero number of mines around
                System.out.println("There are " + array[row-1][col-1].numMines + " mines around this square!");
            }
            
            // if user click on zero-mine square button, clear all zero-mine button in the same connected component
            // and display number of mines for all the square buttons on the boundary
            if (array[row-1][col-1].numMines == 0)
            {
                List<Square> explored = explore(array, row, col);
                
                for (Square a : explored)
                {
                    a.numMines = 9; // for console checking, should display 0 in front end
                    
                    // to display the boundary here
                    for (Square b : neighbors(array, a.rowPos+1, a.colPos+1))
                    {
                        if ((b.numMines > 0) && (b.numMines <9))
                            b.numMines = 7;
                            
                    }
                }
                
                // console checking, instead of print number of mines here, 
                // later on we should clear Mines, and display number
                // of mines of all squares on the boundary
                printNumberOfMines(array);
            }
        }
    }
    
    // explore the connected component of the square mine with numMine = 0
    public List<Square> explore(Square[][] array, int row, int col)
    {
        List<Square> explored = new ArrayList<Square>(); // list of squares explored/ visited

        List<Square> queue = new ArrayList<Square>(); // queue of unexplored/unvisited squares
        
        // source vertex of the connected component
        queue.add(array[row-1][col-1]);
        
        while (!queue.isEmpty())
        {
            Square a = queue.get(0);
            queue.remove(0);          
            queue.addAll(getUnExploredNeighbors(array, a));                  
            a.isExplored = true;
            explored.add(a);
        }
        
        return explored;
    }
    
    // recursive function to get unexplored neighbors with numMines=0 of a specific square sq
    public List<Square> getUnExploredNeighbors(Square[][] array, Square sq)
    {   
        List<Square> list = new ArrayList<Square>();
        
        for (Square a : neighbors(array, sq.rowPos + 1, sq.colPos + 1))
        {
            if ((a.isQueue == false) && (a.isExplored == false) && (a.numMines == 0))
            {
                list.add(a);
                a.isQueue = true;
            }
        }
        return list;        
    }
    
    // get list of neighbors of the selected square
    public List<Square> neighbors(Square[][] array, int i, int j)
    {
        int markedRow = i-1;
        int markedCol = j-1;
        
        List<Square> list = new ArrayList<Square>();
        
        // if the square is not on the boundary
        if ((markedRow > 0) && (markedRow < 9) && ((markedCol > 0) && (markedCol < 9)))
        {   
            list.add(array[markedRow - 1][markedCol - 1]);
            list.add(array[markedRow - 1][markedCol]);
            list.add(array[markedRow - 1][markedCol + 1]);
            list.add(array[markedRow][markedCol - 1]);
            list.add(array[markedRow][markedCol + 1]);
            list.add(array[markedRow + 1][markedCol - 1]);
            list.add(array[markedRow + 1][markedCol]);
            list.add(array[markedRow + 1][markedCol + 1]);
        }
        
        // if the square is on the top boundary of the array, but not on the corner
        if ((markedRow > 0) && (markedRow < 9) && (markedCol == 0))
        {    
            list.add(array[markedRow - 1][markedCol]);
            list.add(array[markedRow - 1][markedCol + 1]);
            list.add(array[markedRow][markedCol + 1]);
            list.add( array[markedRow + 1][markedCol]);
            list.add(array[markedRow + 1][markedCol + 1]);
        }
        
        // if the square is on the bottom boundary of the array, but not on the corner
        if ((markedRow > 0) && (markedRow < 9) && (markedCol == 9))
        {
            list.add(array[markedRow - 1][markedCol]);
            list.add(array[markedRow - 1][markedCol - 1]);
            list.add(array[markedRow][markedCol - 1]);
            list.add(array[markedRow + 1][markedCol]);
            list.add(array[markedRow + 1][markedCol - 1]);
        }
        
        // if the square is on the left boundary of the array, but not on the corner
        if ((markedCol > 0) && (markedCol < 9) && (markedRow == 0))
        {       
            list.add(array[markedRow][markedCol - 1]);
            list.add(array[markedRow + 1][markedCol - 1]);
            list.add(array[markedRow + 1][markedCol]);
            list.add(array[markedRow][markedCol + 1]);
            list.add(array[markedRow + 1][markedCol + 1]);
        }
        
        // if the square is on the right boundary of the array, but not on the corner
        if ((markedCol > 0) && (markedCol < 9) && (markedRow == 9))
        {
            list.add(array[markedRow][markedCol - 1]);
            list.add(array[markedRow - 1][markedCol - 1]);
            list.add(array[markedRow - 1][markedCol]);
            list.add(array[markedRow][markedCol + 1]);
            list.add(array[markedRow - 1][markedCol + 1]);
        }
        
        // if the square is one of the 4 corners, respectively.
        if ((markedRow == 0) && (markedCol == 0))
        {   
            list.add(array[0][1]);
            list.add(array[1][1]);
            list.add(array[1][0]);
        }
        
        // if the square is one of the 4 corners, respectively.
        if ((markedRow == 0) && (markedCol == 9))
        {   
            list.add(array[0][8]);
            list.add(array[1][9]);
            list.add(array[1][8]);
        }
        
        // if the square is one of the 4 corners, respectively.
        if ((markedRow == 9) && (markedCol == 0))
        {   
            list.add(array[8][0]);
            list.add(array[8][1]);
            list.add(array[9][1]);
        }
        
         // if the square is one of the 4 corners, respectively.
        if ((markedRow == 9) && (markedCol == 9))
        {   
            list.add(array[9][8]);
            list.add(array[8][8]);
            list.add(array[8][9]);
        }
        
        return list;
    }
    
    /*
    public static void main(String[] args)
    {
        Game game = new Game();
        
        Scanner scanner = new Scanner(System.in);
        String inputRow, inputCol;
        
        // for console testing
        do 
        {
        System.out.println("Please enter row (1->10):");
        inputRow = scanner.nextLine();
        inputRow = inputRow.replace(" ", "");
        System.out.println("Please enter column (1->10):");
        inputCol = scanner.nextLine();
        inputCol = inputCol.replace(" ", "");
        }
        while (!game.isInteger(inputRow) || !game.isInteger(inputCol));
        
        // generate 10 mines randomly after user makes the 1st click
        game.RandomGenerator(game.arr, Integer.parseInt(inputRow), Integer.parseInt(inputCol));
        
        // print map of mines for console checking
        game.printMines(game.arr);
       
        System.out.println();
        
        // update numMines (number of mines around a non-mine square)
        game.updateArray(game.arr);
        
        // print numMines square board for console checking
        game.printNumberOfMines(game.arr);
        
        // test clear mines method
        int count = 0;
        while (count < 3)
        {
            
            do 
            {
            System.out.println("Please enter row (1->10):");
            inputRow = scanner.nextLine();
            inputRow = inputRow.replace(" ", "");
            System.out.println("Please enter column (1->10):");
            inputCol = scanner.nextLine();
            inputCol = inputCol.replace(" ", "");
            }
            while (!game.isInteger(inputRow) || !game.isInteger(inputCol));
            
            // if user click on a mine -> BOOM game over,
            // if user click on a non-mine square with non-zero numMines -> display it,
            // if user click on a non-mine square with zero numMines -> clear all of its zero numMines neighbors
            // (which is a connected component) and display numMines of the boundary of that component
            game.clearMines(game.arr, Integer.parseInt(inputRow), Integer.parseInt(inputCol));
            count++;
        }
        
        System.out.println("Woohoo!");
    }*/
}
