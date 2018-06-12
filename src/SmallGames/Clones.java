package SmallGames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static App.App.app;

/**
 * By clicking the "Change Color" Buttons the color is changed,
 * by clicking the "Open New Window" Button a new copy of the window is opened
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 27/04/18
 **/
public class Clones extends JInternalFrame {
    private JButton changeColor = new JButton("Change Color");
    private JButton newWind = new JButton("Open New Window");
    private int position = 0;
    private Color[] colors = {Color.black, Color.blue, Color.cyan,
            Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};

    /**
     * Constructor
     *
     * @param colorPosition The position of in the array, at which it starts (the initial color)
     */
    public Clones(int colorPosition) {
        super("Rainbow", true, true, true, true);
        app.addChild(this, 0, 0);
        this.setSize(360, 360);
        setLayout(new FlowLayout());
        setVisible(true);

        setLayout(new FlowLayout());
        changeColor.setFont(new Font("Arial", Font.PLAIN, 20));
        newWind.setFont(new Font("Arial", Font.PLAIN, 20));
        /*
          Action Listener, Pressing the button shifts the color in the array
         */
        changeColor.addActionListener(e -> {
            position++;
            if (position >= 11) {
                position = 0;
            }
            setBackground(colors[position]);
        });
        /*
          Action Listener, Pressing the button opens new window with same color as initial color
         */
        newWind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clones demo = new Clones(position);

            }
        });
        add(changeColor);
        add(newWind);
        position = colorPosition;
        setBackground(colors[position]);

    }
}
