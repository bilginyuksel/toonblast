package toonblast.engine;

import toonblast.element.Element;
import toonblast.Grid;
import toonblast.interaction.InteractorFactory;

import java.util.ArrayList;
import java.util.List;

public class GridManager extends MonoBehavior {
    private final Grid grid;

    public GridManager() {
        List<UnityGrid> unityGrids = loadPrefabs("");

        // build grid and do something with the load part
        this.grid = new Grid(new Element[2][2]);
    }

    public List<UnityGrid> loadPrefabs(String path) {
        return new ArrayList<>();
    }

    public void onClick(UnityGrid unityGrid) {
        var variantId = unityGrid.getVariantId();
    }
}
