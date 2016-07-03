package logic;

import gui.BoardPanel;
import gui.Console;

import java.util.ArrayList;

/**
 * Class that handles a lot of the logic for the game, such as simulating moves,
 * changing players and keeping track of the score
 * @author Linus
 *
 */
public class Controller {
	
	public static final int PLAYERTURN = 0, COMPUTERTURN = 1;
	private int turn = PLAYERTURN;
	private Console console;
	private Board board;
	private int utility = 0;
	private BoardPanel boardPanel;
	
	/**
	 * Create the controller object
	 * @param console
	 * @param board
	 * @param boardPanel
	 */
	public Controller(Console console,Board board, BoardPanel boardPanel){
		this.board = board;
		this.console = console;
		this.boardPanel = boardPanel;
	}

	
	/**
	 * Change player turn
	 */
	public void nextPlayer(Piece piece){
		turn+=1;
		turn%=2;
		
		switch(turn){
		case 0:
			console.addText("Player turn");
			break;
		case 1:
			console.addText("Computer turn");
			break;
		}
		long start = System.nanoTime();
		
		board.setBoard(checkConvert(board.getBoard(),piece));
		long timeElapsed = System.nanoTime() - start;
		System.out.println("TIME TAKEN TO CALC BOARD " + timeElapsed);
//		boardPanel.revalidate();
//		boardPanel.repaint();
		if(board.boardFull()){
			System.out.println("BOARD FULL");
		}
		
		boardPanel.revalidate();
			}
	
	/**
	 * Make an action, i.e place a piece on the given board
	 * @param state of the game
	 * @param piece to place
	 * @return
	 */
	public Piece[][] action(Piece[][] state, Piece piece){
		Piece[][] temp = new Piece[state.length][state.length];
		
		// create a new board 
		for (int i = state.length - 1; i >= 0; --i) {
			for(int j = state.length -1; j>=0; --j){
		    Piece p = state[i][j];
		    if (p != null) {
		        temp[i][j] = new Piece(state[i][j].getColor(),state[i][j].getPosX(),state[i][j].getPosY());
		    }
			}
		}

		temp[piece.getPosX()][piece.getPosY()].setColor(piece.getColor());
		temp = this.checkConvert(temp, piece);
		
		return temp;
	}
	
	/**
	 * Function calculating the utility of the board state
	 * @param piece board state
	 */
	public int calcUtility(Piece[][] piece){
		utility = 0;
		
		// Iterate through the board and calculate utility depedning on 
		// the amount of black and white pieces
		for(int i = 0; i < piece.length;i++){
			for(int j = 0; j  < piece[i].length;j++){
				if(piece[i][j].getColor() ==BoardPanel.BLACK)
					utility --;
				else if(piece[i][j].getColor() == BoardPanel.WHITE)
					utility ++;
			}
		}
		return utility;
	}
	

	/**
	 * Reset the board
	 */
	public void newGame() {
		board.reset();
		boardPanel.revalidate();
		boardPanel.repaint();
	}
	

	// Check for pieces converting, use the last added piece, could've been more simple..
	private Piece[][] checkConvert(Piece[][] state, Piece piece){
		Piece[][] evalState = state;
		if(piece == null)
			return evalState;

		int j = -1;
		
		
		int posX = piece.getPosX();
		int posY = piece.getPosY();
		int color = piece.getColor();
		boolean convert = false;
		
		ArrayList<Piece> piecesToConvert = new ArrayList<Piece>(); 

		// Check left of the piece
		for(int i = posX-1;i>=0;i--){
			// If the spaces is empty, break the loop
			if(evalState[i][posY].getColor()==-1){
				//System.out.println("Break");
				break;}
			else if(evalState[i][posY].getColor()!=color){
				//System.out.println("Add");
				piecesToConvert.add(evalState[i][posY]);
			}
			else if(evalState[i][posY].getColor()==color){
				convert = true;
				//System.out.println("Convert");
				break;
			}
		}
		if(convert){
		convertPieces(piecesToConvert,color);
		}


		
		piecesToConvert = new ArrayList<Piece>(); 
		convert = false;
		
		// Check to the right of the piece
		for(int i = posX+1;i<evalState.length;i++){
			if(evalState[i][posY].getColor()==-1){
				//System.out.println("Break");
				break;}
			else if(evalState[i][posY].getColor()!=color){
				//System.out.println("Add");
				piecesToConvert.add(evalState[i][posY]);
			}
			else if(evalState[i][posY].getColor()==color){
				convert = true;
				//System.out.println("Convert");
				break;
			}
		}
		if(convert){
			convertPieces(piecesToConvert,color);
			}
		piecesToConvert = new ArrayList<Piece>(); 
		convert = false;
		
		// Check to the north of the piece
		for(int i = posY-1;i>=0;i--){
			if(evalState[posX][i].getColor()==-1){
				//System.out.println("Break");
				break;}
			else if(evalState[posX][i].getColor()!=color){
				//System.out.println("Add");
				piecesToConvert.add(evalState[posX][i]);
			}
			else if(evalState[posX][i].getColor()==color){
				convert = true;
				//System.out.println("Convert");
				break;
			}
		}
		if(convert){
			convertPieces(piecesToConvert,color);
			}
		piecesToConvert = new ArrayList<Piece>(); 
		convert = false;
		
		// Check to the south of the piece
		for(int i = posY+1;i<evalState.length;i++){
			if(evalState[posX][i].getColor()==-1){
				//System.out.println("Break");
				break;}
			else if(evalState[posX][i].getColor()!=color){
				//System.out.println("Add");
				piecesToConvert.add(evalState[posX][i]);
			}
			else if(evalState[posX][i].getColor()==color){
				convert = true;
				//System.out.println("Convert");
				break;
			}
		}
		if(convert){
			convertPieces(piecesToConvert,color);
			}

		piecesToConvert = new ArrayList<Piece>(); 
		convert = false;
		j = -1;
		
		// Check to the north-west of the piece
		for(int i = posY-1;i>=0;i--){
			//System.out.println("POSXteno " + (posX+j) + " upleft");
			if((posX+j)<0)
				break;
			if(evalState[posX+j][i].getColor()==-1){
				//System.out.println("Break");
				break;}
			else if(evalState[posX+j][i].getColor()!=color){
				//System.out.println("Add");
				piecesToConvert.add(evalState[posX+j][i]);
			}
			else if(evalState[posX+j][i].getColor()==color){
				convert = true;
				//System.out.println("Convertupleft");
				break;
			}
			j--;
		}
		if(convert){
			convertPieces(piecesToConvert,color);
			}
		
		piecesToConvert = new ArrayList<Piece>(); 
		convert = false;
		j = -1;
		
		// Check to the south west of the piece
		for(int i = posY+1;i<state.length;i++){
			//System.out.println("POSXteno " + (posX+j) + " downleft");
			if((posX+j)<0)
				break;
			if(evalState[posX+j][i].getColor()==-1){
				//System.out.println("Break");
				break;}
			else if(evalState[posX+j][i].getColor()!=color){
				//System.out.println("Add");
				piecesToConvert.add(evalState[posX+j][i]);
			}
			else if(evalState[posX+j][i].getColor()==color){
				convert = true;
				//System.out.println("Convertupleft");
				break;
			}
			j--;
		}
		if(convert){
			convertPieces(piecesToConvert,color);
			}

		piecesToConvert = new ArrayList<Piece>(); 
		convert = false;
		j = 1;
		
		// Check to the south right of the piece
		for(int i = posY+1;i<state.length;i++){
			//System.out.println("POSXteno " + (posX+j) + " downright");
			if((posX+j)>=state.length)
				break;
			if(evalState[posX+j][i].getColor()==-1){
				//System.out.println("Break");
				break;}
			else if(evalState[posX+j][i].getColor()!=color){
				//System.out.println("Add");
				piecesToConvert.add(evalState[posX+j][i]);
			}
			else if(evalState[posX+j][i].getColor()==color){
				convert = true;
				//System.out.println("Convertupleft");
				break;
			}
			j++;
		}
		if(convert){
			convertPieces(piecesToConvert,color);
			}

		piecesToConvert = new ArrayList<Piece>(); 
		convert = false;
		j = 1;
		
		// Check to the north right of the piece
		for(int i = posY-1;i>=0;i--){
			//System.out.println("POSXteno " + (posX+j) + " upright");
			if((posX+j)>=state.length)
				break;
			if(evalState[posX+j][i].getColor()==-1){
				//System.out.println("Break");
				break;}
			else if(evalState[posX+j][i].getColor()!=color){
				//System.out.println("Add");
				piecesToConvert.add(evalState[posX+j][i]);
			}
			else if(evalState[posX+j][i].getColor()==color){
				convert = true;
				break;
			}
			j++;
		}
		if(convert){
			convertPieces(piecesToConvert,color);
			}
			
		
		if(board.boardFull())
			displayWinner();
		
		// Return the new state of the board
		return evalState;
	}

	
	// Display the winner of the game
	private void displayWinner(){
		int score=0;
		
		// Iterate through the board and calculate the score by taking 
		// the difference of the amount of black and white pieces
		for(int i = 0; i < board.getBoard().length;i++){
			for(int j = 0; j < board.getBoard()[i].length;j++){
				if(board.getBoard()[i][j].getColor()==BoardPanel.BLACK)
					score++;
				else if(board.getBoard()[i][j].getColor()==BoardPanel.WHITE)
					score--;
			}
		}
		if(score<0)
			console.addText("White player won");
		else if(score>0)
			console.addText("Black player won");
		else
			console.addText("It's a draw!");
	}
	
	// Method converting all of the pieces in the arraylist to the given color
	private void convertPieces(ArrayList<Piece> piecesToConvert, int color) {
		for(int i = 0; i<piecesToConvert.size();i++){
			piecesToConvert.get(i).setColor(color);
		}
	}
	
	
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}
	
	public Console getConsole(){
		return console;
	}
	
	public int getPlayerTurn(){
		return turn;
	}
	
}
