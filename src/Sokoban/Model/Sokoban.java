package Sokoban.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

/**
 * Main logic class
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 21/05/18
 */
public class Sokoban extends Observable implements Serializable {

    public Square[][][] gameBoard;  //Array of Game Elements. Third Dimension for Players and Crates on Fields
    public Player player;
    private boolean isDone = false; //checks if the game is finished
    private int arrayHeight = 0;
    private int arrayLength = 0;
    private String[] inputFromFileArray;
    private int goalCount = 0;
    private File file;
    private int level;
    private int maxLevel;
    private GameStateBackup gameStateBackup;
    private Stack<GameStateBackup> backlog = new Stack<>();


    /**
     * Constructor, Creates the gameBoard based on a text file
     *
     * @param file  text File that contains the Game field
     * @param level The level of the version, which should be loaded from the file
     */
    public Sokoban(File file, int level) {
        ArrayList<String> inputFromFileArray = new ArrayList<>();
        this.file = file;
        this.level = level;
        this.maxLevel=GameLoader.getLevelCount(file);
        BufferedReader br;
        String line;

        try {
            boolean correctLevel = false;

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                //Only saves the correct Level
                if (line.equals("Level " + level)) {
                    correctLevel = true;
                }
                if (correctLevel) {
                    if (line.equals("Level " + (level + 1))) {
                        correctLevel = false;
                    }

                }
                if (correctLevel && line.contains("#")) {
                    inputFromFileArray.add(line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        arrayHeight = inputFromFileArray.size();

        /*
          finds the longest string, to get max lenght
         */
        for (String s : inputFromFileArray) {
            if (s.length() >= arrayLength) {
                arrayLength = s.length();
            }
        }
        this.inputFromFileArray = inputFromFileArray.toArray(new String[0]);
        buildGameBoard();

    }

    /**
     * Builds the gameboard, goes through the String[] and builds the gameboard based on that
     */
    private void buildGameBoard() {
        goalCount = 0;
        gameBoard = new Square[arrayLength][arrayHeight][2];
        for (int y = 0; y < arrayHeight; y++) {
            char[] temp = inputFromFileArray[y].toCharArray();

            for (int x = 0; x < temp.length; x++) {
                switch (temp[x]) {
                    case '#': {
                        gameBoard[x][y][0] = new Wall(x, y, this);
                        gameBoard[x][y][1] = new Wall(x, y, this);
                        break;
                    }
                    case '$': {
                        gameBoard[x][y][0] = new Floor(BoardElement.EMPTY);
                        gameBoard[x][y][1] = new Crate(x, y, this);

                        break;
                    }
                    case '.': {
                        gameBoard[x][y][0] = new Floor(BoardElement.GOAL);
                        goalCount++;
                        break;
                    }
                    /*
                      Crate on Goal
                     */
                    case '*': {
                        gameBoard[x][y][0] = new Floor(BoardElement.GOAL);
                        gameBoard[x][y][1] = new Crate(x, y, this);
                        goalCount++;
                        break;
                    }
                    /*
                      Player on Goal
                     */
                    case '+': {

                        gameBoard[x][y][0] = new Floor(BoardElement.GOAL);
                        player = new Player(x, y, this);
                        gameBoard[x][y][1] = player;
                        goalCount++;
                        break;
                    }
                    case '@': {
                        gameBoard[x][y][0] = new Floor(BoardElement.EMPTY);
                        player = new Player(x, y, this);
                        gameBoard[x][y][1] = player;
                        break;
                    }
                    case ' ': {
                        gameBoard[x][y][0] = new Floor(BoardElement.EMPTY);
                        break;
                    }

                }
            }
        }
    }

    /**
     * move player
     *
     * @param direction direction of movement
     * @return true if movement was successful
     */
    public boolean moveElement(Direction direction) {
        backup();
        if (!isDone()&& player.move(direction)) {
            backlog.add(gameStateBackup);
            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }

    /**
     * Returns isDone
     *
     * @return a boolean that checks if the game is finished
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * sets isDone
     *
     * @param done a boolean that checks if the game is finished
     */
    public void setDone(boolean done) {
        isDone = done;
        setChanged();
        notifyObservers();
    }

    /**
     * loads last gamestate from backup
     */
    public void undo() {

        if(isDone()){ isDone=false;}
        if (!backlog.isEmpty()) {
            GameStateBackup backup = backlog.pop();
            for (int x = 0; x < arrayLength; x++) {
                for (int y = 0; y < arrayHeight; y++) {
                    gameBoard[x][y][1] = backup.movableObjectsBackup[x][y];
                    if (backup.movableObjectsBackup[x][y] != null) {
                        InteractableElement temp = (InteractableElement) gameBoard[x][y][1];
                        temp.position = backup.positionBackup[x][y];
                        /*
                        For the specific case of restarting the game, then undoing that
                        */
                        if(temp instanceof Player){
                            player=(Player) temp;
                        }

                    }
                }
            }
            setChanged();
            notifyObservers();

        }
    }

    /**
     * Creates Backup of the positions of Players and crates before an update, for undo option
     */
    private void backup() {
        gameStateBackup = new GameStateBackup();
        gameStateBackup.movableObjectsBackup = new Square[arrayLength][arrayHeight];
        gameStateBackup.positionBackup = new Position[arrayLength][arrayHeight];  //Created new everytime to delete old one
        for (int x = 0; x < arrayLength; x++) {
            for (int y = 0; y < arrayHeight; y++) {
                gameStateBackup.movableObjectsBackup[x][y] = gameBoard[x][y][1];
                if (gameBoard[x][y][1] != null) {
                    InteractableElement temp = (InteractableElement) gameBoard[x][y][1];
                    gameStateBackup.positionBackup[x][y] = new Position(temp.position);
                }
            }
        }
    }

    /**
     * Rebuilds board, for example when stuck and want to start again
     */
    public void rebuildBoard() {
        buildGameBoard();
        setChanged();
        notifyObservers();

    }

    /**
     * Returns the goalcount
     *
     * @return the number of goals in the Level, an integer
     */
    public int getGoalCount() {
        return goalCount;
    }

    /**
     * Returns the height of the gameboard
     *
     * @return the number of rows in the sokoban version
     */
    public int getArrayHeight() {
        return arrayHeight;
    }

    /**
     * Returns the length of the gameboard
     *
     * @return the number of lines in the sokoban version
     */
    public int getArrayLength() {
        return arrayLength;
    }

    /**
     * Returns the file the game is based om
     * @return  the txt file the game is created out of
     */
    public File getFile(){
        return file;
    }

    /**
     * Returns the Game Level of the Sokoban version
     * @return  an int, the Level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the Numbers of Levels in the game version
     * @return  an int, the number of Levels in the file
     */
    public int getMaxLevel(){
        return maxLevel;
    }

    /**
     * Creates a new Sokoban object of the same Level and file
     * @return  the new game, a sokoban object of the same Level
     */
    public Sokoban makeBaseCopy() {
        return new Sokoban(file, level);
    }
}



