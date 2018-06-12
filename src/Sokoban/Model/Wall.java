package Sokoban.Model;

import java.io.Serializable;

/**
 * Represents the wall/obstacle
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 03/05/18
 */
public class Wall extends InteractableElement implements Serializable {
    /**
     * Constructor, calls constructor of InteractableElement
     *
     * @param xPos    X Position of Wall
     * @param yPos    Y Position of Wall
     * @param sokoban Reference to the game
     */
    public Wall(int xPos, int yPos, Sokoban sokoban) {
        super(xPos, yPos, sokoban);
    }

}