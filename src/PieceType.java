public enum PieceType {
    KING,
    QUEEN,
    BISHOP,
    KNIGHT,
    ROOK,
    PAWN;

    public boolean isKing() {
        return this == KING;
    }

    public boolean isQueen() {
        return this == QUEEN;
    }

    public boolean isBishop() {
        return this == BISHOP;
    }

    public boolean isKnight() {
        return this == KNIGHT;
    }

    public boolean isRook() {
        return this == ROOK;
    }

    public boolean isPawn() {
        return this == PAWN;
    }
}
