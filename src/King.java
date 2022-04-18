import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class King extends Piece {

    public King(Colour color, int weight) {
        super(PieceType.KING, color, weight, false);
    }

    public King(King other) {
        super(other.getPieceType(), other.getColor(), other.getWeight(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        // targets will also contain enemy squares
        List<Square> targets = getTargets(board, square);
        targets.removeIf(sq -> sq.isSettled() && hasSameColor(sq.getPiece()));
        if (!hasMoved()) {
            Square sq = Target.getNextSettledInDirection(board, square, Direction.N);
            if (sq != null && sq.getPiece().isRook() && !sq.getPiece().hasMoved()) {
                targets.add(board.getSquareAt(square.getX(), square.getY()+2));     // y+1 is already added
            }
            sq = Target.getNextSettledInDirection(board, square, Direction.S);
            if (sq != null && sq.getPiece().isRook() && !sq.getPiece().hasMoved()) {
                targets.add(board.getSquareAt(square.getX(), square.getY()-2));
            }
            targets.add(square);
        }
        getTargets(board, board.getEnemyKingSq()).forEach(targets::remove);

        boolean w = getColor().bool();

        // NOTE: add getEnemyPieces by color!
        for (Square esq : board.getEnemyPiecesSq()) {
            Piece enemy = esq.getPiece();
            List<Square> enemyTargets = enemy.getValidTargets(board, esq);
            if (enemy.isPawn()) {
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
        if (!hasMoved()) {
            if (!targets.remove(square)) {
                targets.remove(board.getSquareAt(square.getX(), square.getY()+2));
                targets.remove(board.getSquareAt(square.getX(), square.getY()-2));
                return targets;
            }
            if (targets.remove(board.getSquareAt(square.getX(), square.getY()+2)) &&
                targets.contains(board.getSquareAt(square.getX(), square.getY()+1))) {
                targets.add(board.getSquareAt(square.getX(), 7));
            }
            if (targets.remove(board.getSquareAt(square.getX(), square.getY()-2)) &&
                targets.contains(board.getSquareAt(square.getX(), square.getY()-1))) {
                targets.add(board.getSquareAt(square.getX(), 0));
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
}