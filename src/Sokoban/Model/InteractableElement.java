package Sokoban.Model;

import java.io.Serializable;

/**
 * An Element that can be moved, either a player or a crate
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 03/05/18
 */
public abstract class InteractableElement extends Square implements Serializable {
    public Position position;
    protected Sokoban sokoban;
    public InteractableElement(int x, int y, Sokoban sokoban){
        this.position = new Position(x, y);
        this.sokoban=sokoban;
    }

    /**
     * Move into the given direction
     * @param direction the direction, in which the Element moves
     * @return  if the move was sucessfull. Default false, only called by Walls
     */
    public boolean move(Direction direction){
        return false;
    }

}
