package Sokoban.Model;

import java.io.Serializable;

/**
 * Represents a crate
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 03/05/18
 */
public class Crate extends InteractableElement implements Serializable {
    /**
     * Constructor, calls constructor of InteractableElement
     *
     * @param xPos    X Position of Crate
     * @param yPos    Y Position of Crate
     * @param sokoban Reference to the game
     */
    public Crate(int xPos, int yPos, Sokoban sokoban) {
        super(xPos, yPos, sokoban);
    }

    /**
     * Moves a crate in a given direction. The crate cannot move other elements
     *
     * @param direction the direction, in which the Crate moves.
     * @return if the move was successful
     */
    @Override
    public boolean move(Direction direction) {
        Position movePosition = Position.movePosition(direction, this.position);
        if (sokoban.gameBoard[movePosition.getxPos()][movePosition.getyPos()][1] == null) {
            sokoban.gameBoard[position.getxPos()][position.getyPos()][1] = null;
            position = movePosition;
            sokoban.gameBoard[position.getxPos()][position.getyPos()][1] = this;
            return true;

        }
        return false;
    }
}
