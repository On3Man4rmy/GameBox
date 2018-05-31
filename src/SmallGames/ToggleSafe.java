package SmallGames;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

import static App.App.app;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

/**
 * Programm ist Erweiterung von Drehsafe, jetzt werden neue Windows geöffnet bei Falscher eingabe
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 27/04/18
 **/

public class ToggleSafe  extends JInternalFrame implements ActionListener {

    JButton[] knoepfe = new JButton[10];     //Feld für Knöpfe
    int pos = 0;                             //Speichert die derzeitig Position, wo man im Passwort ist
    static char[] password = "82".toCharArray();  //Passwort, gespeichert als Char[] Array, weil Substring nervig ist
    boolean richtung=true;   //Steuert Richtung. True ist Uhrzeigersinn, false ist Gegen Uhrzeigersinn
    int speed=0;    //Pause zwischen drehung in milisecs
    int schritte=0; //Zählt die Schritte, die man schon eingegeben hat. Nach drei eingaben ändert sich richtung
    Rotation rot=new Rotation();  //Objekt der Rotation klasse, hier erzeugt damit zugriff im Actionlistener möglich (anstatt im Konstruktor)
    Stack<JInternalFrame> windows;      //refrence to all windows, to close them all at once
    public WrapInteger windowcount=new WrapInteger();       //counts windows so it closes when all are closes

    /**
     * Konstrukotr:
     * zuerst wird das knoepfe Array gefüllt (mit Knöepfen von 0 bis 9)
     * Dann wird 4 mal ein Panel erzeugt, dem je 3 Knoepfe, bzw 2 Knoepfe und ein lehres Panel zugeordnet werden
     * Die Panels werden dann zum Fenster hinzugefügt.
     * @param speed
     *
     */
    public ToggleSafe(int speed,Stack<JInternalFrame> windows ){
        super("ToggleSafe", true, true,true,true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(500,500);
        setLayout(new FlowLayout());
        setVisible(true);
        app.addChild(this, 0, 0);
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
        this.windows=windows;

        this.speed=speed/2;             //geswindigkeit verdoppel, durch halbieren der sleep zeit
        for (int i = 0; i < 10; i++) { // 10 Knöpfe im Array
            knoepfe[i] = new JButton("" + i); // erzeugen
            knoepfe[i].setFont(new Font("Courier", Font.BOLD, 34));
            knoepfe[i].addActionListener(this); // ... und registrieren
        }
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(knoepfe[3]);
        panel.add(knoepfe[2]);
        panel.add(knoepfe[1]);


        JPanel pane2=new JPanel();
        pane2.setLayout(new GridLayout(1, 3));
        setLayout(new GridLayout(4,1));
        pane2.add(knoepfe[4]);
        pane2.add(new JPanel());
        pane2.add(knoepfe[0]);

        JPanel pane3=new JPanel();
        pane3.setLayout(new GridLayout(1, 3));
        pane3.add(knoepfe[5]);
        pane3.add(new JPanel());
        pane3.add(knoepfe[9]);

        JPanel pane4=new JPanel();
        pane4.setLayout(new GridLayout(1, 3));
        pane4.add(knoepfe[6]);
        pane4.add(knoepfe[7]);
        pane4.add(knoepfe[8]);

        add(panel);
        add(pane2);
        add(pane3);
        add(pane4);

        for (JButton i :knoepfe) {
            i.setFont(new Font("Courier", Font.BOLD, 34));

        }
        rot.speed=speed;    //speed in der Totationsklasse auch halbieren
        rot.knoepfe=knoepfe;    //Knöpfe im Thread setzen
        new Thread(rot).start();    //neuen Thread starten
        windows.add(this);

    }
    /**
     * ActionPerformed: Wenn ein Knopf gedrückt wird.
     * Wenn der gedrückte Knopf mit der an dieser Position stehenden Zahl übereinstimmt, werden die Knöpfe grün
     * Zudem wird pos erhöht.
     * Bei Falscher eingabe wird die Farbe zu Rot geändert, die Position resetted und ein neues Fenster geöffent,
     * mit doppelt dreh gescwhindigkeit
     * Wenn die position mit der länge des Passwortes übereinstimmt, schliest sich das Fenster
     * Wenn alle Fenster geschlossen sind (Windowcount==0), wird ein neues Fenster "Sesam öffen dich" geöffnet, welches gratuliert
     * Nach 3 eingaben wird die Drehrichtung des WIndows invertiert
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(schritte==2){    //Wenn 3tte Eingabe
            schritte=0;
            rot.richtung=!rot.richtung;
        }

        if(e.getActionCommand().equals(""+password[pos])){  //Richtige Eingabe


            for(JButton i:knoepfe){
                i.setBackground(Color.green);
            }
            pos++;

        }
        else {                                              //Falsche Eingabe
            for (JButton i : knoepfe) {
                i.setBackground(Color.red);
                pos = 0;

            }
            ToggleSafe demo = new ToggleSafe(speed,this.windows);        //neues Window
            demo.windowcount=windowcount;
            windowcount.integer++;



        }

        if(pos==password.length) {                  //Richtiges Passwort
            closewindow();
        }
        schritte++;             //Schritte erhöhen
    }

    public void exit(){
        for(JInternalFrame i:windows){
            i.dispose();
        }
        windows.clear();
    }
    /**
     * Methode um Window zu schliesn und WIndowcounter um eins zu verkleinern
     */
    public void closewindow(){
        windowcount.integer--;
        if(windowcount.integer ==0){       //Alle anderen Fenster schon geschlossen

            JInternalFrame schloss=new JInternalFrame("Open Sesame");
            schloss.setClosable(true);
            schloss.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setIconifiable(true);
            setMaximizable(true);

            JPanel panel = new JPanel();
            JLabel label = new JLabel("Lock opened");
            label.setFont(new Font("Courier", Font.BOLD, 34));


            panel.add(label);

            schloss.add(panel);
            schloss.setVisible(true);
            schloss.setLayout(new FlowLayout());
            schloss.setSize(360,150);
            app.addChild(schloss,10,10);
        }
        this.dispose();
    }
    /**
     * main methode:
     * erzeugt ertes window
     */
}

/**
 * Integer Class, can be passed by reference
 * Used to count number of windows
 *
 */
class WrapInteger{
    public int integer =1;
}


/**
 * Rotation klasse
 */
class Rotation extends Frame implements Runnable {

    public int speed=0;     //Gescwhindigkeit der Rotation, in milisekunden zwischen drehung
    public boolean richtung=true;   //Richtungsvariable
    public JButton[] knoepfe = new JButton[10]; //Buttons


    public void run() {
        while (true) {
            for (JButton i : knoepfe) {    //Schleife durch button Array

                if (richtung) {    //Wenn Richtung true ist, wird der Wert des Textes der Knöpfe erhöht
                    i.setText(Integer.parseInt(i.getText()) >= 9 ? 0 + "" : Integer.parseInt(i.getText()) + 1 + "");
                } else {                    // Und Wenn es false ist veringert
                    i.setText(Integer.parseInt(i.getText()) == 0 ? 9 + "" : Integer.parseInt(i.getText()) - 1 + "");
                }
            }
            try {
                Thread.sleep(speed); //1 sec pause beim drehen
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
