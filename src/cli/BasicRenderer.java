package cli;

import com.googlecode.lanterna.terminal.Terminal;
import toonblast.Grid;
import toonblast.ToonBlast;
import toonblast.ToonBlastGameState;
import toonblast.element.*;

import java.io.IOException;

public class BasicRenderer {
    private final Terminal terminal;

    public BasicRenderer(Terminal terminal) {
        this.terminal = terminal;
    }

    public void render(ToonBlast toonBlast) {
        setTerminalCursorPositionTopLeft();

        renderPanel(toonBlast.getGameState());
        renderGrid(toonBlast.getGrid());
    }

    private void renderPanel(ToonBlastGameState state) {
        writeString("Remaining moves: " + state.getRemainingMoveCount());
        writeLineEnd();
        writeString("Remaining goals by variant: " + state.getGoals());
        writeLineEnd();
    }

    private void renderGrid(Grid grid) {
        var toonBlastGrid = grid.getToonBlastGrid();

        writeString("     ");
        for (int i = 0; i < toonBlastGrid[0].length; i++) {
            writeString("y" + i + ". ");
        }
        writeLineEnd();

        var axis = 0;
        for (Element[] elements : toonBlastGrid) {
            writeString("x" + axis++ + ". ");
            for (Element element : elements) {
                renderElement(element);
            }
            writeLineEnd();
        }
    }

    private void renderElement(Element e) {
        if (e instanceof Cube)
            renderCube((Cube) e);
        else if (e instanceof Bomb)
            renderBomb((Bomb) e);
        else if (e instanceof Rocket)
            renderRocket((Rocket) e);
        else if (e instanceof ExplosiveToon)
            renderExplosiveToon((ExplosiveToon) e);
        else
            renderEmptyElement();
    }

    private void renderCube(Cube c) {
        writeString(" " + "C-" + c.getColor());
    }

    private void renderBomb(Bomb b) {
        writeString(" " + "B-" + b.getVariantId());
    }

    private void renderRocket(Rocket r) {
        writeString(" " + "R-" + r.getDirection().toString().charAt(0));
    }

    private void renderExplosiveToon(ExplosiveToon et) {
        writeString(" " + "ET" + et.getVariantId());
    }

    private void renderEmptyElement() {
        writeString(" NIL");
    }

    private void writeString(String s) {
        try {
            for (char character : s.toCharArray()) {
                terminal.putCharacter(character);
            }
        } catch (IOException e) {
            throw new RuntimeException("Terminal put character doesn't work");
        }
    }

    private void writeLineEnd() {
        try {
            terminal.putCharacter('\n');
            terminal.flush();
        } catch (IOException e) {
            throw new RuntimeException("Terminal flush doesn't work");
        }
    }

    private void setTerminalCursorPositionTopLeft() {
        try {
            terminal.setCursorPosition(0, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
