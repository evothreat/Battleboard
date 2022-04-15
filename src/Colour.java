public enum Colour {
    BLACK,
    WHITE;

    public Colour toggle() {
        return this == BLACK ? WHITE : BLACK;
    }

    public int sign() {
        return this == BLACK ? -1 : 1;
    }

    public boolean bool() {
        return this == WHITE;
    }
}
