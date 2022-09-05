package toonblast;

import toonblast.element.Element;

import java.util.*;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private final Map<Element, Coordinate> toonBlastGridCoordinateMap;
    private final Element[][] toonBlastGrid;
    private final ToonBlastElementPool elementPool;

    public Grid(Element[][] toonBlastGrid, ToonBlastElementPool pool) {
        this.toonBlastGrid = toonBlastGrid;
        this.toonBlastGridCoordinateMap = buildToonBlastCoordinateMap(toonBlastGrid);
        this.elementPool = pool;
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
     * Return pieces which are on the ground (ending row of the grid)
     */
    public List<Element> applyGravity() {
        for (int i = toonBlastGrid.length - 1; i >= 0; i--) {
            for (int j = toonBlastGrid[i].length - 1; j >= 0; j--) {
                if (toonBlastGrid[i][j] != null) {
                    moveElementToBottom(i, j);
                }
            }
        }

        return Collections.emptyList();
    }

    private void moveElementToBottom(int i, int j) {
        for (int k = i + 1; k < toonBlastGrid.length; k++) {
            if (toonBlastGrid[k][j] != null) {
                break;
            }

            toonBlastGrid[k][j] = toonBlastGrid[k - 1][j];
            toonBlastGrid[k - 1][j] = null;
            toonBlastGridCoordinateMap.computeIfPresent(toonBlastGrid[k][j], (element, coordinate) -> {
                coordinate.x++;
                return coordinate;
            });
        }
    }

    /**
     * Spawns new elements if there are empty cells on the 0th X axis
     */
    public void spawnElements() {
        Optional<Coordinate> c;
        while ((c = findFirstNullOnFirstRow()).isPresent()) {
            var coordinate = c.get();
            toonBlastGrid[coordinate.x][coordinate.y] = elementPool.spawn();
            toonBlastGridCoordinateMap.put(toonBlastGrid[coordinate.x][coordinate.y], coordinate);
            moveElementToBottom(coordinate.x, coordinate.y);
        }
    }

    private Optional<Coordinate> findFirstNullOnFirstRow() {
        for (int i = 0; i < toonBlastGrid[0].length; i++) {
            if (toonBlastGrid[0][i] == null) {
                return Optional.of(Coordinate.newCoordinate(0, i));
            }
        }

        return Optional.empty();
    }

    public void removeAll(List<Element> elements) {
        for (Element e : elements) {
            var coordinate = toonBlastGridCoordinateMap.remove(e);
            toonBlastGrid[coordinate.x][coordinate.y] = null;
        }
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

    public Element[][] getToonBlastGrid() {
        return toonBlastGrid;
    }
}
