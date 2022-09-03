package cli;

import toonblast.Grid;
import toonblast.ToonBlast;
import toonblast.ToonBlastGameState;
import toonblast.element.*;

public class BasicRenderer {
    public void render(ToonBlast toonBlast) {
        renderPanel(toonBlast.getGameState());
        renderGrid(toonBlast.getGrid());
    }

    private void renderPanel(ToonBlastGameState state) {
        System.out.println("Remaining moves: " + state.getRemainingMoveCount());
        System.out.println("Remaining goals by variant: " + state.getGoals());
    }

    private void renderGrid(Grid grid) {
        var toonBlastGrid = grid.getToonBlastGrid();

        System.out.print("     ");
        for (int i = 0; i < toonBlastGrid[0].length; i++) {
            System.out.print("y" + i + ". ");
        }
        System.out.println();

        var axis = 0;
        for (Element[] elements : toonBlastGrid) {
            System.out.print("x" + axis++ + ". ");
            for (Element element : elements) {
                renderElement(element);
            }
            System.out.println();
        }
    }

    private void renderElement(Element e) {
        if (e instanceof Cube)
            renderCube((Cube) e);
        else if (e instanceof Bomb)
            renderBomb();
        else if (e instanceof Rocket)
            renderRocket((Rocket) e);
        else if (e instanceof ExplosiveToon)
            renderExplosiveToon((ExplosiveToon) e);
    }

    private void renderCube(Cube c) {
        System.out.print(" " + "C-" + c.getColor());
    }

    private void renderBomb() {
        System.out.println(" " + "B");
    }

    private void renderRocket(Rocket r) {
        System.out.print(" " + "R-" + r.getDirection());
    }

    private void renderExplosiveToon(ExplosiveToon et) {
        System.out.print(" " + "ET" + et.getVariantId());
    }

}
