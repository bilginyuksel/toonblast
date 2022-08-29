package toonblast.element;

public class Bomb extends Element implements Interactable {
    private final int direction;

    public Bomb(int direction) {
        super(2);

        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
