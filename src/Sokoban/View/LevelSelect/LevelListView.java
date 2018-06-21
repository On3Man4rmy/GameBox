package Sokoban.View.LevelSelect;

import Sokoban.Model.GameLoader;
import Sokoban.Model.Sokoban;
import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Frame in which the selectable Levels are shown
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 07/06/18
 */
public class LevelListView extends JInternalFrame {
    // Action to be executed on level selection
    private Consumer<Sokoban> actionLevelSelected;

    /**
     * Constructor, creates window, adds LevelViews to it
     *
     * @param levelList Game File, which version is to be loaded
     */
    public LevelListView(File levelList) {
        super("Level List", true, true);

        ArrayList<Sokoban> sokobanList = loadFile(levelList);
        JPanel listPane = new JPanel();
        listPane.setLayout(new GridLayout(0, 3));
        for (int i = 1; i <= sokobanList.size(); i++) {
            Sokoban sokoban = sokobanList.get(i - 1);
            LevelView levelView = new LevelView("Level " + i, sokoban);
            levelView.setActionLevelSelected(getActionLevelSelected());
            listPane.add(levelView);
        }

        JScrollPane listScroller = new JScrollPane(listPane);
        listPane.setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());

        listScroller.setPreferredSize(new Dimension(300, 300));
        listScroller.getVerticalScrollBar().setUnitIncrement(15);
        loadFile(levelList);

        add(listScroller);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocation(0, 0);
        setTitle("Select Level");
    }

    /**
     * Get action to be executed on level selection
     *
     * @return the action to be executed
     */
    private Supplier<Consumer<Sokoban>> getActionLevelSelected() {
        return () -> actionLevelSelected;
    }

    /**
     * Set action to be executed on level selection
     *
     * @param actionLevelSelected action to be executed on level selection
     */
    public void setActionLevelSelected(Consumer<Sokoban> actionLevelSelected) {
        this.actionLevelSelected = actionLevelSelected;
    }

    /**
     * Creates every possible level of the game in the selected File
     *
     * @param levelList The selected version of the game
     * @return A ArrayList of Sokoban games
     */
    private ArrayList<Sokoban> loadFile(File levelList) {
        ArrayList<Sokoban> sokobanList = new ArrayList<>();
        int levels = GameLoader.getLevelCount(levelList);
        for (int i = 1; i <= levels; i++) {
            sokobanList.add(new Sokoban(levelList, i));
        }

        return sokobanList;
    }
}
