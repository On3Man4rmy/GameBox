package Sokoban.Model;

import java.io.Serializable;

/**
 * An Element that can be moved, either a player or a crate
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 03/05/18
 */
public abstract class InteractableElement extends Square implements Serializable {
    public Position position;
    Sokoban sokoban;

    /**
     * Constructor, uses x and y to create Position object
     *
     * @param x       X Position of Element
     * @param y       Y Position of Element
     * @param sokoban Reference to game
     */
    public InteractableElement(int x, int y, Sokoban sokoban) {
        this.position = new Position(x, y);
        this.sokoban = sokoban;
    }

    /**
     * Move into the given direction
     *
     * @param direction the direction, in which the Element moves
     * @return if the move was successful. Default false, only called by Walls
     */
    public boolean move(Direction direction) {
        return false;
    }

}
