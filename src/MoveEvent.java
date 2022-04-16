public enum MoveEvent {
    NONE,
    ILLEGAL,
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

    public boolean isIllegalMove() {
        return this == ILLEGAL;
    }
}
