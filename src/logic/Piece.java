package logic;

/**
 * Class representing a piece on the board
 * @author Linus
 *
 */
public class Piece {
	
	private int color,positionX,positionY;
	public Piece(int color,int positionX,int positionY){
		this.color = color;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public int getColor(){
		return color;
	}
	
	public int getPosX(){
		return positionX;
	}
	
	public int getPosY(){
		return positionY;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setXPos(int x) {
		this.positionX = x;
	}
	public void setYPos(int y) {
		this.positionY = y;
	}
}
