package toonblast.interaction;

import toonblast.Grid;

public class InteractorFactory {
    public static Interactor getInteractor(Grid grid, int cellVariantId) {
        return switch (cellVariantId) {
            case 1 -> new CubeInteractor(grid);
            case 2 -> new BombInteractor(grid);
            default -> throw new RuntimeException();
        };
    }
}
