package toonblast;

import toonblast.element.Element;
import toonblast.element.Interactable;
import toonblast.interaction.InteractorFactory;

import java.util.List;

public class ToonBlast {
    private final Grid grid;
    private final Engine engine;
    private int remainingMoves;

    public ToonBlast(Engine engine, int moves, Element[][] elements) {
        this.engine = engine;
        this.remainingMoves = moves;

        this.grid = new Grid(elements);
    }

    public void play(Element element) {
        if (!(element instanceof Interactable)) {
            return;
        }

        var interactor = InteractorFactory.getInteractor(grid, element.getVariantId());
        var interactions = interactor.interact(element);

        explode(interactions);
        grid.applyGravity();

        remainingMoves--;
    }

    private void explode(List<Element> interactions) {
        interactions.forEach(engine::explode);

        grid.removeAll(interactions);
    }
}
