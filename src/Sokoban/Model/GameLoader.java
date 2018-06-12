package Sokoban.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Used to count Levels of a file
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 11/05/18
 */
public class GameLoader {
    /**
     * gets the number of levels from a file
     *
     * @param file the file which is counted through
     * @return the number of levels as integer
     */
    public static int getLevelCount(File file) {
        BufferedReader br;
        String line;
        int levelCount = 0;
        int level = 1;

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                if (line.equals("Level " + level)) {
                    levelCount++;
                    level++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return levelCount;
    }
}
