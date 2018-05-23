package Sokoban.Model;

import java.io.Serializable;

/**
 * An unmovable Floor, can either be a goal or empty
 *@Author Tobias Fetzer 198318, Simon Stratemeier 199067
 *@Version: 1.0
 *@Date: 03/05/18
 */
public class Floor extends Square implements Serializable {
    public boolean goal=false;

    public Floor(FloorElement floorElement){
        switch (floorElement){
            case GOAL: goal=true;
            break;
            case EMPTY: break;
        }

    }

    @Override
    public String toString() {
        return "Floor";
    }
}
