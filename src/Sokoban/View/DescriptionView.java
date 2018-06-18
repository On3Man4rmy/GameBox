package Sokoban.View;

import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;

public class DescriptionView extends JPanel {


    public DescriptionView() {
        JLabel label = new JLabel("Controls:", SwingConstants.CENTER);
        setLayout(new GridLayout(8, 1));
        setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        add(new JPanel().add(label));
        label = new JLabel("W, A, S, D : Move Up, Down, Left, Right", SwingConstants.CENTER);
        add(new JPanel().add(label));
        label = new JLabel("Ctrl + S : Save Game", SwingConstants.CENTER);
        add(new JPanel().add(label));
        label = new JLabel("Ctrl + L : Load Game from Files", SwingConstants.CENTER);
        add(new JPanel().add(label));
        label = new JLabel("Ctrl + R : Restart the Game", SwingConstants.CENTER);
        add(new JPanel().add(label));
        label = new JLabel("Ctrl + Z : Undo Move", SwingConstants.CENTER);
        add(new JPanel().add(label));
        label = new JLabel("Escape : Show/Hide Controls", SwingConstants.CENTER);
        add(new JPanel().add(label));
        JPanel panel = new JPanel();
        panel.setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        panel.setLayout(new GridLayout(2, 1));                               //Gridlayout with 3 grids to decrease line spacing
        label = new JLabel("Mouse-click next to Player:", SwingConstants.CENTER);
        panel.add(label);
        label = new JLabel("Move to that position", SwingConstants.CENTER);
        panel.add(label);
        add(panel);


    }
}
