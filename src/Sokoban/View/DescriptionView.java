package Sokoban.View;

import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;

public class DescriptionView extends JPanel {


    public DescriptionView() {
        setLayout(new GridLayout(8, 1));
        setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        add(new JPanel().add(new JLabel("Controls:", SwingConstants.CENTER)));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        panel.add(new JLabel("W, A, S, D / Arrows Keys :", SwingConstants.CENTER));
        panel.add(new JLabel("Move Up, Down, Left, Right", SwingConstants.CENTER));
        add(panel);
        add(new JLabel("Ctrl + S : Save Game", SwingConstants.CENTER));
        add(new JLabel("Ctrl + L : Load Game from Files", SwingConstants.CENTER));
        add(new JLabel("Ctrl + R : Restart the Game", SwingConstants.CENTER));
        add(new JLabel("Ctrl + Z : Undo Move", SwingConstants.CENTER));
        add(new JLabel("Escape : Show/Hide Controls", SwingConstants.CENTER));
        panel = new JPanel();
        panel.setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        panel.setLayout(new GridLayout(2, 1));
        panel.add(new JLabel("Mouse-click next to Player:", SwingConstants.CENTER));
        panel.add(new JLabel("Move to that position", SwingConstants.CENTER));
        add(panel);


    }
}
