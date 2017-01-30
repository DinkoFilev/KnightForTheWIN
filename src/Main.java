
import game.Board;

/**
 * 
 * Move the chess piece "KNIGHT" from any location on a "3 x 3" Chess board
 * and make it go to the far right hand bottom corner.
 * Position 3 3 is the final one before the game exits
 * 
 *            -----------
			1 | 0 | 3 |  |
		
		 	  -----------
			2 |   |   | 1|
		
		      -----------
			3 | 2 |   | K| < ---- target position
			
"0" is the start position
 *
 * * @author Dinko Filev
 */
public class Main {

	public static void main(String[] args) {
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);

		Board board = Board.getInstance(x, y);
		
		 //Board board = Board.getInstance(2,3); // for testing
		
		Thread th = new Thread(board);
		th.start();

	}
}
