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
        // should be after removeIf, cause target is settled and has same color!
        if (!hasMoved() && !didCastling) {
            Square sq = Target.getNextPieceSqInDirection(board, square, Direction.N);
            if (sq != null && sq.getPiece().isRook() && !sq.getPiece().hasMoved()) {
                targets.add(sq);
            }
            sq = Target.getNextPieceSqInDirection(board, square, Direction.S);
            if (sq != null && sq.getPiece().isRook() && !sq.getPiece().hasMoved()) {
                targets.add(sq);
            }
        }
        // add own square, to check whether king is under attack
        targets.add(square);

        boolean w = getColor() == Colour.WHITE;
        Direction straight = w ? Direction.E : Direction.W;

        for (int i = 0; i < 8 && !targets.isEmpty(); i++) {                                 // isEmpty() for optimization
            for (int j = 0; j < 8; j++) {
                Square src = board.getSquareAt(i, j);
                Piece p = src.getPiece();
                if (p == null || hasSameColor(p)) continue;
                if (p.isKing()) {
                    getTargets(board, src).forEach(targets::remove);
                    continue;
                }
                List<Square> enemyTargets = p.getValidTargets(board, src);
                if (p.isPawn()) {
                    enemyTargets.removeIf(dst -> Direction.from2Squares(src, dst) == straight);
                    enemyTargets.add(board.getSquareAt(w ? src.getX()+1 : src.getX()-1, src.getY()-1));
                    enemyTargets.add(board.getSquareAt(w ? src.getX()+1 : src.getX()-1, src.getY()+1));
                }
                enemyTargets.forEach(t -> {
                    if (t != null) targets.remove(t);
                });
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