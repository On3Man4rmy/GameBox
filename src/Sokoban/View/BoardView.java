package Sokoban.View;

import Sokoban.Model.*;
import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;

/**
 * Represents the Gameboard, which is shown as GridLayout
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 09/05/18
 */

public class BoardView extends JPanel implements Observer {
    public Sokoban sokoban;
    private boolean listenMouseEvents = false;
    private SquareView[][] squareViews;


    int rows;
    int cols;

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

    public void enableMouseListener() {
        if (!listenMouseEvents) {
            listenMouseEvents = !listenMouseEvents;
        }
    }

    public void disableMouseListener() {
        if (listenMouseEvents) {
            listenMouseEvents = !listenMouseEvents;
        }
    }


    /**
     * Creates board the first time, creates mouselistener
     */
    public void loadBoard() {
        cols = sokoban.getArrayLength();
        rows = sokoban.getArrayHeight();

        squareViews = new SquareView[cols][rows];

        removeAll();
        setLayout(new GridLayout(rows, cols));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                SquareView newSquareView = new SquareView();
                final int x = j;
                final int y = i;
                MouseListener mouseListener = new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (listenMouseEvents) {
                            if (Position.movePosition(Direction.LEFT, sokoban.player.position).equals(new Position((int) x, (int) y))) {
                                sokoban.moveElement(Direction.LEFT);
                            } else if (Position.movePosition(Direction.UP, sokoban.player.position).equals(new Position((int) x, (int) y))) {
                                sokoban.moveElement(Direction.UP);
                            } else if (Position.movePosition(Direction.DOWN, sokoban.player.position).equals(new Position((int) x, (int) y))) {
                                sokoban.moveElement(Direction.DOWN);
                            } else if (Position.movePosition(Direction.RIGHT, sokoban.player.position).equals(new Position((int) x, (int) y))) {
                                sokoban.moveElement(Direction.RIGHT);
                            }
                        }
                    }
                };
                newSquareView.addMouseListener(mouseListener);
                squareViews[j][i] = newSquareView;
                add(newSquareView);
            }
        }
    }

    /**
     * Updates the Gameboard, sets JPanels in Gridlayout to correct Color and name, based on sokoban
     */
    public void updateBoard() {
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
                        /**
                         * checks if Player is on Goal, if yes, changes icon to indicate this
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
                        /**
                         * checks if Crate is on Goal, if yes, changes backgroundcolor and icon to indicate this
                         * also increases goal count
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

    @Override
    public void update(Observable o, Object arg) {
        if (o == sokoban) {
            updateBoard();
        }
    }


    public void registerKeyAction(String key, String actionName, Consumer<ActionEvent> callback) {
        getInputMap().put(KeyStroke.getKeyStroke(key), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.accept(e);
            }
        });

    }

    @Override
    public void setBackground(Color bg) {

    }


}
