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

    public boolean isCheckOrMate() {
        return this == CHECK || this == CHECKMATE || this == STALEMATE;
    }
}
