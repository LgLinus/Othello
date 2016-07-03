package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import logic.Board;
import logic.Computer;
import logic.Controller;
import logic.Piece;

/**
 * The purpose of the BoardPanel class is to draw the GUI of the board
 * @author Linus
 *
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel implements MouseListener{
	
	public static final int BLACK = 0,WHITE = 1;
	private Board board;
	private int gridSizeX=640/4, gridSizeY = 640/4;
	private int xOffset = 48,yOffset = 48;
	private Controller controller;
	private Image bgimg;
	private Image white_piece_img;
	private Image black_piece_img;
	private String boardbg = "/res/othello_bg.png";
	private String white_piece = "/res/white_piece.png";
	private String black_piece = "/res/black_piece.png";
	private Computer 	computer;
	
	/**
	 * Create the boardPanel
	 * @param console 
	 */
	public BoardPanel(Console console){
		this.setPreferredSize(new Dimension(640+64+32,640+64));
		
		board = new Board();
		
		// Calculate gridSize
		gridSizeX = 640/board.getBoard().length;
		gridSizeY = 640/board.getBoard().length;
		this.controller = new Controller(console,board,this);
		
		console.setController(controller);
		computer = new Computer(controller,board);
		
		// Add mouseListener so we can check for mouse press events
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				// If player turnx
				if(controller.getPlayerTurn() == Controller.PLAYERTURN){
				int posX, posY;

				// Calculate the position of the mouse in grid
				posX = (e.getX()-xOffset)/gridSizeX;
				posY = (e.getY()-yOffset)/gridSizeY;
				
				//  Add the piece to the current position
				Piece piece = board.addPiece(posX, posY, controller.getPlayerTurn());
				
				// Change the turn to the computer
				if(piece!=null){
				controller.nextPlayer(piece);
				controller.getBoardPanel().repaint();
				computer.draw();
				}
				repaint();
				}
			}
		});

		/* Create the background and resize it to fit the entire panel */
		//bgimg = image.getScaledInstance(640, 640,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		black_piece_img = new ImageIcon(this.getClass().getResource(black_piece)).getImage(); 
		bgimg = new ImageIcon(this.getClass().getResource(boardbg)).getImage(); 
		white_piece_img = new ImageIcon(this.getClass().getResource(white_piece)).getImage();
		
		this.revalidate();
		this.repaint();
		this.setVisible(true);
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(bgimg, 0, 0, null);
		drawBoard(g2d);
		System.out.println("UPDATE");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Draw the board
	 * @param g2d
	 */
	public void drawBoard(Graphics2D g2d){
		Piece[][] pieces = board.getBoard();
		
		for(int i = 0; i < pieces.length;i++){
			for(int j = 0; j < pieces[i].length;j++){
				int color = -1;
				color = board.getPiece(i, j);
				drawPiece(g2d,i,j,color);
			}
		}
		
		g2d.setColor(Color.BLACK);
		// Draw lines
		for(int i = 0; i < pieces.length+1;i++){
			g2d.drawLine(xOffset + gridSizeX*i, yOffset, xOffset + gridSizeX*i, 684);
		}

		for(int i = 0; i < pieces.length+1;i++){
			g2d.drawLine(xOffset, yOffset + gridSizeY*i,684
					,yOffset + gridSizeY*i);
		}
	}
	
	private void drawPiece(Graphics2D g2d,int posX, int posY, int color){
		switch(color){
		case BLACK:
			g2d.setColor(Color.BLACK);
			g2d.drawImage(black_piece_img, xOffset + gridSizeX*posX + gridSizeX/2 - white_piece_img.getWidth(null)/2, yOffset +gridSizeY*posY + gridSizeY / 2 - white_piece_img.getHeight(null)/2, null);
			
			break;
		case WHITE:
			g2d.setColor(Color.WHITE);
			g2d.drawImage(white_piece_img, xOffset + gridSizeX*posX -white_piece_img.getWidth(null)/2+ gridSizeX/2, yOffset +gridSizeY*posY + gridSizeY/2 - white_piece_img.getHeight(null)/2, null);
			break;
			
		}
		
	}
	public Board getBoard(){
		return board;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int posX, posY;

		posX = e.getX()/gridSizeX;
		posY = e.getY()/gridSizeY;
		
		board.addPiece(posX, posY, 1);
		System.out.println("POSX " +posX + "\nPOSY " + posY);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int posX, posY;

		posX = e.getX()/gridSizeX;
		posY = e.getY()/gridSizeY;
		
		board.addPiece(posX, posY, 1);
		this.repaint();
		System.out.println("POSX " +posX + "\nPOSY " + posY);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
