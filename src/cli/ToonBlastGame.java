package cli;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import toonblast.Engine;
import toonblast.GameOver;
import toonblast.ToonBlast;
import toonblast.ToonBlastGameState;
import toonblast.element.Cube;
import toonblast.element.Element;
import toonblast.element.ExplosiveToon;
import toonblast.element.Rocket;
import toonblast.interaction.InteractorFactory;

import java.io.IOException;
import java.util.*;

public class ToonBlastGame implements GameOver, Engine {
    private final ToonBlast toonBlast;
    private final BasicRenderer basicRenderer;
    private final Terminal terminal;

    public ToonBlastGame() {
        var interactorFactory = new InteractorFactory();
        var gameGoals = new HashMap<Integer, Integer>();
        gameGoals.put(5, 7);
        var toonBlastGameState = new ToonBlastGameState(this, 10, gameGoals);

        var randomElements = generateRandomElements(10, 10, 3, 7);
        toonBlast = new ToonBlast(interactorFactory, this, toonBlastGameState, randomElements);

        var terminalFactory = new DefaultTerminalFactory();
        try {
            terminal = terminalFactory.createTerminal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        basicRenderer = new BasicRenderer(terminal);
    }

    public void start() {
        class GameThread extends Thread {
            @Override
            public void run() {
                //noinspection InfiniteLoopStatement
                while (true) {
                    run(1000);
                }
            }

            private void run(long millis) {
                try {
                    sleep(millis);
                    update();
                    System.out.println("Screen updated...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        class InputThread extends Thread {
            private final List<Character> input = new ArrayList<>();

            @Override
            public void run() {
                //noinspection InfiniteLoopStatement
                while (true) {
                    readTerminalInput();
                }
            }

            private boolean isInputValid() {
                var toonBlastGrid = toonBlast.getGrid().getToonBlastGrid();
                var isXLengthSmaller = (input.get(0) - '0') < toonBlastGrid.length;
                var isYLengthSmaller = (input.get(1) - '0') < toonBlastGrid[0].length;

                return input.size() == 2 && isXLengthSmaller && isYLengthSmaller;
            }

            private void readTerminalInput() {
                try {
                    var stroke = terminal.readInput();
                    System.out.println("Input captured, stroke= " + stroke.getCharacter());
                    if (stroke.getCharacter() == ' ') {
                        if (isInputValid()) {
                            var x = input.get(0) - '0';
                            var y = input.get(1) - '0';
                            play(toonBlast.getGrid().getToonBlastGrid()[x][y]);
                        } else {
                            System.out.println("Invalid input: " + input);
                        }
                        input.clear();
                        return;
                    }

                    input.add(stroke.getCharacter());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        var gameThread = new GameThread();
        var inputThread = new InputThread();

        gameThread.start();
        inputThread.start();
    }

    public void update() {
        basicRenderer.render(toonBlast);
    }

    public void play(Element e) {
        System.out.println("Play called for element: " + e.getVariantId());
        toonBlast.blast(e);
    }

    public void stop() {

    }

    @Override
    public void over(boolean won) {
        stop();
    }

    @Override
    public void explode(Element element) {

    }

    static class Tuple {
        public int x, y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple tuple = (Tuple) o;
            return x == tuple.x && y == tuple.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private Element[][] generateRandomElements(int w, int h, int rocketCount, int explosiveToonCount) {
        var elements = new Element[w][h];

        var r = new Random();
        var listOfPotentialCoordinates = buildShuffledListOfPotentialCoordinates(w, h);

        var lastAvailableCoordinateIdx = -1;
        for (int i = 0; i < rocketCount; i++) {
            var tuple = listOfPotentialCoordinates.get(++lastAvailableCoordinateIdx);
            // direction for rocket could be 1 or 0
            elements[tuple.x][tuple.y] = new Rocket(r.nextInt(2));
        }

        for (int i = 0; i < explosiveToonCount; i++) {
            var tuple = listOfPotentialCoordinates.get(++lastAvailableCoordinateIdx);
            elements[tuple.x][tuple.y] = new ExplosiveToon(5);
        }

        while (++lastAvailableCoordinateIdx < listOfPotentialCoordinates.size()) {
            var tuple = listOfPotentialCoordinates.get(lastAvailableCoordinateIdx);
            elements[tuple.x][tuple.y] = new Cube(r.nextInt(3));
        }

        return elements;
    }

    private List<Tuple> buildShuffledListOfPotentialCoordinates(int w, int h) {
        var listOfPotentialCoordinates = new ArrayList<Tuple>();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                listOfPotentialCoordinates.add(new Tuple(i, j));
            }
        }
        Collections.shuffle(listOfPotentialCoordinates);
        return listOfPotentialCoordinates;
    }
}
