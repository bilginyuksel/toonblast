package toonblast.element;

public class Cube extends Element implements Interactable {
    private final int color;

    public Cube(int color) {
        super(1);

        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return color == cube.color;
    }
}
