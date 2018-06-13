package App;

import GameOfLife.Construction;
import GameOfLife.GameOfLife;
import GameOfLife.StartGameWindow;
import GameOfLife.ViewGame;
import SmallGames.Clones;
import SmallGames.Rainbow;
import SmallGames.TenColors;
import SmallGames.ToggleSafe;
import Sokoban.Model.Sokoban;
import Sokoban.View.GameView;
import Sokoban.View.LevelSelect.LevelListView;

import javax.swing.*;
import java.io.File;
import java.util.Stack;

/**
 * Desktop pane, Open new games from here
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @date: 09/05/18
 */
public class App extends JFrame {
    public static JDesktopPane desk;
    public static App app = new App();
    public int SCALEFACTOR = 15; //Windowsize is based on Gamesize*SCALEFACTOR
    private LevelListView levelListView;
    private Menu[] menus = {
            new Menu("New Game")
                    .addItem("Game Of Life", e -> {
                        StartGameWindow sgw = new StartGameWindow(App.desk);      //Creates a Startgame Window

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
                    .addItem("Ten Colors", "Change the Color of the new window by  clicking a button", e -> {
                        TenColors tenColors = new TenColors();
                    })

                    .addItem("Rainbow", "A window that circles through colors", e -> {
                        Rainbow rainbow = new Rainbow();
                        app.addChild(rainbow, 0, 0);
                    })
                    .addItem("Clones", "Can change Color of new window and create copy of it", e -> {
                        Clones clones = new Clones(0);
                    })
                    .addItem("ToggleSafe", "<html> Solve the rotating save,<br>but everytime you are wrong a new safe opens that rotates faster</html>", e -> {
                ToggleSafe toggleSafe = new ToggleSafe(5000, new Stack<>());
            })
    };

    /**
     * Constructor
     * Creates Desktop Pane and opens Game of Life instance
     */
    private App() {
        desk = new JDesktopPane();
        desk.setDesktopManager(new DefaultDesktopManager());
        setContentPane(desk);

        JMenuBar menuBar = new JMenuBar();
        for (JMenu menu : menus) {
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

    /**
     * Adds child to Desktop Pane
     *
     * @param child the child to be added
     * @param x     the x Position
     * @param y     the y Position
     */
    public void addChild(JInternalFrame child, int x, int y) {
        child.setLocation(x, y);
        child.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        desk.add(child);
        child.setVisible(true);
    }

    public static void main(String[] args) {

    }

    /**
     * Opens Level List view for Sokoban
     *
     * @param selectedFile the selected version of the game (the .txt file)
     */
    public void loadLevelListView(File selectedFile) {
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
