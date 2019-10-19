package players;

import checkersRules.Game;

/**
 * The {@code HumanPlayer} class represents a user of the checkers game that
 * can update the game by clicking on tiles on the board.
 */

public class HumanPlayer extends Player {
	
	@Override
	public boolean isHuman() {
		return true;
	}

	@Override
	public void updateGame(Game game) {
		
	}

}
