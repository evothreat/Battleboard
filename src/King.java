import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class King extends Piece {

    private boolean didCastling;

    public King(Colour color, int weight) {
        super(PieceType.KING, color, weight, false);
    }

    public King(King other) {
        super(other.getPieceType(), other.getColor(), other.getWeight(), other.hasMoved());
        didCastling = other.didCastling;
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        // targets will also contain enemy squares
        List<Square> targets = getTargets(board, square);
        targets.removeIf(sq -> sq.isSettled() && hasSameColor(sq.getPiece()));
        getTargets(board, board.getEnemyKingSq()).forEach(targets::remove);

        // should be after removeIf, cause target is settled and has same color!
        if (!hasMoved() && !didCastling) {
            Square sq = Target.getNextSettledInDirection(board, square, Direction.N);
            if (sq != null && sq.getPiece().isRook() && !sq.getPiece().hasMoved()) {
                targets.add(sq);
            }
            sq = Target.getNextSettledInDirection(board, square, Direction.S);
            if (sq != null && sq.getPiece().isRook() && !sq.getPiece().hasMoved()) {
                targets.add(sq);
            }
        }
        // add own square, to check whether king is under attack
        targets.add(square);

        boolean w = getColor().bool();

        // NOTE: add getEnemyPieces by color!
        for (Square esq : board.getEnemyPiecesSq()) {
            Piece piece = esq.getPiece();
            List<Square> enemyTargets = piece.getValidTargets(board, esq);
            if (piece.isPawn()) {
                enemyTargets.removeIf(dst -> Direction.from2Squares(esq, dst).isDirect());
                enemyTargets.add(board.getSquareAt(w ? esq.getX()+1 : esq.getX()-1, esq.getY()-1));
                enemyTargets.add(board.getSquareAt(w ? esq.getX()+1 : esq.getX()-1, esq.getY()+1));
                enemyTargets.forEach(targets::remove);      // check for null?
                continue;
            }
            for (Square t : enemyTargets) {
                if (targets.remove(t)) {
                    Direction dir = Direction.from2Squares(esq, square);
                    Square behindSq = board.getSquareAt(square.getX()+dir.getX(), square.getY()+dir.getY());
                    if (behindSq != null) targets.remove(behindSq);
                }
            }
        }
        for (int i = targets.size() - 1; i >= 0; i--) {
            Piece piece = targets.get(i).getPiece();
            if (piece == null || !hasSameColor(piece)) {
                break;
            }
            if (piece.isKing()) {
                targets.remove(i);
                break;
            }
            if (piece.isRook()) {
                targets.remove(i);
            }
        }
        return targets;
    }

    private List<Square> getTargets(Board board, Square square) {
        int x = square.getX();
        int y = square.getY();
        List<Square> targets = new ArrayList<>();
        targets.add(board.getSquareAt(x - 1, y + 1));
        targets.add(board.getSquareAt(x, y + 1));
        targets.add(board.getSquareAt(x + 1, y + 1));
        targets.add(board.getSquareAt(x + 1, y));
        targets.add(board.getSquareAt(x + 1, y - 1));
        targets.add(board.getSquareAt(x, y - 1));
        targets.add(board.getSquareAt(x - 1, y - 1));
        targets.add(board.getSquareAt(x - 1, y));
        targets.removeIf(Objects::isNull);
        return targets;
    }

    public void setDidCastling(boolean didCastling) {
        this.didCastling = didCastling;
    }
}