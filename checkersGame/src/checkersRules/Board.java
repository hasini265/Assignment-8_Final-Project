package checkersRules;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import checkersRules.Board;

/* The {@code Board} class represents a game state for checkers. A standard
 * checker board is 8 x 8 (64) tiles, alternating white/black. Checkers are
 * only allowed on black tiles and can therefore only move diagonally. */

public class Board {
	
	/** An ID indicating a point was not on the checker board. */
	public static final int INVALID = -1;
	
	/** The ID of an empty checker board tile. */
	public static final int EMPTY = 0;
	
	/** The ID of a white checker in the checker board. */
	public static final int BLACK_CHECKER = 4 * 1 + 2 * 1 + 1 * 0;
	
	/** The ID of a white checker in the checker board. */
	public static final int WHITE_CHECKER = 4 * 1 + 2 * 0 + 1 * 0;
	
	/** The ID of a black checker that is also a king. */
	public static final int BLACK_KING = 4 * 1 + 2 * 1 + 1 * 1;
	
	/** The ID of a white checker that is also a king. */
	public static final int WHITE_KING = 4 * 1 + 2 * 0 + 1 * 1;
	
	/** represents the current state of the board*/
	
	private int[] state;
	
	public Board() {
		reset();
	}
	
	// a copy of this checker board is made.
	public Board copy() {
		Board copy = new Board();
		copy.state = state.clone();
		return copy;
	}
	
	public void reset() {
		
		// Reset the state
		this.state = new int[3];
		for (int i = 0; i < 12; i ++) {
			set(i, BLACK_CHECKER);
			set(31 - i, WHITE_CHECKER);
		}
	}
	
	public List<Point> find(int id) {
		
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < 32; i ++) {
			if (get(i) == id) {
				points.add(toPoint(i));
			}
		}
		
		return points;
	}
	
	public void set(int x, int y, int id) {
		set(toIndex(x, y), id);
	}
	
	public void set(int index, int id) {
		
		// Out of range
		if (!isValidIndex(index)) {
			return;
		}
		
		// Set to EMPTY if its Invalid ID
		if (id < 0) {
			id = EMPTY;
		}
		
		for (int i = 0; i < state.length; i ++) {
			boolean set = ((1 << (state.length - i - 1)) & id) != 0;
			this.state[i] = setBit(state[i], index, set);
		}
	}
	
	public int get(int x, int y) {
		return get(toIndex(x, y));
	}
	
	public int get(int index) {
		if (!isValidIndex(index)) {
			return INVALID;
		}
		return getBit(state[0], index) * 4 + getBit(state[1], index) * 2
				+ getBit(state[2], index);
	}
	
	public static Point toPoint(int index) {
		int y = index / 4;
		int x = 2 * (index % 4) + (y + 1) % 2;
		return !isValidIndex(index)? new Point(-1, -1) : new Point(x, y);
	}
	
	public static int toIndex(int x, int y) {
		
		if (!isValidPoint(new Point(x, y))) {
			return -1;
		}
		
		return y * 4 + x / 2;
	}
	
	public static int toIndex(Point p) {
		return (p == null)? -1 : toIndex(p.x, p.y);
	}
	
	/**
	 * Sets or clears the specified bit in the target value and returns
	 * the updated value.*/
	
	public static int setBit(int target, int bit, boolean set) {
		
		if (bit < 0 || bit > 31) {
			return target;
		}
		
		// Set the bit
		if (set) {
			target |= (1 << bit);
		}
		
		else {
			target &= (~(1 << bit));
		}
		
		return target;
	}
	
	// Gets the state of a bit and determines if it is set (1) or not (0).
	
	public static int getBit(int target, int bit) {
		
		if (bit < 0 || bit > 31) {
			return 0;
		}
		
		return (target & (1 << bit)) != 0? 1 : 0;
	}
	
	public static Point middle(Point p1, Point p2) {
	
		// A point isn't initialized
		if (p1 == null || p2 == null) {
			return new Point(-1, -1);
		}
		
		return middle(p1.x, p1.y, p2.x, p2.y);
	}
	
	public static Point middle(int index1, int index2) {
		return middle(toPoint(index1), toPoint(index2));
	}
	
	public static Point middle(int x1, int y1, int x2, int y2) {
		
		// Check coordinates
		int dx = x2 - x1, dy = y2 - y1;
		if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || 
				x1 > 7 || y1 > 7 || x2 > 7 || y2 > 7) {
			return new Point(-1, -1);
		} else if (x1 % 2 == y1 % 2 || x2 % 2 == y2 % 2) { 
			return new Point(-1, -1);
		} else if (Math.abs(dx) != Math.abs(dy) || Math.abs(dx) != 2) {
			return new Point(-1, -1);
		}
		
		return new Point(x1 + dx / 2, y1 + dy / 2);
	}
	
	public static boolean isValidIndex(int testIndex) {
		return testIndex >= 0 && testIndex < 32;
	}
	
	public static boolean isValidPoint(Point testPoint) {
		
		if (testPoint == null) {
			return false;
		}
		
		final int x = testPoint.x, y = testPoint.y;
		if (x < 0 || x > 7 || y < 0 || y > 7) {
			return false;
		}
		
		if (x % 2 == y % 2) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		String obj = getClass().getName() + "[";
		for (int i = 0; i < 31; i ++) {
			obj += get(i) + ", ";
		}
		obj += get(31);
		
		return obj + "]";
	}

}
