public enum Color {
    BLACK,
    WHITE;

    public Color toggle() {
        return this == BLACK ? WHITE : BLACK;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    public boolean isWhite() {
        return this == WHITE;
    }
}
