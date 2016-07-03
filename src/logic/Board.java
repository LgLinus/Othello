package logic;


/**
 * Class representing the othello board
 * @author Linus
 *
 */
public class Board {

	// Keeps track of all pieces on the board
	private Piece[][] board;
	
	/**
	 * Create the board
	 */
	public Board(){
		createBoard();
	}
	
	/**
	 * Create the gameboard
	 */
	public void createBoard(){
		board = new Piece[4][4];

		for(int i = 0; i < board.length;i++){
			for(int j = 0; j < board.length;j++){
				board[i][j] = new Piece(-1,i,j);
			}
		}
		
	}
	
	/**
	 * Add a piece at the given position
	 * @param posX
	 * @param posY
	 * @param color
	 */
	public Piece addPiece(int posX, int posY,int color){
		Piece piece = null;
		// Make sure board is empty
		if(board[posX][posY].getColor()==-1){
			board[posX][posY] = new Piece(color,posX,posY);
			piece = board[posX][posY];
		}
		return piece;
	}
	
	/**
	 * Return the piece type on the position
	 * @param posX position in width
	 * @param posY position in height
	 * @return color, -1 is empty, 0 is white and 1 is black
	 */
	public int getPiece(int posX, int posY){
		return board[posX][posY].getColor();
	}
	
	/**
	 * Return all the pieces as an array
	 * @return
	 */
	public Piece[][] getBoard(){
		return board;
	}
	
	/**
	 * Method checking if the board is full
	 * @return
	 */
	public boolean boardFull(){
		boolean full = true;
		for(int i = 0; i < board.length;i++){
			for(int j = 0; j < board[i].length;j++){
				if(board[i][j].getColor()==-1){
					full = false;
					break;
					}
			}
		}
		return full;
	}
	
	/**
	 * Set the board to the given board, used for resetting the game
	 * @param board, new board
	 */
	public void setBoard(Piece[][] board){
		this.board = board;
	}

	/**
	 * Reset the board
	 */
	public void reset() {
		createBoard();
	}

	
}
