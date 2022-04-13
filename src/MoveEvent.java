public enum MoveEvent {
    NONE,
    MOVE,
    CASTLING,
    PROMOTION,
    CHECK,
    STALEMATE,
    CHECKMATE;

    public boolean isGameOver() {
        return this == STALEMATE || this == CHECKMATE;
    }
}
