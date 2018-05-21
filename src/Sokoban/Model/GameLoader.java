package Sokoban.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Loads games for preview when choosing Level (I think)
 *@Author Tobias Fetzer 198318, Simon Stratemeier 199067
 *@Version: 1.0
 *@Date: 11/05/18
 */
public class GameLoader {
    public static int getLevelCount(File file) {
        ArrayList<String> inputFromFileArray = new ArrayList<>();
        BufferedReader br;
        String line;
        int levelCount = 0;
        int level = 1;

        try {
            boolean correctLevel=false;

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                if(line.equals("Level "+ level)) {
                    correctLevel=true;
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
