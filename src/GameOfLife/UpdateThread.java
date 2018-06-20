package GameOfLife;

/**
 * Thread class, used to update the game
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 27/04/18
 */
public class UpdateThread extends Thread {
    private int speed = 100;        //wait between update in milliseconds
    private GameOfLife game;        //reference to the game it's connected to
    private boolean isDone=false;

    /**
     * Constructor
     *
     * @param game the game updated by the thread
     */
    public UpdateThread(GameOfLife game) {
        this.game = game;
    }

    /**
     * Run method, if game isn't done and is running, updates the game with in interval set by variable  "speed"
     */
    @Override
    public void run() {
        while (!isDone) {
            if (game.isRun) {
                game.updateField();
            }
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the speed in which the thread updates the game
     *
     * @param speed the speed (integer), in milliseconds between updates
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * returns the speed
     *
     * @return the speed, an integer, in milliseconds between updates
     */
    public int getSpeed() {
        return speed;
    }


    /**
     * Sets the boolean isDone
     * @param done  done, a boolean that controls if the thread is supposed to be running
     */
    public void setDone(boolean done) {
        isDone = done;
    }
}
