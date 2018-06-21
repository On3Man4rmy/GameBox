package GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Class to show the Gamefield
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 01/05/18
 */
public class BoardView extends JPanel implements Observer {
    private GameOfLife model;       //References to the game and view
    private ViewGame viewGame;
    private JButton boardElements[][];  //Array of buttons, represents the gamefields

    private boolean flipX = false;               //false is normal, true is flipped
    private boolean flipY = false;
    boolean rotate = false;
    private GridLayout grid;
    private GridLayout rotGrid;
    private boolean mousePresses = false;     //controls if mouse is currently being clicked down

    /**
     * @param model    The gamemodel
     * @param viewGame The window
     */
    public BoardView(GameOfLife model, ViewGame viewGame) {
        this.boardElements = new JButton[model.getLength()][model.getHeight()];
        this.rotGrid = new GridLayout(model.getLength(), model.getHeight());
        this.grid = new GridLayout(model.getHeight(), model.getLength());
        this.viewGame = viewGame;
        this.model = model;

        model.addObserver(this);
        this.setLayout(grid);       //Layout of Buttons
        initializeBoard();
        updateLayout();
    }

    /**
     * Initialize the board, add listeners to buttons
     */
    private void initializeBoard() {
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getLength(); x++) {
                final int xPos = x;
                final int yPos = y;
                boardElements[x][y] = new JButton();
                boardElements[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {       //Painting hy passing over buttons
                        if (mousePresses && model.isPaint) {                           //If isPaint is true and mouse is pressed
                            setCell(xPos, yPos, true);            //reanimate passed over cell
                        }
                    }

                    public void mousePressed(java.awt.event.MouseEvent evt) {       //mouse is pressed down

                        mousePresses = true;
                        if (model.isPaint) {                           //If isPaint is true
                            setCell(xPos, yPos, true);            //reanimate pressed on cell
                        }


                    }

                    public void mouseReleased(java.awt.event.MouseEvent evt) {       //mouse is released

                        mousePresses = false;

                    }

                });
                boardElements[x][y].addActionListener(e -> {
                    if (model.isSet) {//setting cell to alive
                        toggleCell(xPos, yPos);
                    }
                    if (viewGame.isFigure) {                  //Setting Figure to the clicked cell
                        model.addFigure(getCellX(xPos), getCellY(yPos), viewGame.getFigure());
                    }
                    if (!model.isSet && !viewGame.isFigure) {           //Cells can be set alive always, but only killed in Set Mode
                        setCell(xPos, yPos, true);
                    }
                });
            }
        }
        updateBoard();
    }

    /**
     * Update board method, checks the array of cells and recolors the buttons accordingly
     */
    private void updateBoard() {
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getLength(); x++) {
                boolean modelElement = getCell(x, y);

                /*
                  Update of thread can sometimes be called while new view is still being build, thus causing a crash
                 */
                if (boardElements[x][y] != null) {
                    if (modelElement) {
                        boardElements[x][y].setBackground(viewGame.getAlive());
                    } else {
                        boardElements[x][y].setBackground(viewGame.getDead());
                    }
                }
            }
        }
    }

    /**
     * Updates the button Layout when rotating
     */
    private void updateLayout() {
        removeAll();
        if (rotate) {
            this.setLayout(rotGrid);
            for (int y = 0; y < model.getLength(); y++) {
                for (int x = 0; x < model.getHeight(); x++) {
                    add(boardElements[y][model.getHeight() - 1 - x]);
                }
            }
        } else {
            this.setLayout(grid);
            for (int y = 0; y < model.getHeight(); y++) {
                for (int x = 0; x < model.getLength(); x++) {
                    add(boardElements[x][y]);
                }
            }
        }
        updateBoard();
    }

    /**
     * method to rotate, update the Layout
     */
    public void rotate() {
        rotate = !rotate;
        updateLayout();
    }

    /**
     * Update method, updates the board when GameOfLife changes
     * @param o     the connected Observable
     * @param arg   optional objects, not used here
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            updateBoard();
        }
    }

    /**
     * Changes the status of a cell
     *
     * @param x x location of cell
     * @param y y location of cell
     */
    private void toggleCell(int x, int y) {
        setCell(x, y, !getCell(x, y));
    }

    /**
     * Sets cell to a specific status
     *
     * @param x    x location of cell
     * @param y    y location of cell
     * @param cell the status to which it is to be set (true=alive, false=dead)
     */
    private void setCell(int x, int y, boolean cell) {
        model.setField(cell, getCellX(x), getCellY(y));
    }

    /**
     * returns the cell
     *
     * @param x x location of cell
     * @param y y location of cell
     * @return the cell
     */
    private boolean getCell(int x, int y) {
        return model.getField(getCellX(x), (getCellY(y)));
    }

    /**
     * Returns actual X-position of cell due to Flipping
     *
     * @param x the x-Position of the clicked field
     * @return the x Position of the cell behind the clicked field
     */
    private int getCellX(int x) {
        return flipX ? model.getLength() - 1 - x : x;
    }

    /**
     * Returns actual Y-position of cell due to Flipping
     *
     * @param y the y-Position of the clicked field
     * @return the y Position of the cell behind the clicked field
     */
    private int getCellY(int y) {
        return flipY ? model.getHeight() - 1 - y : y;
    }

    /**
     * Returns the flipX boolean
     *
     * @return flipX, the boolean that checks if the view should be flipped along the y axis (x values are flipped)
     */
    public boolean isFlipX() {
        return flipX;
    }

    /**
     * Returns the flipY boolean
     *
     * @return flipY, the boolean that checks if the view should be flipped along the x axis (y values are flipped)
     */
    public boolean isFlipY() {
        return flipY;
    }

    /**
     * Sets FlipX, when the player want to flip the view
     *
     * @param flipX the boolean that checks if the view should be flipped along the y axis (x values are flipped)
     */
    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
        updateBoard();
    }

    /**
     * Sets FlipY, when the player want to flip the view
     *
     * @param flipY the boolean that checks if the view should be flipped along the x axis (y values are flipped)
     */
    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
        updateBoard();
    }
}
