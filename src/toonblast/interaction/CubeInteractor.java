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

        return findAdjacentCubes(new HashSet<>(), element, origin);
    }

    public List<Element> findAdjacentCubes(Set<Grid.Coordinate> visited, Element interactionStarter, Grid.Coordinate origin) {
        if (visited.contains(origin) || !grid.valid(origin) || Objects.equals(interactionStarter, grid.get(origin))) {
            return Collections.emptyList();
        }

        visited.add(origin);

        var elements = new ArrayList<Element>();
        elements.addAll(findAdjacentCubes(visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x - 1, origin.y)));
        elements.addAll(findAdjacentCubes(visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x + 1, origin.y)));
        elements.addAll(findAdjacentCubes(visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x, origin.y - 1)));
        elements.addAll(findAdjacentCubes(visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x, origin.y + 1)));
        return elements;
    }
}
