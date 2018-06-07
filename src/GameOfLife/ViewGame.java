package GameOfLife;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;

import App.*;
import App.Menu;


/**
 * Class represts Window in which a version of the game runs
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 27/04/18
 */
public class ViewGame extends JInternalFrame {
    static int nr = 0, xpos = 30, ypos = 30;
    App myView;
    private Color dead = Color.GREEN;                 //saves the colors
    private Color alive = Color.RED;
    boolean isFigure = false;                         //is a figure being set
    boolean[][] figure = {{false}};                  //saves figure
    private BoardView boardView;
    private GameOfLife game;
    Menu[] menus = {
            new Menu("Mode")
                    .addItem("Run/Pause", e -> {
                        game.isRun = !game.isRun;
                        game.isPaint = false;                 //disables paint and set when game runs, viable for change
                        game.isSet = false;
                    })
                    .addItem("Set","Change the status of cells", e -> {
                        game.isRun = false;               //pauses the game while setting, viable for change
                        game.isSet = true;
                        game.isPaint = false;
                        isFigure = false;                        //disables setting figures
                    })
                    .addItem("Paint","<html>Revive multiple cells at once<br>by dragging the mouse over it while clicking the right mouse button</html>", e -> {
                game.isRun = false;
                game.isPaint = true;
                isFigure = false;
            }),
            new Menu("Speed","Change the speed of updates")
                    .addItem("Fast", e -> game.setSpeed(100))
                    .addItem("Medium", e -> game.setSpeed(1000))
                    .addItem("Slow", e -> game.setSpeed(2000)),

            new Menu("Window")
                    .addItem("new View","new Window of the same game", e -> {
                        ViewGame viewGame1 = new ViewGame(App.app, game); //passes refernce to thread and the boolean values
                        App.app.addChild(viewGame1, xpos += 20, ypos += 20);
                    })
                    .addItem("new Game","Start new Game", e -> {
                        StartGameWindow sgw = new StartGameWindow(App.desk);      //Creates a Stargame Window
                    })
                    .addItem("new Copy","Create identical independent copy of the current Game", e -> {
                        GameOfLife copyGame = new GameOfLife(game);
                        ViewGame copyViewGame = new ViewGame(this, copyGame);
                        App.app.addChild(copyViewGame, 10, 10);
                    })
                    .addItem("Change Color Alive", e -> {
                        alive = JColorChooser.showDialog(this, "Select living color", Color.RED);
                    })
                    .addItem("Change Color Dead", e -> {
                        dead = JColorChooser.showDialog(this, "Select dead color", Color.GREEN);
                    })
                    .addItem("FlipX","Flips the game along the Y-Axis", e -> {
                        boardView.setFlipX(!boardView.isFlipX());
                    })
                    .addItem("FlipY","Flips the game along the X-Axis", e -> {
                        boardView.setFlipY(!boardView.isFlipY());
                    })
                    .addItem("Rotate","Rotates the game by 90°", e -> {
                boardView.rotate();
                if (boardView.rotate) {
                    this.setSize((game.getHeight() * myView.SCALEFACTOR), (game.getLength()) * myView.SCALEFACTOR);
                }     //changes the window size
                else {
                    this.setSize((game.getLength() * myView.SCALEFACTOR), (game.getHeight()) * myView.SCALEFACTOR);
                }
            }),
            new Menu("Figure")
                    .addItem("Glider", e -> setFigure(Construction.GLIDER))
                    .addItem("f-pentomino","A rapidly escalating figure", e -> setFigure(Construction.F_PENTOMINO))
                    .addItem("Blinker", e -> setFigure(Construction.BLINKER))
                    .addItem("Biploe", e -> setFigure(Construction.BIPLOE))
                    .addItem("Tumbler", e -> setFigure(Construction.TUMBLER))
                    .addItem("Clear","Kills all cells", e -> game.resetFeld())
    };

    /**
     * Construktor
     *
     * @param myView refrence to ViewGame
     * @param game   reference to GameOfLife
     */

    public ViewGame(App myView, GameOfLife game) {
        super("Game " + (++nr), true, true,true,true);
        JMenuBar menuBar = new JMenuBar();
        this.boardView = new BoardView(game, this);
        this.myView = myView;
        this.game = game;

        for (JMenu menu : menus) { // fuer alle Menues:
            menuBar.add(menu);
        }

        /**
         * checks if Frame is closed, removes observer, if there are no observers end thread
         */
        addInternalFrameListener(new InternalFrameCloseListener() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                game.deleteObserver(boardView);
            }
        });

        add(boardView);
        setJMenuBar(menuBar);

        setVisible(true);
        this.setSize((game.getLength() * myView.SCALEFACTOR), (game.getHeight()) * myView.SCALEFACTOR);

    }

    /**
     * Copy Constructor
     *
     * @param viewGame View to be copied
     * @param game Copy of the game connected to the view
     */
    public ViewGame(ViewGame viewGame, GameOfLife game) {
        this(viewGame.myView, game);
        setDead(viewGame.getDead());
        setAlive(viewGame.getAlive());
        isFigure = viewGame.isFigure;
        figure = new boolean[viewGame.figure[0].length][viewGame.figure.length];
        for (int y = 0; y < viewGame.figure[0].length; y++) {
            for (int x = 0; x < viewGame.figure.length; x++) {
                figure[y][x] = viewGame.figure[x][y];
            }
        }
    }


    public void setFigure(Construction figure) {
        game.isPaint = false;
        game.isSet = false;
        isFigure = true;
        this.figure = ConstructionField.getForm(figure);
    }
    public boolean[][] getFigure() {
        return figure;
    }

    public Color getAlive() {
        return alive;
    }

    public Color getDead() {
        return dead;
    }


    public void setAlive(Color alive) {
        this.alive = alive;
    }

    public void setDead(Color dead) {
        this.dead = dead;
    }
}
