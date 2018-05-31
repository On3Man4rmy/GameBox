package SmallGames; /**
 * Beim drücken des Knopfes öffnet sich ein neues Fenster, in dme die Regenbogenfarben aufleuchten.
 *
 * @author Tobias Fetzer 198318
 * @date 15/03/2018
 * @version 1.0
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import App.App;

public class Rainbow extends JInternalFrame{
    JButton changeColor = new JButton("Create Rainbow Window");

   public Rainbow() {
        super("Rainbow", true, true,true,true);

        this.setSize(360,100);
        setLayout(new FlowLayout());
        setVisible(true);

        setLayout(new FlowLayout());
        changeColor.setFont(new Font("Arial", Font.PLAIN, 25));

       changeColor.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               RainbowColors regen = new RainbowColors();
               new Thread(regen).start();

           }
       });
        add(changeColor);


    }



}

/**
 * Class to view the windows which change colors
 */
class RainbowColors extends JInternalFrame implements Runnable {
    Color[] farben = {Color.red, Color.orange, Color.yellow,
            Color.green, Color.blue, Color.magenta};

    /**
     * Constructor
     */
    public RainbowColors(){
        super("Rainbows", true, true,true,true);
        this.setSize(360,360);
        setLayout(new FlowLayout());
        setVisible(true);

    }

    /**
     * Run methode, adds child to Desktop and updates the colors in a 1 second interval
     */
    public void run() {
        RainbowColors demo = new RainbowColors();
        App.app.addChild(demo,10,10);

        while (true) {
            for (Color i : farben) {
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

