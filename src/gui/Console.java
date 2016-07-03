package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import logic.Controller;

/**
 * Class consisting of a Jpanel, JTextArea and a JButton. The purpose of the class is to
 * display important information, such as amount of nodes searched and the depth searched.
 * 
 * @author Linus
 *
 */
@SuppressWarnings("serial")
public class Console extends JPanel{

	private JTextArea ta;
	private JButton btnRestart;
	private Controller controller;
	
	public Console(){
		this.setPreferredSize(new Dimension(256,640+64));
		this.setBackground(Color.RED);
		setBorder(null);
		
		ta = new JTextArea("Welcome to othello!\n In order to start, select which "
				+ "square you want to place your piece");
		ta.setPreferredSize(new Dimension(256,640));
		ta.setLineWrap(true);
		this.add(ta);
		
		btnRestart = new JButton("Restart");
		// Add button to restart the game
		btnRestart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.newGame();
				addText("New game\n\n\n");
			}
			
		});
		btnRestart.setPreferredSize(new Dimension(256,64));
		btnRestart.setLocation(0, 640);
		
		this.add(btnRestart);
	}
	
	
	public void setController(Controller controller){
		this.controller = controller;
	}
	
	/**
	 * Add the text to the JTextArea
	 * @param txt to add
	 */
	public void addText(String txt){
		ta.setText(txt + "\n" + ta.getText());
	}
	


}
