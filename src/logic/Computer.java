package logic;

/**
 * Class representing the computer player, it uses alpha-beta pruning to make the best possible move.
 * 
 * @author Linus Granath
 */
import gui.BoardPanel;

import java.util.ArrayList;

public class Computer {

	private Board board;
	private Controller controller;
	private final int MAXDEPTH =12;
	private final int TIMELIMIT = 5000;
	private int bestAction = -1;
	private int depthReached = 0;
	private long searchStart = 0;
	private int nodesExamined = 0;
	
	/**
	 * Create the computer object
	 * @param controller
	 * @param board, gameboard
	 */
	public Computer(Controller controller, Board board){
		this.board = board;
		this.controller = controller;
	}
	

	
	/**
	 * Return the best possible move the computer can make
	 * @param state
	 * @return
	 */
	public int alphaBetaSearch(Piece[][] state){
		
		// Reset the depth, bestaction and nodesexamined
		depthReached = 0;
		bestAction = 0;
		nodesExamined = 0;
		
		// Retrieve start time of search 
		searchStart = System.currentTimeMillis();
		
		// Retrieve the best possible utility value
		int utility = this.maxValue(state, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		

		// Display the best action made, and the depth of the tree search
		this.controller.getConsole().addText("\nAction made [" + (this.bestAction%state.length) + "," + (this.bestAction/state.length) + "]\nUtility " + utility + "\nDepth" + depthReached
				+ "\nNodes searched: " + nodesExamined +"\n");
		
		
		return this.bestAction;
	}

	/**
	 * Calculate the best choice for the min player(non-computer)
	 * @param state, current state of the board
	 * @param depth, the depth in the tree
	 * @param alpha
	 * @param beta
	 * @param searchStart, the time the search started
	 * @return
	 */
	public int minValue(Piece[][] state, int depth, int alpha, int beta){
		
		depth++;
		nodesExamined++;
		
		if(depth>depthReached)
			depthReached = depth;
		
		// Create the piece
		Piece piece = new Piece(BoardPanel.BLACK, -1, -1);
		
		// Temporary board
		Piece[][] tempState = state;
		
		
		int utility = 0;
		
		ArrayList<Integer> availableMoves;
		// Retrieve all the avalible moves
		availableMoves = getAvalibleMoves(state);
		
		/* If the board is full, or if we've reached the depth we want, return the utility value*/
		if(availableMoves.size()<=0){
			return controller.calcUtility(state);}
		if(depth>=this.MAXDEPTH){
			return controller.calcUtility(state);
			}

		utility = Integer.MAX_VALUE;
		
		for(int i = 0; i < availableMoves.size();i++){
			
			if(System.currentTimeMillis()-searchStart>=this.TIMELIMIT)
				return utility;
			
			// Adjust the position for the piece, depending on the avalible space
			piece.setXPos(availableMoves.get(i)%state.length);
			piece.setYPos(availableMoves.get(i)/state.length);
			
			// Make a move, and change the state of the board
			tempState = controller.action(state, piece);
			
			// Calculate the utility of the best move by the player
			utility = Math.min(utility, maxValue(tempState,depth,alpha,beta));

			beta = Math.min(beta, utility);
			
			if (utility<=alpha) return utility;
		}
		
		return utility;	}
	
	/**
	 * Method retrieving the best possible utility for the computer player
	 * @param state of the baord
	 * @param depth, current depth in the tree
	 * @param alpha
	 * @param beta
	 * @param searchStart, start time of search
	 * @return
	 */
	public int maxValue(Piece[][] state, int depth,int alpha, int beta){
		depth++;
		
		nodesExamined++;
		
		if(depth>depthReached)
			depthReached = depth;
		
		int utility = 0;
		Piece piece = new Piece(BoardPanel.WHITE,-1,-1);
		Piece[][] tempState = state;
		ArrayList<Integer> freePlaces;
		freePlaces = getAvalibleMoves(tempState);
		
		if(freePlaces.size()<=0){
			
			return controller.calcUtility(state);}
		

		if(depth>=this.MAXDEPTH){
			printBoard(state);
			return controller.calcUtility(state);
		}
		
		utility = Integer.MIN_VALUE;

		for(int i = 0; i < freePlaces.size();i++){
			
			// Break off if the search takes to long
			if(System.currentTimeMillis()-searchStart>=this.TIMELIMIT)
				return utility;
				
			// Adjust the position for the piece, depending on the avalible space
			piece.setXPos(freePlaces.get(i)%state.length);
			piece.setYPos(freePlaces.get(i)/state.length);
			
			// Make a move, and change the state
			tempState = controller.action(state, piece);
			
			// Calculate the best move for the player
			int temputility = minValue(tempState,depth,alpha,beta);
			
			
			if(temputility>utility){
				
				utility = temputility;
				
				// If it's the first move, store the best possible action made.
				if(depth==1){
					bestAction = freePlaces.get(i);
					System.out.println("ACTION" + this.bestAction);
					}
				}
			alpha = Math.max(alpha, utility);
			
			if(utility>= beta){
				return utility;
			}
		}
		return utility;
	}
	
	/**
	 * Method returning all the possible moves for the current state of the game,
	 * by checking which spaces are empty
	 * @param state of the game
	 * @return
	 */
	public ArrayList<Integer> getAvalibleMoves(Piece[][] state){


		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		for(int i = 0; i < state.length;i++){
			for(int j = 0 ; j< state[i].length;j++){
				if(state[i][j].getColor() == -1)
					validMoves.add((i+ j*state.length));
			}
		}
		
		return validMoves;
	}
	

	
	/**
	 * Make the move for the computer
	 */
	public void draw(){
		int action = this.alphaBetaSearch(board.getBoard().clone());
		Piece piece = this.board.addPiece(action%board.getBoard().length, action/board.getBoard().length, BoardPanel.WHITE);

		if(piece!=null)
			controller.nextPlayer(piece);
	}
	
	// Print the board, mainly used for debugging
	private void printBoard(Piece[][] tempState) {
		for(int i = 0; i< tempState.length;i++){
			System.out.println();
			for(int j  = 0; j < tempState.length;j++){
				System.out.print(tempState[j][i].getColor()+" ,");
			}
		}
		System.out.println();
	}

	

}
