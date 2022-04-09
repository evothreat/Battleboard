import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Target {

    static List<Square> getTargetsInCross(Board board, Square square, Color color) {
        return Stream.of(
                getTargetsInDirection(board, square, color, Direction.N),
                getTargetsInDirection(board, square, color, Direction.S),
                getTargetsInDirection(board, square, color, Direction.W),
                getTargetsInDirection(board, square, color, Direction.E)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static List<Square> getTargetsInDiagonal(Board board, Square square, Color color) {
        return Stream.of(
                getTargetsInDirection(board, square, color, Direction.NE),
                getTargetsInDirection(board, square, color, Direction.SE),
                getTargetsInDirection(board, square, color, Direction.SW),
                getTargetsInDirection(board, square, color, Direction.NW)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static public List<Square> getTargetsInDirection(Board board, Square square, Color color, Direction dir) {
        List<Square> targets = new ArrayList<>();
        int currX = square.getX() + dir.getX();
        int currY = square.getY() + dir.getY();
        while (currX >= 0 && currX < 8 && currY >= 0 && currY < 8) {
            Square sq = board.getSquareAt(currX, currY);
            targets.add(sq);
            // our piece or enemy's piece in the way
            if (sq.isSettled()) {
                // if our piece, remove it
                if (sq.getPiece().getColor() == color) {
                    targets.remove(targets.size()-1);
                }
                break;
            }
            currX += dir.getX();
            currY += dir.getY();
        }
        return targets;
    }
}
