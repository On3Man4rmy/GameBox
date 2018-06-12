package Sokoban.Model;

import java.io.Serializable;

/**
 * Used to save Movable objects and their position
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 21/05/18
 */
class GameStateBackup implements Serializable {
    public Square[][] movableObjectsBackup;     //position of movable objects
    public Position[][] positionBackup;         //backup of position objects

}
