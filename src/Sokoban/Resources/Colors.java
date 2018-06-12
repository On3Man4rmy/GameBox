package Sokoban.Resources;

import java.awt.*;

/**
 * Enum, lists Colors that are used in the game
 * Code from https://stackoverflow.com/a/19858051
 */

public enum Colors {
    PIED_PIPER_BUTTERLAND(253, 242, 212),
    CANARINHO(251, 220, 95),
    WILD_VIOLET_PETAL(189, 148, 214),
    SURRENDER_V2(210, 231, 166),
    GREENISH(76, 115, 24),
    // http://www.colourlovers.com/color/F8975A/A_Spring_Trumpet
    A_SWING_TRUMPET_V2(248, 151, 90),
    // http://www.colourlovers.com/color/723E30/A_lifetime_ago
    A_LIFETIME_AGO(114, 62, 48);

    private final int r;
    private final int g;
    private final int b;
    private final String rgb;

    /**
     * COnstructor, creates Colors out of r g b values
     */
    private Colors(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.rgb = r + ", " + g + ", " + b;
    }

    /**
     * returns a color with the rpg value
     *
     * @return the color
     */
    public Color getColor() {
        return new Color(r, g, b);
    }

}