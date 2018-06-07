package Sokoban.Model;

import java.io.Serializable;

/**
 * Represents a crate
 *@Author Tobias Fetzer 198318, Simon Stratemeier 199067
 *@Version: 1.0
 *@Date: 03/05/18

 */
public class Crate extends MovableElement implements Serializable {
    public Crate(int xPos, int yPos){
        position=new Position(xPos,yPos);
    }

}
