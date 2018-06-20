package Sokoban.View;

import Sokoban.Model.Sokoban;
import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @date: 19/06/18
 *
 * Shows the end game state, after a puzzle is solved, offers button to start next Level(if available)
 */
public class MenuView extends JPanel {
    private JLabel label = new JLabel("", SwingConstants.CENTER);
    private GameView gameView;
    private Sokoban sokoban;
    private JButton next =new JButton("Next Level");


    /**
     * Constructor, creates the Layout for endgame Message and Next game button, as well as their colors
     */
    public MenuView(GameView gameView, String stringLabel ) {
        this.gameView=gameView;
        sokoban=gameView.sokoban;
        setText(stringLabel);
        setLayout(new GridLayout(5,1));
        setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        add(new JPanel()).setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        add(new JPanel()).setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        JPanel panel=new JPanel();
        label.setForeground(Colors.A_LIFETIME_AGO.getColor());
        panel.add(label);
        add(panel).setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        JPanel nextButtonPanel=new JPanel();
        nextButtonPanel.setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        next.addActionListener(evt-> loadNextGame());
        next.setBackground(Colors.CANARINHO.getColor());
        nextButtonPanel.add(next);
        add(nextButtonPanel);
        add(new JPanel()).setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        next.setVisible(!(sokoban.getLevel()==sokoban.getMaxLevel()));  //Button only visible when it's not the last Level
    }

    /**
     * Sets text for end game message
     *
     * @param label text of the end game message, a string
     */
    public void setText(String label) {
        this.label.setText(label);
    }

    /**
     * Loads the next Level in the file
     */
    private void loadNextGame(){
        gameView.loadNewSokoban( new Sokoban(sokoban.getFile(),sokoban.getLevel()+1));
        next.setVisible(!(sokoban.getLevel()==sokoban.getMaxLevel()));
    }
}
