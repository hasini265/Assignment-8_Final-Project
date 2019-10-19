package players;

import checkersRules.Game;

/**
 * The {@code Player} class is an abstract class that represents a player in a
 * game of checkers.
 */

public abstract class Player {
	
public abstract boolean isHuman();

    //updates the game	
	public abstract void updateGame(Game game);
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[isHuman=" + isHuman() + "]";
	}

}
