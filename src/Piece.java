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

    public Square canAttackKing(Board board, Square square) {
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
        Direction enemyToKingDir = Direction.from2Points(enemySq.getX(), enemySq.getY(), kingSq.getX(), kingSq.getY());
        List<Square> enemyToKingTargets = Target.getTargetsInDirection(board, enemySq, color.toggle(), enemyToKingDir);
        return enemyToKingTargets.stream().anyMatch(ownTargets::contains);
    }

    public MoveEvent doesCheckOrMateAt(Board board, Square square) {
        Square enemyKingSq = square.getPiece().canAttackKing(board, square);
        Colour enemyColor = square.getPiece().getColor().toggle();
        if (enemyKingSq != null) {
            Piece enemyKing = enemyKingSq.getPiece();
            List<Square> enemyKingTargets = enemyKing.getValidTargets(board, enemyKingSq);
            //if (enemyKingTargets.contains(square)) {
            //    return MoveEvent.CHECK;
            //}
            // CHECK FOR CHECKMATE -> enemy's king can't escape
            if (enemyKingTargets.isEmpty()) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Square sq = board.getSquareAt(i, j);
                        if (sq.isSettled() && sq.getPiece().getColor() == enemyColor && !sq.getPiece().isKing()) {
                            // ENEMY'S KING CAN BE DEFENDED -> enemy's king can be defended
                            if (sq.getPiece().canDefendKing(board, sq, square, enemyKingSq)) {
                                return MoveEvent.CHECK;
                            }
                        }
                    }
                }
                return MoveEvent.CHECKMATE;
            }
            return MoveEvent.CHECK;
        }
        // CHECK FOR STALEMATE -> enemy has no targets, so can't move
        if (!board.canMove(enemyColor)) {
            return MoveEvent.STALEMATE;
        }
        return MoveEvent.NONE;
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
