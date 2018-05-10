package SmallGames; /**
 * Durch drücken des "Change_Color" Knopfes wird die Farbe geändert,
 * durch drücken des "newWind" Knopfes öffnet sch eine default Kopie des Fesnters
 *
 *
 * @author Tobias Fetzer 198318
 * @date 15/03/2018
 * @version 1.0
 **/

import App.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clones extends JInternalFrame {
    JButton changeColor=new JButton("Change Color");
    JButton newWind=new JButton("Open New Window");
    int position=0;
    Color[] colors ={Color.black, Color.blue, Color.cyan,
            Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};



    public Clones(int colorPosition){
        super("Rainbow", true, true);

        setIconifiable(true);
        setMaximizable(true);
        this.setSize(360,360);
        setLayout(new FlowLayout());
        setVisible(true);

        setLayout(new FlowLayout());
        changeColor.setFont(new Font("Arial", Font.PLAIN, 20));
        newWind.setFont(new Font("Arial", Font.PLAIN, 20));
        changeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                position++;
                if(position>=11){
                    position=0;
                }
                setBackground(colors[position]);
            }


        });
        newWind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clones demo = new Clones(position) ;
                App.app.addChild(demo,10,10);

            }
        });
        add(changeColor);
        add(newWind);
        position=colorPosition;
        setBackground(colors[position]);

    }
}
