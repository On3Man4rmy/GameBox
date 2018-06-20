package SmallGames;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import static App.App.app;

/**
 * Program is an extension of Drehsafe, now a new window is opened upon wrong entry
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 27/04/18
 **/

public class ToggleSafe extends JInternalFrame implements ActionListener {

    private JButton[] buttons = new JButton[10];            //Field of buttons
    private int pos = 0;                                    //Saves current position, where in password one is
    private static char[] password = "8245".toCharArray();  //Password, saved as Char[] Array, cause Substring are annoying
    private int speed = 0;                                  //Break between rotation in milliseconds
    private int steps = 0;                                  //Counts number of entered steps. After 3 entries change direction
    private Rotation rot = new Rotation();                  //Object of rotation class, created here for access in the Actionlistener as opposed to the constructor)
    private LinkedList<JInternalFrame> windows;             //reference to all windows, to close them all at once
    private WrapInteger windowcount = new WrapInteger();    //counts windows so it closes when all are closes

    /**
     * Constructor:
     * First fills array with Buttons from 0 to 9
     * then creates 4 Panels, with 3 buttons (or 2 buttons + one empty panel)
     * Adds panels to the window
     *
     * @param speed speed of rotation in milliseconds between update
     */
    public ToggleSafe(int speed, LinkedList<JInternalFrame> windows) {
        super("ToggleSafe", true, true, true, true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(500, 500);
        setLayout(new FlowLayout());
        setVisible(true);
        app.addChild(this, 0, 0);
        /*
          Clicking on the close button (red X) closes all windows of this instance of the game
         */
        addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                exit();
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
        this.windows = windows;

        this.speed = speed / 2;             //doubles speed by halving rotation delay
        for (int i = 0; i < 10; i++) {
            buttons[i] = new JButton("" + i);
            buttons[i].setFont(new Font("Courier", Font.BOLD, 34));
            buttons[i].addActionListener(this);
        }
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(buttons[3]);
        panel.add(buttons[2]);
        panel.add(buttons[1]);


        JPanel pane2 = new JPanel();
        pane2.setLayout(new GridLayout(1, 3));
        setLayout(new GridLayout(4, 1));
        pane2.add(buttons[4]);
        pane2.add(new JPanel());
        pane2.add(buttons[0]);

        JPanel pane3 = new JPanel();
        pane3.setLayout(new GridLayout(1, 3));
        pane3.add(buttons[5]);
        pane3.add(new JPanel());
        pane3.add(buttons[9]);

        JPanel pane4 = new JPanel();
        pane4.setLayout(new GridLayout(1, 3));
        pane4.add(buttons[6]);
        pane4.add(buttons[7]);
        pane4.add(buttons[8]);

        add(panel);
        add(pane2);
        add(pane3);
        add(pane4);

        for (JButton i : buttons) {
            i.setFont(new Font("Courier", Font.BOLD, 34));

        }
        rot.speed = speed;          //speed in the  rotation class is also doubled
        rot.Buttons = buttons;      //sets buttons in thread
        new Thread(rot).start();    //starts thread
        windows.add(this);

    }

    /**
     * If the clicked button is the expected number, background is green
     * By wrong entry the color changed to red, password needs to be entered again, speed is doubled and new window is opened
     * If the complete password is entered the window is closed
     * If all windows are closed a "Open sesame" window opens
     * After 3 entries the direction changes
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (steps == 2) {    //If 3rd entry
            steps = 0;
            rot.direction = !rot.direction;
        }

        if (e.getActionCommand().equals("" + password[pos])) {          //correct entry


            for (JButton i : buttons) {
                i.setBackground(Color.green);
            }
            pos++;

        } else {                                                        //wrong entry
            for (JButton i : buttons) {
                i.setBackground(Color.red);
                pos = 0;

            }
            ToggleSafe demo = new ToggleSafe(speed, this.windows);      //new window
            demo.windowcount = windowcount;
            windowcount.integer++;


        }

        if (pos == password.length) {                                   //correct password
            closewindow();
        }
        steps++;                                                        //increase steps
    }

    /**
     * Method to close all windows of this instance of the game and stop their threads
     */
    private void exit() {
        for (JInternalFrame i : windows) {
            i.dispose();
            ((ToggleSafe)i).rot.setDone(true);
        }
        windows.clear();
    }

    /**
     * Method to close a window and decrease Windowcounter by one. if all windows are closed open lock
     */
    private void closewindow() {
        windowcount.integer--;
        if (windowcount.integer == 0) {       //All other windows are closed

            JInternalFrame lock = new JInternalFrame("Open Sesame");
            lock.setClosable(true);
            lock.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setIconifiable(true);
            setMaximizable(true);

            JPanel panel = new JPanel();
            JLabel label = new JLabel("Lock opened");
            label.setFont(new Font("Courier", Font.BOLD, 34));


            panel.add(label);

            lock.add(panel);
            lock.setVisible(true);
            lock.setLayout(new FlowLayout());
            lock.setSize(360, 150);
            app.addChild(lock, 10, 10);
            windows.clear();
        }
        this.dispose();
        rot.setDone(true);
    }


}
/**
 * Integer Class, can be passed by reference.
 * Necessary because Integers are passed by value, thus opening a new window would give it the same value,
 * but it wouldn't be connected to other windows
 * Used to count number of windows
 */
class WrapInteger {
    public int integer = 1;
}


/**
 * Rotation class
 */
class Rotation extends Frame implements Runnable {

    public int speed = 0;                                   //Speed of rotation, in milliseconds between rotation by one
    public boolean direction = true;                        //Controls direction, true is clockwise
    public JButton[] Buttons = new JButton[10];             //Buttons
    private boolean isDone=false;                           //used to end thread

    /**
     * Run method, rotates the buttons (or rather their label)
     */
    public void run() {
        while (!isDone()) {
            for (JButton i : Buttons) {    //Loops through buttons array

                if (direction) {    //If direction is true, increases the value of the buttons
                    i.setText(Integer.parseInt(i.getText()) >= 9 ? 0 + "" : Integer.parseInt(i.getText()) + 1 + "");
                } else {                    // and if it's false decrease it
                    i.setText(Integer.parseInt(i.getText()) == 0 ? 9 + "" : Integer.parseInt(i.getText()) - 1 + "");
                }
            }
            try {
                Thread.sleep(speed); //1 sec break while rotating
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
    /**
     * Returns isDone that is used to end the thread
     * @return  isDone, a boolean
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets isDone
     * @param done  a boolean used to end the thread
     */
    public void setDone(boolean done) {
        isDone = done;
    }
}
