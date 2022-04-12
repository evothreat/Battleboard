public enum Colour {
    BLACK,
    WHITE;

    public Colour toggle() {
        return this == BLACK ? WHITE : BLACK;
    }
}
