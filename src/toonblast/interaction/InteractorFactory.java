package toonblast.interaction;

import toonblast.Grid;

public class InteractorFactory {
    public Interactor getInteractor(Grid grid, int cellVariantId) {
        return switch (cellVariantId) {
            case 1 -> new CubeInteractor(grid);
            case 2 -> new RocketInteractor(grid);
            default -> throw new RuntimeException();
        };
    }
}
