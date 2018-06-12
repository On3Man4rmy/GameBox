package Sokoban.View;

import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JPanel {
    JLabel label = new JLabel("", SwingConstants.CENTER);

    /**
     * Constructor, sets the Colors for end game message
     */
    public MenuView() {
        setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        setLayout(new BorderLayout());
        label.setForeground(Colors.A_LIFETIME_AGO.getColor());
        add(label, BorderLayout.CENTER);
    }

    /**
     * Sets text for end game message
     * @param label text of the end game message, a string
     */
    public void setText(String label) {
        this.label.setText(label);
    }
}
