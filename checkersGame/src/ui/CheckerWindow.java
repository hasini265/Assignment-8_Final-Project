package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import players.Player;
import ui.CheckersBoard;
import ui.OptionPanel;

/*This class is a window that is used to play a game of checkers.
 It also contains a component to change the game options.*/

public class CheckerWindow extends JFrame {

private static final long serialVersionUID = 8782122389400590079L;
	
/** The default width for the checkers window. */
	public static final int DEFAULT_WIDTH = 500;
	
	/** The default height for the checkers window. */
	public static final int DEFAULT_HEIGHT = 600;
	
	/** The default title for the checkers window. */
	public static final String DEFAULT_TITLE = "Java Checkers";
	
	/** The checker board component playing the updatable game. */
	private CheckersBoard boardd;
	
	private OptionPanel opts;
	
	public CheckerWindow() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
	}
	
	public CheckerWindow(Player player1, Player player2) {
		this();
		setPlayer1(player1);
		setPlayer2(player2);
	}
	
	public CheckerWindow(int width, int height, String title) {
		
		// Setup the window
		super(title);
		super.setSize(width, height);
		super.setLocationByPlatform(true);
		
		// Setup the components
		JPanel layout = new JPanel(new BorderLayout());
		this.boardd = new CheckersBoard(this);
		this.opts = new OptionPanel(this);
		layout.add(boardd, BorderLayout.CENTER);
		layout.add(opts, BorderLayout.SOUTH);
		this.add(layout);
	}
	
	public CheckersBoard getBoardd() {
		return boardd;
	}

	// Updates the type of player that is being used for player 1.
	public void setPlayer1(Player player1) {
		this.boardd.setPlayer1(player1);
		this.boardd.update();
	}
	
	//Updates the type of player that is being used for player 2.
	public void setPlayer2(Player player2) {
		this.boardd.setPlayer2(player2);
		this.boardd.update();
	}
	
	//Resets the game of checkers in the window.
	public void restart() {
		this.boardd.getGame().restart();
		this.boardd.update();
	}
	
	public void setGameState(String state) {
		this.boardd.getGame().setGameState(state);
	}

}
