package toonblast.element;

public class Rocket extends Element implements Interactable {
    private final int direction;

    public Rocket(int direction) {
        super(2);

        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
