/*This class is the graphical user interface representation of
 * a checkers game. It is responsible for drawing the checker board and
 * allowing moves to be made.*/

package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;
import javax.swing.JButton;

import checkersRules.MoveGenerate;
import checkersRules.Board;
import checkersRules.Game;
import players.HumanPlayer;
import players.Player;
import ui.CheckerWindow;
import ui.CheckersBoard;

public class CheckersBoard extends JButton {
	
private static final long serialVersionUID = -6014690893709316364L;
	
/** The amount of milliseconds before a computer player takes a move. */
	private static final int TIMER_DELAY = 1000;
	
	/** The number of pixels of padding between this component's border and the
	 * actual checker board that is drawn. */
	private static final int PADDING = 16;

	/** The game of checkers that is being played on this component. */
	private Game game;
	
	/** The window containing this checker board UI component. */
	private CheckerWindow window;
	
	/** The player in control of the black checkers. */
	private Player player1;
	
	/** The player in control of the white checkers. */
	private Player player2;
	
	/** The last point that the current player selected on the checker board. */
	private Point selected;
	
	 /**If the selection
	 * is valid, a green colour is used to highlight the tile. Otherwise, a red
	 * colour is used. */
	private boolean selectionValid;
	
	private Color lightTile;

	private Color darkTile;
	
	/** A convenience flag to check if the game is over. */
	private boolean isGameOver;

	/** The timer to control how fast a computer player makes a move. */
	private Timer timer;
	
	public CheckersBoard(CheckerWindow window) {
		this(window, new Game(), null, null);
	}
	
	public CheckersBoard(CheckerWindow window, Game game,
			Player player1, Player player2) {
		
		// Setup the component
		super.setBorderPainted(false);
		super.setFocusPainted(false);
		super.setContentAreaFilled(false);
		super.setBackground(Color.BLACK);
		this.addActionListener(new ClickListener());
		
		// Setup the game
		this.game = (game == null)? new Game() : game;
		this.lightTile = Color.WHITE;
		this.darkTile = Color.BLUE;
		this.window = window;
		setPlayer1(player1);
		setPlayer2(player2);
	}
	
	// Checks if the game is over and redraws the component graphics.
	public void update() {
		runPlayer();
		this.isGameOver = game.isGameOver();
		repaint();
	}
	
	private void runPlayer() {
		
		Player players = getCurrentPlayer();
		if (players == null || players.isHuman())
			return;
	
		// Set a timer to run
		this.timer = new Timer(TIMER_DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getCurrentPlayer().updateGame(game);
				timer.stop();
				update();
			}
		});
		this.timer.start();
	}
	
	public synchronized boolean setGameState(boolean testValue,
			String newState, String expected) {
		
		if (testValue && !game.getGameState().equals(expected)) {
			return false;
		}
		
		this.game.setGameState(newState);
		repaint();
		
		return true;
	}
	
	// Draws the current checkers game state.
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Game game = this.game.copy();
		
		// Perform calculations
		final int BOX_PADDING = 4;
		final int W = getWidth(), H = getHeight();
		final int DIM = W < H? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
		final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
		final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
		final int CHECKER_SIZE = Math.max(0, BOX_SIZE - 2 * BOX_PADDING);
		
		// Draw checker board
		g.setColor(Color.BLACK);
		g.drawRect(OFFSET_X - 1, OFFSET_Y - 1, BOX_SIZE * 8 + 1, BOX_SIZE * 8 + 1);
		g.setColor(lightTile);
		g.fillRect(OFFSET_X, OFFSET_Y, BOX_SIZE * 8, BOX_SIZE * 8);
		g.setColor(darkTile);
		for (int y = 0; y < 8; y ++) {
			for (int x = (y + 1) % 2; x < 8; x += 2) {
				g.fillRect(OFFSET_X + x * BOX_SIZE, OFFSET_Y + y * BOX_SIZE,
						BOX_SIZE, BOX_SIZE);
			}
		}
		
		// Highlight the selected tile if valid
		if (Board.isValidPoint(selected)) {
			g.setColor(selectionValid? Color.GREEN : Color.RED);
			g.fillRect(OFFSET_X + selected.x * BOX_SIZE,
					OFFSET_Y + selected.y * BOX_SIZE,
					BOX_SIZE, BOX_SIZE);
		}
		
		// Draw the checkers
		Board b = game.getBoard();
		for (int y = 0; y < 8; y ++) {
			int cy = OFFSET_Y + y * BOX_SIZE + BOX_PADDING;
			for (int x = (y + 1) % 2; x < 8; x += 2) {
				int id = b.get(x, y);
				
				if (id == Board.EMPTY) {
					continue;
				}
				
				int cx = OFFSET_X + x * BOX_SIZE + BOX_PADDING;
				
				if (id == Board.BLACK_CHECKER) {
					g.setColor(Color.DARK_GRAY);
					g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.LIGHT_GRAY);
					g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.GRAY);
					g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.LIGHT_GRAY);
					g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
				}
				
				else if (id == Board.BLACK_KING) {
					g.setColor(Color.DARK_GRAY);
					g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.LIGHT_GRAY);
					g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.DARK_GRAY);
					g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.LIGHT_GRAY);
					g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.BLACK);
					g.fillOval(cx - 1, cy - 2, CHECKER_SIZE, CHECKER_SIZE);
				}
				
				else if (id == Board.WHITE_CHECKER) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.DARK_GRAY);
					g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.PINK);
					g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.DARK_GRAY);
					g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
				}
				
				else if (id == Board.WHITE_KING) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.DARK_GRAY);
					g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.LIGHT_GRAY);
					g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.DARK_GRAY);
					g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
					g.setColor(Color.RED);
					g.fillOval(cx - 1, cy - 2, CHECKER_SIZE, CHECKER_SIZE);
				}
				
				// Any king (add some extra highlights)
				if (id == Board.BLACK_KING || id == Board.WHITE_KING) {
					g.setColor(new Color(255, 240,0));
					g.drawOval(cx - 1, cy - 2, CHECKER_SIZE, CHECKER_SIZE);
					g.drawOval(cx + 1, cy, CHECKER_SIZE - 4, CHECKER_SIZE - 4);
				}
			}
		}
		
		// Draw the player turn sign
		String msg = game.isP1Turn()? "Player 1 turn" : "Player 2 turn";
		int width = g.getFontMetrics().stringWidth(msg);
		Color back = game.isP1Turn()? Color.BLACK : Color.BLACK;
		Color front = game.isP1Turn()? Color.WHITE : Color.WHITE;
		g.setColor(back);
		g.fillRect(W / 2 - width / 2 - 5, OFFSET_Y + 8 * BOX_SIZE + 2,
				width + 10, 15);
		g.setColor(front);
		g.drawString(msg, W / 2 - width / 2, OFFSET_Y + 8 * BOX_SIZE + 2 + 11);
		
		// Draw a game over sign
		if (isGameOver) {
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			msg = "GAME OVER";
			width = g.getFontMetrics().stringWidth(msg);
			g.setColor(new Color(240, 240, 255));
			g.fillRoundRect(W / 2 - width / 2 - 5,
					OFFSET_Y + BOX_SIZE * 4 - 16,
					width + 10, 30, 10, 10);
			g.setColor(Color.RED);
			g.drawString(msg, W / 2 - width / 2, OFFSET_Y + BOX_SIZE * 4 + 7);
		}
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = (game == null)? new Game() : game;
	}

	public CheckerWindow getWindow() {
		return window;
	}

	public void setWindow(CheckerWindow window) {
		this.window = window;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = (player1 == null)? new HumanPlayer() : player1;
		if (game.isP1Turn() && !this.player1.isHuman()) {
			this.selected = null;
		}
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = (player2 == null)? new HumanPlayer() : player2;
		if (!game.isP1Turn() && !this.player2.isHuman()) {
			this.selected = null;
		}
	}
	
	public Player getCurrentPlayer() {
		return game.isP1Turn()? player1 : player2;
	}

	public Color getLightTile() {
		return lightTile;
	}

	public void setLightTile(Color lightTile) {
		this.lightTile = (lightTile == null)? Color.WHITE : lightTile;
	}

	public Color getDarkTile() {
		return darkTile;
	}

	public void setDarkTile(Color darkTile) {
		this.darkTile = (darkTile == null)? Color.BLUE : darkTile;
	}

	private void handleClick(int x, int y) {
		
		// The game is over or the current player isn't human
		if (isGameOver || !getCurrentPlayer().isHuman()) {
			return;
		}
		
		Game copy = game.copy();
		
		// Determine what square (if any) was selected
		final int W = getWidth(), H = getHeight();
		final int DIM = W < H? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
		final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
		final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
		x = (x - OFFSET_X) / BOX_SIZE;
		y = (y - OFFSET_Y) / BOX_SIZE;
		Point sel = new Point(x, y);
		
		if (Board.isValidPoint(sel) && Board.isValidPoint(selected)) {
			boolean change = copy.isP1Turn();
			String expected = copy.getGameState();
			boolean move = copy.move(selected, sel);
			boolean updated = (move?
					setGameState(true, copy.getGameState(), expected) : false);
			change = (copy.isP1Turn() != change);
			this.selected = change? null : sel;
		} else {
			this.selected = sel;
		}
		
		// Check if the selection is valid
		this.selectionValid = isValidSelection(
				copy.getBoard(), copy.isP1Turn(), selected);
		
		update();
	}
	
	/**
	 * Checks if a selected point is valid in the context of the current
	 * player's turn.*/
	
	private boolean isValidSelection(Board b, boolean isP1Turn, Point selected) {

		int i = Board.toIndex(selected), id = b.get(i);
		if (id == Board.EMPTY || id == Board.INVALID) { 
			return false;
		} else if(isP1Turn ^ (id == Board.BLACK_CHECKER ||
				id == Board.BLACK_KING)) { 
			return false;
		} else if (!MoveGenerate.getSkips(b, i).isEmpty()) {
			return true;
		} else if (MoveGenerate.getMoves(b, i).isEmpty()) {
			return false;
		}
		
		// Determine if there is a skip available for another checker
		List<Point> points = b.find(
				isP1Turn? Board.BLACK_CHECKER : Board.WHITE_CHECKER);
		points.addAll(b.find(
				isP1Turn? Board.BLACK_KING : Board.WHITE_KING));
		for (Point p : points) {
			int checker = Board.toIndex(p);
			if (checker == i) {
				continue;
			}
			if (!MoveGenerate.getSkips(b, checker).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	private class ClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Get the new mouse coordinates and handle the click
			Point m = CheckersBoard.this.getMousePosition();
			if (m != null) {
				handleClick(m.x, m.y);
			}
		}
	}


}
