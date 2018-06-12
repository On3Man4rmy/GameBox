package Sokoban.Model;

import java.io.Serializable;

/**
 * Represents the Player
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 03/05/18
 */
public class Player extends InteractableElement implements Serializable {

    /**
     * Constructor, calls constructor of InteractableElement
     *
     * @param xPos    X Position of Player
     * @param yPos    Y Position of Player
     * @param sokoban Reference to the game
     */
    public Player(int xPos, int yPos, Sokoban sokoban) {
        super(xPos, yPos, sokoban);
    }

    /**
     * Moves a Player in a direction. If it encounters another InteractableElement there, it tries to move it
     *
     * @param direction the direction, in which the Player moves
     * @return if the move was successful
     */
    @Override
    public boolean move(Direction direction) {
        Position movePosition = Position.movePosition(direction, this.position);
        InteractableElement element = (InteractableElement) sokoban.gameBoard[movePosition.xPos][movePosition.yPos][1];
        if (element == null || element.move(direction)) {
            sokoban.gameBoard[position.xPos][position.yPos][1] = null;
            position = movePosition;
            sokoban.gameBoard[position.xPos][position.yPos][1] = this;
            return true;

        }
        return false;
    }

}
