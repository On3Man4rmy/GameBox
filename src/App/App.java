package App;

import GameOfLife.*;

import javax.swing.*;

public class App extends JFrame {
    public static JDesktopPane desk;
    public static  App app = new App();
    public int SCALEFACTOR = 15; //Windowsize is based on Gamesize*SCALEFACTOR



    public App(){
        desk = new JDesktopPane();
        desk.setDesktopManager(new DefaultDesktopManager());
        setContentPane(desk);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocation(0, 0);
        setTitle("GameBox");
        setVisible(true);
        GameOfLife game = new GameOfLife(64, 32, Construction.GLIDER);  //game created with Gleiter Figure
        ViewGame viewGame = new ViewGame(this, game);
        addChild(viewGame, 50, 50);
        game.isRun=true;

        setTitle("Game Of Life");
        setVisible(true);


    }

    public void addChild(JInternalFrame child, int x, int y) {
        child.setLocation(x, y);
        child.setSize(200, 400);
        child.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        desk.add(child);
        child.setVisible(true);
    }

    public static void main(String[] args) {

    }
}
