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

public class Rainbow extends JInternalFrame implements ActionListener {
    JButton changeColor = new JButton("Create Rainbow Window");

   public Rainbow() {
        super("Rainbow", true, true);

        setIconifiable(true);
        setMaximizable(true);
        this.setSize(360,100);
        setLayout(new FlowLayout());
        setVisible(true);

        setLayout(new FlowLayout());
        changeColor.setFont(new Font("Arial", Font.PLAIN, 25));

       changeColor.addActionListener(this);
        add(changeColor);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        RainbowColors regen = new RainbowColors();
        new Thread(regen).start();
    }



}

class RainbowColors extends JInternalFrame implements Runnable {
    Color[] farben = {Color.red, Color.orange, Color.yellow,
            Color.green, Color.blue, Color.magenta};


    public RainbowColors(){
        super("Rainbows", true, true);

        setIconifiable(true);
        setMaximizable(true);
        this.setSize(360,360);
        setLayout(new FlowLayout());
        setVisible(true);

    }
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

