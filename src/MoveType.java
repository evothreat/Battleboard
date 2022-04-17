public enum MoveType {
    ILLEGAL,
    MOVE,
    CASTLING,
    PROMOTION,
    EN_PASSANT;

    public boolean isLegal() {
        return this != ILLEGAL;
    }
}
