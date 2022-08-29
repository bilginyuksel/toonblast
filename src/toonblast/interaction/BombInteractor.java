package toonblast.interaction;

import toonblast.element.Bomb;
import toonblast.element.Element;
import toonblast.element.Cube;
import toonblast.Grid;

import java.util.ArrayList;
import java.util.List;

public class BombInteractor implements Interactor {
    private final Grid grid;

    public BombInteractor(Grid grid) {
        this.grid = grid;
    }

    @Override
    public List<Element> interact(Element element) {
        var bomb = (Bomb) element;
        // bomb direction will be left or right and respectively it will +1 or -1
        // +1 means it will be right direction -1 means it will be left direction
        var direction = bomb.getDirection();
        var bombOrigin = grid.get(element);

        return findTargetCells(bombOrigin, direction);
    }

    private List<Element> findTargetCells(Grid.Coordinate origin, int direction) {
        var targetCells = new ArrayList<Element>();
        for (Element element = grid.get(origin); grid.valid(origin); origin.x += direction, element = grid.get(origin)) {
            if (element instanceof Cube) {
                targetCells.add(element);
            }
        }

        return targetCells;
    }
}
