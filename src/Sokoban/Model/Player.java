package Sokoban.Model;

import java.io.Serializable;

/**
 * Represents the Player
 *@Author Tobias Fetzer 198318, Simon Stratemeier 199067
 *@Version: 1.0
 *@Date: 03/05/18
 */
public class Player extends MovableElement implements Serializable {

    public Player(int xPos, int yPos){
        position=new Position(xPos,yPos);
    }

    @Override
    public String toString() {
        return "Player";
    }
}
