import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Target {

    static List<Position> getTargetsInCross(Board board, Position position, Color color) {
        return Stream.of(
                getTargetsInDirection(board, position, color, Direction.N),
                getTargetsInDirection(board, position, color, Direction.S),
                getTargetsInDirection(board, position, color, Direction.W),
                getTargetsInDirection(board, position, color, Direction.E)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static List<Position> getTargetsInDiagonal(Board board, Position position, Color color) {
        return Stream.of(
                getTargetsInDirection(board, position, color, Direction.NE),
                getTargetsInDirection(board, position, color, Direction.SE),
                getTargetsInDirection(board, position, color, Direction.SW),
                getTargetsInDirection(board, position, color, Direction.NW)
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    static public List<Position> getTargetsInDirection(Board board, Position position, Color color, Direction direction) {
        List<Position> targets = new ArrayList<>();
        int currX = position.getX() + direction.getX();
        int currY = position.getY() + direction.getY();
        while (currX >= 0 && currX < 8 && currY >= 0 && currY < 8) {
            targets.add(new Position(currX, currY));
            // our piece or enemy's piece in the way
            if (board.getPieceAt(currX, currY) != null) {
                // if our piece, remove it
                if (board.getPieceAt(currX, currY).getColor() == color) {
                    targets.remove(targets.size()-1);
                }
                break;
            }
            currX += direction.getX();
            currY += direction.getY();
        }
        return targets;
    }
}
