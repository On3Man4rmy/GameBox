package App;

import GameOfLife.*;
import SmallGames.*;
import Sokoban.Model.Sokoban;
import Sokoban.View.GameView;
import Sokoban.View.LevelSelect.*;

import javax.swing.*;
import java.io.File;
import java.util.Stack;

/**
 * Desktop pane, Open new games from here
 *
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 09/05/18
 */
public class App extends JFrame {
    public static JDesktopPane desk;
    public static App app = new App();
    public int SCALEFACTOR = 15; //Windowsize is based on Gamesize*SCALEFACTOR
    LevelListView levelListView;
    private Menu[] menus = {
            new Menu("New Game")
                    .addItem("Game Of Life", e -> {
                        StartGameWindow sgw = new StartGameWindow(App.desk);      //Creates a Stargame Window

                    })

                    .addItem(new Menu("Sokoban")
                            .addItem("minicosmos", e -> {
                                loadLevelListView(new File("src/Sokoban/Resources/minicosmos.txt"));

                            })
                            .addItem("nabokosmos", e -> {
                                loadLevelListView(new File("src/Sokoban/Resources/nabokosmos.txt"));

                            })
                            .addItem("yoshiomurase", e -> {
                                loadLevelListView(new File("src/Sokoban/Resources/yoshiomurase.txt"));

                            })
                    )
                    .addItem("Ten Colors", e -> {
                        TenColors tenColors = new TenColors();
                    })

                    .addItem("Rainbow", e -> {
                        Rainbow rainbow = new Rainbow();
                        app.addChild(rainbow, 0, 0);
                    })
                    .addItem("Clones", e -> {
                        Clones clones = new Clones(0);
                    })
                    .addItem("ToggleSafe", e -> {
                ToggleSafe toggleSafe = new ToggleSafe(5000, new Stack<>());
            })
    };

    /*private Menu sokoMenu; = {
            new Menu("Sokoban")
                    .addItem("minicosmos", e -> {
                    loadLevelListView(new File("src/Sokoban/Resources/minicosmos.txt"));

            })
                    .addItem("nabokosmos", e -> {
                loadLevelListView(new File("src/Sokoban/Resources/nabokosmos.txt"));

            })
                    .addItem("yoshiomurase", e -> {
                loadLevelListView(new File("src/Sokoban/Resources/yoshiomurase.txt"));

            })
    };*/

    public App() {
        desk = new JDesktopPane();
        desk.setDesktopManager(new DefaultDesktopManager());
        setContentPane(desk);

        JMenuBar menuBar = new JMenuBar();
        for (JMenu menu : menus) { // fuer alle Menues:
            menuBar.add(menu);
        }
        setJMenuBar(menuBar);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocation(0, 0);
        setTitle("GameBox");
        setVisible(true);
        GameOfLife game = new GameOfLife(64, 32, Construction.GLIDER);  //game created with Gleiter Figure
        ViewGame viewGame = new ViewGame(this, game);
        addChild(viewGame, 50, 50);
        game.isRun = true;

        setTitle("Game Of Life");
        setVisible(true);

    }

    public void addChild(JInternalFrame child, int x, int y) {
        child.setLocation(x, y);
        child.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        desk.add(child);
        child.setVisible(true);
    }

    public static void main(String[] args) {

    }

    private void loadLevelListView(File selectedFile) {
        //TODO: Replace FIlechooser with submenu, so there are no problems with choosing illegal files
        if (selectedFile != null) {
            levelListView = new LevelListView(selectedFile);
            addChild(levelListView, 10, 10);
            levelListView.setActionLevelSelected(sokoban1 ->
            {
                try {
                    addChild(new GameView((Sokoban) sokoban1.clone()), 0, 0);
                    levelListView.setVisible(false);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            });
            levelListView.setVisible(true);
        }
    }

}
