package GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import App.App;

import static App.App.app;

/**
 * Window that gives option to enter the size of a new game
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 27/04/18
 */
public class StartGameWindow extends JInternalFrame implements ActionListener {
    private JDesktopPane desk;
    private JTextField xSize = new JTextField("X"); //Textfield to enter the rotHeight and rotLength
    private JTextField ySize = new JTextField("Y");
    private JButton startGame = new JButton("Start");                     //startbutton
    private static int x = 0;
    private static int y = 0;

    /**
     * Construktor, adds Listeners to Button
     *
     * @param desk //refernce to JDesktopPane
     */
    public StartGameWindow(JDesktopPane desk) {
        super("Set Size", true, true);
        setIconifiable(true);
        setMaximizable(true);

        app.addChild(this, 10, 10); // Ein Kindfenster einfuegen

        this.desk = desk;
        this.setLayout(new FlowLayout());
        startGame.addActionListener(this);
        this.setSize(200, 100);

        this.add(xSize);
        this.add(ySize);
        this.add(startGame);
        setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * start methode, called to create a new game
     */
    public void start() throws PropertyVetoException {
        GameOfLife game = new GameOfLife(x, y, Construction.GLIDER);  //game created with Gleiter Figure
        ViewGame viewGame = new ViewGame(app, game);
        app.addChild(viewGame, 10, 10); // Ein Kindfenster einfuegen
        this.setClosed(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Start": {
                if (isNumeric(xSize.getText())) {            //checks if Text was an integer
                    x = Integer.parseInt(xSize.getText());  //saves the entered text as int
                }
                if (isNumeric(ySize.getText())) {
                    y = Integer.parseInt(ySize.getText());
                }
                if (y > 0 && x > 0) {                               //checks if positive numbers where entered
                    try {
                        start();
                    } catch (PropertyVetoException e1) {
                        e1.printStackTrace();
                    }
                }
                break;
            }
        }

    }

    public static boolean isNumeric(String str)                 //checks if a String is an Integer
    {
        if (str == null) return false;
        try {
            Integer i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}

