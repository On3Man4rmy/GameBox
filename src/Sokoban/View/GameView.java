package Sokoban.View;

import App.Menu;
import Sokoban.Model.Direction;
import Sokoban.Model.Sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;

import static App.App.app;

/**
 * The window, in which the game is shown
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 09/05/18
 */

public class GameView extends JInternalFrame implements Observer {
    public Sokoban sokoban;
    BoardView boardView;
    private MenuView menuView;
    public DescriptionView descriptionView;
    private Menu[] menus = {new Menu("Options")
            .addItem("Restart", e -> {
                sokoban.rebuildBoard();
                sokoban.setDone(false);

            })
            .addItem("Save", e -> saveGame())
            .addItem("Load", e -> loadGame())
            .addItem("Show Controls", e -> {
        if (descriptionView.isVisible()) {
            descriptionView.setVisible(false);
            menuView.setVisible(sokoban.isDone());
            boardView.setVisible(!sokoban.isDone());
        } else {
            descriptionView.setVisible(true);
            menuView.setVisible(false);
            boardView.setVisible(false);

        }


    })

            , new Menu("Choose Level")
            .addItem("minicosmos", e -> {
                app.loadLevelListView(new File("src/Sokoban/Resources/minicosmos.txt"));

            })
            .addItem("nabokosmos", e -> {
                app.loadLevelListView(new File("src/Sokoban/Resources/nabokosmos.txt"));

            })
            .addItem("yoshiomurase", e -> {
        app.loadLevelListView(new File("src/Sokoban/Resources/yoshiomurase.txt"));

    })


    };
    private Container contentPane = getContentPane();

    /**
     * Constructor, sets menubar and End game message, prepares the window for the game
     *
     * @param sokoban The sokoban version connected to the view
     */
    public GameView(Sokoban sokoban) {
        super("Game", true, true, true, true);
        registerKeyEvents();
        this.setSize((sokoban.getArrayLength() * 30), sokoban.getArrayHeight() * 50);

        LayoutManager overlay = new OverlayLayout(contentPane);
        contentPane.setLayout(overlay);
        JMenuBar mb = new JMenuBar();
        mb.add(menus[0]);
        mb.add(menus[1]);
        setJMenuBar(mb);
        this.sokoban = sokoban;
        this.sokoban.addObserver(this);

        boardView = new BoardView(sokoban);
        menuView = new MenuView(this,"Game Won!");
        descriptionView = new DescriptionView();
        menuView.setVisible(false);
        descriptionView.setVisible(false);
        contentPane.add(menuView);
        contentPane.add(boardView);
        contentPane.add(descriptionView);

        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == sokoban) {
            menuView.setVisible(sokoban.isDone());
            boardView.setVisible(!sokoban.isDone());
            descriptionView.setVisible(false);
        }
    }

    /**
     * Registers keys for possible actions
     */
    private void registerKeyEvents() {
        registerKeyAction("W", "moveUp", actionEvent ->
                sokoban.moveElement(Direction.UP));
        registerKeyAction("A", "moveLeft", actionEvent ->
                sokoban.moveElement(Direction.LEFT));
        registerKeyAction("S", "moveDown", actionEvent ->
                sokoban.moveElement(Direction.DOWN));
        registerKeyAction("D", "moveRight", actionEvent ->
                sokoban.moveElement(Direction.RIGHT));

        registerKeyAction(KeyEvent.VK_UP,0, "moveUp", actionEvent ->
                sokoban.moveElement(Direction.UP));
        registerKeyAction(KeyEvent.VK_LEFT,0, "moveLeft", actionEvent ->
                sokoban.moveElement(Direction.LEFT));
        registerKeyAction(KeyEvent.VK_DOWN,0, "moveDown", actionEvent ->
                sokoban.moveElement(Direction.DOWN));
        registerKeyAction(KeyEvent.VK_RIGHT,0, "moveRight", actionEvent ->
                sokoban.moveElement(Direction.RIGHT));

        registerKeyAction(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK, "save", actionEvent ->
                saveGame());
        registerKeyAction(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK, "load", actionEvent ->
                loadGame());
        registerKeyAction(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK, "restart", actionEvent ->
                sokoban.rebuildBoard());
        registerKeyAction(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK, "undo", actionEvent ->
                sokoban.undo());
        registerKeyAction(KeyEvent.VK_ESCAPE, 0, "Show Controls", actionEvent -> {
            if (descriptionView.isVisible()) {
                descriptionView.setVisible(false);
                menuView.setVisible(sokoban.isDone());
                boardView.setVisible(!sokoban.isDone());
            } else {
                descriptionView.setVisible(true);
                menuView.setVisible(false);
                boardView.setVisible(false);

            }

        });
    }

    /**
     * set what pressing the key does
     *
     * @param key        the pressed key
     * @param actionName name of the action
     * @param callback   the performed action
     */
    private void registerKeyAction(String key, String actionName, Consumer<ActionEvent> callback) {
        getInputMap().put(KeyStroke.getKeyStroke(key), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.accept(e);
            }
        });
    }

    /**
     * set what pressing the key does
     *
     * @param keyCode    the pressed key as KeyCode
     * @param modifiers  modifiers, like Ctrl
     * @param actionName name of the action
     * @param callback   the performed action
     */
    private void registerKeyAction(int keyCode, int modifiers, String actionName, Consumer<ActionEvent> callback) {
        getInputMap().put(KeyStroke.getKeyStroke(keyCode, modifiers), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.accept(e);
            }
        });
    }


    /**
     * Saves the game
     */
    private void saveGame() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        String filename = "Sokoban" + dateFormat.format(date) + ".ser";

        try {
            FileOutputStream fs = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(sokoban);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game from Save
     */
    private void loadGame() {
        JFileChooser c = new JFileChooser(new File("./"));
        File selectedFile = null;
        int returnValue = c.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = c.getSelectedFile();
        }
        if (selectedFile != null) {
            try {
                FileInputStream fs = new FileInputStream(selectedFile);
                ObjectInputStream is = new ObjectInputStream(fs);
                loadNewSokoban((Sokoban) is.readObject());
                menuView.setVisible(sokoban.isDone());
                boardView.setVisible(!sokoban.isDone());
                is.close();
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Loads a new version of Sokoban into the view, and connects it to observers
     * @param newSokoban    the sokoban version, that is loaded into the view
     */

    public void loadNewSokoban(Sokoban newSokoban){
        newSokoban.addObserver(this);
        sokoban.deleteObserver(this);
        sokoban = newSokoban;
        contentPane.removeAll();
        boardView = new BoardView(sokoban);
        contentPane.add(boardView);
        menuView = new MenuView(this,"Game Won!");
        contentPane.add(menuView);
        contentPane.add(descriptionView);
        setVisible(false);      //Force Frame refresh
        setVisible(true);
    }


}
