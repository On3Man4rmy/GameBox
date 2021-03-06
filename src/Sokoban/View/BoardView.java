package Sokoban.View;

import Sokoban.Model.*;
import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Represents the Gameboard, which is shown as GridLayout
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 09/05/18
 */

public class BoardView extends JPanel implements Observer {
    private Sokoban sokoban;
    private boolean listenMouseEvents = false;
    private SquareView[][] squareViews;
    // Move Player according mouseposition relative to playerposition
    // E.g. if mouse is pressed above player then the player moves up
    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            double x = e.getX();
            double y = e.getY();
            double width = getWidth();
            double height = getHeight();
            Player player = sokoban.player;
            int boardHeight = sokoban.getArrayHeight();
            int boardWidth = sokoban.getArrayLength();
            double boardTileWidth = width / boardWidth;
            double boardTileHeight = height / boardHeight;
            int playerX = player.position.getxPos();
            int playerY = player.position.getyPos();
            double tileX = playerX * boardTileWidth + boardTileWidth * 0.5;
            double tileY = playerY * boardTileHeight + boardTileHeight * 0.5;

            // Is mouseclick in top left half
            boolean topLeftHalf = -(y - tileY) > (x - tileX);
            boolean bottomLeftHalf = -(y - tileY) < -(x - tileX);

            if(topLeftHalf && bottomLeftHalf) {
                sokoban.moveElement(Direction.LEFT);
            } else if(topLeftHalf) {
                sokoban.moveElement(Direction.UP);
            } else if(bottomLeftHalf) {
                sokoban.moveElement(Direction.DOWN);
            } else {
                sokoban.moveElement(Direction.RIGHT);
            }
        }
    };



    private int rows;
    private int cols;

    /**
     * Constructor
     *
     * @param sokoban the game which is shown in the view
     */
    public BoardView(Sokoban sokoban) {

        this.sokoban = sokoban;
        this.sokoban.addObserver(this);
        loadBoard();
        updateBoard();
        enableMouseListener();

        setVisible(true);
    }

    /**
     * turns the mouselistener on, when the game is properly loaded and ot just previewed
     */
    public void enableMouseListener() {
        if (!listenMouseEvents) {
            addMouseListener(mouseListener);
            listenMouseEvents = !listenMouseEvents;
        }
    }

    /**
     * turns the mouselistener off, when the game is just previewed
     */
    public void disableMouseListener() {
        if (listenMouseEvents) {
            removeMouseListener(mouseListener);
            listenMouseEvents = !listenMouseEvents;
        }
    }


    /**
     * Creates board the first time, creates mouselistener
     */
    private void loadBoard() {
        cols = sokoban.getArrayLength();
        rows = sokoban.getArrayHeight();

        squareViews = new SquareView[cols][rows];

        removeAll();
        setLayout(new GridLayout(rows, cols));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                SquareView newSquareView = new SquareView();
                squareViews[j][i] = newSquareView;
                add(newSquareView);
            }
        }
    }

    /**
     * Updates the Gameboard, sets JPanels in Gridlayout to correct Color and name, based on sokoban
     */
    private void updateBoard() {
        int cratesOnGoalCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Square[] squareContent = sokoban.gameBoard[j][i];
                for (Square content : squareContent) {
                    if (content instanceof Floor) {
                        if (((Floor) content).goal) {
                            squareViews[j][i].setBackground(Colors.WILD_VIOLET_PETAL.getColor());
                            squareViews[j][i].setText(".");
                        } else {
                            squareViews[j][i].setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
                            squareViews[j][i].setText("");
                        }
                    }
                    if (content instanceof Player) {
                        squareViews[j][i].setBackground(Colors.CANARINHO.getColor());
                        squareViews[j][i].setText("@");
                        /*
                          checks if Player is on Goal, if yes, changes icon to indicate this
                         */
                        Floor temp = (Floor) sokoban.gameBoard[j][i][0];
                        if (temp.goal) {
                            squareViews[j][i].setText("+");


                        }
                    }
                    if (content instanceof Wall) {
                        squareViews[j][i].setBackground(Colors.SURRENDER_V2.getColor());
                        squareViews[j][i].setText("#");
                    }
                    if (content instanceof Crate) {
                        squareViews[j][i].setBackground(Colors.A_SWING_TRUMPET_V2.getColor());
                        squareViews[j][i].setText("$");
                        /*
                          checks if Crate is on Goal, if yes, changes backgroundcolor and icon to indicate this
                          also increases goal count
                         */
                        Floor temp = (Floor) sokoban.gameBoard[j][i][0];
                        if (temp.goal) {
                            cratesOnGoalCount++;
                            squareViews[j][i].setBackground(Color.RED);
                            squareViews[j][i].setText("*");


                        }
                    }

                }
            }
        }
        if (!sokoban.isDone()) {
            if (cratesOnGoalCount == sokoban.getGoalCount()) {
                sokoban.setDone(true);
            }
        }
    }

    /**
     * Update method, updates the board
     * @param o     The Observable that is connected to the view
     * @param arg   optional objects, not used
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == sokoban) {
            updateBoard();
        }
    }
}
