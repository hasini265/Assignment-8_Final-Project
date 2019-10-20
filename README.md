# Assignment-8_Final-Project
Checkers game done in Java for my college assignment.

Description :

Checkers Game is played between two people on an 8*8 checked board. Each player has 12 pieces that are like flat round 
disks that fit inside of each of the boxes on the board.This game is played between 2 players , so one player has the 
dark pieces and the other takes light pieces.That means after a player makes his/her move, the next move goes to another
player.At the end the player without pieces remaining, or who vannot move due to being blocked, loses the game.
2 types of players are present in this game :
All player classes extend the abstract Player class and either implement the logic to update the game or allow the user 
to input their moves.
Human Player : For Human players, this means interacting with and selecting tileson the checker board. It performs no
                  updates on the game as human player can interact with the user interface to update the game.
Computer Player : Computer player can update the game state with out user intercation by determining the weight of a 
                  move based on a number of factors like how safe checker is before/after, whether it can take an
                  opponent's checker after,etc..

Run :

Run with java ui.Main                                                                                              

main : This class contains the main method to create the GUI and start the checkers game.This program is a simple 
       implementation of the standard checkers game, with standard rules in java.
 
Rules :

Below mentioned are the standard checkers rules that are implemented in the game :
1. Checkers can only move diagonally, on dark tiles. 
2. Normal checkers can only move forward diagonally (for black checkers,
   this is down and for white checkers, this is up). 
3. A checker becomes a king when it reaches the opponents end and cannot
   move forward anymore. 
4. Once a checker becomes a king, the player's turn is over. 
5. After a checker/king moves one space diagonally, the player's turn is over.
6. If an opponent's checker/king can be skipped, it must be skipped. 
7. If after a skip, the same checker can skip again, it must. Otherwise, the turn is over.
8. The game is over if a player either has no more checkers or cannot make a move on their turn.
9. The player with the black checkers moves first.
















