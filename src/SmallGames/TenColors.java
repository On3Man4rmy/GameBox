package SmallGames; /**
 * Durch drücken der Knöpfe lässt sich die Hintergundfarbe ändern
 *
 *
 * @author Tobias Fetzer 198318
 * @date 15/03/2018
 * @version 1.0
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TenColors extends JInternalFrame {

 JButton[] knoepfe = { new JButton("black"), new JButton("blue"), new JButton("cyan"),
         new JButton("green"),new JButton("magenta"),new JButton("orange"),new JButton("pink"),
         new JButton("red"),new JButton("white"),new JButton("yellow")};


    public TenColors(){
        super("Ten Colors", true, true);
        setIconifiable(true);
        setMaximizable(true);
        this.setSize(360,360);

        setLayout(new FlowLayout());
        setVisible(true);

        for(int i=0;i<knoepfe.length;i++){
            knoepfe[i].setFont(new Font("Arial", Font.PLAIN, 30));
            knoepfe[i].setForeground(getCorrectColor(i));
            final int col=i;
            knoepfe[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setBackground(getCorrectColor(col));
                }
            });

            add(knoepfe[i]);
        }

    }







    public Color getCorrectColor(int i){

        switch(i){

            case 0: return Color.black;
            case 1: return Color.blue;
            case 2: return Color.cyan;
            case 3: return Color.green;
            case 4: return Color.magenta;
            case 5: return Color.orange;
            case 6: return Color.pink;
            case 7: return Color.red;
            case 8: return Color.white;
            case 9: return Color.yellow;
        }
        return Color.black;
    }
    }

