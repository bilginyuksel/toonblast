package toonblast.interaction;

import toonblast.element.Element;
import toonblast.Grid;

import java.util.*;

public class CubeInteractor implements Interactor {
    private final Grid grid;

    public CubeInteractor(Grid grid) {
        this.grid = grid;
    }

    @Override
    public List<Element> interact(Element element) {
        var origin = grid.get(element);
        var cubes = new ArrayList<Element>();
        findAdjacentIdenticalCubes(cubes, new HashSet<>(), element, origin);

        return cubes;
    }

    public void findAdjacentIdenticalCubes(List<Element> cubes, Set<Grid.Coordinate> visited, Element interactionStarter, Grid.Coordinate origin) {
        if (visited.contains(origin) || !grid.valid(origin) || Objects.equals(interactionStarter, grid.get(origin))) {
            return;
        }

        visited.add(origin);
        cubes.add(grid.get(origin));

        findAdjacentIdenticalCubes(cubes, visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x - 1, origin.y));
        findAdjacentIdenticalCubes(cubes, visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x + 1, origin.y));
        findAdjacentIdenticalCubes(cubes, visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x, origin.y - 1));
        findAdjacentIdenticalCubes(cubes, visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x, origin.y + 1));
    }
}
