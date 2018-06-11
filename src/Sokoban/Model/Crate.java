package Sokoban.Model;

import java.io.Serializable;

/**
 * Represents a crate
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 03/05/18
 */
public class Crate extends InteractableElement implements Serializable {

    public Crate(int xPos, int yPos,Sokoban sokoban) {
        super(xPos,yPos,sokoban);
    }

    /**
     * Moves a crate in a given direction. The crate cannot move other elements
     * @param direction the direction, in which the Crate moves.
     * @return  if the move was sucessfull
     */
    @Override
    public boolean move(Direction direction){
        Position movePosition = Position.movePosition(direction, this.position);
        if(sokoban.gameBoard[movePosition.xPos][movePosition.yPos][1]==null){
            sokoban.gameBoard[position.xPos][position.yPos][1]=null;
            position=movePosition;
            sokoban.gameBoard[position.xPos][position.yPos][1]=this;
            return true;

        }
        return false;
    }
}
