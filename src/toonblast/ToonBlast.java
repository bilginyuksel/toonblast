package toonblast;

import toonblast.element.Element;
import toonblast.element.Interactable;
import toonblast.interaction.InteractorFactory;

import java.util.List;

public class ToonBlast {
    private final ToonBlastGameState gameState;
    private final InteractorFactory interactorFactory;
    private final Grid grid;
    private final Engine engine;

    public ToonBlast(InteractorFactory interactorFactory, Engine engine, ToonBlastGameState gameState, Element[][] elements) {
        this.interactorFactory = interactorFactory;
        this.engine = engine;
        this.gameState = gameState;

        this.grid = new Grid(elements);
    }

    public void play(Element element) {
        if (!(element instanceof Interactable)) {
            return;
        }

        var interactor = interactorFactory.getInteractor(grid, element.getVariantId());
        var interactions = interactor.interact(element);

        explode(interactions);
        applyExplosionSideEffects(interactions);

        var heavyObjects = grid.applyGravity();

        updateGameState(interactions, heavyObjects);
    }

    private void explode(List<Element> interactions) {
        interactions.forEach(engine::explode);

        grid.removeAll(interactions);
    }

    private void updateGameState(List<Element> interactions, List<Element> heavyObjects) {
        for (Element interaction : interactions) {
            gameState.updateToonBlastState(interaction.getVariantId(), 1);
        }

        for (Element heavyObject : heavyObjects) {
            gameState.updateToonBlastState(heavyObject.getVariantId(), 1);
        }
    }

    private void applyExplosionSideEffects(List<Element> interactions) {

    }
}
