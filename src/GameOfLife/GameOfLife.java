package GameOfLife;

import java.util.Observable;
import java.util.Observer;

/**
 * Game of Live Main logic class
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 27/04/18
 */
public class GameOfLife extends Observable {
    public boolean isRun = false;           // checks if game is supposed to be paused
    public boolean isPaint = false;         // checks if game is in paint mode
    public boolean isSet = false;           //checks if game is in set mode
    private Boolean isDone = false;
    private UpdateThread thread = new UpdateThread(this);
    private boolean[][] fields;        //Field of cells, true if alive, false if dead

    /**
     * Constructor, sets size of game field
     *
     * @param x Cells to the right
     * @param y Cells down
     */
    public GameOfLife(int x, int y) {
        fields = new boolean[x][y];
    }

    /**
     * Constructor, sets size of game field and initializes it with figure
     *
     * @param x      Cells to the right
     * @param y      Cells down
     * @param muster enum, represents a figure to placed on the field
     */

    public GameOfLife(int x, int y, Construction muster) {
        fields = new boolean[x][y];
        boolean[][] figure = ConstructionField.getForm(muster);
        if (figure.length < getLength() && figure[0].length < getHeight()) {
            for (y = 0; y < figure[0].length; y++) {
                for (x = 0; x < figure.length; x++) {
                    fields[x][y] = figure[x][y];
                }
            }
        }

        thread.start();
    }

    /**
     * Copy Constructor
     *
     * @param game The game to be copied
     */
    public GameOfLife(GameOfLife game) {
        fields = new boolean[game.getLength()][game.getHeight()];
        isRun = game.isRun;         // checks if game is supposed to be paused
        isPaint = game.isPaint;
        isSet = game.isSet;
        isDone = game.isDone;
        setFields(game.fields);
        thread.start();
        thread.setSpeed(game.thread.getSpeed());

    }

    /**
     * Sets the value of a field to that of the parameter value
     *
     * @param value the new value
     * @param x     the x Position
     * @param y     the y position
     */
    public void setField(boolean value, int x, int y) {
        if (fields[x][y] != value) {
            fields[x][y] = value;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * returns the value of a field
     *
     * @param x the x value of the field
     * @param y the y value
     * @return the value of the field
     */
    public boolean getField(int x, int y) {
        return fields[x][y];
    }

    /**
     * overwrites the gamefield with a new gamefield after update
     *
     * @param fields the updated game field
     */
    private void setFields(boolean[][] fields) {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getLength(); x++) {
                if (this.fields[x][y] != fields[x][y]) {
                    this.fields[x][y] = fields[x][y];
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * updates the field by calculating the new state
     */
    public void updateField() {

        boolean[][] tempFeld = new boolean[getLength()][getHeight()];
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getLength(); x++) {
                tempFeld[x][y] = checkSurrounding(x, y);
            }
        }
        setFields(tempFeld);
    }

    /**
     * @param initial_x x of the cell (left - right)
     * @param initial_y y position of cell(up - down)
     * @return Is cell alive?(true =yes, false =no)
     */
    private boolean checkSurrounding(int initial_x, int initial_y) {
        boolean cellsLives = false;     //return value
        int countOfLivingCells = 0;     // number of surrounding living cells
        for (int y = initial_y - 1; y <= initial_y + 1; y++) {      //iterates through the directly surrounding cells
            for (int x = initial_x - 1; x <= initial_x + 1; x++) {
                int yPos = y;                       //position of currently looked at cell (one of the cells surrounding the initial cell
                int xPos = x;
                /*
                  checks if cell is outside of array, if yes, check on the other side of the array
                 */
                if (!(x == initial_x && y == initial_y)) {
                    if (xPos == -1) {
                        xPos = getLength() - 1;
                    }
                    if (xPos == getLength()) {
                        xPos = 0;
                    }
                    if (yPos == -1) {
                        yPos = getHeight() - 1;
                    }
                    if (yPos == getHeight()) {
                        yPos = 0;
                    }
                    if (fields[xPos][yPos]) {         //increases count if surrounding cell is alive
                        countOfLivingCells++;
                    }
                }
            }
        }

        /*
          Implements game rules
         */
        if (!fields[initial_x][initial_y] && countOfLivingCells == 3) {   //exactly 3 living cells revive a dead cell
            cellsLives = true;
        }
        if (countOfLivingCells < 2) {       //at less than 2 living cells the cell dies
            cellsLives = false;
        }
        if (fields[initial_x][initial_y] && countOfLivingCells >= 2) {        //at 2 or 3 cells the cell survives
            cellsLives = true;
        }
        if (countOfLivingCells > 3) {                       //dies at over 3 living cells
            cellsLives = false;
        }
        return cellsLives;
    }

    /**
     * resets the field = kills every cell
     */
    public void resetField() {
        setFields(new boolean[getLength()][getHeight()]);
    }

    public int getHeight() {
        return fields[0].length;
    }

    public int getLength() {
        return fields.length;
    }

    /**
     * Places a figure in the game
     *
     * @param initial_x The x value where the figure begins
     * @param initial_y The y Value where the figure begins
     * @param figure    the figure to be added, a boolean array, where true represents the cells of the figure
     */
    public void addFigure(int initial_x, int initial_y, boolean[][] figure) {
        for (int y = 0; y < figure[0].length; y++) {
            for (int x = 0; x < figure.length; x++) {
                if (figure[x][y]) {
                    int xPos = (x + initial_x) % getLength();     //Modulo used to prevent out of bounds exceptions
                    int yPos = (y + initial_y) % getHeight();
                    setField(true, xPos, yPos);
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Used to set the speed = wait time for thread
     *
     * @param speed speed in milliseconds to wait for update
     */
    public void setSpeed(int speed) {
        thread.setSpeed(speed);
    }

    /**
     * returns isDone, an boolean
     *
     * @return a boolean to check if the thread should be ended, used when all views are closed
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * removes an observer. If all observers are removed, ends the thread
     * @param o the observer that should be removed
     */
    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
        if (countObservers() == 0) {
            isDone = false;
        }
    }
}

