package toonblast.interaction;

import toonblast.element.Element;
import toonblast.Grid;
import toonblast.element.Explosive;

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

    private List<Element> findAdjacentCubes(Set<Grid.Coordinate> visited, Element interactionStarter, Grid.Coordinate origin) {
        if (notValid(origin, interactionStarter, visited)) {
            return Collections.emptyList();
        }

        visited.add(origin);

        var element = grid.get(origin);
        var elements = new ArrayList<>(Collections.singletonList(element));

        // Do not look for neighbors if the element is explosive
        if (element instanceof Explosive) {
            return elements;
        }

        elements.addAll(findAdjacentCubes(visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x - 1, origin.y)));
        elements.addAll(findAdjacentCubes(visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x + 1, origin.y)));
        elements.addAll(findAdjacentCubes(visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x, origin.y - 1)));
        elements.addAll(findAdjacentCubes(visited, interactionStarter, Grid.Coordinate.newCoordinate(origin.x, origin.y + 1)));
        return elements;
    }

    private boolean notValid(Grid.Coordinate origin, Element interactionStarter, Set<Grid.Coordinate> visited) {
        if (!grid.valid(origin) || visited.contains(origin)) {
            return true;
        }

        var compareTo = grid.get(origin);
        var isEqualOrExplosive = Objects.equals(interactionStarter, compareTo) || compareTo instanceof Explosive;

        return !isEqualOrExplosive;
    }

}
