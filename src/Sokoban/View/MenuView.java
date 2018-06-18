package Sokoban.View;

import Sokoban.Model.Sokoban;
import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JPanel {
    private JLabel label = new JLabel("", SwingConstants.CENTER);
    private GameView gameView;
    private Sokoban sokoban;

    /**
     * Constructor, sets the Colors for end game message
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
        JButton next =new JButton("Next Level");
        next.addActionListener(evt->{


                }
        );
        next.setBackground(Colors.CANARINHO.getColor());
        nextButtonPanel.add(next);
        add(nextButtonPanel);
        add(new JPanel()).setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());

    }

    /**
     * Sets text for end game message
     *
     * @param label text of the end game message, a string
     */
    public void setText(String label) {
        this.label.setText(label);
    }

    private void loadNextGame(){
        Sokoban newSokoban = new Sokoban(sokoban.);
        newSokoban.addObserver(this);
        sokoban.deleteObserver(this);
        sokoban = newSokoban;
        contentPane.removeAll();
        boardView = new BoardView(sokoban);
        contentPane.add(boardView);
        menuView = new MenuView();
        menuView.setText("Game Won!");
        contentPane.add(menuView);
        setVisible(false);
        setVisible(true);
        menuView.setVisible(sokoban.isDone());
        boardView.setVisible(!sokoban.isDone());
    }
}
