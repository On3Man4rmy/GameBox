package Sokoban.Model;

import java.io.Serializable;

/**
 * Represents the Player
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 03/05/18
 */
public class Player extends InteractableElement implements Serializable {


    public Player(int xPos, int yPos,Sokoban sokoban) {
        super(xPos,yPos,sokoban);
    }

    /**
     * Moves a Player in a direction. If it encounters another InteractableElement there, it tries to move it
     * @param direction the direction, in which the Player moves
     * @return if the move was sucessful
     */
    @Override
    public boolean move(Direction direction){
        Position movePosition = Position.movePosition(direction, this.position);
        InteractableElement element=(InteractableElement)sokoban.gameBoard[movePosition.xPos][movePosition.yPos][1];
        if(element==null||element.move(direction)){
            sokoban.gameBoard[position.xPos][position.yPos][1]=null;
            position=movePosition;
            sokoban.gameBoard[position.xPos][position.yPos][1]=this;
            return true;

        }
        return false;
    }

}
