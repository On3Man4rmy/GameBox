package Sokoban.Model;

import java.io.Serializable;

/**
 * An unmovable Floor, can either be a goal or empty
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 03/05/18
 */
public class Floor extends Square implements Serializable {
    public boolean goal = false;

    /**
     * Construcor, sets goal to true if element is  a goal
     *
     * @param floorElement which kind of floorElement it is supposed to be
     */
    public Floor(FloorElement floorElement) {
        switch (floorElement) {
            case GOAL:
                goal = true;
                break;
            case EMPTY:
                break;
        }

    }

}
