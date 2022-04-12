import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class King extends Piece {

    private boolean didCastling;

    public King(Color color) {
        super(color, PieceType.KING);
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        List<Square> targets = getTargets(board, square);
        targets.removeIf(sq -> sq.isSettled() && hasSameColor(sq.getPiece()));
        // should be after remove, cause is settled and has same color!
        if (!hasMoved() && !didCastling) {
            int x = square.getX();
            int y = square.getY();
            if (!board.getSquareAt(x, y - 1).isSettled() && !board.getSquareAt(x, y - 2).isSettled() && !board.getSquareAt(x, y - 3).isSettled()) {
                Square sq = board.getSquareAt(x, y - 4);
                Piece p = sq.getPiece();
                if (p != null && hasSameColor(p) && p.isRook() && !((Rook) p).hasMoved()) {
                    targets.add(sq);
                }
            }
            if (!board.getSquareAt(x, y + 1).isSettled() && !board.getSquareAt(x, y + 2).isSettled()) {
                Square sq = board.getSquareAt(x, y + 3);
                Piece p = sq.getPiece();
                if (p != null && hasSameColor(p) && p.isRook() && !((Rook) p).hasMoved()) {
                    targets.add(sq);
                }
            }
        }
        boolean w = getColor().isWhite();
        Direction pawnDir = w ? Direction.E : Direction.W;
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
                    enemyTargets.removeIf(dst -> Direction.from2Points(src.getX(), src.getY(), dst.getX(), dst.getY()) == pawnDir);
                    enemyTargets.add(board.getSquareAt(w ? src.getX()+1 : src.getX()-1, src.getY()-1));
                    enemyTargets.add(board.getSquareAt(w ? src.getX()+1 : src.getX()-1, src.getY()+1));
                }
                enemyTargets.forEach(t -> {
                    if (t != null) targets.remove(t);
                });
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