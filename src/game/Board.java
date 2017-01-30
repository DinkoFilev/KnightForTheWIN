package game;

public class Board extends Thread {
	private static Board instance;//Singleton instance
	private String[][] board; //Chessboard  3x3 where spaces that have never been occupied are marked  with "empty space"
	private int xPosition; // x-coordinate  of the chessboard
	private int yPosition; //  y-coordinate  of the chessboard
	private int movesCount; // moves count
	private int[] xMove = { 2, 0, 0, 0, 1, 1, 2, 2 };//available moves through 
	private int[] yMove = { 2, 0, 1, 2, 0, 2, 0, 1 };//combination between xMove , yMove
	private int availableMoveCounter; // counter for xMove and yMove length
										

	private Board(int xPosition, int yPosition) {
		board = new String[3][3];
		resetBoard();
		this.xPosition = xPosition - 1; // prepare positions to work only with array indexes
		this.yPosition = yPosition - 1;
		movesCount = 0;
		availableMoveCounter = 0;

	}

	/**
	 * Check if the first chosen position equal to the finish position
	 */
	private void startGame() {
		if (xPosition == 2 && yPosition == 2) {
			finishGame(); // if first position = target .Finish the game
			return;
		}
		board[this.xPosition][this.yPosition] = "K"; // mark first position with "K"
		printBoard();
		board[this.xPosition][this.yPosition] = "0";//after printing the board , remark the position.
		printStraightLine();
		moveKnight(xPosition, yPosition);//run the method who actually play the game

	}
	/**
	 * Enter point of the Thread
	 */
	@Override
	public void run() {
		startGame();

	}

	/**
	 * Singleton with parameter check
	 * 
	 * @param xPosition
	 * @param yPosition
	 * @return instance of Board only if parameters passed validation
	 * or
	 * @Throws error message and return null
	 *         
	 * 
	 */
	public static Board getInstance(int xPosition, int yPosition) {
		try {
			if (instance == null && validatePosition(xPosition, yPosition)) {
				instance = new Board(xPosition, yPosition);
			} else if (!validatePosition(xPosition, yPosition)) {

				System.err.print(
						"Invalid position value : try with x,y - position between 1 - 3, x = 2 & y = 2  is also illegal");
				return null;
			}
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}

		return instance;
	}

	/**
	 * Clear Board replace all cells with empty space
	 */
	private void resetBoard() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				board[row][col] = " ";
			}
		}

	}

	/**
	 * Recursively move the knight to a new position on the board
	 * and print the board after every move
	 * 
	 * New positions on the board where knight will try to reach but only if they are not illegal moves
	 * @param newXPosition 
	 * @param newYPosition 
	 */
	private void moveKnight(int newXPosition, int newYPosition) {
		if (availableMoveCounter == xMove.length) {
			availableMoveCounter = 0; //  reset xMove yMove counter
		}
		boolean move = isLegalMove(newXPosition, newYPosition); // check positions
		if (move && newXPosition == 2 && newYPosition == 2) { // if everything is ok and the knight reaches 
			finishGame();                                     // right hand bottom corner
			return; 										//game will finish
		}

		if (move) { //if new position is approved . Move the knight to the new position
			xPosition = newXPosition;
			yPosition = newYPosition;
			board[xPosition][yPosition] = "K";//mark the current (new) position with "K"
			movesCount++;
			printBoard();
			board[xPosition][yPosition] = String.valueOf(movesCount);//after print  , remark the position with move count
			printStraightLine();
		}

		moveKnight(xMove[availableMoveCounter], yMove[availableMoveCounter++]); // call method recursively  with available positions
	}

	/**
	 * Print Board with updated changes
	 */
	private void printBoard() {
		System.out.println("   1   2   3");

		for (int row = 0; row < board.length; row++) {

			System.out.println("  -----------");
			System.out.print(row + 1);
			for (int col = 0; col < board.length; col++) {
				System.out.print(" | ");
				System.out.print(board[row][col]);

			}

			System.out.println("|");
			System.out.println();
		}
		System.out.println("  -----------");

	}

	/** Validate X position and Y position before create an instance
	 * 
	 * @param xPosition
	 * @param yPosition
	 * @return
	 */
	private static boolean validatePosition(int xPosition, int yPosition) {
		if (xPosition <= 3 && xPosition > 0 && yPosition <= 3 && yPosition > 0 && (xPosition != 2 || yPosition != 2)) {
			return true;
		}

		return false;
	}

	/*
	 *  Check for illegal moves , moves out of range
	 */
	private boolean isLegalMove(int newXPosition, int newYPosition) {
		boolean move = board[newXPosition][newYPosition].equals(" ");
		
		if (move) {
			move = ((newXPosition == xPosition + 1 || newXPosition == xPosition - 1)
					&& (newYPosition == yPosition + 2 || newYPosition == yPosition - 2))
					|| ((newYPosition == yPosition + 1 || newYPosition == yPosition - 1)
							&& (newXPosition == xPosition + 2 || newXPosition == xPosition - 2));
		}
		
		return move;
	}

	/*
	 *  Just print one straight line with some delay managed by a thread.
	 *  Used to separate printed chessboards
	 */
	private void printStraightLine() {
		for (int i = 0; i < 40; i++) {
			try {
				Thread.sleep(35);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			System.out.print("_");
		}
		System.out.println();
	}

	
	/*
	 * When the final target is reached game is finished
	 * this method is the last station in the game
	 * moves the knight to the final target
	 * print results
	 */
	private void finishGame() {
		board[2][2] = "K";
		printBoard();
		movesCount++;
		System.out.println("CONGRATULATIONS");
		System.out.println("KNIGHT reached the destination with : " + movesCount + " moves");
		printStraightLine();

	}

}
