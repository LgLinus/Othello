package gui;

import java.awt.FlowLayout;

import javax.swing.JFrame;

/**
 * Class that creates the window(frame) of the game
 * @author Linus
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	public MainFrame(){
		
		this.setSize(640+256+128, 640+128);
		this.setTitle("Othello");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Console console = new Console();
		
		this.setLayout(new FlowLayout());
		this.add(new BoardPanel(console));
		this.add(console);
		this.setVisible(true);
	}
}
