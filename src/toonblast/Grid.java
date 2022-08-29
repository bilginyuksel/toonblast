package toonblast;

import toonblast.element.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    public static class Coordinate {
        public int x;
        public int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Coordinate newCoordinate(int x, int y) {
            return new Coordinate(x, y);
        }
    }

    private final Map<Element, Coordinate> toonBlastGridCoordinateMap;
    private final Element[][] toonBlastGrid;

    public Grid(Element[][] toonBlastGrid) {
        this.toonBlastGrid = toonBlastGrid;
        this.toonBlastGridCoordinateMap = buildToonBlastCoordinateMap(toonBlastGrid);
    }


    public Element get(Coordinate c) {
        return toonBlastGrid[c.x][c.y];
    }

    public Coordinate get(Element c) {
        var coordinate = toonBlastGridCoordinateMap.get(c);
        return Coordinate.newCoordinate(coordinate.x, coordinate.y);
    }

    public boolean valid(Coordinate c) {
        return c.x >= 0 &&
                c.y >= 0 &&
                c.x < toonBlastGrid.length &&
                c.y < toonBlastGrid[0].length;
    }

    /**
     * Apply gravity to the whole grid to move pieces downwards
     */
    public void applyGravity() {

    }

    public void removeAll(List<Element> elements) {

    }

    private Map<Element, Coordinate> buildToonBlastCoordinateMap(Element[][] toonBlastGrid) {
        var coordinateMap = new HashMap<Element, Coordinate>();
        for (int i = 0; i < toonBlastGrid.length; i++) {
            for (int j = 0; j < toonBlastGrid[i].length; j++) {
                coordinateMap.put(toonBlastGrid[i][j], Coordinate.newCoordinate(i, j));
            }
        }

        return coordinateMap;
    }
}
