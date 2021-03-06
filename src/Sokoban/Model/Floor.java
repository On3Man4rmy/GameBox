package Sokoban.Model;

import java.io.Serializable;

/**
 * An unmovable Floor, can either be a goal or empty
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 03/05/18
 */
public class Floor extends Square implements Serializable {
    public boolean goal = false;

    /**
     * Constructor, sets goal to true if element is  a goal
     *
     * @param boardElement which kind of boardElement it is supposed to be
     */
    public Floor(BoardElement boardElement) {
        switch (boardElement) {
            case GOAL:
                goal = true;
                break;
            case EMPTY:
                break;
        }

    }

}
