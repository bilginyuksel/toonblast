package cli;

import toonblast.Engine;
import toonblast.GameOver;
import toonblast.ToonBlast;
import toonblast.ToonBlastGameState;
import toonblast.element.Cube;
import toonblast.element.Element;
import toonblast.element.ExplosiveToon;
import toonblast.element.Rocket;
import toonblast.interaction.InteractorFactory;

import java.util.*;

public class ToonBlastGame implements GameOver, Engine {
    private final ToonBlast toonBlast;
    private final BasicRenderer basicRenderer;

    public ToonBlastGame() {
        var interactorFactory = new InteractorFactory();
        var toonBlastGameState = new ToonBlastGameState(this, 10, Map.of(5, 7));

        var randomElements = generateRandomElements(10, 10, 3, 7);
        toonBlast = new ToonBlast(interactorFactory, this, toonBlastGameState, randomElements);
        basicRenderer = new BasicRenderer();
    }

    public void update() {
        basicRenderer.render(toonBlast);
    }

    public void play(Element e) {
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
