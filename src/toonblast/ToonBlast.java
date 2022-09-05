package toonblast;

import toonblast.element.Cube;
import toonblast.element.Element;
import toonblast.element.Interactable;
import toonblast.element.Toon;
import toonblast.interaction.InteractorFactory;

import java.util.List;

public class ToonBlast implements ToonBlastElementPool {
    private final ToonBlastGameState gameState;
    private final InteractorFactory interactorFactory;
    private final Grid grid;
    private final Engine engine;

    public ToonBlast(InteractorFactory interactorFactory, Engine engine, ToonBlastGameState gameState, Element[][] elements) {
        this.interactorFactory = interactorFactory;
        this.engine = engine;
        this.gameState = gameState;

        this.grid = new Grid(elements, this);
    }

    public void blast(Element element) {
        if (!(element instanceof Interactable)) {
            return;
        }

        var interactor = interactorFactory.getInteractor(grid, element.getVariantId());
        var interactions = interactor.interact(element);

        explode(interactions);
        var rigidToonList = applyGravity();
        grid.spawnElements();

        updateGameState(interactions, rigidToonList);
    }

    private List<Element> applyGravity() {
        var elementsOnGround = grid.applyGravity();

        return elementsOnGround.stream().filter(e -> e instanceof Toon).toList();
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

    public Grid getGrid() {
        return grid;
    }

    public ToonBlastGameState getGameState() {
        return gameState;
    }

    @Override
    public Element spawn() {
        return new Cube(3);
    }
}
