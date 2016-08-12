import static org.junit.Assert.*;
import org.junit.Test;
 
public class MineSweeperTest {
 
	Game game = new Game();
 
    
    // Check if a board of 100 squares are generated.
    @Test
	public void testGame() {
    	
    	assertEquals(100, game.arr.length * game.arr[0].length);
		
	}
    
	// Test Square class method
    @Test
    public void testUpdateNumMines() {
        
    	game.arr[0][0].updateNumMines(3);
    
    	assertEquals("# of mines around square at row 0, col 0: ", 3, game.arr[0][0].numMines);
    }
    
    // Test Square class attribute
    @Test
	public void testIsMines() {
		
		System.out.println("@Test if square is mine at row 0, col 0: " + game.arr[0][0].isMine);
		assertEquals(0, game.arr[0][0].isMine);

	}

    // Test method to randomly assign 10 mines on the game board, the first square the player click
    // cannot be a mine. Here let's say the player click on square of row 4 and column 6. 
	@Test
	public void testRandomGenerator() {
		
		game.RandomGenerator(game.arr, 4, 6);
		
		int totalMines = 0;
		
		for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                totalMines += game.arr[i][j].isMine;
            }
        }
		
		assertEquals(0, game.arr[3][5].isMine); // there should be no mine at square with row 4 and column 6. 
		assertEquals(10, totalMines); // total of mines should be 10.
		
	}	

	// Test clear mines method. 
	@Test
	public void testClearMines() {
		
		game.arr[0][0].isMine = 1;
		
		game.clearMines(game.arr, 1, 1); // Should print "BOOM! You click on mine!" on the console.
				
	}

	// Test get Neighbors method. If a square is corner square, it should have 3 neighbors,
	// if it's not a corner square, but is on the boundary of game board, it should have 5 neighbors,
	// if it is in the interior of the game board, it should have 8 neighbors.
	@Test
	public void testNeighbors() {
		
		int length1 = game.neighbors(game.arr, 1, 1).size(); // corner square
		int length2 = game.neighbors(game.arr, 1, 5).size(); // square on first row
		int length3 = game.neighbors(game.arr, 4, 6).size(); // interior square
		
		assertEquals(3, length1);
		assertEquals(5, length2);
		assertEquals(8, length3);
		
	}
	
	// Test reset method in Square class, numMines is reseted to -1, 
	// isMine is reseted to 0, isExplored is reset to false.
	@Test
	public void testSquareReset()
	{
		game.arr[0][0].reset();
		
		assertEquals(-1, game.arr[0][0].numMines);
		assertEquals(0, game.arr[0][0].isMine);
		assertFalse(game.arr[0][0].isExplored);
	}
	
}
