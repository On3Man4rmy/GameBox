package GameOfLife;

/**
 * Saves the figure as boolean array and returns it
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 27/04/18
 */
public class ConstructionField {
    static private boolean[][] glider = {{false, true, false},
            {false, false, true},
            {true, true, true}};
    static private boolean[][] fPentomino =
            {{false, true, true},    //f-Pentomino, chaotic development
                    {true, true, false},
                    {false, true, false}};
    static private boolean[][] blinker = {{true}, {true}, {true}}; //Blinker, changes orientation
    static private boolean[][] bipole = {{true, true, false, false}, {true, true, false, false},
            {false, false, true, true}, {false, false, true, true}};
    static private boolean[][] tumbler = {{false, true, true, false, true, true, false}, {false, true, true, false, true, true, false},
            {false, false, true, false, true, false, false}, {true, false, true, false, true, false, true}, {true, false, true, false, true, false, true},
            {true, true, false, false, false, true, true}
    };

    /**
     * @param muster enum of figure
     * @return the figure
     */
    public static boolean[][] getForm(Construction muster) {
        switch (muster) {
            case GLIDER:
                return glider;
            case F_PENTOMINO:
                return fPentomino;
            case BLINKER:
                return blinker;
            case BIPLOE:
                return bipole;
            case TUMBLER:
                return tumbler;

        }
        return new boolean[1][1];   //default return, dead cell
    }
}
