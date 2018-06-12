package SmallGames;


import App.App;

import javax.swing.*;
import java.awt.*;

/**
 * Clicking the Button opens a new window in which flashes the colors of the rainbow.
 *
 * @author Tobias Fetzer 198318
 * @version 1.0
 * @date 15/03/2018
 **/
public class Rainbow extends JInternalFrame {
    private JButton changeColor = new JButton("Create Rainbow Window");

    /**
     * Constructor
     */
    public Rainbow() {
        super("Rainbow", true, true, true, true);

        this.setSize(360, 100);
        setLayout(new FlowLayout());
        setVisible(true);

        setLayout(new FlowLayout());
        changeColor.setFont(new Font("Arial", Font.PLAIN, 25));

        changeColor.addActionListener(e -> {
            RainbowColors regen = new RainbowColors();
            new Thread(regen).start();

        });
        add(changeColor);


    }


}

/**
 * Class to view the windows which change colors
 */
class RainbowColors extends JInternalFrame implements Runnable {
    private Color[] colors = {Color.red, Color.orange, Color.yellow,
            Color.green, Color.blue, Color.magenta};

    /**
     * Constructor
     */
    public RainbowColors() {
        super("Rainbows", true, true, true, true);
        this.setSize(360, 360);
        setLayout(new FlowLayout());
        setVisible(true);

    }

    /**
     * Run method, adds child to Desktop and updates the colors in a 1 second interval
     */
    public void run() {
        RainbowColors demo = new RainbowColors();
        App.app.addChild(demo, 10, 10);

        while (true) {
            for (Color i : colors) {
                demo.setBackground(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}

