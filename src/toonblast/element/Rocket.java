package toonblast.element;

import java.util.Random;

public class Rocket extends Element implements Interactable {
    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN;

        private final static Random random = new Random();

        public static Direction getRandomDirection() {
            return switch (random.nextInt(4)) {
                case 0 -> LEFT;
                case 1 -> RIGHT;
                case 2 -> UP;
                default -> DOWN;
            };
        }
    }

    private final Direction direction;

    public Rocket(Direction direction) {
        super(2);

        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
