import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Color color) {
        super(color, PieceType.KING);
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
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

        targets.removeIf(sq -> sq == null || sq.isSettled() && hasSameColor(sq.getPiece()));

        boolean w = getColor().isWhite();
        Direction pawnDir = w ? Direction.E : Direction.W;

        for (int i = 0; i < 8 && !targets.isEmpty(); i++) {             // isEmpty() for optimization
            for (int j = 0; j < 8 && !targets.isEmpty(); j++) {
                Square src = board.getSquareAt(i, j);
                Piece p = src.getPiece();
                if (!src.isSettled() || hasSameColor(p) || p.isKing()) continue;
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
}