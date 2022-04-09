import java.util.List;

abstract public class Piece {

    private final Color color;
    private final PieceType pieceType;

    public Piece(Color color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasSameColor(Piece other) {
        return other != null && color == other.getColor();
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean canAttackKing(Board board, Square square) {
        return getValidTargets(board, square).contains(board.getKingSquare(color.toggle()));
    }

    public boolean canDefendKing(Board board, Square ownSquare, Square enemySquare) {
        List<Square> ownTargets = getValidTargets(board, ownSquare);
        if (ownTargets.contains(enemySquare)) {
            return true;
        }
        if (enemySquare.getPiece().getPieceType().isKnight()) {
            return false;
        }
        Square kingSquare = board.getKingSquare(color);
        Direction enemyToKingDir = Direction.from2Points(enemySquare.getX(), enemySquare.getY(),
                                                         kingSquare.getX(), kingSquare.getY());
        List<Square> kingDirTargets = Target.getTargetsInDirection(board, enemySquare, color.toggle(), enemyToKingDir);
        return kingDirTargets.stream().anyMatch(ownTargets::contains);
    }

    abstract List<Square> getValidTargets(Board board, Square square);
}
