import java.util.List;

abstract public class Piece {

    private final Colour color;
    private final PieceType pieceType;

    // needed only for king & rook...
    private boolean hasMoved;

    public Piece(Colour color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public Colour getColor() {
        return color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasSameColor(Piece other) {
        return other != null && color == other.getColor();
    }

    public boolean canAttackKing(Board board, Square square) {
        return getValidTargets(board, square).contains(board.getKingSquare(color.toggle()));
    }

    public boolean canDefendKing(Board board, Square ownSquare, Square enemySquare) {
        List<Square> ownTargets = getValidTargets(board, ownSquare);
        if (ownTargets.contains(enemySquare)) {
            return true;
        }
        if (enemySquare.getPiece().isKnight()) {
            return false;
        }
        Square kingSquare = board.getKingSquare(color);
        Direction enemyToKingDir = Direction.from2Points(enemySquare.getX(), enemySquare.getY(),
                                                         kingSquare.getX(), kingSquare.getY());
        List<Square> kingDirTargets = Target.getTargetsInDirection(board, enemySquare, color.toggle(), enemyToKingDir);
        return kingDirTargets.stream().anyMatch(ownTargets::contains);
    }

    public boolean isKing() {
        return pieceType == PieceType.KING;
    }

    public boolean isQueen() {
        return pieceType == PieceType.QUEEN;
    }

    public boolean isBishop() {
        return pieceType == PieceType.BISHOP;
    }

    public boolean isKnight() {
        return pieceType == PieceType.KNIGHT;
    }

    public boolean isRook() {
        return pieceType == PieceType.ROOK;
    }

    public boolean isPawn() {
        return pieceType == PieceType.PAWN;
    }

    public boolean isWhite() {
        return color == Colour.WHITE;
    }

    public boolean isBlack() {
        return color == Colour.BLACK;
    }

    abstract List<Square> getValidTargets(Board board, Square square);
}
