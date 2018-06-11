package Sokoban.Model;

import java.io.Serializable;

/**
 * Represents the wall/obstacle
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 03/05/18
 */
public class Wall extends InteractableElement implements Serializable {

    public Wall(int xPos, int yPos,Sokoban sokoban) {
        super(xPos,yPos,sokoban);
    }

}