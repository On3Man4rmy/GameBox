package Sokoban.View;

import Sokoban.Resources.Colors;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a square in the view, for example a wall
 */
public class SquareView extends JPanel {
    private JLabel label = new JLabel("", SwingConstants.CENTER);

    /**
     * Constructor, sets default Background colors of the squares
     */
    public SquareView() {
        setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }

    /**
     * Sets text displayed on a square
     *
     * @param label the text displayed on the square, indicates what it is(for example @ for players)
     */
    public void setText(String label) {
        this.label.setText(label);
    }
}
