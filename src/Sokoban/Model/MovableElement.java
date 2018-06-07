package Sokoban.Model;

import java.io.Serializable;

/**
 * An Element that can be moved, either a player or a crate
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 03/05/18
 */
public abstract class MovableElement extends Square implements Serializable {
    public Position position;

}
