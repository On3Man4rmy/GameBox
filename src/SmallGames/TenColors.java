package SmallGames;


import javax.swing.*;
import java.awt.*;

import static App.App.app;

/**
 * Changes Background color by clicking the button
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 27/04/18
 **/
public class TenColors extends JInternalFrame {

    private JButton[] buttons = {new JButton("black"), new JButton("blue"), new JButton("cyan"),
            new JButton("green"), new JButton("magenta"), new JButton("orange"), new JButton("pink"),
            new JButton("red"), new JButton("white"), new JButton("yellow")};

    /**
     * Constructor, initializes the buttons
     */
    public TenColors() {
        super("Ten Colors", true, true, true, true);
        this.setSize(360, 360);
        app.addChild(this, 0, 0);
        setLayout(new FlowLayout());
        setVisible(true);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 30));
            buttons[i].setForeground(getCorrectColor(i));
            final int col = i;
            buttons[i].addActionListener(e -> setBackground(getCorrectColor(col)));

            add(buttons[i]);
        }

    }


    /**
     * returns color based of its position in the Buttons array
     *
     * @param i integer, position of the button in the Buttons array
     * @return the returned color
     */
    private Color getCorrectColor(int i) {

        switch (i) {

            case 0:
                return Color.black;
            case 1:
                return Color.blue;
            case 2:
                return Color.cyan;
            case 3:
                return Color.green;
            case 4:
                return Color.magenta;
            case 5:
                return Color.orange;
            case 6:
                return Color.pink;
            case 7:
                return Color.red;
            case 8:
                return Color.white;
            case 9:
                return Color.yellow;
        }
        return Color.black;
    }
}

