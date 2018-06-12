package Sokoban.Model;

public enum FloorElement {
    WALL(0), GOAL(1), CRATE(2), PLAYER(3), EMPTY(4);
    private int index;

    /**
     * Constructor
     *
     * @param i, index
     */
    FloorElement(int i) {
        index = i;
    }

    /**
     * returns the FloorElement at a given index
     *
     * @param index index of the wanted element
     * @return the desired element
     */
    public static FloorElement getFloorElement(int index) {
        for (FloorElement f : FloorElement.values()) {
            if (f.index == index) return f;
        }
        throw new IllegalArgumentException("Element not found");
    }

}
