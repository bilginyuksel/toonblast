package toonblast.interaction;

import toonblast.element.Explosive;
import toonblast.element.Rocket;
import toonblast.element.Element;
import toonblast.element.Cube;
import toonblast.Grid;

import java.util.ArrayList;
import java.util.List;

public class RocketInteractor implements Interactor {
    private final Grid grid;

    public RocketInteractor(Grid grid) {
        this.grid = grid;
    }

    @Override
    public List<Element> interact(Element element) {
        var bomb = (Rocket) element;

        var direction = bomb.getDirection();
        var bombOrigin = grid.get(element);

        return interact(bombOrigin, direction);
    }

    private List<Element> interact(Grid.Coordinate origin, Rocket.Direction direction) {
        var targetCells = new ArrayList<Element>();
        while (grid.valid(origin)) {
            var element = grid.get(origin);
            switch (direction) {
                case LEFT -> origin.x--;
                case RIGHT -> origin.x++;
                case UP -> origin.y--;
                case DOWN -> origin.y++;
            }

            if (element instanceof Cube || element instanceof Explosive) {
                targetCells.add(element);
            }
        }

        return targetCells;
    }
}
