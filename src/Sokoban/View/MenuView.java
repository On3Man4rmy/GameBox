package Sokoban.View;

import Sokoban.Model.Sokoban;
import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JPanel {
    private JLabel label = new JLabel("", SwingConstants.CENTER);
    private GameView gameView;
    private Sokoban sokoban;
    private JButton next =new JButton("Next Level");


    /**
     * Constructor, creates the Layout for endgame Message and Next game button, as well as their colors
     */
    public MenuView(GameView gameView ) {
        this.gameView=gameView;
        sokoban=gameView.sokoban;
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
        next.setVisible(!(sokoban.getLevel()==sokoban.getMaxLevel()));  //Button only visible when it'S not the last Level
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
        Sokoban newSokoban = new Sokoban(sokoban.getFile(),sokoban.getLevel()+1);
        newSokoban.addObserver(gameView);
        sokoban.deleteObserver(gameView);
        sokoban = newSokoban;
        gameView.sokoban=sokoban;
        gameView.getContentPane().removeAll();
        gameView.boardView = new BoardView(sokoban);
        gameView.getContentPane().add(gameView.boardView);
        gameView.getContentPane().add(this);
        gameView.getContentPane().add(gameView.descriptionView);
        next.setVisible(!(sokoban.getLevel()==sokoban.getMaxLevel()));
        gameView.setVisible(false);
        gameView.setVisible(true);

    }
}
