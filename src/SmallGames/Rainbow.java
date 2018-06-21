package SmallGames;


import App.App;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.util.LinkedList;

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

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
    private boolean isDone = false;

    /**
     * Constructor
     */
    public RainbowColors() {
        super("Rainbows", true, true, true, true);
        this.setSize(360, 360);
        setLayout(new FlowLayout());
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        /*
          Clicking on the close button (red X) closes the window and ends the thread
         */
        addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                dispose();
                setDone(true);
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {

            }
        });


    }

    /**
     * Run method, adds child to Desktop and updates the colors in a 1 second interval
     */
    public void run() {
        RainbowColors demo = new RainbowColors();
        App.app.addChild(demo, 10, 10);

        while (!isDone()) {
            for (Color i : colors) {
                demo.setBackground(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Returns isDone that is used to end the thread
     *
     * @return isDone, a boolean
     */
    private boolean isDone() {
        return isDone;
    }

    /**
     * Sets isDone
     *
     * @param done a boolean used to end the thread
     */
    private void setDone(boolean done) {
        isDone = done;
    }
}

