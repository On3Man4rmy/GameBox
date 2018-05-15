package GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Class to show the Gamefield
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 01/05/18
 */
public class BoardView extends JPanel implements Observer {
    private GameOfLife model;       //Refernces to the game and view
    private ViewGame viewGame;
    private JButton boardElements[][];  //Array of buttons, represents the gamefields

    private boolean flipX = false;               //false is normal, true is flipped
    private boolean flipY = false;
    boolean rotate = false;
    private GridLayout grid;
    private GridLayout rotGrid;
    static int count = 0;
    boolean mousePresses=false;     //controlls if mouse is currenntly being clicked down

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

    private void initializeBoard() {
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getLength(); x++) {
                final int xPos = x;
                final int yPos = y;
                boardElements[x][y] = new JButton();
                boardElements[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {       //Painting hy passing over buttons
                        if (mousePresses&&model.isPaint) {                           //If isPaint is true and mouse is pressed
                            setCell(xPos, yPos, true);            //reanimate passed over cell
                        }
                    }
                    public void mousePressed(java.awt.event.MouseEvent evt) {       //mouse is pressed down

                        mousePresses=true;
                        if (model.isPaint) {                           //If isPaint is true
                            setCell(xPos, yPos, true);            //reanimate pressed on cell
                        }


                    }
                    public void mouseReleased(java.awt.event.MouseEvent evt) {       //mouse is released

                        mousePresses=false;

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
     * Update board methode, checks the array of cells and recolors the buttons accordingly
     */
    private void updateBoard() {
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getLength(); x++) {
                boolean modelElement = getCell(x, y);

                /**
                 * Update of thread can sometimes be called while new view is still being build, thus causing a crash
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


    public void updateLayout() {
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
     * Methode to rotate, sets the flips the roatation anf flipX varaible
     * replaces the Layout with the rotated Layout
     * removes buttons and adds the rotated ones
     */
    public void rotate() {
        rotate = !rotate;
        updateLayout();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            updateBoard();
        }
    }

    private void toggleCell(int x, int y) {
        setCell(x, y, !getCell(x, y));
    }

    private void setCell(int x, int y, boolean cell) {
        model.setField(cell, getCellX(x), getCellY(y));
    }

    private boolean getCell(int x, int y) {
        return model.getField(getCellX(x), (getCellY(y)));
    }

    private int getCellX(int x) {
        return flipX ? model.getLength() - 1 - x : x;
    }

    private int getCellY(int y) {
        return flipY ? model.getHeight() - 1 - y : y;
    }

    public boolean isFlipX() {
        return flipX;
    }

    public boolean isFlipY() {
        return flipY;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }
}
