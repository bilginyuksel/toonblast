package toonblast.element;

public abstract class Element {
    private final int variantId;

    public Element(int variantId) {
        this.variantId = variantId;
    }

    public int getVariantId() {
        return variantId;
    }
}
