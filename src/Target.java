import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Target {

    static List<Square> getTargetsInCross(Board board, Position position, Color color) {
        return Stream.of(
                getTargetsInDirection(board, position, color, Direction.N),
                getTargetsInDirection(board, position, color, Direction.S),
                getTargetsInDirection(board, position, color, Direction.W),
                getTargetsInDirection(board, position, color, Direction.E)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static List<Square> getTargetsInDiagonal(Board board, Position position, Color color) {
        return Stream.of(
                getTargetsInDirection(board, position, color, Direction.NE),
                getTargetsInDirection(board, position, color, Direction.SE),
                getTargetsInDirection(board, position, color, Direction.SW),
                getTargetsInDirection(board, position, color, Direction.NW)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static public List<Square> getTargetsInDirection(Board board, Position position, Color color, Direction dir) {
        List<Square> targets = new ArrayList<>();
        int currX = position.getX() + dir.getX();
        int currY = position.getY() + dir.getY();
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
