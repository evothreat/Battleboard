import java.util.ArrayList;
import java.util.List;

abstract public class Piece {

    private final PieceType pieceType;
    private final Colour color;
    private final int weight;
    private boolean hasMoved;

    public Piece(PieceType pieceType, Colour color, int weight, boolean hasMoved) {
        this.color = color;
        this.pieceType = pieceType;
        this.hasMoved = hasMoved;
        this.weight = weight;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Colour getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
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

    public Square getEnemyKing(Board board, Square square) {
        Colour c = square.getPiece().getColor().toggle();
        return getValidTargets(board, square).stream().filter(sq -> sq.isSettled() &&
                                                              sq.getPiece().isKing() &&
                                                              sq.getPiece().getColor() == c).findFirst().orElse(null);
    }

    public boolean canDefendKing(Board board, Square ownSq, Square enemySq, Square kingSq) {
        List<Square> ownTargets = getValidTargets(board, ownSq);
        if (ownTargets.contains(enemySq)) {
            return true;
        }
        if (enemySq.getPiece().isKnight()) {
            return false;
        }
        Direction enemyToKingDir = Direction.from2Squares(enemySq, kingSq);
        List<Square> enemyToKingTargets = Target.getTargetsInDirection(board, enemySq, color.toggle(), enemyToKingDir);
        return enemyToKingTargets.stream().anyMatch(ownTargets::contains);
    }

    public List<Square> getKingDefenseTargets(Board board, Square ownSq, Square enemySq, Square kingSq) {
        List<Square> defense = new ArrayList<>();
        if (enemySq.getPiece().isKnight()) {
            return defense;
        }
        List<Square> ownTargets = getValidTargets(board, ownSq);
        List<Square> enemyToKingTargets = Target.getTargetsInDirection(board, enemySq, color.toggle(), Direction.from2Squares(enemySq, kingSq));
        for (Square ot : ownTargets) {
            if (ot.equals(enemySq) || enemyToKingTargets.contains(ot)) {
                defense.add(ot);
            }
        }
        return defense;
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
