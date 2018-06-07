package Sokoban.Model;

import java.io.Serializable;

/**
 * Saves the position of a movable Element
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 03/05/18
 */
public class Position implements Serializable {
    int xPos;
    int yPos;

    public Position(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Copy constructor
     *
     * @param position Position object that is copied
     */
    public Position(Position position) {
        xPos = position.xPos;
        yPos = position.yPos;
    }

    /**
     * returns position after a move is performed
     *
     * @param direction direction of movement
     * @param position  position which is moved
     * @return new position
     */
    public static Position movePosition(Direction direction, Position position) {
        Position returnPosition;
        switch (direction) {
            case UP:
                returnPosition = new Position(position.xPos, position.yPos - 1);
                break;
            case DOWN:
                returnPosition = new Position(position.xPos, position.yPos + 1);
                break;
            case LEFT:
                returnPosition = new Position(position.xPos - 1, position.yPos);
                break;
            case RIGHT:
                returnPosition = new Position(position.xPos + 1, position.yPos);
                break;
            default:
                returnPosition = null;
        }
        return returnPosition;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position pos = (Position) obj;
            if (pos.xPos == xPos && pos.yPos == yPos)
                return true;
        }
        return false;

    }
}
