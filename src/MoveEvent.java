public enum MoveEvent {
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
